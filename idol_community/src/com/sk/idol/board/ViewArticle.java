package com.sk.idol.board;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import com.sk.idol.member.MemberService;

/**
 * Servlet implementation class ViewArticle
 */
@WebServlet({ "/article", "/like.do", "/delArticle.do" })
public class ViewArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String path = request.getServletPath();
    	HttpSession session = request.getSession(false);
    	BoardService service = new BoardService();
		if(path.equals("/article")) {
			int boardNum = Integer.parseInt(request.getParameter("num"));
			Board b = service.getBoardDetail(boardNum);
			if(b != null) {	
				Cookie[] cookies = request.getCookies();
				Cookie viewCookie = null;

				// 쿠키가 있을 경우
				if (cookies != null && cookies.length > 0) {
					for (int i = 0; i < cookies.length; i++) {
						// Cookie의 name이 view + boardNum와 일치하는 쿠키를 viewCookie에 넣어줌
						if (cookies[i].getName().equals("view" + boardNum)) {
							viewCookie = cookies[i];
						}
					}
				}

				// 만일 viewCookie가 null일 경우 쿠키를 생성해서 조회수 증가 로직을 처리함.
				if (viewCookie == null) {
					System.out.println("cookie 없음");

					Cookie newCookie = new Cookie("view" + boardNum, "" + boardNum + "");
					response.addCookie(newCookie);

					int result = service.upViewCnt(boardNum);
					if (result > 0) {
						System.out.println("조회수 증가");
						b.setViewCnt(b.getViewCnt() + 1);
					} else {
						System.out.println("조회수 증가 에러");
					}
				}
				
				request.setAttribute("b", b);
				request.getRequestDispatcher("replyList").forward(request, response);
			} else {
				response.sendRedirect("boardList?msg=fail");
			}
		} else if(path.equals("/like.do")) {
			int boardNum = Integer.parseInt(request.getParameter("num"));
			
			Cookie[] cookies = request.getCookies();
			Cookie viewCookie = null;

			if (cookies != null && cookies.length > 0) {
				for (int i = 0; i < cookies.length; i++) {
					if (cookies[i].getName().equals("like" + boardNum)) {
						viewCookie = cookies[i];
					}
				}
			}

			JSONObject jobj = new JSONObject();
			if (viewCookie == null) {
				System.out.println("cookie 없음");

				Cookie newCookie = new Cookie("like" + boardNum, "" + boardNum + "");
				response.addCookie(newCookie);

				int result = service.upLike(boardNum);
				
				if(result != 0) {
					jobj.put("msg", "success");
				} else {
					jobj.put("msg", "fail");
				}
			} else {
				jobj.put("msg", "done");
			}
			response.setContentType("application/json"); // 데이터 타입을 json으로 설정하기 위한 세팅
			response.getWriter().write("" + jobj.toJSONString());
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
				int boardNum = Integer.parseInt(request.getParameter("num"));
				int result = service.delArticle(boardNum);
				if(result == 0) {
					response.sendRedirect("article?num="+boardNum+"&msg=fail");
					return;
				} else {
					response.sendRedirect("boardList");
				}
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		doGet(request, response);
	}

}
