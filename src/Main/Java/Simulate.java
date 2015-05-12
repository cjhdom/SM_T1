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
	
	public void init(){//��� ������ Ŭ������ �ʱ�ȭ�Ѵ�
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
	
	public boolean checkCaught(){//�������� Ȯ���ϴ� �޼ҵ�
		if ((Math.abs(t.x - p.x) == 1 && Math.abs(t.y - p.y) == 0) || (Math.abs(t.x - p.x) == 0 && Math.abs(t.y - p.y) == 1) || (t.x-p.x == 0 && t.y-p.y == 0)){
			//x��ǥ�� ���̰� 1���� y��ǥ�� 0���ų� �׹ݴ�ų� x,y��ǥ�� �����Ѱ�� �����ٰ� �Ǵ��Ѵ�.
			return false;
		}
		else{
			return true;
		}
	}
	
	public int start(){//�ùķ��̼��� �����ϴ� �޼ҵ�
		loadMap();//���ο� ������ �����Ѵ�
		init();//�ʿ��� Ŭ������ �ʱ�ȭ�Ѵ�
		int s, e;
		long eTime = 0,sTime = System.currentTimeMillis()/1000;//�����ѽð��� ���Ѵ�
		printMap();//������ ����Ѵ�
		while (checkCaught()){//������ ������� �ݺ�
			s = -1;
			e = -1;
			for (int i=0;i<24;i++){//�ִܰŸ��� ���ϱ� ���� ����ġ�� �� ��ǥ�� ���Ѵ�
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
			if (e == -1){//�濡 ���� ���
				m.move(null);
			}
			else{
				m.move(shortest(s,e));//�ִܰŸ����� ���� ����ġ�� ���ڷ� ������
			}
			if (!checkCaught()){//������� ���������� ����ϰ� �ݺ����� �����Ѵ�
				printMap();
				break;
			}
			currentNode.x = ((p.x - 1) / 2);//���� ����ġ�� �����Ѵ�
			currentNode.y = ((p.y - 1) / 2);
			
			String[] thiefString = {"f","f","f","f"};//������ ������ �������� �ʵ��� ���ڰ����� ����ġ������ ����ġ�� �˷��ش�
			if (printing[t.x+1][t.y] == '#'){//�����ʿ� �����ִ� ���
				thiefString[0] = "t";
			}
			if (printing[t.x-1][t.y] == '#'){//���ʿ� ���� �ִ� ���
				thiefString[1] = "t";
			}
			if (printing[t.x][t.y+1] == '#'){//�Ʒ��� ���� �ִ� ���
				thiefString[2] = "t";
			}
			if (printing[t.x][t.y-1] == '#'){//���� ���� �ִ°��
				thiefString[3] = "t";
			}
			m=t;
			m.move(thiefString);
			
			printMap();
		}
		eTime = System.currentTimeMillis()/1000;//��� �� ���Ŀ� �ð��� ����ѵ�
		System.out.println("�� �ҿ�ð��� " + (int)(eTime - sTime) + "�� �Դϴ�.");
		return (int)(eTime - sTime);//�� �ҿ� �ð��� ����Ѵ�
	}
	
	public String[] shortest(int s, int e){//�ִܰŸ� �˰��� : �����迭�� ����ϴ� ���ͽ�Ʈ�� �˰��� ����
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
	
			for(j=0; j<n; j++){//�ִ�  ����� �Ÿ��� ���Ͽ��� �ִ� ��θ� �Ǵ��Ѵ�
				if(distance[j] > distance[k] + myMap.adjacent[k][j]){
					distance[j] = distance[k] + myMap.adjacent[k][j];
					index[j]=k;
				}
			}
		}
		String[] retString = new String[2];
		for(j=0;j<24;j++){//�ִ� ��θ� ���Ѵ�
			int l=j;
			if (!myMap.adjacentCompare[j].equals(String.valueOf(currentNode.x) + String.valueOf(currentNode.y)))
				continue;
			retString[0] = String.valueOf(currentNode.x) + String.valueOf(currentNode.y);
			retString[1] = myMap.adjacentCompare[index[l]];
		} 
    	return retString;//�ִܰ���� ��ó�� �ΰ��� ��� ��ġ�� �����Ѵ�
	}

	public void printMap(){
		try{
			Thread.sleep(500);//����ϱ����� 0.5�� ����
		}
		catch (Exception ex){}
		
		for (int x=0;x<80;x++){//�ڹٿ��� �ܼ�â�� ����� �����Ƿ� 80���� ������ ����Ѵ�
			System.out.print("\n");
		}
		
		for (int y = 0;y<7;y++){
			for (int x = 0;x<17;x++){
				if (t.x == x && t.y == y){//����ġ�� �������ִ°��
					System.out.print("T");
					continue;
				}
				else if (p.x == x && p.y == y){//����ġ�� ������ �ִ� ���
					System.out.print("P");
					continue;
				}
				else{//�ƴϸ� ������ ���
					System.out.print(printing[x][y]);
				}
			}
			System.out.print("\n");
		}
	}

	public void loadMap(){//���ο� ������ �ҷ��´�
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
