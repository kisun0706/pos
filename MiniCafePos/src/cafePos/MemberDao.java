package cafePos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @author User
 *데이터베이스와 연동하기 위한 클래스
 *
 */
public class MemberDao {
	private Connection conn;
	private static final String USERNAME="root";
	private static final String PASSWORD="root";
	private static final String URL="jdbc:mysql://127.0.0.1/pksdb";
	
	
	/**Dao를 싱클톤 패턴으로 구현 */
	private static MemberDao instance;	
	
	/** mysql과 연동*/
	private MemberDao() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		conn=DriverManager.getConnection(URL,USERNAME,PASSWORD);
	}
	
	public static MemberDao getInstance() throws ClassNotFoundException, SQLException {
		if(null==instance) {
			instance=new MemberDao();
		}
		return instance;
	}
	
	
	/**
	 * 
	 * @param name 회원 이름
	 * @return 입력된 name과 데이터베이스의 name을 비교하여 같으면 해당 row 출력
	 * @throws SQLException 
	 */
	public MemberVo selectMember(String name) throws SQLException {
		MemberVo vo=new MemberVo();
		
		String sql="select * from member where name=?";
		PreparedStatement ps=conn.prepareStatement(sql);
		ps.setString(1, name);
		
		ResultSet rs=ps.executeQuery();
		if(rs.next()) {
			vo.setNo(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setTel(rs.getString(3));
		}
		
		rs.close();
		ps.close();
		return vo;
	}
	
	/**
	 * 
	 * @param name	member 테이블의 name에 들어갈 입력값
	 * @param tel	member 테이블의 tel에 들어갈 입력값
	 * @return	member 테이블에 영향 받은 레코드가 0개 이상이면 실행
	 * @throws SQLException 
	 */
	public boolean insertMember(String name, String tel) throws SQLException {
		String sql="insert into member(name, tel) values (?,?)";
		PreparedStatement ps=conn.prepareStatement(sql);
		ps.setString(1, name);
		ps.setString(2, tel);
				
		return ps.executeUpdate()>0;
	}
	
}
