package model1.board;

import common.JDBConnect;
import org.mariadb.jdbc.message.client.ExecutePacket;

import javax.servlet.ServletContext;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class BoardDAO extends JDBConnect {
    public BoardDAO(ServletContext application){
        super(application); //
    }

    // 검색 조건에 맞는 게시물의 개수를 반환
    public int selectCount(Map<String, Object> map){
        int totalCount = 0; // 결과(게시물 수)를 담을 변수

        // 게시물 수를 얻어오는 쿼리문 작성
        String query = "SELECT COUNT(*) FROM board";
        if(map.get("searchWord") != null){
            query += " WHERE " + map.get("searchField") + " "
                    + " LIKE  '%" + map.get("searchWord") + "%'";
        }

        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            rs.next(); // 커서를 첫 번째 행으로 이동
            totalCount = rs.getInt(1); // 첫 번째 컬럼 값을 가져옴
        } catch (Exception e) {
            System.out.println("게시물 수를 구하는 중 예외 발생");
            e.printStackTrace();
        }

        return totalCount;
    }

    public List<BoardDTO> selectList(Map<String, Object> map){
        List<BoardDTO> bbs = new Vector<>();

        String query = "SELECT * FROM board";
        if(map.get("searchWord") != null){
            query += " WHERE " + map.get("searchField") + " "
                    + " LIKE  '%" + map.get("searchWord") + "%'";
        }
        query += " ORDER BY num DESC ";

        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while(rs.next()){
                // 한 행(게시물 하나)의 내용을 DTO에 저장
                BoardDTO dto = new BoardDTO();

                dto.setNum(rs.getString("num"));
                dto.setTitle(rs.getString("title"));
                dto.setContent(rs.getString("content"));
                dto.setPostdate(rs.getDate("postdate"));
                dto.setId(rs.getString("id"));
                dto.setVisitcount(rs.getString("visitcount"));

                bbs.add(dto);
            }
        } catch (Exception e) {
            System.out.println("게시물 조회 중 예외 발생");
            e.printStackTrace();
        }

        return bbs;
    }

    public List<BoardDTO> selectListPage(Map<String, Object> map){
        List<BoardDTO> bbs = new Vector<>();

        String query = "SELECT * FROM ( "
                + " SELECT @ROWNUM := @ROWNUM + 1 AS ROWNUM, b.* "
                + " FROM  board b,(SELECT @ROWNUM := 0 ) TMP ";

        // 검색 조건 추가
        if (map.get("searchWord") != null) {
            query += " WHERE " + map.get("searchField")
                    + " LIKE '%" + map.get("searchWord") + "%' ";
        }

        query += " ORDER BY  num DESC ) T " +
                " WHERE ROWNUM BETWEEN ? AND ? ;";

        try{
            psmt = con.prepareStatement(query);
            psmt.setString(1, map.get("start").toString());
            psmt.setString(2, map.get("end").toString());
            rs = psmt.executeQuery();
            while(rs.next()){
                // 한 행(게시물 하나)의 내용을 DTO에 저장
                BoardDTO dto = new BoardDTO();

                dto.setNum(rs.getString("num"));
                dto.setTitle(rs.getString("title"));
                dto.setContent(rs.getString("content"));
                dto.setPostdate(rs.getDate("postdate"));
                dto.setId(rs.getString("id"));
                dto.setVisitcount(rs.getString("visitcount"));

                bbs.add(dto);
            }
        } catch (Exception e) {
            System.out.println("게시물 조회 중 예외 발생");
            e.printStackTrace();
        }

        return bbs;
    }
    public List<BoardDTO> selectListPage2(Map<String, Object> map){
        List<BoardDTO> bbs = new Vector<>();

        String query = "SELECT * FROM board ";

        // 검색 조건 추가
        if (map.get("searchWord") != null) {
            query += " WHERE " + map.get("searchField")
                    + " LIKE '%" + map.get("searchWord") + "%' ";
        }

        query += " ORDER BY num DESC " +
                " LIMIT ? OFFSET ? ";

        try{
            psmt = con.prepareStatement(query);
            psmt.setString(1, map.get("pageSize").toString());
            psmt.setString(2, map.get("offset").toString());
            rs = psmt.executeQuery();
            while(rs.next()){
                // 한 행(게시물 하나)의 내용을 DTO에 저장
                BoardDTO dto = new BoardDTO();

                dto.setNum(rs.getString("num"));
                dto.setTitle(rs.getString("title"));
                dto.setContent(rs.getString("content"));
                dto.setPostdate(rs.getDate("postdate"));
                dto.setId(rs.getString("id"));
                dto.setVisitcount(rs.getString("visitcount"));

                bbs.add(dto);
            }
        } catch (Exception e) {
            System.out.println("게시물 조회 중 예외 발생");
            e.printStackTrace();
        }

        return bbs;
    }

    // 게시글 데이터를 받아 DB에 추가
    public int insertWrite(BoardDTO dto){
        int result = 0;

        try{
            String query = "INSERT INTO board( " +
                    " num, title, content, id, visitcount)" +
                    " VALUES (" +
                    " null, ?, ?, ?, 0)";
            psmt = con.prepareStatement(query);
            psmt.setString(1, dto.getTitle());
            psmt.setString(2, dto.getContent());
            psmt.setString(3, dto.getId());

            result = psmt.executeUpdate();
        } catch (Exception e){
            System.out.println("게시물 입력 중 예외 발생");
            e.printStackTrace();
        }

        return result;
    }

    // 지정한 게시물을 찾아 내용을 반환
    public BoardDTO selectView(String num){
        BoardDTO dto = new BoardDTO();

        String query = "SELECT B.*, M.name " +
                " FROM member M INNER JOIN board B " +
                " ON M.id=B.id" +
                " WHERE num=?";
        try{
            psmt = con.prepareStatement(query);
            psmt.setString(1, num);
            rs = psmt.executeQuery();
            if(rs.next()){
                dto.setNum(rs.getString(1));
                dto.setTitle(rs.getString(2));
                dto.setContent(rs.getString("content"));
                dto.setPostdate(rs.getDate("postdate"));
                dto.setId(rs.getString("id"));
                dto.setVisitcount(rs.getString(6));
                dto.setName(rs.getString("name"));
            }
        } catch (Exception e) {
            System.out.println("게시물 상세보기 중 예외 발생");
            e.printStackTrace();
        }
        return dto;
    }

    // 지정한 게시물의 조회수를 1 증가
    public void updateVisitCount(String num){
        String query = "UPDATE board SET " +
                " visitcount=visitcount+1 " +
                " WHERE num=?";
        try{
            psmt = con.prepareStatement(query);
            psmt.setString(1,num);
            psmt.executeQuery();
        } catch (Exception e) {
            System.out.println("게시물 조회수 증가 중 예외 발생");
            e.printStackTrace();
        }
    }

    // 지정한 게시물 수정
    public int updateEdit(BoardDTO dto){
        int result = 0;

        try{
            String query = "UPDATE board SET " +
                    " title=?, content=? " +
                    " WHERE num=?";

            psmt = con.prepareStatement(query);
            psmt.setString(1, dto.getTitle());
            psmt.setString(2, dto.getContent());
            psmt.setString(3, dto.getNum());
            result = psmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("게시물 수정 중 예외 발생");
            e.printStackTrace();
        }
        return result;
    }

    // 지정한 게시물 삭제
    public int deletePost(BoardDTO dto){
        int result = 0;

        try{
            String query = "DELETE FROM board WHERE num=?";

            psmt = con.prepareStatement(query);
            psmt.setString(1, dto.getNum());
            result = psmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("게시물 삭제 중 예외 발생");
            e.printStackTrace();
        }
        return result;

    }

}
