#  MiniCafePos
## 프로그램주제
-카페에서 사용할 수있는 pos 프로그램으로 회원제를 바탕으로 할인 혜택을 적용 할 수 있습니다.
##핵심 기능
java
String price=totalPriceArea.getText();
					int discountPrice=(int) (Integer.parseInt(price)*0.95);
					//영수증 띄우기 전에 회원인지 아닌지 확인하고 회원이면 총가격에서 5% 할인
					String check=JOptionPane.showInputDialog(new CafeFrame(), "이름: ");
					dao=MemberDao.getInstance();
					vo=dao.selectMember(check);
					if(null==vo.getName()) {
						JOptionPane.showMessageDialog(new CafeFrame(), "총 "+price+"원 입니다.\n"+time);	
						sales+=Integer.parseInt(price);//할인 미적용 가격을 매출에 등록
					}
					else {
						JOptionPane.showMessageDialog(new CafeFrame(), "회원이므로 총 "+discountPrice+"원 입니다.\n"+time);	
						sales+=discountPrice;//할인된 가격을 매출에 등록
					}
			
mysql
				select*from member;
+----+--------+---------------+
| no | name   | tel           |
+----+--------+---------------+
|  1 | 홍길동 | 010-1234-5678 |
|  2 | 고길동 | 010-2345-6789 |
+----+--------+---------------+

