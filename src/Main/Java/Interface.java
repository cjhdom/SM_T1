import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;


import java.awt.FlowLayout;
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
	
	private Word word;
	private Message msg;
	private int flag;
	private int screenWidth;
	private int screenHeight;
	
	
	Database db;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					String resultStr = null;
					resultStr = JOptionPane.showInputDialog("ip �Է� : 203.252.160.105");
					Interface frame = new Interface(resultStr);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public Interface(String ip) {
		/*
		 * �ʱ�ȭ �κ��� ���� �ۼ��� �ʿ� ����
		 */
		db = new Database(ip);
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
		setTitle("[��ȭ��]����� ���α׷�_�ڡڡ�Talkids�ڡڡ�");
		setBounds(0, 0, screenWidth, screenHeight);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel backGround = new JLabel(new ImageIcon("icon\\mainBG.png"));
		backGround.setBounds(0, 0, screenWidth, screenHeight);
		contentPane.add(backGround);
		
		JLabel lblParent = new JLabel(new ImageIcon("icon\\pressP.png"));
		lblParent.setBounds(screenWidth/2-220, screenHeight/2+160, 180, 50);
		backGround.add(lblParent);
		
		JLabel lblChild = new JLabel(new ImageIcon("icon\\pressC.png"));
		lblChild.setBounds(screenWidth/2+40, screenHeight/2+160, 180, 50);
		backGround.add(lblChild);
		/*
		 * �θ��� ��ư Ŭ�� ��
		 */
		BufferedImage buttonIcon1 = null;
		try {
			buttonIcon1 = ImageIO.read(new File("icon\\parent.png"));
		} catch (IOException e1) {
			displayError("����!");
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
		 * ���̸�� ��ư Ŭ�� ��
		 */
		BufferedImage buttonIcon2 = null;
		try {
			buttonIcon2 = ImageIO.read(new File("icon\\child.png"));
		} catch (IOException e1) {
			displayError("����!");
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
		 * ���θ�� Ű���� input �޴� �κ�
		 * C : ���̸�� ȣ��
		 * P : �θ��� ȣ��
		 */
		
		keyListen = new JTextField();
		keyListen.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(e.getKeyCode()==KeyEvent.VK_C){
					System.out.println("child mode");
					displayCM();
				}
				else if(e.getKeyCode()==KeyEvent.VK_P){
					System.out.println("parent mode");
					displayPM();
					
				}
			}
		});
		keyListen.setBounds(0, 0, 0, 0);
		contentPane.add(keyListen);
		keyListen.setColumns(10);
		keyListen.requestFocus();
	}
	
	public void displayCM(){
		childPane = new JPanel();
		childPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(childPane);
		childPane.setLayout(null);
		
		childBG = new JLabel(new ImageIcon("icon\\childBG.png"));
		childBG.setBounds(0, 0, screenWidth, screenHeight);
		contentPane.add(childBG);
		
		msgTA = new JTextArea("");
		msgTA.setEditable(false);
		msgTA.setBounds(12, 250, 460, 21);
		childBG.add(msgTA);
		msgTA.setColumns(10);
		
		selectBtn1 = new JButton("");
		selectBtn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				msgTA.append(selectBtn1.getText());
				keyListen.requestFocus();
			}
		});
		selectBtn1.setBounds(5, 300, 80, 23);
		childBG.add(selectBtn1);
		
		selectBtn2 = new JButton("");
		selectBtn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				msgTA.append(selectBtn2.getText());
				keyListen.requestFocus();
			}
		});
		selectBtn2.setBounds(85, 300, 80, 23);
		childBG.add(selectBtn2);
		
		selectBtn3 = new JButton("");
		selectBtn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				msgTA.append(selectBtn3.getText());
				keyListen.requestFocus();
			}
		});
		selectBtn3.setBounds(165, 300, 80, 23);
		childBG.add(selectBtn3);
		
		selectBtn4 = new JButton("");
		selectBtn4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				msgTA.append(selectBtn4.getText());
				keyListen.requestFocus();
			}
		});
		selectBtn4.setBounds(245, 300, 80, 23);
		childBG.add(selectBtn4);
		
		comMsgTA = new JTextArea("");
		comMsgTA.setEditable(false);
		comMsgTA.setBounds(12, 10, 235, 30);
		childBG.add(comMsgTA);
		
		userMsgTA = new JTextArea("");
		userMsgTA.setEditable(false);
		userMsgTA.setBounds(350, 90, 235, 30);
		childBG.add(userMsgTA);
		
		comMsgTA2 = new JTextArea("");
		comMsgTA2.setEditable(false);
		comMsgTA2.setBounds(12, 210, 235, 30);
		childBG.add(comMsgTA2);
		
		wordMsgImage = new JLabel("");
		wordMsgImage.setBounds(12, 40, 296, 132);
		childBG.add(wordMsgImage);
		
		wordMsgLB = new JLabel("");
		wordMsgLB.setBounds(12, 180, 76, 15);
		childBG.add(wordMsgLB);
		
		meanMsgLB = new JLabel("");
		meanMsgLB.setBounds(100, 180, 76, 15);
		childBG.add(meanMsgLB);
		
		/*/////////////////////////////////////////////////////////////////////////////////////////
		 * ������� UI �κ� 
		 * �� �Ʒ����� ��ǲ �ް� �Լ� �����ϴ� �κ�
		 *////////////////////////////////////////////////////////////////////////////////////////////
		
		JButton btnBack = new JButton("DEL");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelMsg();
				keyListen.requestFocus();
			}
		});		
		btnBack.setBounds(475, 250, 63, 23);
		childBG.add(btnBack);
		
		btnSend = new JButton("S");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendMsg();
				keyListen.requestFocus();
			}
		});
		btnSend.setBounds(536, 250, 63, 23);
		childBG.add(btnSend);
		
		JButton btnGoMain = new JButton("Go Main");
		btnGoMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayMain();
			}
		});
		btnGoMain.setBounds(515, 0, 80, 23);
		childBG.add(btnGoMain);
		
		/* work : ���� ��ư �̹��� �־����  - button.setIcon(new ImageIcon("PATH"));
		 */
		
		JButton soundBtn = new JButton("");
		soundBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					child.displaySound();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					displayError("�Ҹ� ������ ����� �� �����ϴ�.");
				}
			}
		});
		soundBtn.setBounds(270, 12, 26, 23);
		childBG.add(soundBtn);
		
		
		
		/*
		 * RECEIVE MSG �����ϴ� �κ�
		 */
		goNext();
		
		/*
		 * ���̸�� Ű���� input �޴� �κ�
		 * Back Space : �޽��� �����
		 * F1 : ���� ȭ������ ���ư���
		 * Enter : �޽��� ������
		 * 1 : 1��° ������ ����
		 *  2 : 2��° ������ ����
		 *  3 : 3��° ������ ����
		 *  4 : 4��° ������ ����
		 *  �� : �������� �Ѿ��
		 */
		
		keyListen = new JTextField();
		keyListen.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE){
					cancelMsg();
				}
				if(e.getKeyCode()==KeyEvent.VK_F1){
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
		childPane.add(keyListen);
		keyListen.setColumns(10);
		keyListen.requestFocus();
		
	}

	
	public void displayPM(){
		parentPane = new JPanel();
		parentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(parentPane);
		parentPane.setLayout(null);
		
		parentBG = new JLabel(new ImageIcon("icon\\parentBG.png"));
		parentBG.setBounds(0, 0, screenWidth, screenHeight);
		contentPane.add(parentBG);
		
		wordList = new JTable();
		JScrollPane sp = new JScrollPane(wordList);
		sp.setSize(300, 291);
		sp.setLocation(10, 10);
		parentBG.add(sp);
		wordList.setRowSelectionAllowed(false);
		wordList.setModel(new DefaultTableModel(
			new String[][] {
			},
			new String[] {
				"WORD", "MEAN"
			}
		));
		wordList.setCellSelectionEnabled(true);
		wordList.setBounds(12, 10, 205, 291);
		
		searchTF = new JTextField();
		searchTF.setBounds(324, 34, 236, 21);
		parentBG.add(searchTF);
		searchTF.setColumns(10);
		
		engResult = new JLabel("");
		engResult.setFont(new Font("����", Font.BOLD, 18));
		engResult.setBounds(322, 55, 296, 32);
		parentBG.add(engResult);
		
		korResult = new JLabel("");
		korResult.setFont(new Font("����", Font.PLAIN, 15));
		korResult.setBounds(322, 90, 296, 32);
		parentBG.add(korResult);
		
		picResult = new JLabel("");
		picResult.setIcon(new ImageIcon());
		picResult.setBounds(322, 123, 296, 132);
		parentBG.add(picResult);
		
		/*
		 * ������� UI
		 * ���⼭ ���� ��ư input �� ���� ���� �Լ� ȣ��
		 */
		
		JButton searchBtn = new JButton("FIND");
		searchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				findWord();
			}
		});
		searchBtn.setBounds(562, 34, 65, 23);
		parentBG.add(searchBtn);
		
		/* work : ���� ��ư �̹��� �־����  - button.setIcon(new ImageIcon("PATH"));
		 */
		
		JButton soundBtn = new JButton("");
		soundBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayS();
			}
		});
		soundBtn.setBounds(396, 62, 26, 23);
		parentBG.add(soundBtn);
		
		JButton btnAdd = new JButton("ADD");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addWord();
			}
		});
		btnAdd.setFont(new Font("����", Font.BOLD, 18));
		btnAdd.setBounds(325, 267, 80, 36);
		parentBG.add(btnAdd);
		
		JButton btnDelete = new JButton("DELETE");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteWord();
			}
		});
		btnDelete.setFont(new Font("����", Font.BOLD, 18));
		btnDelete.setBounds(420, 267, 120, 36);
		parentBG.add(btnDelete);
		
		JButton btnGoMain = new JButton("Go Main");
		btnGoMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayMain();
			}
		});
		btnGoMain.setBounds(550, 0, 80, 23);
		parentBG.add(btnGoMain);
		
		/*
		 * SHOWLIST ���� �κ�
		 * 
		 */
		String[][] newTable= db.showList();
		if(newTable==null) displayError("Can not find the LIST");
		else{
			wordList.setModel(new DefaultTableModel(newTable,
					new String[] {
						"WORD", "MEAN"
					}
				));
			parentBG.updateUI();
		}
	}
	/*
	 * �޽����� �ܾ� ����(type:2)�� ���, �ܾ�, ��, ���� �ڸ��� ������ ������ִ� �Լ�
	 */
	public void setWordMsg(String path, String word, String mean){
		wordMsgImage.setIcon(new ImageIcon(path));
		wordMsgLB.setText(word);
		meanMsgLB.setText(mean);
		msgTA.setEditable(true);
		
	}
	
	/*
	 * ����ڰ� �޽��� ������ �� ����
	 */
	public void sendMsg(){
		String buffer = msgTA.getText();
		msgTA.setText("");
		try {
			displayMsg(buffer, 2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			displayError("send-display Msg error!");
		}
	}
	
	/*
	 * ��ǳ�� ���� comMsgTA, userMsgTA, comMsgTA2 �߿��� �˸��� ���� �޽��� �������
	 */
	public void displayMsg(String message, int type) throws InterruptedException{
		if(type==1)	{
			comMsgTA.setText(message);
			//�ܾ������� ���
			if(msg.getType().equals("1")){	
				setWordMsg(msg.getImage(), msg.getWord(), msg.getMean());
				selectBtn1.setVisible(false);
				selectBtn2.setVisible(false);
				selectBtn3.setVisible(false);
				selectBtn4.setVisible(false);				
			}else if(msg.getType().equals("2")){ //�Ϲ� ������ ���
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
			child.setSoundPath(msg.getSound());
			
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
	 * UI���� �κ� �ڼ��� ������ �ʿ� ���� �׳� ������ ������ִ� �κ�
	 * �������̵�
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
		flag=4;
	}
	
	
	public void cancelMsg(){
		msgTA.setText("");
	}
	
	
	public void findWord(){
		String buffer = searchTF.getText();
		word = new Word();
		word = parent.findWM(buffer);
		if(word.getWord()==null){
			displayError("�˻� ����� �����ϴ�.");
		}else{
			parent.setStr(word.getWord());
			displayWord();
		}
		searchTF.setText("");
		
		/*
		 * SHOWLIST ����� Sorting��
		 */
		String[][] newTable= db.showList();
		if(newTable==null) displayError("Can not find the LIST");
		else{
			wordList.setModel(new DefaultTableModel(newTable,
					new String[] {
						"WORD", "MEAN"
					}
				));
			parentBG.updateUI();
		}		
	}
	public void displayS(){
		try {
			parent.displaySound();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			displayError("�Ҹ� ������ ã�� �� �����ϴ�.");
		}
			
	}
	public void displayWord(){
		if(word.getImage()==null){
			picResult.setIcon(new ImageIcon("images\\noImage.jpg"));
		}else picResult.setIcon(new ImageIcon(word.getImage()));
		engResult.setText(word.getWord());
		korResult.setText(word.getMean());
		parent.setStr(word.getWord());	
		parentBG.updateUI();
	}
	public void deleteWord(){
		if(!parent.deleteFromDB(word)) displayError("Can not delete the word");
		/*
		 * SHOWLIST ����� Sorting��
		 */
		String[][] newTable= db.showList();
		if(newTable==null) displayError("Can not find the LIST");
		else{
			wordList.setModel(new DefaultTableModel(newTable,
					new String[] {
						"WORD", "MEAN"
					}
				));
			parentBG.updateUI();
		}
	}
	public void addWord(){
		System.out.println("addWord");
		if(!parent.addtoList(word)) displayError("Can nor add to LIST");
		/*
		 * SHOWLIST ����� Sorting��
		 */
		String[][] newTable= db.showList();
		if(newTable==null) displayError("Can not find the LIST");
		else{
			wordList.setModel(new DefaultTableModel(newTable,
					new String[] {
						"WORD", "MEAN"
					}
				));
			parentBG.updateUI();
		}
	}
	public void goNext(){
		/*
		 * �ʱ�ȭ
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
		 * RECEIVE MSG ����
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

