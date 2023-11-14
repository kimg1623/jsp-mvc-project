<%@ page import="model1.board.BoardDAO" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="model1.board.BoardDTO" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: Jiwon Kim
  Date: 2023-11-14
  Time: 오후 1:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // DAO를 생성해 DB에 연결
    BoardDAO dao = new BoardDAO(application);

    //사용자가 입력한 검색 조건을 Map에 저장
    Map<String, Object> param = new HashMap<>();
    String searchField = request.getParameter("searchField");
    String searchWord = request.getParameter("searchWord");
    if(searchWord != null){
        param.put("searchField", searchField);
        param.put("searchWord", searchWord);
    }
    int totalCount = dao.selectCount(param);
    List<BoardDTO> boardLists = dao.selectList(param);
    dao.close();
%>
<html>
<head>
    <title>회원제 게시판</title>
</head>
<body>

</body>
</html>
