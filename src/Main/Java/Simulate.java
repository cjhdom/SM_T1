import java.util.ArrayList;

public class Simulate {
	Map[][] newMap;
	ArrayList<Obstacle> obList = new ArrayList<Obstacle>();
	char[][] printing;
	Map myMap;
	Thief t;
	Police p;
	Thing currentNode;
	
	public Simulate(){ 
		
	}
	
	public void init(){//모든 변수및 클래스를 초기화한다
		for (int y = 0;y<7;y++){
			for (int x = 0;x<17;x++){
				if (printing[x][y] == '#'){
					obList.add(new Obstacle(x,y));
				}
			}
		}
		t = new Thief(1,1);
		p = new Police(15,5);
		currentNode = new Thing(7,2);
	}
	
	public boolean checkCaught(){//잡혔는지 확인하는 메소드
		if ((Math.abs(t.x - p.x) == 1 && Math.abs(t.y - p.y) == 0) || (Math.abs(t.x - p.x) == 0 && Math.abs(t.y - p.y) == 1) || (t.x-p.x == 0 && t.y-p.y == 0)){
			//x좌표가 차이가 1나고 y좌표가 0나거나 그반대거나 x,y좌표가 동일한경우 잡혔다고 판단한다.
			return false;
		}
		else{
			return true;
		}
	}
	
	public int start(){//시뮬레이션을 시작하는 메소드
		loadMap();//새로운 지도를 생성한다
		init();//필요한 클래스를 초기화한다
		int s, e;
		long eTime = 0,sTime = System.currentTimeMillis()/1000;//시작한시간을 구한다
		printMap();//지도를 출력한다
		while (checkCaught()){//잡히지 않은경우 반복
			s = -1;
			e = -1;
			for (int i=0;i<24;i++){//최단거리를 구하기 위해 현위치의 방 좌표를 구한다
				if (myMap.adjacentCompare[i].equals(String.valueOf(((t.x - 1) / 2)) + String.valueOf(((t.y - 1) / 2)))){
					s = i;
				}
				if (((p.x - 1) % 2) == 0 && ((p.y - 1) % 2) == 0){
					if (myMap.adjacentCompare[i].equals(String.valueOf(((p.x - 1) / 2)) + String.valueOf(((p.y - 1) / 2)))){
						e = i;
					}
				}
			}
	
			MoveFunc m;//polymorphorism
			m = p;
			if (e == -1){//방에 없는 경우
				m.move(null);
			}
			else{
				m.move(shortest(s,e));//최단거리에서 다음 방위치를 인자로 보낸다
			}
			if (!checkCaught()){//잡힌경우 마지막으로 출력하고 반복문을 종료한다
				printMap();
				break;
			}
			currentNode.x = ((p.x - 1) / 2);//현재 방위치를 갱신한다
			currentNode.y = ((p.y - 1) / 2);
			
			String[] thiefString = {"f","f","f","f"};//도둑이 벽으로 움직이지 않도록 인자값으로 현위치에서의 벽위치를 알려준다
			if (printing[t.x+1][t.y] == '#'){//오른쪽에 벽이있는 경우
				thiefString[0] = "t";
			}
			if (printing[t.x-1][t.y] == '#'){//왼쪽에 벽이 있는 경우
				thiefString[1] = "t";
			}
			if (printing[t.x][t.y+1] == '#'){//아래에 벽이 있는 경우
				thiefString[2] = "t";
			}
			if (printing[t.x][t.y-1] == '#'){//위에 벽이 있는경우
				thiefString[3] = "t";
			}
			m=t;
			m.move(thiefString);
			
			printMap();
		}
		eTime = System.currentTimeMillis()/1000;//잡고 난 이후에 시간을 기록한뒤
		System.out.println("총 소요시간은 " + (int)(eTime - sTime) + "초 입니다.");
		return (int)(eTime - sTime);//총 소요 시간을 계산한다
	}
	
	public String[] shortest(int s, int e){//최단거리 알고리즘 : 인접배열을 사용하는 다익스트라 알고리즘 구현
		int i, j, k = 0, min;
		int[] v = new int[24], distance = new int[24], index = new int[24];
		int n=24, m=30000;
		
		for(j=0; j<n; j++){
			v[j] = 0;
			distance[j] = m;
		}
		distance[s] = 0;
		index[s] = 0;
	
		for(i=0; i<n; i++){
			min = m;
			for(j=0; j<n; j++){
				if(v[j] == 0 && distance[j] < min){
					k = j;
					min = distance[j];
				}
			}
	
			v[k] = 1;
	
			if (min == m)
				break;
	
			for(j=0; j<n; j++){//최단  경로의 거리를 구하여서 최단 경로를 판단한다
				if(distance[j] > distance[k] + myMap.adjacent[k][j]){
					distance[j] = distance[k] + myMap.adjacent[k][j];
					index[j]=k;
				}
			}
		}
		String[] retString = new String[2];
		for(j=0;j<24;j++){//최단 경로를 구한다
			int l=j;
			if (!myMap.adjacentCompare[j].equals(String.valueOf(currentNode.x) + String.valueOf(currentNode.y)))
				continue;
			retString[0] = String.valueOf(currentNode.x) + String.valueOf(currentNode.y);
			retString[1] = myMap.adjacentCompare[index[l]];
		} 
    	return retString;//최단경로의 맨처음 두개의 노드 위치를 리턴한다
	}

	public void printMap(){
		try{
			Thread.sleep(500);//출력하기전에 0.5초 쉰다
		}
		catch (Exception ex){}
		
		for (int x=0;x<80;x++){//자바에서 콘솔창을 지울수 없으므로 80줄의 빈줄을 출력한다
			System.out.print("\n");
		}
		
		for (int y = 0;y<7;y++){
			for (int x = 0;x<17;x++){
				if (t.x == x && t.y == y){//현위치에 도둑이있는경우
					System.out.print("T");
					continue;
				}
				else if (p.x == x && p.y == y){//현위치에 경찰이 있는 경우
					System.out.print("P");
					continue;
				}
				else{//아니면 지도를 출력
					System.out.print(printing[x][y]);
				}
			}
			System.out.print("\n");
		}
	}

	public void loadMap(){//새로운 지도를 불러온다
		myMap = new Map(0,0);
		newMap = myMap.createMap();
		while (newMap == null){
			newMap = myMap.createMap();
		}
		printing = new char[17][7];
		
		for (int x=0;x<17;x++){
			for (int y=0;y<7;y++){
				if ((x == 0 || x == 16 || y == 0 || y == 6) || (x % 2 == 0 && y % 2 == 0)){
					printing[x][y] = '#';
				}
				else{
					printing[x][y] = ' ';
				}
			}
		}
		for (int x = 0;x<8;x++){
			for (int y = 0;y<3;y++){
				if (newMap[x][y].wall[0]){//right wall
					printing[(x + 1) * 2][(y * 2) + 1] = '#';
				}
				if (newMap[x][y].wall[1]){//left wall
					printing[x * 2][(y * 2) + 1] = '#';
				}
				if (newMap[x][y].wall[2]){//lower wall
					printing[(x * 2) + 1][(y + 1) * 2] = '#';
				}
				if (newMap[x][y].wall[3]){//upper wall
					printing[(x * 2) + 1][y * 2] = '#';
				}
			}
		}
	}
}
