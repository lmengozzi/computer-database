package com.excilys.lmengozzi.cdb.persistence;

import static org.mockito.Mockito.*;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.excilys.lmengozzi.cdb.business.Computer;
import com.excilys.lmengozzi.cdb.persistence.ComputerManager;

/**
 * 
 * Test class for {@link com.excilys.lmengozzi.cdb.persistence.ComputerManager}
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ComputerManagerTest {

	@Mock
	ComputerManager managerMock;

	@Test
	public void putTest() throws SQLException {

		MockitoAnnotations.initMocks(ComputerManagerTest.class);
		
		Computer a = new Computer(), b = new Computer(), c = new Computer();
		
		managerMock.put(a);
		managerMock.put(b);
		
		verify(managerMock).put(a);
	}

	@Test
	public void findByIdTest() throws SQLException {

		MockitoAnnotations.initMocks(ComputerManagerTest.class);

		long id = 10;

		managerMock.findById(id);

		verify(managerMock).findById(10);
	}
	
	public void findAllTest() throws SQLException {
		
	}
	
	public void getCountTest() throws SQLException {

	}
	
	public void findPage() throws SQLException {
		
	}
	
	public void delete() throws SQLException {
		
	}

}
