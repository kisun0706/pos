package cafePos;

import javax.swing.JFrame;
/**
 * 
 * @author User
 * �������� �⺻���� ���� ���� �� �г� Ŭ������ ���¿� ����� ������ ������ �����ϱ� ���� Ŭ����
 * 
 */
public class CafeFrame extends JFrame{
	public CafeFrame() {
		super("POS");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(997, 660);
		setLocationRelativeTo(null);
		setContentPane(new CafePanel());
		
		setVisible(true);
	}
}
