import java.io.UnsupportedEncodingException;
import java.io.IOException;

import javax.xml.parsers.*;

import java.net.*;
import java.util.regex.Pattern;

import org.w3c.dom.*;
import org.xml.sax.*;

public class Parent extends User{
	public Word word;
	private Database db;
	
	public Parent(Database db){
		word = new Word();
		this.db = db;
	}
	public Word findWM(String buffer){
		if(Pattern.matches("[a-zA-Z]+", buffer)){
			this.word = db.searchByWord(buffer);
			if(word.getWord()==null){
				if(!findNaver(buffer)){
					return null;
				}
			}else{
				word.isFromNaver=false;
				setSoundPath(word.getSound());
			}
			return word;
		}else if(Pattern.matches("[∞°-∆R]+", buffer)){
			//¿‘∑¬∞™¿Ã «—±€¿œ ∂ß øπø‹√≥∏Æ
			this.word = db.searchByMean(buffer);
			if(word.getWord()!=null){
				word.isFromNaver=false;
				setSoundPath(word.getSound());
				return word;
			}
			else return null;
		}else{
			return null;
		}
	}
	public boolean findNaver(String buffer){
		String uri="";
		try {
			uri = "http://openapi.naver.com/search?key=6087bae8d3adee2a9ba9305a87aeb7ae&query="+URLEncoder.encode(buffer, "utf-8")+"&target=encyc&start=1&display=10";
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try{
			DocumentBuilder builder = dbf.newDocumentBuilder();
			Document doc = builder.parse(uri);
			Element root = doc.getDocumentElement();
			NodeList list = root.getElementsByTagName("item");
			
			Element element = (Element)list.item(0);
			String str;
			if(element==null) return false;
			NodeList list2 = element.getElementsByTagName("title");
			Element cElement = (Element)list2.item(0);
			if(cElement.getFirstChild()!=null){
				str = cElement.getFirstChild().getNodeValue();
				str = str.replaceAll("<b>", "");
				str = str.replaceAll("</b>", "");
				word.setWord(buffer);
				word.setMean(str);
				word.isFromNaver=true;
				return true;
			}
			
		}catch (ParserConfigurationException e){
			return false;
		}
		catch (SAXException e){
			return false;
		}
		catch(IOException e){ 
			return false;
		}
		return false;
		
	}
	
	public boolean deleteFromDB(Word w){
		if(db.delWord(w.getWord())){
			return true;
		}
		return false;
	}
	public boolean addtoList(Word word){
		if(db.insertWord(word))return true;
		return false;
	}
}
