package cafePos;
/**
 * 
 * @author User
 * member 테이블의 레코드 데이터값 입출력을 위한 클래스
 */
public class MemberVo {
	private int no;
	private String name;
	private String tel;
	/**
	 * 
	 * @return 입력 받은 no 리턴
	 */
	public int getNo() {
		return no;
	}
	/**
	 * 
	 * @param no member 테이블의 no항목을 입력받기 위함
	 */
	public void setNo(int no) {
		this.no = no;
	}
	/**
	 * 
	 * @return 입력 받은 name 리턴
	 */
	public String getName() {
		return name;
	}
	/**
	 * 
	 * @param name member 테이블의 name 항목을 입력받기 위함
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 
	 * @return	입력 받은 tel 리턴
	 */
	public String getTel() {
		return tel;
	}
	/**
	 * 
	 * @param tel	member 테이블의 tel 항목을 입력받기 위함
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}
	/**
	 * 출력전용메소드
	 */
	public String toString() {
		return "no. "+no+"\n이름: "+name+"\n연락처: "+tel;
	}
	
}
