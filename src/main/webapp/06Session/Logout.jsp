<%--
  Created by IntelliJ IDEA.
  User: Jiwon Kim
  Date: 2023-11-13
  Time: 오후 5:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    //방법1: 회원인증정보 속성 삭제
    session.removeAttribute("UserId");
    session.removeAttribute("UserName");

    //방법2: 모든 속성 한꺼번에 삭제
    //session.invalidate();

    // 속성 삭제 후 페이지 이동
    response.sendRedirect("LoginForm.jsp");
%>