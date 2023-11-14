package model1.board;

import common.JDBConnect;
import org.mariadb.jdbc.message.client.ExecutePacket;

import javax.servlet.ServletContext;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class BoardDAO extends JDBConnect {
    public BoardDAO(ServletContext application){
        super(application);
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

}
