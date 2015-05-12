import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class Index {
	static char[][] fastestMap = new char[17][7];
	static int avg=-1, best=-1, bestIndex=-1,totalIndex=0;
	
	public static void header(){//��� �޴��� �������� �ش� ���
		System.out.println("������� �ùķ����� v1.0");
		System.out.println("----------------------------");
	}

	public static void footer(){//��� �޴��� �������� ������ ���
		System.out.println("----------------------------");
	}
	
	public static void pressEnter(){//����Ű�� ���������� �޴� �̵��� ���ߴ� �޼ҵ�
		System.out.println("�ڷ� ������ ����Ű�� ��������");
		Scanner scan = new Scanner(System.in);
		scan.nextLine();
	}
	
	public static void getFastest(){//�ְ� ����� ������ִ� �޼ҵ�
		header();
		if (best == -1){//����� ���°��
			System.out.println("����� �����ϴ�!");
		}
		else{
			for (int y=0;y<7;y++){
				for (int x=0;x<17;x++){
					System.out.print(fastestMap[x][y]);//���� ������ ���
				}
				System.out.print("\n");
			}
			System.out.println("�ְ� ����� " + best + "�� �Դϴ�.");
		}
		footer();
		pressEnter();
	}
	
	public static void getAvg(){//��� ���� ����ϴ� �޼ҵ�
		header();
		if (avg == -1){//����� ���°��
			System.out.println("����� �����ϴ�!");
		}
		else{
			System.out.println("��� ����� " + avg + "�� �Դϴ�.");
		}
		footer();
		pressEnter();
	}
	
	public static void mainMenu(){//���θ޴� ��� �޼ҵ�
		header();
		System.out.println("1.�ùķ��̼� ����");
		System.out.println("2.��� �˻�");
		System.out.println("3.����");
		footer();
	}
	
	public static void recordMenu(){//��ϸ޴� ��� �޼ҵ�
		header();
		System.out.println("1.�ְ� ��� �˻�");
		System.out.println("2.��� ��� �˻�");
		System.out.println("3.���θ޴���");
		footer();
	}
	
	public static void record(){//��ϸ޴� ó���ϴ� �޼ҵ�
		fileLoad();
		int choice = 0;
		Scanner scan = new Scanner(System.in);
		while (choice != 3){
			recordMenu();
			choice = scan.nextInt();
			switch (choice){
			case 1 : getFastest(); break; //1���� ������� �ְ����� ����Ѵ�
			case 2 : getAvg(); break; //2���� ������� �ְ����� ����Ѵ�
			default : continue; 
			}
		}
	}
	
	public static void fileLoad(){//������ �о���� �Լ�
		try {
			BufferedReader in = new BufferedReader(new FileReader("time.txt"));//time.txt�� ����
			String s;
			ArrayList<Integer> allTime = new ArrayList<Integer>();//�迭����Ʈ�� ��� �ð��� �߰��Ѵ�
			while ((s = in.readLine()) != null) {
				allTime.add(Integer.parseInt(s));
			}
			totalIndex = allTime.size();//�� ��ϵ� ��ϼ��� �����Ѵ�, ���߿� �����Ҷ� �ε����� �ο��ϱ� �����̴�.
			if (allTime.size() != 0){//����� �ִ� ���
				best = Collections.min(allTime);//�ְ����� �ּҰ��̴�
				int sum = 0;
				for (int i=0;i<allTime.size();i++){//����� ���Ѵ�
					if (allTime.get(i) == best){
						bestIndex = i;
					}
					sum += allTime.get(i);
				}
				avg = sum / allTime.size();
			}
			in.close();
			
			in = new BufferedReader(new FileReader("map.txt"));//���� ������ �д´�
			while ((s = in.readLine()) != null){
				try{
					if (Integer.parseInt(s) == bestIndex){//�ð��� �ε����� �´°��
						for (int y=0;y<7;y++){
							s = in.readLine();
							for (int x=0;x<17;x++){
								fastestMap[x][y] = s.charAt(x);//�����迭�� ����ִ´�
							}
						}
					}
					else{
						continue;
					}
				}
				catch (Exception ex){}
			}
		} 
		catch (IOException e) {
			System.err.println(e); // ������ �ִٸ� �޽��� ���
		}
	}
	
	public static void fileSave(int time, char[][] map){//���Ͽ� �����ϴ� �޼ҵ�
		try{
			BufferedWriter out = new BufferedWriter(new FileWriter("map.txt",true));//����� �ʰ� �߰��ϱ����� true
			PrintWriter fileWriter = new PrintWriter(out);
			String s = "";
			fileWriter.println(totalIndex);//�켱 �ε����� ����Ѵ�
			totalIndex++;
			for (int y=0;y<7;y++){
				for (int x=0;x<17;x++){
					s += map[x][y];//����ߴ� ���� ����Ѵ�
				}
				fileWriter.println(s);
				s = "";
			}
			fileWriter.close();
			out.close();
			out = new BufferedWriter(new FileWriter("time.txt",true));
			fileWriter = new PrintWriter(out);
			fileWriter.println(time);//�ҿ�� �ð��� ����Ѵ�
			fileWriter.close();
		}
		catch (Exception e){
			System.err.print(e); //������ �ֵ��� ����Ѵ�
		}
	}
	
	public static void main(String[] args){
		fileLoad();//��ϵ��� �ҷ��´�
		int choice = 0, time = 0;
		Simulate s = new Simulate();
		while (choice != 3){
			mainMenu();//���θ޴� ���
			Scanner scan = new Scanner(System.in);
			choice = scan.nextInt();//������ �Է¹޴´�
			switch (choice){
			case 1 : time = s.start(); fileSave(time,s.printing); break;//�ùķ��̼� ����
			case 2 : record(); break;//��ϸ޴� ȣ��
			case 3 : System.exit(0); break;//����
			default : continue;
			}
		}
	}
}
