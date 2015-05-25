import java.util.*;

import redis.clients.jedis.*;

import com.google.common.primitives.Ints;
import com.google.gson.*;

public class Database {
	private Jedis jedis;
	
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
			System.out.println("wordQuiz: "+jedis.llen("wordQuiz"));
			int rNum = ran.nextInt(Ints.checkedCast(jedis.llen("wordQuiz")));
			msg = gson.fromJson(jedis.get(jedis.lindex("wordQuiz", rNum)), Message.class);
		}else{
			System.out.println("engQuiz: "+jedis.llen("engQuiz"));
			int rNum = ran.nextInt(Ints.checkedCast(jedis.llen("engQuiz")));
			System.out.println(rNum);
			msg = gson.fromJson(jedis.get(jedis.lindex("engQuiz", rNum)), Message.class);
		}
		return msg;
	}

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
	/*public Word searchByMean(String str){
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
	}*/
	public boolean insertWord(Word w){
		try{
			Gson gson = new Gson();
			String json = gson.toJson(w);
			jedis.set("1:"+w.getWord(), json);
		}catch(Exception e){
			e.printStackTrace(System.out);
			return false;
		}
		return true;
	}
	public boolean delWord(String word){
		if(jedis.exists("1:"+word)){
			jedis.del("1:"+word);
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
