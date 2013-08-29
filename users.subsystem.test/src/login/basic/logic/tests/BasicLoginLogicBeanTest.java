/**
* Copyright © 2013 Universidad Icesi
* 
* This file is part of SongStock.
* 
* SongStock is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
* 
* SongStock is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
* 
* You should have received a copy of the GNU General Public License
* along with SongStock.  If not, see <http://www.gnu.org/licenses/>.
**/
package login.basic.logic.tests;

import static org.junit.Assert.*;

import javax.ejb.EJB;

import login.basic.logic.beans.BasicLoginLogicBean;
import login.basic.logic.beans.IBasicLoginLogicBean;
import login.basic.logic.exceptions.LoginLogicException;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import register.logic.beans.IRegisterLogicBean;
import register.logic.beans.RegisterLogicBean;
import register.logic.exceptions.RegisterLogicException;

import users.dtos.IUser;
import users.dtos.impl.User;
import users.persistence.beans.IUserPersistenceBean;
import users.persistence.beans.UserPersistenceBean;
import users.persistence.exceptions.UserPersistenceException;

/**
 * Test cases for the BasicLoginLogicBean.
 */
@RunWith(Arquillian.class)
public class BasicLoginLogicBeanTest {

	/**
	 * Test user instance.
	 */
	private IUser testUser;
	/**
	 * BasicLoginLogicBean instance.
	 */
	@EJB
	private IBasicLoginLogicBean loginLogicBean;
	
	/**
	 * Creates a test archive with the classes and configuration files needed to run the test cases.
	 * 
	 * @return .jar file containing the classes and configuration files needed to run the test cases 
	 */
	@Deployment
	public static JavaArchive createTestArchive() {
		return ShrinkWrap.create(JavaArchive.class, "LoginLogicBeanTest.jar")
				.addClasses(  
						IUser.class,
						User.class,
						IBasicLoginLogicBean.class,
						LoginLogicException.class,
						BasicLoginLogicBean.class,
						IRegisterLogicBean.class,
						RegisterLogicException.class,
						RegisterLogicBean.class,
						IUserPersistenceBean.class,
						UserPersistenceException.class,
						UserPersistenceBean.class,
						users.persistence.entities.User.class)
				.addAsManifestResource("META-INF/test-persistence.xml", "persistence.xml");
	}

	/**
	 * Test method for {@link login.basic.logic.beans.BasicLoginLogicBean#login(users.dtos.IUser)}.
	 * @throws Exception 
	 */
	@Test
	public void testLogin() throws Exception {
		System.out.println("login");
		
        // Invoke the login method on the bean instance with correct email and password
        testUser = new User();
		testUser.setEmail("admin");
		testUser.setPassword("admin");
        IUser resultUser = loginLogicBean.login(testUser);
        
        assertEquals("admin", resultUser.getName());
        
        // Invoke the login method on the bean instance with correct email and wrong password
        try {
			testUser.setEmail("admin");
			testUser.setPassword("123");
			resultUser = loginLogicBean.login(testUser);
		} catch (LoginLogicException e) {
			assertEquals("Wrong password.", e.getMessage());
		}
        
        // Invoke the login method on the bean instance with non-existing user
        try {
			testUser.setEmail("test");
			testUser.setPassword("test");
			resultUser = loginLogicBean.login(testUser);
		} catch (LoginLogicException e) {
			System.out.println("--------------------"+e.getMessage());
			assertEquals("Wrong user.", e.getMessage());
		}
	}

}
