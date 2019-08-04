package com.sk.idol.member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SignupCtrl
 */
@WebServlet({ "/signup", "/signup.do" })
public class SignupCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getServletPath();
		if(path.equals("/signup")) {
			request.getRequestDispatcher("member/signup.jsp").forward(request, response);
		} else {
			String memberId = request.getParameterValues("memberId")[0] + "@" + request.getParameterValues("memberId")[1];
			String memberPw = request.getParameter("memberPw");
			String memberName = request.getParameter("memberName");
			String memberTel = request.getParameterValues("memberTel")[0];
			memberTel += "-" + request.getParameterValues("memberTel")[1];
			memberTel += "-" + request.getParameterValues("memberTel")[2];
			String memberGender = request.getParameter("memberGender");
			String birthDate = request.getParameter("birthDate");
			
			Member m = new Member(memberId, memberPw, memberName, memberTel, memberGender, birthDate);
			MemberService service = new MemberService();
			Boolean isSignup= service.signup(m);
			System.out.println(isSignup);
			if(isSignup) {
				response.sendRedirect("login?msg=success");			
			} else {
				response.sendRedirect("signup?msg=fail");	
			}
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		doGet(request, response);
	}

}
