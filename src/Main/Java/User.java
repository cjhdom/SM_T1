import java.io.*;

import sun.audio.*;

public class User {
	private String str;
	private String soundPath;
	public String getSoundPath() {
		return soundPath;
	}
	public void setSoundPath(String soundPath) {
		this.soundPath = soundPath;
	}
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
	
	public boolean displaySound(){
		if(soundPath==null){
			/*Process p = Runtime.getRuntime().exec("C:\\Program Files\\Internet Explorer\\iexplore.exe \"http://www.howjsay.com/index.php?word="+str+"\"");
			try {
				Thread.sleep(25000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			p.destroy();
			*/
			return false;
		}
		else{
			try {
				InputStream in;
				in = new FileInputStream(soundPath);
				AudioStream as = new AudioStream(in);
				AudioPlayer.player.start(as);
			} catch (FileNotFoundException e) {
				return false;
			} catch(IOException e){
				return false;
			}
	        return true;
		}
	
	}

	
}
