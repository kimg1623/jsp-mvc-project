-- 모델2 방식의 파일첨부형 게시판 테이블 생성
create table mvcboard (
    idx int primary key auto_increment,
    name varchar(50) not null,
    title varchar(200) not null,
    content varchar(2000) not null,
    postdate date default sysdate() not null,
    ofile varchar(200),
    sfile varchar(30),
    downcount int(5) default 0 not null,
    pass varchar(50) not null,
    visitcount int default 0 not null
);