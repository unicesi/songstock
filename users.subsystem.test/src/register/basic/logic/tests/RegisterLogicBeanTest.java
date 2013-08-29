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
package register.basic.logic.tests;

import static org.junit.Assert.*;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
 * Test cases for the RegisterLogicBean
 */
@RunWith(Arquillian.class)
public class RegisterLogicBeanTest {

	private IUser testUser;
	@EJB
	private IRegisterLogicBean registerLogicBean;
	@EJB
	private IUserPersistenceBean userPersistenceBean;
	@PersistenceContext(unitName = "users.persistence.jpa")
	private EntityManager entityManager;

	/**
	 * Creates a test archive with the classes and configuration files needed to run the test cases.
	 * 
	 * @return .jar file containing the classes and configuration files needed to run the test cases 
	 */
	@Deployment
	public static JavaArchive createTestArchive() {
		return ShrinkWrap
				.create(JavaArchive.class, "LoginLogicBeanTest.jar")
				.addClasses(IUser.class, User.class,
						IBasicLoginLogicBean.class, LoginLogicException.class,
						BasicLoginLogicBean.class, IRegisterLogicBean.class,
						RegisterLogicException.class, RegisterLogicBean.class,
						IUserPersistenceBean.class,
						UserPersistenceException.class,
						UserPersistenceBean.class,
						users.persistence.entities.User.class)
				.addAsManifestResource("META-INF/test-persistence.xml",
						"persistence.xml");
	}

	/**
	 * Test method for
	 * {@link register.logic.beans.RegisterLogicBean#registerNewUser(users.dtos.IUser)}
	 * .
	 */
	@Test
	public void testRegisterNewUser() {
		System.out.println("registerNewUser");
		register();

		// Invoke the registerNewUser method on the bean instance with existing
		// user
		testUser.setEmail("admin");
		testUser.setName("admin");
		testUser.setPassword("admin");
		try {
			registerLogicBean.registerNewUser(testUser);
		} catch (RegisterLogicException e) {
			assertEquals("User already exists.", e.getMessage());
		}
		clean();
	}

	private void register() {
		// Invoke the registerNewUser method on the bean instance with new user
		testUser = new User();
		testUser.setEmail("newUser");
		testUser.setName("newUser");
		testUser.setPassword("newUser");
		boolean result;
		try {
			result = registerLogicBean.registerNewUser(testUser);
			assertTrue(result);
		} catch (RegisterLogicException e) {
			// This occurs when the test has been executed previously
		}
	}

	/**
	 * Cleans the database modifications to allow future tests
	 */
	private void clean() {
		try {
			IUser iUser = new User();
			iUser.setEmail("newUser");
			userPersistenceBean.deleteUser(iUser);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link register.logic.beans.RegisterLogicBean#updateUser(users.dtos.IUser)}
	 * .
	 */
	@Test
	public void testUpdateUser() throws Exception {
		System.out.println("updateUser");

		register();
		// Invoke the updateUser method on the bean instance with changed name
		// and password
		testUser = new User();
		testUser.setEmail("newUser");
		testUser.setName("new_user");
		testUser.setPassword("new_user");
		registerLogicBean.updateUser(testUser);

		// Check if the changes were saved correctly
		IUser resultUser = userPersistenceBean.getUserByEmail(testUser);
		assertEquals(testUser.getName(), resultUser.getName());

		clean();
	}

}
