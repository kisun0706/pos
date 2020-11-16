package cafePos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @author User
 *�����ͺ��̽��� �����ϱ� ���� Ŭ����
 *
 */
public class MemberDao {
	private Connection conn;
	private static final String USERNAME="root";
	private static final String PASSWORD="root";
	private static final String URL="jdbc:mysql://127.0.0.1/pksdb";
	
	
	/**Dao�� ��Ŭ�� �������� ���� */
	private static MemberDao instance;	
	
	/** mysql�� ����*/
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
	 * @param name ȸ�� �̸�
	 * @return �Էµ� name�� �����ͺ��̽��� name�� ���Ͽ� ������ �ش� row ���
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
	 * @param name	member ���̺��� name�� �� �Է°�
	 * @param tel	member ���̺��� tel�� �� �Է°�
	 * @return	member ���̺� ���� ���� ���ڵ尡 0�� �̻��̸� ����
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
