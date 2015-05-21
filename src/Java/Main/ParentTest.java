import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class ParentTest extends TestCase{
	Parent p;
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
		p = new Parent(db);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFindWM() {
				
		Word reWord = new Word();
		reWord = p.findWM("*");
		assertNull(reWord);
	}

	@Test
	public void testFindNaver() {
		assertFalse(p.findNaver("dkkfjksd"));
	}

}
