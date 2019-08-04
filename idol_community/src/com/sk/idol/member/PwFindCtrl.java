package com.sk.idol.member;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class IdFindCtrl
 */
@WebServlet({ "/pwFind", "/pwFind.do" })
public class PwFindCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getServletPath();
		if(path.equals("/pwFind")) {
			request.getRequestDispatcher("member/pwFind.jsp").forward(request, response);
		} else {
			String memberName = request.getParameter("name");
			System.out.println(memberName);
			String memberId = request.getParameter("id");
			System.out.println(memberId);
			String memberTel = request.getParameter("tel");
			System.out.println(memberTel);
			
			MemberService service = new MemberService();
			Boolean result = service.pwFind(memberName, memberId, memberTel);
			if(result) {
				response.getWriter().write("success"); 				
			} else {
				response.getWriter().write("fail");
			}
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		doGet(request, response);
	}

}
