import java.util.ArrayList;


public class Police extends Person implements MoveFunc{

	public void move(String[] name){
		if (name != null){//Police가 노드(방)안에 있으면
			//인자의 노드 이름에서 x,y좌표를 읽어온다
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
		else{//없다면
			int nextX = this.x - this.lastX, nextY = this.y - this.lastY;//전에 있엇던 곳 반대로 움직인다
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
