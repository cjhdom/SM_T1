import java.util.Random;


public class Thief extends Person implements MoveFunc{
	
	public void move(String[] name){//MoveFunc 인터페이스를 implement한다
		Random rand = new Random();//난수 생성 클래스
		
		while (true){
			int newX = x, newY = y, randNum = rand.nextInt(4), wallNum = 0;//벽의 개수
			for (int i=0;i<4;i++){
				if (name[i].equals("t"))//인자로 받은 문자열 배열 값이 t일경우 벽이 있다라는 뜻.
					wallNum++;
			}
			switch (randNum){//랜덤으로 생성된 숫자의 경우에 따라 다음 좌표를 계산한다
			case 0 : newX++; break;
			case 1 : newX--; break;
			case 2 : newY++; break;
			case 3 : newY--; break;
			}
			
			if (name[randNum].equals("t")){
				if (wallNum >= 3){//외길에서 벽을 만난경우 다시 돌아갈수 있도록 lastX와 lastY를 수정한다
					int tempX = x, tempY = y;
					switch (randNum){
					case 0 : tempX--; break;
					case 1 : tempX++; break;
					case 2 : tempY--; break;
					case 3 : tempY++; break;
					}
					if (tempX == lastX && tempY == lastY){
						newX = lastX;
						newY = lastY;
						lastX = -1;
						lastY = -1;
					}
					else{
						continue;
					}
				}
				else{
					continue;
				}
			}
			else{
				if (newX == lastX && newY == lastY){//새로운 움직임이 뒤로 돌아가는고 외길이 아니면 다시 계산한다
					continue;
				}
			}
			
			lastX = x;
			lastY = y;
			x = newX;
			y = newY;
			break;
		}
	}
	
	public Thief(int x,int y){
		super(x,y);
		this.lastX = x;
		this.lastY = y;
	}
}
