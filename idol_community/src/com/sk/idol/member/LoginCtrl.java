package com.sk.idol.member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginCtrl
 */
@WebServlet({ "/login", "/login.do" })
public class LoginCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getServletPath();
		if(path.equals("/login")) {
			request.getRequestDispatcher("member/login.jsp").forward(request, response);
		} else {
			String memberId = request.getParameterValues("memberId")[0] + "@" + request.getParameterValues("memberId")[1];
			String memberPw = request.getParameter("memberPw");
			
			MemberService service = new MemberService();
			String grade = service.login(memberId, memberPw);
			if(grade != null) {
				HttpSession session = request.getSession();
				session.setAttribute("memberId", memberId);
				session.setAttribute("grade", grade);
				response.sendRedirect("home");				
			} else {
				response.sendRedirect("login?msg=fail");	
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
