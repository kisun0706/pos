package cafePos;
/**
 * 
 * @author User
 * member ���̺��� ���ڵ� �����Ͱ� ������� ���� Ŭ����
 */
public class MemberVo {
	private int no;
	private String name;
	private String tel;
	/**
	 * 
	 * @return �Է� ���� no ����
	 */
	public int getNo() {
		return no;
	}
	/**
	 * 
	 * @param no member ���̺��� no�׸��� �Է¹ޱ� ����
	 */
	public void setNo(int no) {
		this.no = no;
	}
	/**
	 * 
	 * @return �Է� ���� name ����
	 */
	public String getName() {
		return name;
	}
	/**
	 * 
	 * @param name member ���̺��� name �׸��� �Է¹ޱ� ����
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 
	 * @return	�Է� ���� tel ����
	 */
	public String getTel() {
		return tel;
	}
	/**
	 * 
	 * @param tel	member ���̺��� tel �׸��� �Է¹ޱ� ����
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}
	/**
	 * �������޼ҵ�
	 */
	public String toString() {
		return "no. "+no+"\n�̸�: "+name+"\n����ó: "+tel;
	}
	
}
