package com.excilys.lmengozzi.cdb.persistence;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * 
 * Test class for {@link com.excilys.lmengozzi.cdb.persistence.ComputerDAO}
 *
 */

//TODO ComputerManagerTest
/* code below crashes with java.lang.NoClassDefFoundError: org/junit/runners/model/MultipleFailureException
@ContextConfiguration(locations = { "classpath:persistenceContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class) */
public class ComputerDAOTest {
	
	IComputerManager manager;

	@Test
	@Ignore("Spring test not functionnal")
	public void findPageTest() {
		GenericXmlApplicationContext appContext = new GenericXmlApplicationContext();
		appContext.load("applicationContext.xml");
		appContext.load("persistenceContext.xml");
		appContext.refresh();
		manager = appContext.getBean(IComputerManager.class);
		manager.findPage(0, 50, "name", true, "Amiga");
		appContext.close();
	}
	/*
	@Test
	public void putTest() throws SQLException {
		
	}

	@Test
	public void findByIdTest() throws SQLException {
		
	}
	
	public void findAllTest() throws SQLException {
		
	}
	
	public void getCountTest() throws SQLException {

	}
	
	public void findPage() throws SQLException {
		
	}
	
	public void delete() throws SQLException {
		
	}
*/
}