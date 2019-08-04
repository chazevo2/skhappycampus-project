drop table reply;
drop table board;
drop table member;

drop sequence reply_seq;
drop sequence board_seq;

-- 회원
create table member (
	member_id varchar2(50) not null,
	member_pw varchar2(20) not null,
	member_name varchar2(20) not null,
	member_tel varchar2(13) not null,
    member_gender char(1) not null,
	birth_date char(10) not null,
	member_grade char(1) not null,
	sign_date char(10) not null,
	member_img varchar2(50),
	constraint member_pk primary key(member_id)
);

-- 게시판
create table board (
	category varchar2(30) not null,
	board_num number not null,
	board_title varchar2(100) not null,
	board_content varchar2(4000) not null,
	board_writer varchar2(50) not null,
	write_date char(10) not null,
	view_cnt number,
	board_like number,
	constraint board_pk primary key(board_num),
	constraint board_fk foreign key(board_writer) references member(member_id) on delete set null
);
create sequence board_seq nocache;

-- 댓글
create table reply (
	reply_num number not null,-- reply_seq
	board_num number not null,
	reply_writer varchar2(50) not null,
	reply_content varchar2(300) not null,
	write_date char(10) not null,
	parent_num number,-- 대댓글 경우 해당 댓글 번호
	constraint reply_pk primary key(reply_num),
	constraint reply_fk1 foreign key(board_num) references board(board_num) on delete cascade,
	constraint reply_fk2 foreign key(reply_writer) references member(member_id) on delete set null
);
create sequence reply_seq nocache;