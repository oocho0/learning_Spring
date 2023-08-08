package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcMemberRepository implements MemberRepository {

    /*
     * DB에 붙으려면 DataSource 가 필요함 javax.sql.DataSource 로부터 옴
     * Spring 으로 부터 dataSource 를 주입받아야 함
     * application.preperties 로 DB를 셋팅해놓으면 스프링부트가 데이터를 연결해서
     * DataSource 를 만들어 놓음(접속 정보) 이걸 주입 받을 수 있음
     */
    private final DataSource dataSource;

    public JdbcMemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Member save(Member member) {
        /* 쿼리문을 짜야함
        * 다들 Exception이 많아서 try-catch를 잘해야함 */
        String sql = "INSERT INTO member(name) VALUES(?)";// ?->파라미터 바인딩 할 곳
        Connection conn = null; // 데이터베이스 커넥션을 가지고 온 후에
        PreparedStatement pstmt = null; // 문장을 작성
        ResultSet rs = null;
        try {
            conn = getConnection();  // 데이터베이스 커넥션을 가지고 온 후에 (진짜 데이터베이스와 연결된 소켓을 얻을 수 있음 SQL문을 날려서 받을 수 있음)
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); // 문장을 작성 (sql을 먼저 넣고 insert를 하고나서 키값을 얻을 수 있도록 하는 값을 넣음)
            pstmt.setString(1, member.getName());  // VALUES(?)의 ?와 미칭되는 parameterindex = 1, getName을 값으로 넣음
            pstmt.executeUpdate(); // DB에 쿼리가 날라가서 데이터를 업데이트

            rs = pstmt.getGeneratedKeys(); // insert하고 생성된 키값을 꺼냄(RETURN_GENERATED_KEYS와 매칭됨 : AUTO_INCREMENT되는 ID값(long)이 나옴)

            if (rs.next()) { // 값이 있으면
                member.setId(rs.getLong(1)); // long형식 값(ID)을 꺼내서 아이디 값으로 넣어줌
            } else {
                throw new SQLException("id 조회 실패");
            }
            return member;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs); // 외부와 연결되었던 모든 변수들의 연결을 끊어줘야함, 리소스 반환해야함
        }
    }

    @Override
    public Optional<Member> findById(Long id) {
        String sql = "SELECT * FROM member WHERE id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection(); // 데이터베이스 커넥션을 가지고 온 후에
            pstmt = conn.prepareStatement(sql); // sql문장을 넣어 쿼리문 작성
            pstmt.setLong(1, id); // ?와 매칭되는 parameterindex=1, 매개변수 id를 값으로 넣음
            rs = pstmt.executeQuery(); // DB에 쿼리가 날아감
            if (rs.next()) { // 값이 있으면
                Member member = new Member();
                member.setId(rs.getLong("id")); // member의 id에 id 열의 값을 넣어줌
                member.setName(rs.getString("name")); // member의 name에 name 열의 값을 넣어줌
                return Optional.of(member); // 반환
            } else {
                return Optional.empty(); // 없으면 null 반환
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Member> findByName(String name) {
        String sql = "SELECT * FROM member WHERE name = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public List<Member> findAll() {
        String sql = "SELECT * FROM member";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            List<Member> members = new ArrayList<>(); // 리스트에 담음
            while(rs.next()) { // loop를 돌려서 쭉 담음
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                members.add(member);
            }
            return members;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    /* JdbcMemberRepository 생성자에서 DataSource를 초기화할때
     *  dataSource.getConnection(); 해서 값을 얻을 수도 있지만 그러면 계속 새로운 커넥션이 주어짐
     *  Spring framework를 통해서 데이터.getConnection을 쓸 때는
     *  DataSourceUtils를 통해서 이 .getConnection을 획득해야 함
     *  그래야 같은 @transactional 안에 속한 connection을 얻을 수 있음
     *  만약 데이터소스로부터 dataSource.getConnection을 하는 경우에
     *  @transactional과 상관없이 connection pool로 부터 매번 새로운 connection을 얻게 되서
     *  @transactional을 사용할 수 없어짐
     */
    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* 닫을 때도 DataSourceUtils를 사용해야함 */
    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}
