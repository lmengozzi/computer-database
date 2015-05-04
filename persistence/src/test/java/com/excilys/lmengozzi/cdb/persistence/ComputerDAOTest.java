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
/* Code below crashes with java.lang.NoClassDefFoundError: org/junit/runners/model/MultipleFailureException
 * It appears the unit test class SpringJUnit4ClassRunner is not found (it's known by all modules...)
@ContextConfiguration(locations = { "classpath:persistenceContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class) */
public class ComputerDAOTest {
	
	IComputerManager manager;

	@Test
	@Ignore("Spring test not functional")
	public void findPageTest() {
		GenericXmlApplicationContext appContext = new GenericXmlApplicationContext();
		appContext.load("applicationContext.xml");
		appContext.load("persistenceContext.xml");
		appContext.refresh();
		manager = appContext.getBean(IComputerManager.class);
		manager.findPage(0, 50, "name", true, "Amiga");
		appContext.close();
	}

	@Test
	@Ignore
	public void putTest() {
		
	}

	@Test
	@Ignore
	public void findByIdTest() {
		
	}

	@Test
	@Ignore
	public void findAllTest() {
		
	}

	@Test
	@Ignore
	public void getCountTest() {

	}

	@Test
	@Ignore
	public void findPage() {
		
	}

	@Test
	@Ignore
	public void delete() {
		
	}
}
