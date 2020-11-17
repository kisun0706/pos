#  MiniCafePos
## 프로그램주제
-카페에서 사용할 수있는 pos 프로그램으로 회원제를 바탕으로 할인 혜택을 적용 할 수 있습니다.

## 핵심 기능

```java
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
			
```mysql
				select*from member;
+----+--------+---------------+
| no | name   | tel           |
+----+--------+---------------+
|  1 | 홍길동 | 010-1234-5678 |
|  2 | 고길동 | 010-2345-6789 |
+----+--------+---------------+
```

## 힘들었거나 아쉬운 점

-jtable의 활용은 처음이라 그 기능들을 써먹는 데 시간이 소요됐다.   
-원래 같은 메뉴를 여러 번 눌렀을 때 jtable의 row가 계속 나열되는 것이 아니라 해당 메뉴의 수량만 늘어나게 하려고 했는데, 그것을 구현하지 못했다.   
-원래 결제 버튼을 누르면 메뉴 이름, 가격, 수량을 모두 joptionpane으로 출력하려고 했으나, jtable의 모든 데이터를 출력하는 방법을 찾지 못하여 총 가격만 출력하였다.

## 유튜브
[![Watch the video](https://img.youtube.com/vi/동영상id/hqdefault.jpg)](https://www.youtube.com/watch?v=REL0pt4SZl0)

## javadoc
https://kisun0706.github.io/pos/MiniCafePos/doc/index.html
