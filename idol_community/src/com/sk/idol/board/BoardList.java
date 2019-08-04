package com.sk.idol.board;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sk.idol.board.Board;

/**
 * Servlet implementation class BoardWrite
 */
@WebServlet({ "/boardList", "/categoryBoard", "/search" })
public class BoardList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getServletPath();
		BoardService service = new BoardService();
		if (path.equals("/boardList")) {
			// 글목록 불러오기
			ArrayList<Board> list = service.getBoardList();
			request.setAttribute("list", list);
			request.getRequestDispatcher("board/boardList.jsp").forward(request, response);
		} else if(path.equals("/categoryBoard")) {
			String category = request.getParameter("category");
			ArrayList<Board> list = service.getBoardList(category);
			request.setAttribute("list", list);
			request.getRequestDispatcher("board/boardList.jsp").forward(request, response);
		} else {
			String category = request.getParameter("searchCategory");
			String searchType = request.getParameter("searchType");
			System.out.println(searchType);
			String searchValue = request.getParameter("searchValue");
			ArrayList<Board> list = service.getBoardList(category, searchValue, searchType);
			request.setAttribute("list", list);
			request.getRequestDispatcher("board/boardList.jsp").forward(request, response);
		}
	}

}