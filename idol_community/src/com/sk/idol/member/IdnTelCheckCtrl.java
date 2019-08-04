package com.sk.idol.member;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class IdCheckCtrl
 */
@WebServlet({ "/idCheck.do", "/telCheck.do" })
public class IdnTelCheckCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getServletPath();
		if(path.equals("/idCheck.do")) {
			String memberId = request.getParameter("id");
			MemberService service = new MemberService();
			int result = service.idCheck(memberId);
			response.getWriter().write("" + result); 
		} else {
			String memberTel = request.getParameter("tel");
			MemberService service = new MemberService();
			int result = service.telCheck(memberTel);
			response.getWriter().write("" + result); 
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		doGet(request, response);
	}

}
