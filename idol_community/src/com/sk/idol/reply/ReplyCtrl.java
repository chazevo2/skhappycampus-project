package com.sk.idol.reply;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import com.sk.idol.board.Board;
import com.sk.idol.board.BoardService;

/**
 * Servlet implementation class ReplyCtrl
 */
@WebServlet({ "/reply.do", "/replyList", "/delReply.do" })
public class ReplyCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getServletPath();
		HttpSession session = request.getSession(false);
		if(path.equals("/reply.do")) {
			if (session == null) {
				// 세션이 없을때
				response.sendRedirect("login?msg=access");
				return;
			}

			if (session.getAttribute("memberId") == null) {
				// 로그인이 안된 상태
				response.sendRedirect("login?msg=access");
				return;
			} else {
				// 세션 아이디가 null이 아닐때
				String replyWriter = (String) session.getAttribute("memberId");
				int boardNum = Integer.parseInt(request.getParameter("boardNum"));
				int parentNum = Integer.parseInt(request.getParameter("parentNum"));
				String replyContent = request.getParameter("replyContent");
				Reply r = new Reply(boardNum, replyWriter, replyContent);
				r.setParentNum(parentNum);
				ReplyService service = new ReplyService();
				
				Boolean isWrite = service.write(r);
				JSONObject jobj = new JSONObject();
				if(isWrite) {
					jobj.put("msg", "success");
				} else {
					jobj.put("msg", "fail");
				}
				response.setContentType("application/json");
				response.getWriter().write("" + jobj.toJSONString());
			}
		} else if(path.equals("/replyList")) {
			// 댓글 불러오기
			int boardNum = Integer.parseInt(request.getParameter("num"));
			ReplyService service = new ReplyService();
			ArrayList<Reply> replyList = service.getReplylist(boardNum);
			request.setAttribute("replyList", replyList);
			request.getRequestDispatcher("board/article.jsp").forward(request, response);
		} else {
			if (session == null) {
				// 세션이 없을때
				response.sendRedirect("login?msg=access");
				return;
			}

			if (session.getAttribute("memberId") == null) {
				// 로그인이 안된 상태
				response.sendRedirect("login?msg=access");
				return;
			} else {
				int replyNum = Integer.parseInt(request.getParameter("num"));
				ReplyService service = new ReplyService();
				int result = service.delReply(replyNum);
				
				JSONObject jobj = new JSONObject();
				if(result == 0) {
					jobj.put("msg", "success");
				} else {
					jobj.put("msg", "fail");
				}
				response.setContentType("application/json"); // 데이터 타입을 json으로 설정하기 위한 세팅
				response.getWriter().write("" + jobj.toJSONString());
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		doGet(request, response);
	}

}
