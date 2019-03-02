package level1;

import javax.swing.*;

public class algorithm extends JFrame{
	
	// 각 플레이어의 합 0으로 초기화 
	public void init(int sum[],int Player) 
	{
		for(int i=0;i<Player;i++) 
		{
			sum[i] = 0;
		}
	}
	
	// 합 배열중에 최댓값인 인덱스를 구한다
	public int max(int sum[])  
	{
		int max=0;
		int maxNum=0;
		for(int i=0;i<sum.length;i++) 
		{
			if(max<sum[i])
			{
				max = sum[i];
				maxNum = i;
			}
		}
		return maxNum;
	}
	
	// 숫자 랜덤 배정 및 중복검사
	public int RandomCheck(JButton CardBtn[],int Player) 
	{
		int n = (int)(Math.random()*(Player*3))+1;
		for(int i=0;i<(Player*3);i++)
		{
			if((CardBtn[i].getText()).equals(Integer.toString(n))) 
			{   // 반복문 및 조건문을 통해 중복검사
				n = (int)(Math.random()*(Player*3))+1;  
				//중복시 랜덤값을 새로받고
				i=-1;  
				// 새로받은 랜덤값에 대해 다시 플레이어의 첫번째 카드부터 비교하기 위해 재설정
			}
		}
		return n;
	}
	
	// 버튼 클릭시 플레이어번호에 맞는 이미지 변경
	public void ImageChange(JButton b,int sum[],int Player,int count,int CheckedNum)  
	{
		for(int i=0;i<Player;i++) 
		{
			if(count>3*(i) && count<=3*(i+1)) //2인일때 count>0 && count<=3, count>3 && count <=6
			{	
					sum[i] += Integer.parseInt(b.getText());  
					// 결과 저장
					b.setIcon(new ImageIcon("img/PlayerIMG"+Integer.toString((i+1))+".png")); 
					// 몇번째 플레이어가 선택했는지 알수있도록 버튼 이미지 변경
					System.out.println("Player"+Integer.toString(i+1)+"이 선택한 카드의 숫자 : "+Integer.toString(CheckedNum)); 
					// 콘솔창을 통해서도 나타냄
					
			}
		}
	}
	
	// 컴퓨터 선택처리
	public void ComputerSelect(JButton CardBtn[],int count,int Player,int CheckedNum,int computer,int sum[])
	{
		if(count >= (Player*3)-3) // 남은카드가 3장일때
		{
			for(int j=0;j<CardBtn.length;j++) //카드버튼 배열의 처음부터 끝까지 확인했을때
			{
				if(CardBtn[j].getText().equals("")) // 클릭되지 않은 버튼이있다면
				{
					CheckedNum = RandomCheck(CardBtn,Player);// 숫자 랜덤 배정 및 중복검사
					CardBtn[j].setIcon(new ImageIcon("img/ComputerIMG.png")); // 컴퓨터이미지로 버튼변경
					CardBtn[j].setText(Integer.toString(CheckedNum)); // 클릭된것 처럼 버튼텍스트 변경 
					CardBtn[j].setEnabled(false); 
					System.out.println("Computer"+"가 선택한 카드의 숫자 : "+Integer.toString(CheckedNum));
					computer+=Integer.parseInt(CardBtn[j].getText()); // 컴퓨터의 결과값 계산
					count++;
				}
			}
		}
	}
	
	
}
