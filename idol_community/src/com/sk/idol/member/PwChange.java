package com.sk.idol.member;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class PwChange
 */
@WebServlet({ "/pwChange", "/pwChange.do" })
public class PwChange extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getServletPath();
		if(path.equals("/pwChange")) {
			request.getRequestDispatcher("member/pwChange.jsp").forward(request, response);
		} else {
			HttpSession session = request.getSession(false);
			if(session == null) {
				// 세션이 없을때
				response.getWriter().write("access"); 
				return;
			}
			
			if(session.getAttribute("memberId") == null) {
				// 로그인이 안된 상태
				response.getWriter().write("access"); 
				return;
			} else {
				String memberId = (String) session.getAttribute("memberId");
				String id = request.getParameter("id");
				if(!memberId.equals(id)) {
					// 부모창이 존재하지 않거나 변형되었을 경우
					response.getWriter().write("access");
					System.out.println("ssssss");
					return;
				}
				String memberPw = request.getParameter("pw");
				String newPassword = request.getParameter("newPw");
				
				MemberService service = new MemberService();
				int result = service.pwChange(memberId, memberPw, newPassword);
				if(result == 0) {
					response.getWriter().write("notMatch"); 
				} else {
					response.getWriter().write("match"); 
				}
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		doGet(request, response);
	}

}
