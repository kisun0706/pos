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
 * �г� ���� ���� ��ư�� textarea, ���̺��� ���¿� ���(actionlistener)�� �����ϱ� ���� Ŭ����
 */
public class CafePanel extends JPanel{

	JButton MBtns[]=new JButton[6];		
	String[] menu={"�Ƹ޸�ī��", "ī���", "�ٴҶ��", "ī���ī", "īǪġ��", "ī��Ḷ���߶�"};		
	int[] price=  {  2000,      2500,      3000,      3000,     2500,       3000};

	JButton FBtns[]=new JButton[5];
	String[] functions= {"����", "���", "����", "ȸ����ȸ", "ȸ������"};
	
	
	String [] ColName = {"�޴�","����","����"};
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
	 *�޴� ��ư ���� ���� 
	 */
	class MenuBtn extends JPanel{
		public MenuBtn() {
			setLayout(new GridLayout(2,3,5,5));
			setBackground(Color.WHITE);			
			for(int i=0; i<MBtns.length; ++i) {
				MBtns[i]=new JButton(menu[i]);
				add(MBtns[i]);
				MBtns[i].setFocusable(false);
				MBtns[i].setFont(new Font("���� ���", Font.BOLD, 16));
			}
		}
	}
	
	/**
	 * 
	 * @author User
	 * ��� ��ư ���� ���� 
	 */
	class FunctionBtn extends JPanel{
		public FunctionBtn() {
			setLayout(new GridLayout(2,3,20,10));
			setBackground(Color.WHITE);			
			for(int i=0; i<FBtns.length; ++i) {
				FBtns[i]=new JButton(functions[i]);
				add(FBtns[i]);
				FBtns[i].setFocusable(false);
				FBtns[i].setFont(new Font("���� ���", Font.BOLD, 18));
			}
		}
	}
	
	/** 
	 * 
	 * @author User
	 *�ֹ���� ����� ���̺� ���� ���� 
	 */
	class OrderScreen extends JPanel{
		public OrderScreen() {
			setBackground(Color.WHITE);
			DefaultTableModel m=(DefaultTableModel)table.getModel();
			table.setRowHeight(50);
			table.getTableHeader().setFont(new Font("���� ���", Font.BOLD,15));
			table.getTableHeader().setBackground(Color.WHITE);
			JScrollPane scroll=new JScrollPane(table);
			scroll.setPreferredSize(new Dimension(470,500));
			add(scroll);
		}
	}
	
	/** 
	 * �г��� �����ϰ� �� ���� �޴� ��ư, ��� ��ư, �ֹ����â, �Ѱ��ݶ� ���� 
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
		 * �޴� ��ư ��� ����
		 */
		for(int i=0; i<MBtns.length; ++i) {
			final int index=i;
			MBtns[i].addActionListener(new ActionListener() {
				
				/** 
				 * �޴� ��ư ������ �ֹ����(���̺�)�� �߰�
				 */
				@Override
				public void actionPerformed(ActionEvent e) {
					JButton menub=(JButton)e.getSource();
					DefaultTableModel m=(DefaultTableModel)table.getModel();
					m.addRow(new Object[]{menu[index],count,price[index]});
					
					/** 
					 * ���� �÷��� �����͸� �Ѱ��ݶ��� �����Ͽ� ��� 
					 */
					int sum=0;
					for(int i=0; i<table.getRowCount(); ++i) {
						sum+=(int)table.getValueAt(i, 2);
					}
					totalPriceArea.setText(String.valueOf(sum));
					totalPriceArea.setFont(new Font("���� ���", Font.BOLD, 16));					
				}
			});
		}
		
		/** 
		 * ���� ��ư ��� ���� 
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
					/** ���� ������ ���� */
					String price=totalPriceArea.getText();	
					/** 5% ���� ���� ���� */
					int discountPrice=(int) (Integer.parseInt(price)*0.95);	
					
					String check=JOptionPane.showInputDialog(new CafeFrame(), "�̸�: ");
					dao=MemberDao.getInstance();
					vo=dao.selectMember(check);
					if(null==vo.getName()) {	/** �����ͺ��̽��� ���� �̸��̸� ȸ���� �ƴϹǷ� ���� ������ ���� ���� */
						JOptionPane.showMessageDialog(new CafeFrame(), "�� "+price+"�� �Դϴ�.\n"+time);	
						sales+=Integer.parseInt(price);	/** ���� ������ ���� ���⿡ ����*/
					}
					else {
						/** �����ͺ��̽��� �ִ� �̸��̸� ȸ���̹Ƿ� ���� ���� ���� ����*/
						JOptionPane.showMessageDialog(new CafeFrame(), "ȸ���̹Ƿ� �� "+discountPrice+"�� �Դϴ�.\n"+time);	
						sales+=discountPrice;	/** ���ε� ������ ���⿡ ��� */
					}
				} catch (ClassNotFoundException | SQLException e1) {
					e1.printStackTrace();
				}				
				
			}
		});
		
		/** 
		 * ��� ��ư ��� ����
		 */
		FBtns[1].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton cancelb=(JButton)e.getSource();
				DefaultTableModel m=(DefaultTableModel)table.getModel();
				/** ���̺��� ���� �����ϸ� n�� ����*/
				int n=table.getSelectedRow();
				
				/** ���� ���õ� ���� �ֹ���� ���� ���� ������ �ش� �ุ ���� */
				if(n>=0 && n<table.getRowCount()) {
					/**�Ѱ��ݶ����� ���� ������ �޴��� ���ݸ� ���� */
					int num=Integer.parseInt(totalPriceArea.getText());
					int num2=(int)m.getValueAt(n, 2);
					int res=num-num2;
					totalPriceArea.setText(String.valueOf(res));
					
					m.removeRow(n);					
				}
				else {
					/** ���õ� ���� ������ ��ü �� ����*/
					m.setRowCount(0);
					totalPriceArea.setText("");
				}
				
			}
		});
		
		/** 
		 * ���� ��ư ��� ����
		 */
		FBtns[2].addActionListener(new ActionListener() {	
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton salesb=(JButton)e.getSource();
				Date date=new Date();
				SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String time=format.format(date);
				
				/** int Ÿ���� sales�� ���� ����� ���� ���*/
				JOptionPane.showMessageDialog(new CafeFrame(), "���� ����: "+String.valueOf(sales)+"��\n"+time);
			}
		});
		
		/** 
		 * ȸ�� ��ȸ ��ư ��� ����
		 */
		FBtns[3].addActionListener(new ActionListener() {
			  														
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
				JButton memberb=(JButton)e.getSource();
				String customer=JOptionPane.showInputDialog(new CafeFrame(), "�̸�: ");
				dao=MemberDao.getInstance();
				vo=dao.selectMember(customer);
				if(null==vo.getName()) {
					/** �����ͺ��̽��� ���� �̸��̸� ȸ���� �ƴ�*/
					JOptionPane.showMessageDialog(new CafeFrame(), "ȸ�������ϼ���.");
				}
				else {
					/** �����ͺ��̽��� �����ϴ� �̸��̸� ȸ�� ���� ���*/
					JOptionPane.showMessageDialog(new CafeFrame(), vo.toString());
				}
				}
				catch(Exception ex) {
					ex.printStackTrace();
				}			
			}
		});
		
		/** 
		 * ȸ�� ���� ��ư ��� ����
		 */
		FBtns[4].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					JButton joinb=(JButton)e.getSource();
					dao=MemberDao.getInstance();
					vo=new MemberVo();
					
					String name=JOptionPane.showInputDialog(new CafeFrame(), "�̸�: ");
					vo.setName(name);
					String tel=JOptionPane.showInputDialog(new CafeFrame(), "����ó: ");
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



