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
@WebServlet({ "/idFind", "/idFind.do" })
public class IdFindCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getServletPath();
		if(path.equals("/idFind")) {
			request.getRequestDispatcher("member/idFind.jsp").forward(request, response);
		} else {
			String memberName = request.getParameter("name");
			String memberTel = request.getParameter("tel");
			
			MemberService service = new MemberService();
			String memberId = service.idFind(memberName, memberTel);
			response.getWriter().write(""+ memberId); 
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		doGet(request, response);
	}

}
