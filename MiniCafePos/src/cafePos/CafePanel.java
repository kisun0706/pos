package cafePos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * 
 * @author User
 * 패널 위의 각종 버튼과 textarea, 테이블의 형태와 기능(actionlistener)을 구현하기 위한 클래스
 */
public class CafePanel extends JPanel{

	JButton MBtns[]=new JButton[6];		
	String[] menu={"아메리카노", "카페라떼", "바닐라라떼", "카페모카", "카푸치노", "카라멜마끼야또"};		
	int[] price=  {  2000,      2500,      3000,      3000,     2500,       3000};

	JButton FBtns[]=new JButton[5];
	String[] functions= {"결제", "취소", "매출", "회원조회", "회원가입"};
	
	
	String [] ColName = {"메뉴","수량","가격"};
	String [][] Data ;	
	int count =1;
	
	DefaultTableModel model = new DefaultTableModel(Data,ColName);
	JTable table = new JTable(model);
	
	JTextField totalPriceArea=new JTextField(30);
	
	static int sales; 

	MemberVo vo;
	MemberDao dao=null;
	
	/**
	 *  
	 * @author User
	 *메뉴 버튼 형태 구현 
	 */
	class MenuBtn extends JPanel{
		public MenuBtn() {
			setLayout(new GridLayout(2,3,5,5));
			setBackground(Color.WHITE);			
			for(int i=0; i<MBtns.length; ++i) {
				MBtns[i]=new JButton(menu[i]);
				add(MBtns[i]);
				MBtns[i].setFocusable(false);
				MBtns[i].setFont(new Font("맑은 고딕", Font.BOLD, 16));
			}
		}
	}
	
	/**
	 * 
	 * @author User
	 * 기능 버튼 형태 구현 
	 */
	class FunctionBtn extends JPanel{
		public FunctionBtn() {
			setLayout(new GridLayout(2,3,20,10));
			setBackground(Color.WHITE);			
			for(int i=0; i<FBtns.length; ++i) {
				FBtns[i]=new JButton(functions[i]);
				add(FBtns[i]);
				FBtns[i].setFocusable(false);
				FBtns[i].setFont(new Font("맑은 고딕", Font.BOLD, 18));
			}
		}
	}
	
	/** 
	 * 
	 * @author User
	 *주문목록 출력할 테이블 형태 구현 
	 */
	class OrderScreen extends JPanel{
		public OrderScreen() {
			setBackground(Color.WHITE);
			DefaultTableModel m=(DefaultTableModel)table.getModel();
			table.setRowHeight(50);
			table.getTableHeader().setFont(new Font("맑은 고딕", Font.BOLD,15));
			table.getTableHeader().setBackground(Color.WHITE);
			JScrollPane scroll=new JScrollPane(table);
			scroll.setPreferredSize(new Dimension(470,500));
			add(scroll);
		}
	}
	
	/** 
	 * 패널을 생성하고 그 위에 메뉴 버튼, 기능 버튼, 주문목록창, 총가격란 생성 
	 */
	public CafePanel() {
		setLayout(null);
		setBackground(Color.WHITE);
		MenuBtn mbtn=new MenuBtn();
		FunctionBtn fbtn=new FunctionBtn();
		OrderScreen os=new OrderScreen();
		
		totalPriceArea.setSize(475,70);
		totalPriceArea.setLocation(500,530);
		totalPriceArea.setEditable(false);
		totalPriceArea.setBackground(Color.WHITE);
		add(totalPriceArea);
		
		os.setSize(520, 700);
		os.setLocation(480, 0);
		add(os);
		
		mbtn.setSize(470, 350);
		mbtn.setLocation(10, 0);
		add(mbtn);
		
		fbtn.setSize(450,250);
		fbtn.setLocation(20, 360);
		add(fbtn);
		
		/**
		 * 메뉴 버튼 기능 구현
		 */
		for(int i=0; i<MBtns.length; ++i) {
			final int index=i;
			MBtns[i].addActionListener(new ActionListener() {
				
				/** 
				 * 메뉴 버튼 누르면 주문목록(테이블)에 추가
				 */
				@Override
				public void actionPerformed(ActionEvent e) {
					JButton menub=(JButton)e.getSource();
					DefaultTableModel m=(DefaultTableModel)table.getModel();
					m.addRow(new Object[]{menu[index],count,price[index]});
					
					/** 
					 * 가격 컬럼의 데이터를 총가격란에 누적하여 출력 
					 */
					int sum=0;
					for(int i=0; i<table.getRowCount(); ++i) {
						sum+=(int)table.getValueAt(i, 2);
					}
					totalPriceArea.setText(String.valueOf(sum));
					totalPriceArea.setFont(new Font("맑은 고딕", Font.BOLD, 16));					
				}
			});
		}
		
		/** 
		 * 결제 버튼 기능 구현 
		 */
		FBtns[0].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton payb=(JButton)e.getSource();
				Date date=new Date();
				SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String time=format.format(date);
				DefaultTableModel m=(DefaultTableModel)table.getModel();
								
				try {
					/** 할인 미적용 가격 */
					String price=totalPriceArea.getText();	
					/** 5% 할인 적용 가격 */
					int discountPrice=(int) (Integer.parseInt(price)*0.95);	
					
					String check=JOptionPane.showInputDialog(new CafeFrame(), "이름: ");
					dao=MemberDao.getInstance();
					vo=dao.selectMember(check);
					if(null==vo.getName()) {	/** 데이터베이스에 없는 이름이면 회원이 아니므로 할인 미적용 가격 결제 */
						JOptionPane.showMessageDialog(new CafeFrame(), "총 "+price+"원 입니다.\n"+time);	
						sales+=Integer.parseInt(price);	/** 할인 미적용 가격 매출에 저장*/
					}
					else {
						/** 데이터베이스에 있는 이름이면 회원이므로 할인 적용 가격 결제*/
						JOptionPane.showMessageDialog(new CafeFrame(), "회원이므로 총 "+discountPrice+"원 입니다.\n"+time);	
						sales+=discountPrice;	/** 할인된 가격을 매출에 등록 */
					}
				} catch (ClassNotFoundException | SQLException e1) {
					e1.printStackTrace();
				}				
				
			}
		});
		
		/** 
		 * 취소 버튼 기능 구현
		 */
		FBtns[1].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton cancelb=(JButton)e.getSource();
				DefaultTableModel m=(DefaultTableModel)table.getModel();
				/** 테이블의 행을 선택하면 n에 저장*/
				int n=table.getSelectedRow();
				
				/** 만약 선택된 행이 주문목록 범위 내에 있으면 해당 행만 삭제 */
				if(n>=0 && n<table.getRowCount()) {
					/**총가격란에서 선택 삭제된 메뉴의 가격만 빼기 */
					int num=Integer.parseInt(totalPriceArea.getText());
					int num2=(int)m.getValueAt(n, 2);
					int res=num-num2;
					totalPriceArea.setText(String.valueOf(res));
					
					m.removeRow(n);					
				}
				else {
					/** 선택된 행이 없으면 전체 행 삭제*/
					m.setRowCount(0);
					totalPriceArea.setText("");
				}
				
			}
		});
		
		/** 
		 * 매출 버튼 기능 구현
		 */
		FBtns[2].addActionListener(new ActionListener() {	
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton salesb=(JButton)e.getSource();
				Date date=new Date();
				SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String time=format.format(date);
				
				/** int 타입의 sales에 누적 저장된 가격 출력*/
				JOptionPane.showMessageDialog(new CafeFrame(), "현재 매출: "+String.valueOf(sales)+"원\n"+time);
			}
		});
		
		/** 
		 * 회원 조회 버튼 기능 구현
		 */
		FBtns[3].addActionListener(new ActionListener() {
			  														
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
				JButton memberb=(JButton)e.getSource();
				String customer=JOptionPane.showInputDialog(new CafeFrame(), "이름: ");
				dao=MemberDao.getInstance();
				vo=dao.selectMember(customer);
				if(null==vo.getName()) {
					/** 데이터베이스에 없는 이름이면 회원이 아님*/
					JOptionPane.showMessageDialog(new CafeFrame(), "회원가입하세요.");
				}
				else {
					/** 데이터베이스에 존재하는 이름이면 회원 정보 출력*/
					JOptionPane.showMessageDialog(new CafeFrame(), vo.toString());
				}
				}
				catch(Exception ex) {
					ex.printStackTrace();
				}			
			}
		});
		
		/** 
		 * 회원 가입 버튼 기능 구현
		 */
		FBtns[4].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					JButton joinb=(JButton)e.getSource();
					dao=MemberDao.getInstance();
					vo=new MemberVo();
					
					String name=JOptionPane.showInputDialog(new CafeFrame(), "이름: ");
					vo.setName(name);
					String tel=JOptionPane.showInputDialog(new CafeFrame(), "연락처: ");
					vo.setTel(tel);
					
					dao.insertMember(vo.getName(),vo.getTel());
				}
				catch(Exception ex) {
					ex.printStackTrace();
				}
				
			}
		});
	}	
}



