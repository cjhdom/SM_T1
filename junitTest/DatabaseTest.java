import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class DatabaseTest extends TestCase{
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
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSetMsg() {
		assertNotNull(db.setMsg());
	}

	@Test
	public void testSearchByWord() {
		Word w = db.searchByWord(null);
		assertNotNull(w);
		assertNull(w.getMean());
	}

	@Test
	public void testInsertWord() {
		Word w = new Word();
		w = null;
		assertFalse(db.insertWord(w));
	}

	@Test
	public void testDelWord() {
		assertFalse(db.delWord(null));
	}

	@Test
	public void testShowList() {
		assertNotNull(db.showList());
	}

}









