package com.sk.idol.reply;

import java.util.ArrayList;

import com.sk.idol.util.Utility;

public class ReplyService {
	private ReplyDao dao;

	public ReplyService() {
		dao = new ReplyDao(); 
	}
	
	// 글 작성
	public boolean write(Reply r) {
		r.setWriteDate(Utility.getCurrentDate());
		int result = dao.insertReply(r);
		if(result == 1) {
			return true;
		}
		return false;
	}
	
	// 게시글 해당 댓글 목록
	public ArrayList<Reply> getReplylist(int boardNum) {
		return dao.selectReplyList(boardNum);
	}

	// 내가 작성한 댓글
	public ArrayList<Reply> getMyList(String memberId) {
		return dao.selectMyList(memberId);
	}
	
	// 댓글 삭제
	public int delReply(int replyNum) {
		return dao.deleteByNum(replyNum);
	}
	
	
}
