package com.sk.idol.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class WriteCtrl
 */
@WebServlet({ "/write", "/write.do" })
public class WriteCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String path = request.getServletPath();
    	HttpSession session = request.getSession(false);
		if(path.equals("/write")) {
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
				request.getRequestDispatcher("board/write.jsp").forward(request, response);				
			}
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
				// 세션 아이디가 null이 아닐때
				String memberId = (String) session.getAttribute("memberId");
				String category = request.getParameter("category");
				String boardTitle = request.getParameter("boardTitle");
				String boardContent = request.getParameter("boardContent");
				Board b = new Board(category, boardTitle, boardContent);
				b.setBoardWriter(memberId);
				
				BoardService service = new BoardService();
				Boolean isWrite = service.write(b);
				if(isWrite) {
					response.sendRedirect("boardList");	
				} else {
					response.sendRedirect("write?msg=fail");
				}
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		doGet(request, response);
	}

}
