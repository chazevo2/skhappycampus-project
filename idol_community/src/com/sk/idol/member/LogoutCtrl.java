package com.sk.idol.member;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LogoutCtrl
 */
@WebServlet("/logout.do")
public class LogoutCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession(false);
		if(session != null) {
			if(session.getAttribute("memberId") != null) {
				session.removeAttribute("memberId");
			}
			if(session.getAttribute("grade") != null) {
				session.removeAttribute("grade");
			}
			session.invalidate();
			
			response.sendRedirect("home");
		} else {
			// 잘못된 접근
			response.sendRedirect("login?msg=access");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		doGet(request, response);
	}

}
