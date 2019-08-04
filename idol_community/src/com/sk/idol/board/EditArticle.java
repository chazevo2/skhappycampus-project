package com.sk.idol.board;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class EditArticle
 */
@WebServlet({ "/edit", "/edit.do" })
public class EditArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String path = request.getServletPath();
    	HttpSession session = request.getSession(false);
    	BoardService service = new BoardService();
		if(path.equals("/edit")) {
			int boardNum = Integer.parseInt(request.getParameter("num"));
			Board b = service.getBoardDetail(boardNum);
			if(b != null) {
				request.setAttribute("b", b);
				request.getRequestDispatcher("board/edit.jsp").forward(request, response);
			} else {
				response.sendRedirect("article?num="+boardNum+"&msg=acccess");
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
				int boardNum = Integer.parseInt(request.getParameter("boardNum"));
				System.out.println(boardNum);
				String category = request.getParameter("category");
				String boardTitle = request.getParameter("boardTitle");
				String boardContent = request.getParameter("boardContent");
				Board b = new Board(category, boardTitle, boardContent);
				b.setBoardNum(boardNum);
				
				Boolean isEdit = service.edit(b);
				if(isEdit) {
					response.sendRedirect("boardList");	
				} else {
					response.sendRedirect("edit?msg=fail");
				}
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		doGet(request, response);
	}

}
