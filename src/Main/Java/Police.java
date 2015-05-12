import java.util.ArrayList;


public class Police extends Person implements MoveFunc{

	public void move(String[] name){
		if (name != null){//Police�� ���(��)�ȿ� ������
			//������ ��� �̸����� x,y��ǥ�� �о�´�
			int beginX = Integer.parseInt(name[0].substring(0, 1));
			int beginY = Integer.parseInt(name[0].substring(1, 2));
			int targetX = Integer.parseInt(name[1].substring(0, 1));
			int targetY = Integer.parseInt(name[1].substring(1, 2));
			int nextX = targetX - beginX, nextY = targetY - beginY;
			this.lastX = this.x;
			this.lastY = this.y;
			this.x += nextX;
			this.y += nextY;
		}
		else{//���ٸ�
			int nextX = this.x - this.lastX, nextY = this.y - this.lastY;//���� �־��� �� �ݴ�� �����δ�
			this.lastX = this.x;
			this.lastY = this.y;
			this.x += nextX;
			this.y += nextY;
		}
	}
	
	public Police(int x,int y){
		super(x,y);
		this.lastX = x;
		this.lastY = y;
	}
}
