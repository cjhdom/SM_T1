import java.util.Random;


public class Thief extends Person implements MoveFunc{
	
	public void move(String[] name){//MoveFunc �������̽��� implement�Ѵ�
		Random rand = new Random();//���� ���� Ŭ����
		
		while (true){
			int newX = x, newY = y, randNum = rand.nextInt(4), wallNum = 0;//���� ����
			for (int i=0;i<4;i++){
				if (name[i].equals("t"))//���ڷ� ���� ���ڿ� �迭 ���� t�ϰ�� ���� �ִٶ�� ��.
					wallNum++;
			}
			switch (randNum){//�������� ������ ������ ��쿡 ���� ���� ��ǥ�� ����Ѵ�
			case 0 : newX++; break;
			case 1 : newX--; break;
			case 2 : newY++; break;
			case 3 : newY--; break;
			}
			
			if (name[randNum].equals("t")){
				if (wallNum >= 3){//�ܱ濡�� ���� ������� �ٽ� ���ư��� �ֵ��� lastX�� lastY�� �����Ѵ�
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
				if (newX == lastX && newY == lastY){//���ο� �������� �ڷ� ���ư��°� �ܱ��� �ƴϸ� �ٽ� ����Ѵ�
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
