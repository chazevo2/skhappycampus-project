package com.sk.idol.reply;

public class Reply {
	private int replyNum;
	private int boardNum;
	private String replyWriter;
	private String replyContent;
	private String writeDate;
	private int parentNum;

	public Reply() {
	}

	public Reply(int boardNum, String replyWriter, String replyContent) {
		this.boardNum = boardNum;
		this.replyWriter = replyWriter;
		this.replyContent = replyContent;
	}

	public Reply(int replyNum, int boardNum, String replyWriter, String replyContent, String writeDate, int parentNum) {
		this.replyNum = replyNum;
		this.boardNum = boardNum;
		this.replyWriter = replyWriter;
		this.replyContent = replyContent;
		this.writeDate = writeDate;
		this.parentNum = parentNum;
	}

	public int getReplyNum() {
		return replyNum;
	}

	public void setReplyNum(int replyNum) {
		this.replyNum = replyNum;
	}

	public int getBoardNum() {
		return boardNum;
	}

	public void setBoardNum(int boardNum) {
		this.boardNum = boardNum;
	}

	public String getReplyWriter() {
		return replyWriter;
	}

	public void setReplyWriter(String replyWriter) {
		this.replyWriter = replyWriter;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public String getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}

	public int getParentNum() {
		return parentNum;
	}

	public void setParentNum(int parentNum) {
		this.parentNum = parentNum;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(replyNum);
		builder.append(", ");
		builder.append(boardNum);
		builder.append(", ");
		builder.append(replyWriter);
		builder.append(", ");
		builder.append(replyContent);
		builder.append(", ");
		builder.append(writeDate);
		builder.append(", ");
		builder.append(parentNum);
		return builder.toString();
	}

}
