<%@ page import="model1.board.BoardDAO" %>
<%@ page import="model1.board.BoardDTO" %><%--
  Created by IntelliJ IDEA.
  User: Jiwon Kim
  Date: 2023-11-14
  Time: 오후 2:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="./IsLoggedIn.jsp"%>

<%
    // 폼 값 받기
    String title = request.getParameter("title");
    String content = request.getParameter("content");

    // 폼 값을 DTO 객체에 저장
    BoardDTO dto = new BoardDTO();
    dto.setTitle(title);
    dto.setContent(content);
    dto.setId(session.getAttribute("UserId").toString());

    // DAO 객체를 통해 DB에 DTO 저장
    BoardDAO dao = new BoardDAO(application);
    int iResult = dao.insertWrite(dto);
//    int iResult = 0; // 더미 데이터
//    for(int i=1;i<=255;i++){
//        dto.setTitle(title+"-"+i);
//        iResult = dao.insertWrite(dto);
//    }
    dao.close();

    // 성공 or 실패?
    if(iResult == 1){
        response.sendRedirect("List.jsp");
    } else {
        JSFunction.alertBack("글쓰기에 실패하셨습니다.", out);
    }
%>