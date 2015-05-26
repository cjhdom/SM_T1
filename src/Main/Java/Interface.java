import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;


import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.Font;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;












import javax.swing.table.*;




public class Interface extends JFrame{

	private JPanel contentPane;
	private JTextArea msgTA;
	private JButton btnSend;
	
	private JButton selectBtn1;
	private JButton selectBtn2;
	private JButton selectBtn3;
	private JButton selectBtn4;
	
	private JButton button;
	private JButton button_1;
	public int tableCnt;
	private Parent parent;
	private Child child;
	private JTextField keyListen;
	private JPanel childPane;
	private JPanel parentPane;
	private JLabel childBG;
	private JLabel parentBG;
	private JLabel backGround;
	private JTable wordList;
	private JTextField searchTF;
	private JLabel picResult;
	private JLabel engResult;
	private JLabel korResult;
	private JLabel meanMsgLB;
	private JLabel wordMsgLB;
	private JLabel wordMsgImage;
	private JTextArea comMsgTA;
	private JTextArea userMsgTA;
	private JTextArea comMsgTA2;
	private JButton soundBtn2;
	private JButton searchBtn;
	private JButton btnDelete;
	private JButton btnAdd;
	private String[][] newTable;
	
	private Word word;
	private Message msg;
	private int flag;
	
	final private int screenWidth;
	final private int screenHeight;
	
	
	Database db;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					String resultStr = null;
					resultStr = JOptionPane.showInputDialog("ip 입력 : 203.252.160.80");
					final Interface frame = new Interface(resultStr);
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public Interface(String ip) {
		/*
		 * 초기화 부분은 문서 작성할 필요 없음
		 */
		db = new Database();
		if(!db.init(ip)){
			displayError("DB 접속 에러");
			System.exit(0);
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		child = new Child(db);
		parent = new Parent(db);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		screenWidth= screenSize.width;
		screenHeight = screenSize.height-30;
		displayMain();
	}
	
	public void displayMain(){
		setIconImage(Toolkit.getDefaultToolkit().getImage("icon\\titleIcon.png"));
		setTitle("[대화형]영어교육 프로그램_★★★Talkids★★★");
		setResizable(false);
		
		setBounds(0, 0, screenWidth, screenHeight);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		backGround = new JLabel(new ImageIcon("icon\\mainBG.png"));
		backGround.setBounds(0, 0, screenWidth, screenHeight);
		contentPane.add(backGround);
		
		

		JLabel lblParent = new JLabel(new ImageIcon("icon\\pressP.png"));
		lblParent.setBounds(screenWidth/2-220, screenHeight/2+160, 180, 50);
		backGround.add(lblParent);
		
		JLabel lblChild = new JLabel(new ImageIcon("icon\\pressC.png"));
		lblChild.setBounds(screenWidth/2+40, screenHeight/2+160, 180, 50);
		backGround.add(lblChild);
		/*
		 * 부모모드 버튼 클릭 시
		 */
		BufferedImage buttonIcon1 = null;
		try {
			buttonIcon1 = ImageIO.read(new File("icon\\parent.png"));
		} catch (IOException e1) {
			displayError("UI에러!");
		}
		button = new JButton(new ImageIcon(buttonIcon1));
		button.setBorder(BorderFactory.createEmptyBorder());
		button.setContentAreaFilled(false);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayPM();
			}
		});			
		button.setBounds(screenWidth/2-280, screenHeight/2-155, 230, 301);
		backGround.add(button);
		
		/*
		 * 아이모드 버튼 클릭 시
		 */
		BufferedImage buttonIcon2 = null;
		try {
			buttonIcon2 = ImageIO.read(new File("icon\\child.png"));
		} catch (IOException e1) {
			displayError("UI에러!");
		}
		button_1 = new JButton(new ImageIcon(buttonIcon2));
		button_1.setBorder(BorderFactory.createEmptyBorder());
		button_1.setContentAreaFilled(false);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayCM();
			}
		});		
		button_1.setBounds(screenWidth/2+50, screenHeight/2-100, 132, 246);
		backGround.add(button_1);
		
		/*
		 * 메인모드 키보드 input 받는 부분
		 * C : 아이모드 호출
		 * P : 부모모드 호출
		 */
		
		keyListen = new JTextField();
		keyListen.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(e.getKeyCode()==KeyEvent.VK_C){
					displayCM();
				}
				else if(e.getKeyCode()==KeyEvent.VK_P){
					displayPM();
				}
			}
		});
		keyListen.setBounds(0, 0, 0, 0);
		backGround.add(keyListen);
		keyListen.setColumns(10);
		keyListen.requestFocus();
		backGround.updateUI();
	}
	
	public void displayCM(){
		setBounds(0, 0, screenWidth, screenHeight);
		childPane = new JPanel();
		childPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(childPane);
		childPane.setLayout(null);
		
		childBG = new JLabel(new ImageIcon("icon\\childBG.png"));
		childBG.setBounds(0, 0, screenWidth, screenHeight);
		childPane.add(childBG);
		
		selectBtn1 = new JButton("");
		selectBtn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				msgTA.append(selectBtn1.getText());
				keyListen.requestFocus();
			}
		});
		selectBtn1.setBounds(screenWidth/5, screenHeight/5*4, 80, 23);
		childBG.add(selectBtn1);
		
		selectBtn2 = new JButton("");
		selectBtn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				msgTA.append(selectBtn2.getText());
				keyListen.requestFocus();
			}
		});
		selectBtn2.setBounds(screenWidth/5+82, screenHeight/5*4, 80, 23);
		childBG.add(selectBtn2);
		
		selectBtn3 = new JButton("");
		selectBtn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				msgTA.append(selectBtn3.getText());
				keyListen.requestFocus();
			}
		});
		selectBtn3.setBounds(screenWidth/5+164, screenHeight/5*4, 80, 23);
		childBG.add(selectBtn3);
		
		selectBtn4 = new JButton("");
		selectBtn4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				msgTA.append(selectBtn4.getText());
				keyListen.requestFocus();
			}
		});
		selectBtn4.setBounds(screenWidth/5+246, screenHeight/5*4, 80, 23);
		childBG.add(selectBtn4);
		///////////////////////////////////////////////////////////////
		JLabel comMsgLB = new JLabel(new ImageIcon("icon\\comMsg.png"));
		comMsgLB.setBounds(screenWidth/5, screenHeight/5, 350, 60);
		childBG.add(comMsgLB);
		
		comMsgTA = new JTextArea("");
		comMsgTA.setEditable(false);
		comMsgTA.setBounds(30, 6, 290, 48);
		comMsgTA.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		comMsgLB.add(comMsgTA);
		///////////////////////////////////////////////////////////////
		JLabel userMsgLB = new JLabel(new ImageIcon("icon\\userMsg.png"));
		userMsgLB.setBounds(screenWidth/5*4-350, screenHeight/5*2+80, 350, 60);
		childBG.add(userMsgLB);
		
		userMsgTA = new JTextArea("");
		userMsgTA.setEditable(false);
		userMsgTA.setBounds(10, 6, 290, 48);
		userMsgTA.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		userMsgLB.add(userMsgTA);
		///////////////////////////////////////////////////////////////
		JLabel comMsgLB2 = new JLabel(new ImageIcon("icon\\comMsg.png"));
		comMsgLB2.setBounds(screenWidth/5, screenHeight/5*3, 350, 60);
		childBG.add(comMsgLB2);
		
		comMsgTA2 = new JTextArea("");
		comMsgTA2.setEditable(false);
		comMsgTA2.setBounds(30, 6, 290, 48);
		comMsgTA2.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		comMsgLB2.add(comMsgTA2);
		///////////////////////////////////////////////////////////////
		wordMsgImage = new JLabel("");
		wordMsgImage.setBounds(screenWidth/5+30, screenHeight/5+70, 296, 132);
		childBG.add(wordMsgImage);
		
		wordMsgLB = new JLabel("");
		wordMsgLB.setForeground(Color.WHITE);
		wordMsgLB.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
		wordMsgLB.setBounds(screenWidth/5+30, screenHeight/5+210, 150, 40);
		childBG.add(wordMsgLB);
		
		meanMsgLB = new JLabel("");
		meanMsgLB.setForeground(Color.WHITE);
		meanMsgLB.setFont(new Font("HY강B", Font.PLAIN, 20));
		meanMsgLB.setBounds(screenWidth/5+180, screenHeight/5+215, 150, 40);
		childBG.add(meanMsgLB);
		
		msgTA = new JTextArea("");
		msgTA.setEditable(false);
		msgTA.setBounds(screenWidth/5, screenHeight/5*4-40, screenWidth/5*3-130, 21);
		childBG.add(msgTA);
		msgTA.setColumns(10);
		
		/*/////////////////////////////////////////////////////////////////////////////////////////
		 * 여기까진 UI 부분 
		 * 이 아래부터 인풋 받고 함수 실행하는 부분
		 *////////////////////////////////////////////////////////////////////////////////////////////
		
		JButton btnBack = new JButton("DEL");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelMsg();
				keyListen.requestFocus();
			}
		});		
		btnBack.setBounds(screenWidth/5*4-127, screenHeight/5*4-41, 63, 23);
		childBG.add(btnBack);
		
		btnSend = new JButton("S");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendMsg();
				keyListen.requestFocus();
			}
		});
		btnSend.setBounds(screenWidth/5*4-60, screenHeight/5*4-41, 63, 23);
		childBG.add(btnSend);
		
		BufferedImage buttonIcon1 = null;
		try {
			buttonIcon1 = ImageIO.read(new File("icon\\home.png"));
		} catch (IOException e1) {
			displayError("UI에러!");
		}
		
		JButton btnGoMain = new JButton(new ImageIcon(buttonIcon1));
		btnGoMain.setBorder(BorderFactory.createEmptyBorder());
		btnGoMain.setContentAreaFilled(false);
		btnGoMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayMain();
			}
		});
		btnGoMain.setBounds(screenWidth-140, 25, 80, 80);
		childBG.add(btnGoMain);
		
		/* work : 사운드 버튼 이미지 넣어야함  - button.setIcon(new ImageIcon("PATH"));
		 */
		
		try {
			buttonIcon1 = ImageIO.read(new File("icon\\sound.png"));
		} catch (IOException e1) {
			displayError("UI에러!");
		}
		
		JButton soundBtn = new JButton(new ImageIcon(buttonIcon1));
		soundBtn.setBorder(BorderFactory.createEmptyBorder());
		soundBtn.setContentAreaFilled(false);
		soundBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					child.displaySound();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					displayError("소리 파일을 재생할 수 없습니다.");
				}
			}
		});
		soundBtn.setBounds(screenWidth/5+370, screenHeight/5+10, 35, 35);
		childBG.add(soundBtn);
		
		
		
		/*
		 * RECEIVE MSG 시작하는 부분
		 */
		goNext();
		
		/*
		 * 아이모드 키보드 input 받는 부분
		 * Back Space : 메시지 지우기
		 *  ← : 메인 화면으로 돌아가기
		 * Enter : 메시지 보내기
		 *  1 : 1번째 선택지 고르기
		 *  2 : 2번째 선택지 고르기
		 *  3 : 3번째 선택지 고르기
		 *  4 : 4번째 선택지 고르기
		 *  → : 다음으로 넘어가기
		 *  0 : display Sound
		 */
		
		keyListen = new JTextField();
		keyListen.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE){
					cancelMsg();
				}
				if(e.getKeyCode()==KeyEvent.VK_LEFT){
					displayMain();
				}
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					sendMsg();
					msgTA.setText("");
				}
				if(e.getKeyCode()==KeyEvent.VK_1){
					msgTA.append(selectBtn1.getText());
					keyListen.requestFocus();
				}
				if(e.getKeyCode()==KeyEvent.VK_2){
					msgTA.append(selectBtn2.getText());
					keyListen.requestFocus();
				}
				if(e.getKeyCode()==KeyEvent.VK_3){
					msgTA.append(selectBtn3.getText());
					keyListen.requestFocus();
				}
				if(e.getKeyCode()==KeyEvent.VK_4){
					if(flag==4){
						msgTA.append(selectBtn4.getText());
					}
					keyListen.requestFocus();
				}
				if(e.getKeyCode()==KeyEvent.VK_0){
					try {
						child.displaySound();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						displayError("소리 파일을 재생할 수 없습니다.");
						
					}
					keyListen.requestFocus();
				}
				if(e.getKeyCode()==KeyEvent.VK_RIGHT){
					goNext();
					keyListen.requestFocus();
				}
				if(e.getKeyCode()==KeyEvent.VK_A||e.getKeyCode()==KeyEvent.VK_B||e.getKeyCode()==KeyEvent.VK_C||e.getKeyCode()==KeyEvent.VK_D||e.getKeyCode()==KeyEvent.VK_E||e.getKeyCode()==KeyEvent.VK_F||e.getKeyCode()==KeyEvent.VK_G||e.getKeyCode()==KeyEvent.VK_H||e.getKeyCode()==KeyEvent.VK_I
						||e.getKeyCode()==KeyEvent.VK_J||e.getKeyCode()==KeyEvent.VK_K||e.getKeyCode()==KeyEvent.VK_L||e.getKeyCode()==KeyEvent.VK_M||e.getKeyCode()==KeyEvent.VK_N||e.getKeyCode()==KeyEvent.VK_O||e.getKeyCode()==KeyEvent.VK_P||e.getKeyCode()==KeyEvent.VK_Q||e.getKeyCode()==KeyEvent.VK_R
						||e.getKeyCode()==KeyEvent.VK_S||e.getKeyCode()==KeyEvent.VK_T||e.getKeyCode()==KeyEvent.VK_U||e.getKeyCode()==KeyEvent.VK_V||e.getKeyCode()==KeyEvent.VK_W||e.getKeyCode()==KeyEvent.VK_X||e.getKeyCode()==KeyEvent.VK_Y||e.getKeyCode()==KeyEvent.VK_Z||e.getKeyCode()==KeyEvent.VK_SPACE){
					
					msgTA.append(String.valueOf(Character.toChars(e.getKeyCode())));
				}
			}
		});
		keyListen.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				keyListen.requestFocus();
			}
		});
		keyListen.setBounds(0, 0, 0, 0);
		childBG.add(keyListen);
		keyListen.setColumns(10);
		keyListen.requestFocus();
		childBG.updateUI();
	}

	
	public void displayPM(){
		BufferedImage buttonIcon1 = null;
		
		setBounds(0, 0, screenWidth, screenHeight);
		parentPane = new JPanel();
		parentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(parentPane);
		parentPane.setLayout(null);
		
		parentBG = new JLabel(new ImageIcon("icon\\parentBG.png"));
		parentBG.setBounds(0, 0, screenWidth, screenHeight);
		parentPane.add(parentBG);
		
		wordList = new JTable();
		JScrollPane sp = new JScrollPane(wordList);
		sp.setSize(300, screenHeight/3*2);
		sp.setLocation(screenWidth/2-330, screenHeight/6);
		parentBG.add(sp);
		wordList.setRowSelectionAllowed(false);
		wordList.setModel(new DefaultTableModel(
			new String[][] {
			},
			new String[] {
				"WORD", "MEAN"
			}
			){
			public boolean isCellEditable(int rowIndex, int columnIndex) {
			    return false;
			}
		});
		wordList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int rowSelect = wordList.getSelectedRow();
				if(rowSelect==-1)return;
				else{
					String strSelect = newTable[rowSelect][0];
					searchTF.setText(strSelect);
					searchTF.requestFocus();
				}
			}
		});
		
		wordList.setBounds(screenWidth/2-330, screenHeight/6, 205, 500);
		
		
		searchTF = new JTextField();
		searchTF.setBounds(screenWidth/2+30, screenHeight/6, 240, 21);
		parentBG.add(searchTF);
		searchTF.setColumns(10);
		searchTF.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					findWord();
					searchTF.requestFocus();
				}
			}
		});
		engResult = new JLabel("");
		engResult.setFont(new Font("굴림", Font.BOLD, 18));
		engResult.setBounds(screenWidth/2+30, screenHeight/2-135, 296, 32);
		parentBG.add(engResult);
		
		korResult = new JLabel("");
		korResult.setFont(new Font("굴림", Font.PLAIN, 15));
		korResult.setBounds(screenWidth/2+30, screenHeight/2-103, 296, 32);
		parentBG.add(korResult);
		
		picResult = new JLabel("");
		picResult.setIcon(new ImageIcon());
		picResult.setBounds(screenWidth/2+30, screenHeight/2-66, 296, 132);
		parentBG.add(picResult);
		
		/*
		 * 여기까지 UI
		 * 여기서 부터 버튼 input 에 대한 각각 함수 호출
		 */
		
		searchBtn = new JButton("FIND");
		searchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				findWord();
				searchTF.requestFocus();
			}
		});
		searchBtn.setBounds(screenWidth/2+275, screenHeight/6, 65, 23);
		parentBG.add(searchBtn);
		
		try {
			buttonIcon1 = ImageIO.read(new File("icon\\sound.png"));
		} catch (IOException e1) {
			displayError("UI에러!");
		}
		
		soundBtn2 = new JButton(new ImageIcon(buttonIcon1));
		soundBtn2.setBorder(BorderFactory.createEmptyBorder());
		soundBtn2.setContentAreaFilled(false);
		soundBtn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					parent.displaySound();
					searchTF.requestFocus();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					displayError("소리 파일을 찾을 수 없습니다.");
				}
			}
		});
		soundBtn2.setBounds(screenWidth/2+330, screenHeight/2-133, 35, 35);
		parentBG.add(soundBtn2);
		soundBtn2.setVisible(false);
		
		btnAdd = new JButton("ADD");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addWord();
				searchTF.requestFocus();
			}
		});
		btnAdd.setFont(new Font("굴림", Font.BOLD, 18));
		btnAdd.setBounds(screenWidth/2+30, screenHeight/6*5-40, 80, 36);
		parentBG.add(btnAdd);
		
		btnDelete = new JButton("DELETE");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteWord();
				searchTF.requestFocus();
			}
		});
		btnDelete.setFont(new Font("굴림", Font.BOLD, 18));
		btnDelete.setBounds(screenWidth/2+120, screenHeight/6*5-40, 120, 36);
		parentBG.add(btnDelete);
		
		btnDelete.setEnabled(false);
		btnAdd.setEnabled(false);
		
		try {
			buttonIcon1 = ImageIO.read(new File("icon\\home2.png"));
		} catch (IOException e1) {
			displayError("UI에러!");
		}
		
		JButton btnGoMain = new JButton(new ImageIcon(buttonIcon1));
		btnGoMain.setBorder(BorderFactory.createEmptyBorder());
		btnGoMain.setContentAreaFilled(false);
		btnGoMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayMain();
			}
		});
		btnGoMain.setBounds(screenWidth-140, 25, 80, 80);
		parentBG.add(btnGoMain);
		
		/*
		 * SHOWLIST 수행 부분
		 * 
		 */
		newTable= db.showList();
		if(newTable==null) displayError("Can not find the LIST");
		else{
			wordList.setModel(new DefaultTableModel(newTable,
				new String[] {
					"WORD", "MEAN"
				}
				){
				public boolean isCellEditable(int rowIndex, int columnIndex) {
				    return false;
				}
			});
			
			parentBG.updateUI();
		}
	}
	/*
	 * 메시지가 단어 유형(type:2)일 경우, 단어, 뜻, 사진 자리에 각각을 출력해주는 함수
	 */
	public void setWordMsg(String path, String word, String mean){
		if(path!=null) wordMsgImage.setIcon(new ImageIcon(path));
		wordMsgLB.setText(word);
		meanMsgLB.setText(mean);
		msgTA.setEditable(true);
		
	}
	
	/*
	 * 사용자가 메시지 보냈을 때 실행
	 */
	public void sendMsg(){
		String buffer = msgTA.getText();
		msgTA.setText("");
		try {
			displayMsg(buffer, 2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			displayError("send-display Msg error!");
		}
	}
	
	/*
	 * 말풍선 영역 comMsgTA, userMsgTA, comMsgTA2 중에서 알맞은 곳에 메시지 출력해줌
	 */
	public void displayMsg(String message, int type) throws InterruptedException{
		if(type==1)	{
			comMsgTA.setText(message);
			child.setSoundPath(msg.getSound());
			child.setStr(msg.getWord());
			//단어유형일 경우
			if(msg.getType().equals("1")){	
				setWordMsg(msg.getImage(), msg.getWord(), msg.getMean());
				selectBtn1.setVisible(false);
				selectBtn2.setVisible(false);
				selectBtn3.setVisible(false);
				selectBtn4.setVisible(false);
				
			}else if(msg.getType().equals("2")){ //일반 유형일 경우
				selectBtn1.setVisible(true);
				selectBtn2.setVisible(true);
				selectBtn3.setVisible(true);
				selectBtn4.setVisible(true);
				String makeSel = msg.getAnswer();
				int len = makeSel.length();
				int cnt = 0;
				for(int i=0; i<len; i++){
					if(makeSel.charAt(i)==' ') cnt++;
				}
		
				if(cnt<2){
					if(len%3==0) displaySelection(makeSel.substring(0, len/3), makeSel.substring(len/3, len/3*2), makeSel.substring(len/3*2, len));
					else displaySelection(makeSel.substring(0, len/3), makeSel.substring(len/3, len/3*2), makeSel.substring(len/3*2, (len/3)*3), makeSel.substring((len/3)*3, len));
				}else{
					String[] split = makeSel.split("\\s+");
					if(cnt==2){
						displaySelection(split[0]+" ", split[1]+" ", split[2]);
					}else if(cnt==3){
						displaySelection(split[0]+" ", split[1]+" ", split[2]+" ", split[3]);
					}				
				}
			}
			try {
				child.displaySound();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				displayError("displaySound() error!");
			}			
		}
		else if(type==2) {
			userMsgTA.setText(message);
			String re = child.checkMsg(message);
			displayMsg(re, 3);
		}
		else if(type==3){
			comMsgTA2.setText(message);
		}
	}
	/*
	 * UI적인 부분 자세한 설명은 필요 없고 그냥 선택지 출력해주는 부분
	 * 오버라이딩
	 */
	public void displaySelection(String str1, String str2, String str3){
		Random ran = new Random();
		String[] buffer = new String[3];
		int rNum[] = new int[3];
		rNum[0]= ran.nextInt(3);
		buffer[rNum[0]]=str1;
		while(true){
			rNum[1] = ran.nextInt(3);
			if(rNum[0]!=rNum[1]) break;
		}
		buffer[rNum[1]]=str2;
		while(true){
			rNum[2] = ran.nextInt(3);
			if(rNum[0]!=rNum[2]&&rNum[1]!=rNum[2]) break;
		}
		buffer[rNum[2]]=str3;
		selectBtn1.setText(buffer[0]);
		selectBtn2.setText(buffer[1]);
		selectBtn3.setText(buffer[2]);
		flag = 3;
		selectBtn4.setVisible(false);
		childBG.updateUI();
	}
	public void displaySelection(String str1, String str2, String str3, String str4){
		Random ran = new Random();
		String[] buffer = new String[4];
		int rNum[] = new int[4];
		rNum[0]= ran.nextInt(4);
		buffer[rNum[0]]=str1;
		while(true){
			rNum[1] = ran.nextInt(4);
			if(rNum[0]!=rNum[1]) break;
		}
		buffer[rNum[1]]=str2;
		while(true){
			rNum[2] = ran.nextInt(4);
			if(rNum[0]!=rNum[2]&&rNum[1]!=rNum[2]) break;
		}
		buffer[rNum[2]]=str3;
		while(true){
			rNum[3] = ran.nextInt(4);
			if(rNum[0]!=rNum[3]&&rNum[1]!=rNum[3]&&rNum[2]!=rNum[3]) break;
		}
		buffer[rNum[3]]=str4;
		selectBtn1.setText(buffer[0]);
		selectBtn2.setText(buffer[1]);
		selectBtn3.setText(buffer[2]);
		selectBtn4.setText(buffer[3]);
		selectBtn4.setVisible(true);
		flag=4;
		childBG.updateUI();
	}
	
	
	public void cancelMsg(){
		msgTA.setText("");
	}
	
	
	public void findWord(){
		parent.setSoundPath(null);
		String buffer = searchTF.getText();
		buffer = buffer.toLowerCase();
		word = new Word();
		word = parent.findWM(buffer);
		if(word==null){
			displayError("검색 결과가 없습니다.");
		}else if(word.getWord()==null){
			displayError("검색 결과가 없습니다.");
		}else{
			parent.setStr(word.getWord());
			displayWord();
			if(word.isFromNaver==true){
				btnDelete.setEnabled(false);
				btnAdd.setEnabled(true);
			}else{
				btnDelete.setEnabled(true);
				btnAdd.setEnabled(false);
			}
		}
		searchTF.setText("");
		
		/*
		 * SHOWLIST 재실행 Sorting됨
		 */
		newTable= db.showList();
		if(newTable==null) displayError("Can not find the LIST");
		else{
			wordList.setModel(new DefaultTableModel(newTable,
					new String[] {
						"WORD", "MEAN"
					}
					){
					public boolean isCellEditable(int rowIndex, int columnIndex) {
					    return false;
					}
				});
			parentBG.updateUI();
		}		
	}
	
	public void displayWord(){
		if(word.getImage()==null){
			picResult.setIcon(new ImageIcon("images\\noImage.jpg"));
		}else picResult.setIcon(new ImageIcon(word.getImage()));
		engResult.setText(word.getWord());
		korResult.setText(word.getMean());
		parent.setStr(word.getWord());
		soundBtn2.setVisible(true);
		parentBG.updateUI();
	}
	public void deleteWord(){
		if(!parent.deleteFromDB(word)) displayError("Can not delete the word");
		btnDelete.setEnabled(false);
		btnAdd.setEnabled(false);
		/*
		 * SHOWLIST 재실행 Sorting됨
		 */
		newTable= db.showList();
		if(newTable==null) displayError("Can not find the LIST");
		else{
			wordList.setModel(new DefaultTableModel(newTable,
					new String[] {
						"WORD", "MEAN"
					}
					){
					public boolean isCellEditable(int rowIndex, int columnIndex) {
					    return false;
					}
				});
			parentBG.updateUI();
		}
	}
	public void addWord(){
		if(!parent.addtoList(word)) displayError("Can not add to LIST");
		btnDelete.setEnabled(false);
		btnAdd.setEnabled(false);
		
		/*
		 * SHOWLIST 재실행 Sorting됨
		 */
		
		newTable= db.showList();
		if(newTable==null) displayError("Can not find the LIST");
		else{
			wordList.setModel(new DefaultTableModel(newTable,
					new String[] {
						"WORD", "MEAN"
					}
					){
					public boolean isCellEditable(int rowIndex, int columnIndex) {
					    return false;
					}
				});
			parentBG.updateUI();
		}
	}
	public void goNext(){
		/*
		 * 초기화
		 */
		
		selectBtn4.setText("");
		comMsgTA.setText("");
		userMsgTA.setText("");
		comMsgTA2.setText("");
		childBG.updateUI();
		cancelMsg();
		setWordMsg("", "", "");
		child.setSoundPath(null);
		msg = new Message();
		
		/*
		 * RECEIVE MSG 실행
		 */		
		msg = child.receiveMsg();

		if(msg ==null) displayError("DB Error");
		else{
			try {
				displayMsg(msg.getQuestion(), 1);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	public void displayError(String str){
		JOptionPane.showMessageDialog(null, str);
	}
}

