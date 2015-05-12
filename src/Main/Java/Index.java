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
	
	public static void header(){//모든 메뉴의 공통적인 해더 출력
		System.out.println("도둑잡기 시뮬레이터 v1.0");
		System.out.println("----------------------------");
	}

	public static void footer(){//모든 메뉴의 공통적인 꼬릿말 출력
		System.out.println("----------------------------");
	}
	
	public static void pressEnter(){//엔터키를 누를때까지 메뉴 이동을 멈추는 메소드
		System.out.println("뒤로 가려면 엔터키를 누르세요");
		Scanner scan = new Scanner(System.in);
		scan.nextLine();
	}
	
	public static void getFastest(){//최고 기록을 출력해주는 메소드
		header();
		if (best == -1){//기록이 없는경우
			System.out.println("기록이 없습니다!");
		}
		else{
			for (int y=0;y<7;y++){
				for (int x=0;x<17;x++){
					System.out.print(fastestMap[x][y]);//먼저 지도를 출력
				}
				System.out.print("\n");
			}
			System.out.println("최고 기록은 " + best + "초 입니다.");
		}
		footer();
		pressEnter();
	}
	
	public static void getAvg(){//평균 값을 출력하는 메소드
		header();
		if (avg == -1){//기록이 없는경우
			System.out.println("기록이 없습니다!");
		}
		else{
			System.out.println("평균 기록은 " + avg + "초 입니다.");
		}
		footer();
		pressEnter();
	}
	
	public static void mainMenu(){//메인메뉴 출력 메소드
		header();
		System.out.println("1.시뮬레이션 시작");
		System.out.println("2.기록 검색");
		System.out.println("3.종료");
		footer();
	}
	
	public static void recordMenu(){//기록메뉴 출력 메소드
		header();
		System.out.println("1.최고 기록 검색");
		System.out.println("2.평균 기록 검색");
		System.out.println("3.메인메뉴로");
		footer();
	}
	
	public static void record(){//기록메뉴 처리하는 메소드
		fileLoad();
		int choice = 0;
		Scanner scan = new Scanner(System.in);
		while (choice != 3){
			recordMenu();
			choice = scan.nextInt();
			switch (choice){
			case 1 : getFastest(); break; //1번을 누른경우 최고기록을 출력한다
			case 2 : getAvg(); break; //2번을 누른경우 최고기록을 출력한다
			default : continue; 
			}
		}
	}
	
	public static void fileLoad(){//파일을 읽어오는 함수
		try {
			BufferedReader in = new BufferedReader(new FileReader("time.txt"));//time.txt을 연다
			String s;
			ArrayList<Integer> allTime = new ArrayList<Integer>();//배열리스트에 모든 시간을 추가한다
			while ((s = in.readLine()) != null) {
				allTime.add(Integer.parseInt(s));
			}
			totalIndex = allTime.size();//총 기록된 기록수를 저장한다, 나중에 저장할때 인덱스를 부여하기 위함이다.
			if (allTime.size() != 0){//기록이 있는 경우
				best = Collections.min(allTime);//최고기록은 최소값이다
				int sum = 0;
				for (int i=0;i<allTime.size();i++){//평균을 구한다
					if (allTime.get(i) == best){
						bestIndex = i;
					}
					sum += allTime.get(i);
				}
				avg = sum / allTime.size();
			}
			in.close();
			
			in = new BufferedReader(new FileReader("map.txt"));//지도 파일을 읽는다
			while ((s = in.readLine()) != null){
				try{
					if (Integer.parseInt(s) == bestIndex){//시간과 인덱스가 맞는경우
						for (int y=0;y<7;y++){
							s = in.readLine();
							for (int x=0;x<17;x++){
								fastestMap[x][y] = s.charAt(x);//지도배열에 집어넣는다
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
			System.err.println(e); // 에러가 있다면 메시지 출력
		}
	}
	
	public static void fileSave(int time, char[][] map){//파일에 저장하는 메소드
		try{
			BufferedWriter out = new BufferedWriter(new FileWriter("map.txt",true));//덮어쓰지 않고 추가하기위한 true
			PrintWriter fileWriter = new PrintWriter(out);
			String s = "";
			fileWriter.println(totalIndex);//우선 인덱스를 출력한다
			totalIndex++;
			for (int y=0;y<7;y++){
				for (int x=0;x<17;x++){
					s += map[x][y];//사용했던 맵을 기록한다
				}
				fileWriter.println(s);
				s = "";
			}
			fileWriter.close();
			out.close();
			out = new BufferedWriter(new FileWriter("time.txt",true));
			fileWriter = new PrintWriter(out);
			fileWriter.println(time);//소요된 시간을 기록한다
			fileWriter.close();
		}
		catch (Exception e){
			System.err.print(e); //오류가 있따면 출력한다
		}
	}
	
	public static void main(String[] args){
		fileLoad();//기록들을 불러온다
		int choice = 0, time = 0;
		Simulate s = new Simulate();
		while (choice != 3){
			mainMenu();//메인메뉴 출력
			Scanner scan = new Scanner(System.in);
			choice = scan.nextInt();//선택을 입력받는다
			switch (choice){
			case 1 : time = s.start(); fileSave(time,s.printing); break;//시뮬레이션 시작
			case 2 : record(); break;//기록메뉴 호출
			case 3 : System.exit(0); break;//종료
			default : continue;
			}
		}
	}
}
