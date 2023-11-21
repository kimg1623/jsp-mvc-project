<%@ page import="model1.board.BoardDAO" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="model1.board.BoardDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="utils.BoardPage" %>
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

    /** 페이징 처리 start **/
    // 1.전체 페이지 수 계산
    int pageSize = Integer.parseInt(application.getInitParameter("POSTS_PER_PAGE"));
    int blockPage = Integer.parseInt(application.getInitParameter("PAGES_PER_BLOCK"));
    int totalPage = (int)Math.ceil((double)totalCount/pageSize); // 전체 페이지 수

    // 현재 페이지 확인
    int pageNum = 1; // 현재페이지 기본 값
    String pageTemp = request.getParameter("pageNum");
    if(pageTemp != null && !pageTemp.equals(""))
        pageNum = Integer.parseInt(pageTemp); // 파라미터로 요청받은 페이지 수로 수정, 없을 경우 1

    // 목록에 출력할 게시물 범위 계산
    int start = (pageNum - 1)*pageSize+1; // 첫 게시물 번호
    int end = pageNum * pageSize; // 마지막 게시물 번호
    param.put("pageSize", pageSize);
    int offset = (pageNum - 1)*pageSize;
    param.put("offset", offset);
    param.put("start", start);
    param.put("end", end);

    List<BoardDTO> boardLists = dao.selectListPage(param);
    dao.close();
%>
<html>
<head>
    <title>회원제 게시판</title>
    <link href="../css/boardStyle.css" rel="stylesheet"/>
</head>
<body>
    <jsp:include page="../Common/Link.jsp" /> <!--공통 상단 링크-->
    <h2>목록보기(List) - 현재 페이지 : <%=pageNum%>(전체 : <%=totalPage%>)
        <%
            if(searchWord != null) {
            %> 검색키워드 <%=searchWord%>
        <%
            }
        %>
    </h2>
    <!--검색폼-->
    <form method="get">
        <table class="ListTable">
            <tr>
                <td class="txtcenter">
                    <select name="searchField">
                        <option value="title">제목</option>
                        <option value="content">내용</option>
                    </select>
                    <input type="text" name="searchWord"/>
                    <input type="submit" value="검색하기"/>
                </td>
            </tr>
        </table>
    </form>

    <!--게시물 목록 테이블(표)-->
    <table class="ListTable">
        <tr>
            <th class="listno">번호</th>
            <th class="listtitle">제목</th>
            <th class="listwriter">작성자</th>
            <th class="listcnt">조회수</th>
            <th class="listdate">작성일</th>
        </tr>
        <!--목록의 내용-->
        <%
            if(boardLists.isEmpty()){ // 게시물이 없을 때
        %>
        <tr>
            <td colspan="5" class="txtcenter">
                등록된 게시물이 없습니다.
            </td>
        </tr>
        <%
            } else { // 게시물이 있을 때
                int virtualNum = 0; // 화면상에서의 게시물 번호
                int countNum = 0;
                for(BoardDTO dto : boardLists){
                    // virtualNum = totalCount--; // 기존 코드
                    virtualNum = totalCount - (((pageNum - 1)*pageSize)+countNum++);
        %>
        <tr class="txtcenter">
            <td><%=virtualNum%></td>
            <td class="txtleft">
                <a href="View.jsp?num=<%=dto.getNum()%>"><%= dto.getTitle()%></a>
            </td>
            <td class="txtcenter"><%=dto.getId()%></td>
            <td class="txtcenter"><%=dto.getVisitcount()%></td>
            <td class="txtcenter"><%=dto.getPostdate()%></td>
        </tr>
        <%
                }
            }
        %>
    </table>
    <!--목록 하단의 [글쓰기] 버튼-->
    <table class="ListTable">
        <tr class="txtcenter">
            <td>
                <%=BoardPage.pagingStr(totalCount, pageSize, blockPage, pageNum, request.getRequestURI())%>
            </td>
            <td class="txtleft">
                <button type="button" onclick="location.href='Write.jsp';">글쓰기</button>
            </td>
        </tr>
    </table>
</body>
</html>
