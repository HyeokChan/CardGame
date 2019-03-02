package level1;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
public class raGame extends JFrame {
	JButton[] CardBtn; //인원수 지정후 게임시작시 사용할 버튼배열
	TextField Users = new TextField(1);  //인원수 지정을 위한 텍스트필드
	JButton Input = new JButton("게임시작");  // 작성한 인원수를 넘겨주기 위한 버튼
	Checkbox Comchk = new Checkbox("Comp");  // 컴퓨터 유무 옵션처리를 위한 체크박스
	int Player; int[] sum; int count=0; // 인원수를 저장하는 변수, play의 합계산을 위한 변수
	raGame() {
		Container contentPane = getContentPane();  //첫번째 실행창 컨테이너
		JLabel GameTitle = new JLabel("<Number Card Game>"); //게임 타이틀
		JLabel Playerlabel = new JLabel("Player Count :");  
		JLabel Computerlabel = new JLabel("Computer :");  
		GameTitle.setFont(new Font("굴림",Font.BOLD,30)); 
		Playerlabel.setFont(new Font("굴림",Font.BOLD,30));
		Computerlabel.setFont(new Font("굴림",Font.BOLD,20));
		contentPane.setBackground(Color.LIGHT_GRAY);
		setTitle("Level 1");
		contentPane.setLayout(new FlowLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane.add(GameTitle);
		contentPane.add(Playerlabel);
		contentPane.add(Users); 
		contentPane.add(Input);
		contentPane.add(Computerlabel);
		contentPane.add(Comchk);
		Input.addActionListener(new MyActionListener());
		setSize(400,180);
		setVisible(true);
	}
	class MyActionListener extends algorithm implements ActionListener{  //인원수 버튼클릭에 대한 액션리스너
		public void actionPerformed(ActionEvent e) { 
			Player = Integer.parseInt(Users.getText()); // 인원수가 몇명인 게임인지 저장
			sum = new int[Player]; // 인원수에 맞게 sum배열 생성
			
			init(sum,Player); // 플레이어의 합 0으로 초기화 : algorithm
			
			final Frame GamePane = new Frame("Card select");  // 새로운 창 열기frame
			GamePane.setBackground(Color.GRAY);
			GamePane.setVisible(true);
			GamePane.addWindowListener(new WindowAdapter() { //두번째 창만 끄게 만들기 위한 이벤트리스너, 익명클래스
				public void windowClosing(WindowEvent e) {
					GamePane.setVisible(false);
					GamePane.dispose();
				}
			});
			GamePane.setLayout(new GridLayout(Player,3,7,7)); //그리드레이아웃을 통해 버튼배정
			CardBtn = new JButton[(Player*3)]; //인원수에 맞게 버튼갯수 및 버튼배열크기 설정
			for(int i=0;i<CardBtn.length;i++) //반복문을 이용한 카드 버튼만들고, 이벤트리스너 등록
			{
				CardBtn[i] = new JButton(new ImageIcon("img/cardIMG.png"));
				CardBtn[i].setContentAreaFilled(false);
				CardBtn[i].setBorderPainted(false);
				CardBtn[i].setFocusPainted(false);
				GamePane.add(CardBtn[i]);
				CardBtn[i].addActionListener(new MyActionListener2());
			}
			GamePane.setSize(600,600);
			GamePane.setLocation(200,200);
		}
	}
	class MyActionListener2 extends algorithm implements ActionListener {  //카드버튼 클릭에 대한 액션리스너
		public void actionPerformed(ActionEvent e) {  
			JButton b = (JButton)e.getSource();  
			JLabel[] la; //결과 출력을 위한 라벨배열
			la = new JLabel[Player];
			int computer=0; //마지막 남은 3장의 카드를 컴퓨터의 결과로 설정, sum배열을 사용하지않고 따로 배정
			
			int CheckedNum=RandomCheck(CardBtn,Player); // 숫자 랜덤 배정 및 중복검사 : algorithm
			
			b.setText(Integer.toString(CheckedNum));
			b.setEnabled(false);
			count++; // 클릭시 count증가
			ImageChange(b,sum,Player,count,CheckedNum); // 클릭시 count를 고려한 버튼 이미지 변경 
			
			if(Comchk.getState()) //컴퓨터 유무의 옵션처리, 컴퓨터가 포함되었다면
			{
				
				ComputerSelect(CardBtn,count,Player,CheckedNum,computer,sum); // 컴퓨터의 선택 처리 : algorithm (숫자배정,비활성화)
				
				sum[Player-1] = computer; //Player의 마지막에 컴퓨터 결과값 복사
			}
			int maxNum=0;
			if(count == (Player*3)) // 모두 선택되었다면
			{
				maxNum=max(sum);  // 합 배열중에 최댓값인 인덱스를 구한다
				final Frame result = new Frame();  // 새로운 창 열기frame
				result.setBackground(Color.WHITE);
				result.setVisible(true);
				result.addWindowListener(new WindowAdapter() { //세번째 창(게임결과창)만 끄게 만들기 위한 이벤트리스너
					public void windowClosing(WindowEvent e) {
						result.setVisible(false);
						result.dispose();
					}
				});
				result.setLayout(new GridLayout(Player+2,1,2,2)); //플로우레이아웃을 통해 결과 배정
				result.add(new JLabel("-----------Game Result-----------"));
				for(int q=0;q<sum.length;q++) //반복문을 통해 결과창 라벨 생성
				{
					la[q] = new JLabel("         player"+Integer.toString(q+1)+"의 총합 : "+Integer.toString(sum[q]));
					if(Comchk.getState())
					{
						if(q==(Player-1))
						{
							la[q] = new JLabel("         Comp의 총합 : "+Integer.toString(sum[q]));
						}
					}
					result.add(la[q]);
				}
				JLabel resultLabel = new JLabel("         승리 player : "+Integer.toString(maxNum+1)); //승리Player 선언
				
				result.add(resultLabel);
				result.setSize(100,200);
				result.setLocation(400,400);
				count=0; //새게임을 위한 초기화
			}
		}
	}
	public static void main(String [] args) {
		new raGame();
	}
}