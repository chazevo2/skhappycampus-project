package com.sk.idol.member;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.sk.idol.board.Board;
import com.sk.idol.board.BoardService;
import com.sk.idol.reply.Reply;
import com.sk.idol.reply.ReplyService;

/**
 * Servlet implementation class MyInfoCtrl
 */
@WebServlet({ "/myInfo", "/myProfile.do", "/delMyInfo.do" })
public class MyInfoCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getServletPath();
		HttpSession session = request.getSession(false);
		if (path.equals("/myInfo")) {
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
				String memberId = (String) session.getAttribute("memberId");
				MemberService service = new MemberService();
				Member m = service.myInfo(memberId);
				if (m != null) {
					request.setAttribute("m", m);
					// 작성글 가져오기
					BoardService service2 = new BoardService();
					ArrayList<Board> list = service2.getMyList(memberId);
					ReplyService service3 = new ReplyService();
					ArrayList<Reply> list2 = service3.getMyList(memberId);
					request.setAttribute("list", list);
					request.setAttribute("list2", list2);
					request.getRequestDispatcher("member/myInfo.jsp").forward(request, response);
				}
			}

		} else if (path.equals("/myProfile.do")) {
			// 프로필 사진 변경
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
				String memberId = (String) session.getAttribute("memberId");
				String uploadPath = request.getSession().getServletContext().getRealPath("./img");
				System.out.println(uploadPath);

				int size = 10 * 1024 * 1024; // 업로드 사이즈 제한 10M 이하
				String fileName = ""; // 파일명

				try {
					// 파일업로드 및 업로드 후 파일명 가져옴
					MultipartRequest multi = new MultipartRequest(request, uploadPath, size, "utf-8", new DefaultFileRenamePolicy());
					Enumeration files = multi.getFileNames();
					String file = (String) files.nextElement();
					fileName = multi.getFilesystemName(file);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				uploadPath = "./img/" + fileName;
				System.out.println(uploadPath);
				
				JSONObject jobj = new JSONObject();

				
				MemberService service = new MemberService();
				int result = service.setProfile(memberId, uploadPath);
				if(result != 0) {
					jobj.put("url", uploadPath);
				} else {
					jobj.put("url", "fail");
				}
				response.setContentType("application/json"); // 데이터 타입을 json으로 설정하기 위한 세팅
				response.getWriter().write("" + jobj.toJSONString());
				
			}
		} else {
			// 회원 탈퇴
			if(session != null) {
				MemberService service = new MemberService();
				String delId = "";
				String password = request.getParameter("password");
				if(session.getAttribute("memberId") != null) {
					delId = (String) session.getAttribute("memberId");
					System.out.println(delId);
				}
				
				JSONObject jobj = new JSONObject();
				int result = service.delMember(delId, password, 1);
				if(result != 0) {
					jobj.put("msg", "success");
					if(session.getAttribute("memberId") != null) {
						session.removeAttribute("memberId");
					}
					if(session.getAttribute("grade") != null) {
						session.removeAttribute("grade");
					}
					session.invalidate();
				} else {
					jobj.put("msg", "fail");
					
				}
				
				response.setContentType("application/json"); // 데이터 타입을 json으로 설정하기 위한 세팅
				response.getWriter().write("" + jobj.toJSONString());			
			} else {
				// 잘못된 접근
				response.sendRedirect("login?msg=access");
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
