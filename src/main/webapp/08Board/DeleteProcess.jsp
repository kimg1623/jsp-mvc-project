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
    // 일련번호 얻기
    String num = request.getParameter("num");
    System.out.println("test num : "+num);

    // DTO에 저장
    BoardDAO dao = new BoardDAO(application);
    BoardDTO dto = dao.selectView(num);

    String sessionId = session.getAttribute("UserId").toString();

    int delResult = 0;

    if(sessionId.equals(dto.getId())){
        dto.setNum(num);
        delResult = dao.deletePost(dto);
        System.out.println("delResult: "+delResult);
        dao.close();

        // 성공/실패 처리
        if(delResult == 1){
            JSFunction.alertLocation("삭제되었습니다.", "List.jsp", out);
        } else {
            JSFunction.alertBack("수정하기에 실패하셨습니다.", out);
        }
    } else {
        JSFunction.alertBack("본인만 삭제할 수 있습니다.", out);
        return;
    }



%>