package com.sk.idol.member;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

/**
 * Servlet implementation class MemberList
 */
@WebServlet({ "/memberList", "/delMember.do", "/chGrade.do" })
public class MemberList extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getServletPath();
		HttpSession session = request.getSession(false);
		if(path.equals("/memberList")) {
			if (session == null) {
				// 세션이 없을때
				response.sendRedirect("login?msg=access");
				return;
			}

			if (session.getAttribute("memberId") != null && session.getAttribute("grade").equals("A")) {
				// 세션 아이디가 null이 아닐때
				MemberService service = new MemberService();
				ArrayList<Member> list = service.getMemberList();
				if(list != null) {
					request.setAttribute("list", list);
					
					//차트에 넣을값 가져오기
					HashMap<String, Integer> genderRate = service.showGenderChart();
					request.setAttribute("genderRate", genderRate);
					System.out.println(genderRate);
					
					HashMap<String, Integer> ageRate = service.showAgeChart();
					System.out.println(ageRate);
					request.setAttribute("ageRate", ageRate);
					
					request.getRequestDispatcher("member/memberList.jsp").forward(request, response);	
				}
			} else {
				// 비로그인 및 비관리자인 상태
				if(session.getAttribute("memberId") != null) {
					session.removeAttribute("memberId");
				}
				if(session.getAttribute("grade") != null) {
					session.removeAttribute("grade");
				}
				response.sendRedirect("login?msg=access");
				return;
			}			
			
		} else if(path.equals("/delMember.do")) {
			if (session == null) {
				// 세션이 없을때
				response.sendRedirect("login?msg=access");
				return;
			}

			if (session.getAttribute("memberId") != null && session.getAttribute("grade").equals("A")) {
				// 세션 아이디가 null이 아닐때
				MemberService service = new MemberService();
				String delId = request.getParameter("id");
				System.out.println(delId);
				
				JSONObject jobj = new JSONObject();
				int result = service.delMember(delId, "", 0);
				if(result != 0) {
					jobj.put("msg", "success");
				} else {
					jobj.put("msg", "fail");
				}
				response.setContentType("application/json"); // 데이터 타입을 json으로 설정하기 위한 세팅
				response.getWriter().write("" + jobj.toJSONString());
			} else {
				// 비로그인 및 비관리자인 상태
				if(session.getAttribute("memberId") != null) {
					session.removeAttribute("memberId");
				}
				if(session.getAttribute("grade") != null) {
					session.removeAttribute("grade");
				}
				response.sendRedirect("login?msg=access");
				return;
			}
		} else {
			if (session == null) {
				// 세션이 없을때
				response.sendRedirect("login?msg=access");
				return;
			}

			if (session.getAttribute("memberId") != null && session.getAttribute("grade").equals("A")) {
				// 세션 아이디가 null이 아닐때
				MemberService service = new MemberService();
				String chId = request.getParameter("id");
				String newGrade = request.getParameter("newGrade");
				System.out.println(chId);
				System.out.println(newGrade);
				
				JSONObject jobj = new JSONObject();
				int result = service.chGrade(chId, newGrade);
				if(result != 0) {
					jobj.put("msg", "success");
				} else {
					jobj.put("msg", "fail");
				}
				response.setContentType("application/json"); // 데이터 타입을 json으로 설정하기 위한 세팅
				response.getWriter().write("" + jobj.toJSONString());
			} else {
				// 비로그인 및 비관리자인 상태
				if(session.getAttribute("memberId") != null) {
					session.removeAttribute("memberId");
				}
				if(session.getAttribute("grade") != null) {
					session.removeAttribute("grade");
				}
				response.sendRedirect("login?msg=access");
				return;
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		doGet(request, response);
	}

}
