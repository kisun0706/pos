package cafePos;

import javax.swing.JFrame;
/**
 * 
 * @author User
 * 프레임의 기본적인 형태 구현 및 패널 클래스의 형태와 기능을 프레임 위에서 실행하기 위한 클래스
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
