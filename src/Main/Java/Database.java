import java.util.*;

import redis.clients.jedis.*;

import com.google.common.primitives.Ints;
import com.google.gson.*;

public class Database {
	private Jedis jedis;
	private int beforeMsg=-1;
	/*
	 * 서버 IP주소 jedis = new Jedis("IP주소", 포트 번호(6379), timeout);203.252.160.80
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
	 * temp 단어유형, 일반 유형 1-2 중 랜덤 선택
	 * rNum 단어 유형 or 일반 유형의 문제들 중에서 랜덤 선택
	 * DB에서 읽어와서 msg에 저장
	 * msg 리턴
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
	 * 영어 단어로 DB 검색
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
	 * 한글로 DB 검색
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
	 * Naver 에서 검색한 단어 추가
	 * -> child mode 에서 단어 유형으로도 출제 가능
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
	 * DB에서 단어와 메시지 Index 3개의 데이터 모두 삭제
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
