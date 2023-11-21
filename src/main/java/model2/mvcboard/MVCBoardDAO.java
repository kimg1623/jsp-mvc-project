package model2.mvcboard;

import common.DBConnPool;
import model2.mybatis.MVCBoardMapper;
import model2.mybatis.MyBatisConfig;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class MVCBoardDAO extends DBConnPool{ // 커넥션 풀 상속
    MyBatisConfig myBatisConfig = new MyBatisConfig();
    SqlSessionFactory sqlSessionFactory = myBatisConfig.getSqlSessionFactory();
    public MVCBoardDAO() {
        super();
    }

    // 검색 조건에 맞는 게시물의 개수를 반환합니다.
    public int selectCount(Map<String, Object> map) {
        int totalCount = 0;
        try(SqlSession session = sqlSessionFactory.openSession()){
            MVCBoardMapper mapper = session.getMapper(MVCBoardMapper.class);
            totalCount = mapper.selectCount(map);
        } catch (Exception e){
            System.out.println("게시물 카운트 중 예외 발생");
            e.printStackTrace();
        }
        return totalCount;
    }
    // 검색 조건에 맞는 게시물 목록을 반환합니다(페이징 기능 지원).
    public List<MVCBoardDTO> selectListPage(Map<String,Object> map) {
        List<MVCBoardDTO> board = null;
        try(SqlSession session = sqlSessionFactory.openSession()){
            MVCBoardMapper mapper = session.getMapper(MVCBoardMapper.class);
            board = mapper.selectListPage(map);
        } catch (Exception e) {
            System.out.println("게시물 조회 중 예외 발생");
            e.printStackTrace();
        }
        return board;
    }

    // 게시글 데이터를 받아 DB에 추가합니다(파일 업로드 지원).
    public int insertWrite(MVCBoardDTO dto) {
        int result = 0;
        try(SqlSession session = sqlSessionFactory.openSession()){
            MVCBoardMapper mapper = session.getMapper(MVCBoardMapper.class);
            result = mapper.insertWrite(dto);
            if (result == 1) {
                session.commit();
                System.out.println("새로운 mvcboard 저장 성공");
            } else {
                System.out.println("새로운 mvcboard 저장 실패");
            }
        } catch (Exception e) {
            System.out.println("게시물 입력 중 예외 발생");
            e.printStackTrace();
        }
        return result;
    }

    // 주어진 일련번호에 해당하는 게시물을 DTO에 담아 반환합니다.
    public MVCBoardDTO selectView(String idx) {
        MVCBoardDTO dto = new MVCBoardDTO();  // DTO 객체 생성
        try(SqlSession session = sqlSessionFactory.openSession()){
            MVCBoardMapper mapper = session.getMapper(MVCBoardMapper.class);
            dto = mapper.selectView(idx);
        }
        catch (Exception e) {
            System.out.println("게시물 상세보기 중 예외 발생");
            e.printStackTrace();
        }
        return dto;  // 결과 반환
    }

    // 주어진 일련번호에 해당하는 게시물의 조회수를 1 증가시킵니다.
    public void updateVisitCount(String idx) {
        try(SqlSession session = sqlSessionFactory.openSession()){
            MVCBoardMapper mapper = session.getMapper(MVCBoardMapper.class);
            mapper.updateVisitCount(idx);
        }
        catch (Exception e) {
            System.out.println("조회수 증가 중 예외 발생");
            e.printStackTrace();
        }
    }

    // 다운로드 횟수를 1 증가시킵니다.
    public void downCountPlus(String idx) {
        try(SqlSession session = sqlSessionFactory.openSession()){
            MVCBoardMapper mapper = session.getMapper(MVCBoardMapper.class);
            mapper.downCountPlus(idx);
        }
        catch (Exception e) {
            System.out.println("다운로드수 증가 중 예외 발생");
            e.printStackTrace();
        }
    }
    // 입력한 비밀번호가 지정한 일련번호의 게시물의 비밀번호와 일치하는지 확인합니다.
    public boolean confirmPassword(String pass, String idx) {
        boolean isCorr = true;
        Map<String, String> map = new HashMap<>();
        map.put("pass", pass);
        map.put("idx", idx);
        try(SqlSession session = sqlSessionFactory.openSession()){
            MVCBoardMapper mapper = session.getMapper(MVCBoardMapper.class);
            mapper.confirmPassword(map);
        }
        catch (Exception e) {
            System.out.println("비밀번호 확인 중 예외 발생");
            e.printStackTrace();
        }
        return isCorr;
    }

    // 지정한 일련번호의 게시물을 삭제합니다.
    public int deletePost(String idx) {
        int result = 0;
        try(SqlSession session = sqlSessionFactory.openSession()){
            MVCBoardMapper mapper = session.getMapper(MVCBoardMapper.class);
            result = mapper.deletePost(idx);
            if (result == 1) {
                session.commit();
                System.out.println("삭제 성공");
            } else {
                System.out.println("삭제 실패");
            }
        } catch (Exception e) {
            System.out.println("게시물 삭제 중 예외 발생");
            e.printStackTrace();
        }
        return result;
    }

    // 게시글 데이터를 받아 DB에 저장되어 있던 내용을 갱신합니다(파일 업로드 지원).
    public int updatePost(MVCBoardDTO dto) {
        int result = 0;
        try(SqlSession session = sqlSessionFactory.openSession()){
            MVCBoardMapper mapper = session.getMapper(MVCBoardMapper.class);
            result = mapper.updatePost(dto);
            if (result == 1) {
                session.commit();
                System.out.println("수정 성공");
            } else {
                System.out.println("수정 실패");
            }
        } catch (Exception e) {
            System.out.println("게시물 수정 중 예외 발생");
            e.printStackTrace();
        }
        return result;
    }
}