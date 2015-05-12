import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Map extends Thing{
	
	boolean[] wall = {true, true, true, true};//벽은 항상 동서남북 순서를 따른다. true일시 벽이 있다는것
	boolean visited;//방을 방문했는지 여부
	int[][] adjacent = new int[24][24];//인접행렬
	String[] adjacentCompare = {"00","10","20","30","40","50","60","70",
								"01","11","21","31","41","51","61","71",
								"02","12","22","32","42","52","62","72"};//인접행렬노드 번호를 알기 위한 일차원배열
	
	public Map(int x, int y){
		super(x,y);
		visited = false;
	}
	
	//http://www.mazeworks.com/mazegen/mazetut/index.htm사이트에 나온 수도 코드를 구현한 지도 생성 알고리즘
	public Map[][] createMap(){
		Stack cellStack = new Stack();
		Random rand = new Random();
		Map[][] mapList = new Map[8][3];
		
		for (int y=0;y<24;y++){//인접행렬을 초기화 해준다
			for (int x=0;x<24;x++){
				if (x != y){
					adjacent[x][y] = 30000;
				}
				else{
					adjacent[x][y] = 0;
				}				
			}
		}
		
		for (int y=0;y<3;y++){//총 24개의 방을 만든다.
			for (int x=0;x<8;x++){
				mapList[x][y] = new Map(x,y);
			}
		}
		
		int totalCells = 24, visitedCells = 1;
		int randX = rand.nextInt(8), randY = rand.nextInt(3);
		while (visitedCells < totalCells){//방문한 방의 개수가 총 방의 개수랑 같아질때까지 반복한다
			int randC = 4;//열수잇는 벽의 수
			if ((randX == 0 && randY == 0) || (randX == 0 && randY == 2) || (randX == 7 && randY == 0) || (randX == 7 && randY == 2))
				randC = 2;//모서리일 경우 벽은 두개 밖에 열수 없다
			else if (randX == 0 || randY == 0)
				randC = 3;//모서리가 아니지만 벽에 붙어있으면 3개 밖에 열수 없다
			 
			ArrayList usedVal = new ArrayList();
			boolean brokeDown = false;
			for (int x=0;x<randC;x++){//열수잇는 벽의 개수만큼 반복한다
				int randW;				
				while (true){
					randW = rand.nextInt(randC);
					if (!usedVal.contains(randW)){//이미 사용되었던 난수가 다시 사용 되지 않기 위해 저장하고 확인한다
						usedVal.add(randW);
						break;
					}
				}
				
				int nextX = randX, nextY = randY;//랜덤적으로 방을 선택한다
				
				if (randX == 0 && randY == 0){//왼쪽위는 오른쪽 그리고 아래벽을 허물수있다.
					switch (randW){
					case 0 : nextX++; break;
					case 1 : nextY++; break;
					}
				}
				else if ((randX == 7 && randY == 0)){//오른쪽위는 왼쪽 그리고 아래벽을 허물수 있다.
					switch (randW){
					case 0 : nextX--; break;
					case 1 : nextY++; break;
					}
				}
				else if ((randX == 0 && randY == 2)){//왼쪽아래는 오른쪽 그리고 위에벽을 허물수 있다.
					switch (randW){
					case 0 : nextX++; break;
					case 1 : nextY--; break;
					}
				}
				else if ((randX == 7 && randY == 2)){//오른쪽아래는 왼쪽 그리고 위에 벽을 허물수 있다.
					switch (randW){
					case 0 : nextX--; break;
					case 1 : nextY--; break;
					}
				}
				else if (randX == 0){//왼쪽에 붙어있으면 오른쪽, 위쪽 그리고 아래 벽을 허물수 있다.
					switch (randW){
					case 0 : nextX++; break;
					case 1 : nextY++; break;
					case 2 : nextY--; break;
					}
				}
				else if (randX == 7){//오른쪽에 붙어있으면 왼쪽, 위쪽 그리고 아래 벽을 허물수 있다.
					switch (randW){
					case 0 : nextX--; break;
					case 1 : nextY++; break;
					case 2 : nextY--; break;
					}
				}
				else if (randY == 0){//위에 붙어있으면 왼쪽, 오른쪽 그리고 아래벽을 허물수 있다.
					switch (randW){
					case 0 : nextX++; break;
					case 1 : nextX--; break;
					case 2 : nextY++; break; 
					}
				}
				else if (randY == 2){//아래 붙어있으면 오른쪽, 왼쪽 그리고 위에 벽을 허물수 있다.
					switch (randW){
					case 0 : nextX++; break;
					case 1 : nextX--; break;
					case 2 : nextY--; break; 
					}
				}
				else{//나머지는 모든 벽을 다 허물수 있다
					switch (randW){
					case 0 : nextX++; break;
					case 1 : nextX--; break;
					case 2 : nextY++; break;
					case 3 : nextY--; break;
					}
				}
				if (mapList[nextX][nextY].visited){//이미 방문한 방인경우/벽이 허물어져있으면 다시 시작한다
					continue;
				}
				int direction = 0, newDirection = 1;//기본적으로 오른쪽으로 움직엿을때
				//newDirection이면 direction의 반대 방향을 가르키게 된다
				if (randX - nextX == 1){//왼쪽으로 움직였을때
					direction = 1;
					newDirection = 0;
				}
				else if (randY - nextY == -1){//아래로 움직였을때
					direction = 2;
					newDirection = 3;
				}
				else if (randY - nextY == 1){//위로 움직였을때
					direction = 3;
					newDirection = 2;
				}
				mapList[randX][randY].visited = true;//현재 방과 다음 방을 방문했다는 표시를한다.
				mapList[nextX][nextY].visited = true;
				mapList[randX][randY].wall[direction] = false;//방사이에 있는 벽을 허문다
				mapList[nextX][nextY].wall[newDirection] = false;
				brokeDown = true;
				cellStack.push(mapList[randX][randY]);//현위치를 스택에 저장해놓은다
				visitedCells++;//방문한 방의 개수를 늘린다
				
				
				int a1=-1,a2=-1;//인접행렬을 사용하여 현위치와 다음방이 연결되 있음을 기록해놓은다
				for (int i=0;i<adjacentCompare.length;i++){
					if (adjacentCompare[i].equals(String.valueOf(randX) + String.valueOf(randY))){
						a1 = i;
					}
					if (adjacentCompare[i].equals(String.valueOf(nextX) + String.valueOf(nextY))){
						a2 = i;
					}
					if (a1 != -1 && a2 != -1){
						break;
					}
				}
				adjacent[a1][a2] = 1;//양방향으로 갈수 있다.
				adjacent[a2][a1] = 1;
				randX = nextX;
				randY = nextY;
				break;
			}
			if (!brokeDown){//벽이 허물어 지지않았으면
				Map temp = (Map) cellStack.pop();//스택에 있는 마지막으로 방문햇던 방으로 돌아간다
				randX = temp.x;
				randY = temp.y;
			}
		}
		for (int i=0;i<5;i++){			
			x = rand.nextInt(6) + 1;
			y = 1;
			int nextX = x, nextY = y;
			int randC = rand.nextInt(4);
			
			switch (randC){
			case 0 : nextX++; break;
			case 1 : nextX--; break;
			case 2 : nextY++; break;
			case 3 : nextY--; break;
			}
			
			int direction = 0, newDirection = 1;//default moved right
			if (randX - nextX == 1){//moved left
				direction = 1;
				newDirection = 0;
			}
			else if (randY - nextY == -1){//moved down
				direction = 2;
				newDirection = 3;
			}
			else if (randY - nextY == 1){//moved up
				direction = 3;
				newDirection = 2;
			}
			
			mapList[randX][randY].wall[direction] = false;
			mapList[nextX][nextY].wall[newDirection] = false;
			
			int a1=-1,a2=-1;//calculating adjacent matrix
			for (int a=0;a<adjacentCompare.length;i++){
				if (adjacentCompare[i].equals(String.valueOf(randX) + String.valueOf(randY))){
					a1 = a;
				}
				if (adjacentCompare[i].equals(String.valueOf(nextX) + String.valueOf(nextY))){
					a2 = a;
				}
				if (a1 != -1 && a2 != -1){
					break;
				}
			}
			adjacent[a1][a2] = 1;
			adjacent[a2][a1] = 1;
		}
		return mapList;
	}
}