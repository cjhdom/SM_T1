import java.util.*;

import redis.clients.jedis.*;

import com.google.common.primitives.Ints;
import com.google.gson.*;

public class Database {
	private Jedis jedis;
	private int beforeMsg=-1;
	/*
	 * ���� IP�ּ� jedis = new Jedis("IP�ּ�", ��Ʈ ��ȣ(6379), timeout);203.252.160.80
	 */
	
	public Database(){
		
	}
	public boolean init(String ip){
		jedis = new Jedis(ip, 6379, 100000000);
		try{
			jedis.exists("1:");
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	/*
	 * temp �ܾ�����, �Ϲ� ���� 1-2 �� ���� ����
	 * rNum �ܾ� ���� or �Ϲ� ������ ������ �߿��� ���� ����
	 * DB���� �о�ͼ� msg�� ����
	 * msg ����
	 */
	
	public Message setMsg(){
		Message msg= new Message();
		Gson gson = new Gson();
		Random ran = new Random();
		int temp = ran.nextInt(2)+1;
		if(temp==1){
			int rNum;
			while(true){
				rNum = ran.nextInt(Ints.checkedCast(jedis.llen("wordQuiz")));
				if(rNum==beforeMsg);
				else break;
			}
			msg = gson.fromJson(jedis.get(jedis.lindex("wordQuiz", rNum)), Message.class);
			beforeMsg = rNum;
		}else{
			int rNum;
			while(true){
				rNum = ran.nextInt(Ints.checkedCast(jedis.llen("engQuiz")));
				if(rNum==beforeMsg);
				else break;
			}
			msg = gson.fromJson(jedis.get(jedis.lindex("engQuiz", rNum)), Message.class);
			beforeMsg = rNum;
		}
		return msg;
	}
	/*
	 * ���� �ܾ�� DB �˻�
	 */
	public Word searchByWord(String str){
		Word w=new Word();
		try{
			Gson gson = new Gson();
			if(!jedis.exists("1:"+str)){
				w.setWord(null);
				w.setMean(null);
				w.setImage(null);
				return w;
			}
			w = gson.fromJson(jedis.get("1:"+str), Word.class);
			return w;
		}catch(Exception e){
			e.printStackTrace(System.out);
			return w;
		}
	}
	/*
	 * �ѱ۷� DB �˻�
	 */
	public Word searchByMean(String str){
		Word w = new Word();
		try{
			List<String> newTable = new ArrayList<String>();
			newTable.addAll(jedis.keys("1:*"));
			for(int i=0; i<newTable.size(); i++){
				Gson gson = new Gson();
				w = gson.fromJson(jedis.get(newTable.get(i)), Word.class);
				if(w.getMean().equals(str)){
					return w;
				}
			}
			w.setWord(null);
			w.setMean(null);
			w.setImage(null);
			return w;
		}catch(Exception e){
			e.printStackTrace(System.out);
			return w;
		}
	}
	/*
	 * Naver ���� �˻��� �ܾ� �߰�
	 * -> child mode ���� �ܾ� �������ε� ���� ����
	 */
	public boolean insertWord(Word w){
		try{
			Gson gson = new Gson();
			String jsonWord = gson.toJson(w);
			Message temp = gson.fromJson(jsonWord, Message.class);
			temp.setType("1");
			temp.setReRight("Nice Job!");
			temp.setReWrong("Try Again!");
			temp.setQuestion("What is this?");
			temp.setAnswer(temp.getWord());
			temp.setResound("sound\\nicejob.wav");
			String jsonMsg = gson.toJson(temp);
			String msgCnt = Integer.toString(Ints.checkedCast(jedis.llen("wordQuiz"))+1);
			jedis.set("1:"+w.getWord(), jsonWord);
			jedis.set("2:q"+msgCnt, jsonMsg);
			jedis.lpush("wordQuiz", "2:q"+msgCnt);
		}catch(Exception e){
			e.printStackTrace(System.out);
			return false;
		}
		return true;
	}
	
	/*
	 * DB���� �ܾ�� �޽��� Index 3���� ������ ��� ����
	 */
	public boolean delWord(String word){
		if(jedis.exists("1:"+word)){
			jedis.del("1:"+word);
			
			for(int i=1; i<Ints.checkedCast(jedis.llen("wordQuiz"))+1; i++){
				Gson gson = new Gson();
				Message temp = gson.fromJson(jedis.get("2:q"+Integer.toString(i)), Message.class);
				if(temp.getWord().equals(word)){
					jedis.del("2:q"+Integer.toString(i));
					jedis.lrem("wordQuiz", 0, "2:q"+Integer.toString(i));
				}
			}
			return true;
		}
		else{
			return false;
		}
		
	}
	public String[][] showList(){
		Gson gson = new Gson();
		List<String> newTable = new ArrayList<String>();
		newTable.addAll(jedis.keys("1:*"));
		Collections.sort(newTable);
		String[][] returnTable=new String[newTable.size()][2];
		
		for(int i=0; i<newTable.size(); i++){
			Word w = gson.fromJson(jedis.get(newTable.get(i)), Word.class);
			returnTable[i][0] = w.getWord();
			returnTable[i][1] = w.getMean();
		}
		return returnTable;
	}
}
