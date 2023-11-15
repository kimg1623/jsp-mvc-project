<%@ page import="model1.board.BoardDAO" %>
<%@ page import="model1.board.BoardDTO" %><%--
  Created by IntelliJ IDEA.
  User: Jiwon Kim
  Date: 2023-11-14
  Time: 오후 2:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="./IsLoggedIn.jsp"%> <!--로그인 확인 : 왜 디렉티브태그를 사용해서 include를 했을까요?-->
                                      <!--페이지 세션을 사용할 수 있음! Yes!! getAttribute("UserId")-->
<%
    String num = request.getParameter("num");
    BoardDAO dao = new BoardDAO(application);
    BoardDTO dto = dao.selectView(num);
    String sessionId = session.getAttribute("UserId").toString();
    if(!sessionId.equals(dto.getId())){
        JSFunction.alertBack("작성자 본인만 수정할 수 있습니다.", out);
        return;
    }
    dao.close();
%>
<html>
<head>
    <title>회원제 게시판</title>
    <link href="../css/boardStyle.css" rel="stylesheet"/>
    <script>
        function validateForm(form){
            if(form.title.value == ""){
                alert("제목을 입력하세요.");
                form.title.focus();
                return false;
            }
            if(form.content.value == ""){
                alert("내용을 입력하세요.");
                form.content.focus();
                return false;
            }
        }
    </script>
</head>
<body>
    <jsp:include page="../Common/Link.jsp"/>
    <h2>회원제 게시판 - 수정하기(Edit)</h2>
    <form name="writeFrm" method="post" action="EditProcess.jsp" onsubmit="return validateForm(this)">
        <input type="hidden" name="num" value="<%=dto.getNum()%>"/>
        <table class="ListTable">
            <tr>
                <td>제목</td>
                <td>
                    <input type="text" name="title" class="writeTitle" value="<%=dto.getTitle()%>">
                </td>
            </tr>
            <tr>
                <td>내용</td>
                <td>
                    <textarea name="content" class="writeContent"><%= dto.getContent() %></textarea>
                </td>
            </tr>
            <tr>
                <td colspan="2" class="txtcenter">
                    <button type="submit">작성 완료</button>
                    <button type="reset">다시 입력</button>
                    <button type="button" onclick="location.href='List.jsp'">목록 보기</button>
                </td>
            </tr>
        </table>
    </form>
</body>
</html>
