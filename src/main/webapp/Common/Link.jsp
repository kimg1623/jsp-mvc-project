<%--
  Created by IntelliJ IDEA.
  User: Jiwon Kim
  Date: 2023-11-13
  Time: 오후 5:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<table border="1" width="90%">
    <tr>
        <td align="center">
            <% if(session.getAttribute("UserId")==null) { %>
                <a href="../06Session/LoginForm.jsp">로그인</a>
            <% } else {%>
                <a href="../06Session/Logout.jsp">로그아웃</a>
            <% } %>
                &nbsp;&nbsp;&nbsp;
                <a href="../08Board/List.jsp">게시판(페이징X)</a>
                &nbsp;&nbsp;&nbsp;
                <a href="../09PagingBoard/List.jsp">게시판(페이징O)</a>
        </td>
    </tr>
</table>
