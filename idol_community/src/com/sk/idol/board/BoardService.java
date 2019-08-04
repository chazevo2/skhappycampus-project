package com.sk.idol.board;

import java.util.ArrayList;
import java.util.HashMap;

import com.sk.idol.util.Utility;

public class BoardService {
	private BoardDao dao;

	public BoardService() {
		dao = new BoardDao(); 
	}
	
	// 글 작성
	public boolean write(Board b) {
		b.setWriteDate(Utility.getCurrentDate());
		b.setViewCnt(0);
		b.setBoardLike(0);
		int result = dao.insertBoard(b);
		if(result == 1) {
			return true;
		}
		return false;
	}
	
	// 게시글 전체 목록
	public ArrayList<Board> getBoardList() {	
		return dao.selectBoardList();
	}

	// 카테고리별 목록
	public ArrayList<Board> getBoardList(String category) {
		return dao.selectBoardList(category);
	}

	// 글 검색
	public ArrayList<Board> getBoardList(String category, String searchValue, String searchType) {
		return dao.selectBoardList(category, searchValue, searchType);
	}

	// 내목록 가져오기
	public ArrayList<Board> getMyList(String memberId) {
		return dao.selectMyList(memberId);
	}
	
	// 게시글 상세 보기
	public Board getBoardDetail(int boardNum) {
		return dao.selectByNum(boardNum);
	}
	
	// 조회수 증가
	public int upViewCnt(int boardNum) {
		return dao.updateViewCnt(boardNum);
	}
	
	// 글 좋아요
	public int upLike(int boardNum) {
		return dao.updateLike(boardNum);
	}
		
	// 글 삭제
	public int delArticle(int boardNum) {
		return dao.deleteByNum(boardNum);
	}
	
	// 글 수정
	public Boolean edit(Board b) {
		int result = dao.updateBoard(b);
		if(result == 1) {
			return true;
		}
		return false;
	}


}
