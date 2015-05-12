import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Map extends Thing{
	
	boolean[] wall = {true, true, true, true};//���� �׻� �������� ������ ������. true�Ͻ� ���� �ִٴ°�
	boolean visited;//���� �湮�ߴ��� ����
	int[][] adjacent = new int[24][24];//�������
	String[] adjacentCompare = {"00","10","20","30","40","50","60","70",
								"01","11","21","31","41","51","61","71",
								"02","12","22","32","42","52","62","72"};//������ĳ�� ��ȣ�� �˱� ���� �������迭
	
	public Map(int x, int y){
		super(x,y);
		visited = false;
	}
	
	//http://www.mazeworks.com/mazegen/mazetut/index.htm����Ʈ�� ���� ���� �ڵ带 ������ ���� ���� �˰���
	public Map[][] createMap(){
		Stack cellStack = new Stack();
		Random rand = new Random();
		Map[][] mapList = new Map[8][3];
		
		for (int y=0;y<24;y++){//��������� �ʱ�ȭ ���ش�
			for (int x=0;x<24;x++){
				if (x != y){
					adjacent[x][y] = 30000;
				}
				else{
					adjacent[x][y] = 0;
				}				
			}
		}
		
		for (int y=0;y<3;y++){//�� 24���� ���� �����.
			for (int x=0;x<8;x++){
				mapList[x][y] = new Map(x,y);
			}
		}
		
		int totalCells = 24, visitedCells = 1;
		int randX = rand.nextInt(8), randY = rand.nextInt(3);
		while (visitedCells < totalCells){//�湮�� ���� ������ �� ���� ������ ������������ �ݺ��Ѵ�
			int randC = 4;//�����մ� ���� ��
			if ((randX == 0 && randY == 0) || (randX == 0 && randY == 2) || (randX == 7 && randY == 0) || (randX == 7 && randY == 2))
				randC = 2;//�𼭸��� ��� ���� �ΰ� �ۿ� ���� ����
			else if (randX == 0 || randY == 0)
				randC = 3;//�𼭸��� �ƴ����� ���� �پ������� 3�� �ۿ� ���� ����
			 
			ArrayList usedVal = new ArrayList();
			boolean brokeDown = false;
			for (int x=0;x<randC;x++){//�����մ� ���� ������ŭ �ݺ��Ѵ�
				int randW;				
				while (true){
					randW = rand.nextInt(randC);
					if (!usedVal.contains(randW)){//�̹� ���Ǿ��� ������ �ٽ� ��� ���� �ʱ� ���� �����ϰ� Ȯ���Ѵ�
						usedVal.add(randW);
						break;
					}
				}
				
				int nextX = randX, nextY = randY;//���������� ���� �����Ѵ�
				
				if (randX == 0 && randY == 0){//�������� ������ �׸��� �Ʒ����� �㹰���ִ�.
					switch (randW){
					case 0 : nextX++; break;
					case 1 : nextY++; break;
					}
				}
				else if ((randX == 7 && randY == 0)){//���������� ���� �׸��� �Ʒ����� �㹰�� �ִ�.
					switch (randW){
					case 0 : nextX--; break;
					case 1 : nextY++; break;
					}
				}
				else if ((randX == 0 && randY == 2)){//���ʾƷ��� ������ �׸��� �������� �㹰�� �ִ�.
					switch (randW){
					case 0 : nextX++; break;
					case 1 : nextY--; break;
					}
				}
				else if ((randX == 7 && randY == 2)){//�����ʾƷ��� ���� �׸��� ���� ���� �㹰�� �ִ�.
					switch (randW){
					case 0 : nextX--; break;
					case 1 : nextY--; break;
					}
				}
				else if (randX == 0){//���ʿ� �پ������� ������, ���� �׸��� �Ʒ� ���� �㹰�� �ִ�.
					switch (randW){
					case 0 : nextX++; break;
					case 1 : nextY++; break;
					case 2 : nextY--; break;
					}
				}
				else if (randX == 7){//�����ʿ� �پ������� ����, ���� �׸��� �Ʒ� ���� �㹰�� �ִ�.
					switch (randW){
					case 0 : nextX--; break;
					case 1 : nextY++; break;
					case 2 : nextY--; break;
					}
				}
				else if (randY == 0){//���� �پ������� ����, ������ �׸��� �Ʒ����� �㹰�� �ִ�.
					switch (randW){
					case 0 : nextX++; break;
					case 1 : nextX--; break;
					case 2 : nextY++; break; 
					}
				}
				else if (randY == 2){//�Ʒ� �پ������� ������, ���� �׸��� ���� ���� �㹰�� �ִ�.
					switch (randW){
					case 0 : nextX++; break;
					case 1 : nextX--; break;
					case 2 : nextY--; break; 
					}
				}
				else{//�������� ��� ���� �� �㹰�� �ִ�
					switch (randW){
					case 0 : nextX++; break;
					case 1 : nextX--; break;
					case 2 : nextY++; break;
					case 3 : nextY--; break;
					}
				}
				if (mapList[nextX][nextY].visited){//�̹� �湮�� ���ΰ��/���� �㹰���������� �ٽ� �����Ѵ�
					continue;
				}
				int direction = 0, newDirection = 1;//�⺻������ ���������� ����������
				//newDirection�̸� direction�� �ݴ� ������ ����Ű�� �ȴ�
				if (randX - nextX == 1){//�������� ����������
					direction = 1;
					newDirection = 0;
				}
				else if (randY - nextY == -1){//�Ʒ��� ����������
					direction = 2;
					newDirection = 3;
				}
				else if (randY - nextY == 1){//���� ����������
					direction = 3;
					newDirection = 2;
				}
				mapList[randX][randY].visited = true;//���� ��� ���� ���� �湮�ߴٴ� ǥ�ø��Ѵ�.
				mapList[nextX][nextY].visited = true;
				mapList[randX][randY].wall[direction] = false;//����̿� �ִ� ���� �㹮��
				mapList[nextX][nextY].wall[newDirection] = false;
				brokeDown = true;
				cellStack.push(mapList[randX][randY]);//����ġ�� ���ÿ� �����س�����
				visitedCells++;//�湮�� ���� ������ �ø���
				
				
				int a1=-1,a2=-1;//��������� ����Ͽ� ����ġ�� �������� ����� ������ ����س�����
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
				adjacent[a1][a2] = 1;//��������� ���� �ִ�.
				adjacent[a2][a1] = 1;
				randX = nextX;
				randY = nextY;
				break;
			}
			if (!brokeDown){//���� �㹰�� �����ʾ�����
				Map temp = (Map) cellStack.pop();//���ÿ� �ִ� ���������� �湮�޴� ������ ���ư���
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