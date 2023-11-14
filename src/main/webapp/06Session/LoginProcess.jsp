<%@ page import="java.sql.DriverManager" %>
<%@ page import="membership.MemberDTO" %>
<%@ page import="membership.MemberDAO" %><%--
  Created by IntelliJ IDEA.
  User: Jiwon Kim
  Date: 2023-11-13
  Time: 오후 4:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String userId = request.getParameter("user_id");
    String userPwd = request.getParameter("user_pw");

    String MariaDBDriver = application.getInitParameter("MariaDB_Driver");
    String url = application.getInitParameter("MariaDB_URL");
    String id = application.getInitParameter("MariaDB_Id");
    String pwd = application.getInitParameter("MariaDB_Pwd");

    // 회원 테이블 DAO를 통해 회원 정보 DTO 획득
    MemberDAO dao = new MemberDAO(MariaDBDriver, url, id, pwd);
    MemberDTO memberDTO = dao.getMemberDTO(userId, userPwd);
    dao.close();

    // 로그인 성공 여부에 따른 처리
    if(memberDTO.getId() != null){
        //로그인 성공
        session.setAttribute("UserId", memberDTO.getId());
        session.setAttribute("UserName", memberDTO.getName());
        response.sendRedirect("LoginForm.jsp");
    } else {
        //로그인 실패
        request.setAttribute("LoginErrMsg", "로그인 오류입니다.");
        request.getRequestDispatcher("LoginForm.jsp").forward(request, response); // forward
    }
%>