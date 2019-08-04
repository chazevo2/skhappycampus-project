package com.sk.idol.board;

public class Board {
	private String category;
	private int boardNum;
	private String boardTitle;
	private String boardContent;
	private String boardWriter;
	private String writeDate;
	private int viewCnt;
	private int boardLike;

	public Board() {
	}

	public Board(String category, String boardTitle, String boardContent) {
		this.category = category;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
	}

	public Board(String category, int boardNum, String boardTitle, String boardContent, String boardWriter,
			String writeDate, int viewCnt, int boardLike) {
		this.category = category;
		this.boardNum = boardNum;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.boardWriter = boardWriter;
		this.writeDate = writeDate;
		this.viewCnt = viewCnt;
		this.boardLike = boardLike;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getBoardNum() {
		return boardNum;
	}

	public void setBoardNum(int boardNum) {
		this.boardNum = boardNum;
	}

	public String getBoardTitle() {
		return boardTitle;
	}

	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}

	public String getBoardContent() {
		return boardContent;
	}

	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}

	public String getBoardWriter() {
		return boardWriter;
	}

	public void setBoardWriter(String boardWriter) {
		this.boardWriter = boardWriter;
	}

	public String getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}

	public int getViewCnt() {
		return viewCnt;
	}

	public void setViewCnt(int viewCnt) {
		this.viewCnt = viewCnt;
	}

	public int getBoardLike() {
		return boardLike;
	}

	public void setBoardLike(int boardLike) {
		this.boardLike = boardLike;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(category);
		builder.append(", ");
		builder.append(boardNum);
		builder.append(", ");
		builder.append(boardTitle);
		builder.append(", ");
		builder.append(boardContent);
		builder.append(", ");
		builder.append(boardWriter);
		builder.append(", ");
		builder.append(writeDate);
		builder.append(", ");
		builder.append(viewCnt);
		builder.append(", ");
		builder.append(boardLike);
		return builder.toString();
	}

}
