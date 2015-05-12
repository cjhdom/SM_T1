
public class Person extends Thing{
	int lastX;//전에 있었던 x,y 좌표를 저장한다
	int lastY;

	public Person(int x, int y) {
		super(x, y);//상속받은 Thing 슈퍼클래스 생성
	}
}
