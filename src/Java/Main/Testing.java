import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class Testing extends TestCase{
	Child c;
	Database db;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
		db = new Database();
		c = new Child(db);
	}

	@After
	public void tearDown() throws Exception {
	}


	@Test
	public void testCheckMsg() {
		c.msg.setAnswer("Hello");
		c.msg.setReRight("Good Job");
		c.msg.setReWrong("Hello is right answer");
		c.msg.setSound("sound\\hello.wav");
		String result = c.checkMsg(null);
		assertEquals("Hello is right answer", result);
	}/*
	
	@Test
	public void testReceiveMSg(){
		assertNotNull(c.receiveMsg());
	}
*/
}









