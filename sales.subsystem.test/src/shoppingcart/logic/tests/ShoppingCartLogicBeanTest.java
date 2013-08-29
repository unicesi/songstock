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
package shoppingcart.logic.tests;

import static org.junit.Assert.*;

import java.util.List;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import purchasehistory.logic.beans.IPurchaseHistoryLogicBean;
import purchasehistory.logic.beans.PurchaseHistoryLogicBean;
import purchasehistory.logic.exceptions.PurchaseHistoryLogicException;

import sales.dtos.IItem;
import sales.dtos.IShoppingCart;
import sales.dtos.impl.Item;
import sales.dtos.impl.ShoppingCart;
import sales.persistence.beans.ISalesPersistenceBean;
import sales.persistence.beans.SalesPersistenceBean;
import sales.persistence.exceptions.SalesPersistenceException;
import shoppingcart.logic.beans.IShoppingCartLogicBean;
import shoppingcart.logic.beans.ShoppingCartLogicBean;
import shoppingcart.logic.exceptions.ShoppingCartLogicException;
import users.dtos.IUser;
import users.dtos.impl.User;

/**
 * Test cases for the ShoppingCartLogicBean
 * @author Andrés Paz
 * 
 */
@RunWith(Arquillian.class)
public class ShoppingCartLogicBeanTest {

	/**
	 * ShoppingCartLogicBean instance
	 */
	@EJB
	private IShoppingCartLogicBean shoppingCartLogicBean;

	/**
	 * Creates a test archive with the classes and configuration files needed to run the test cases.
	 * 
	 * @return .jar file containing the classes and configuration files needed to run the test cases 
	 */
	@Deployment
	public static JavaArchive createTestArchive() {
		return ShrinkWrap
				.create(JavaArchive.class, "ShoppingCartLogicBeanTest.jar")
				.addClasses(IUser.class, User.class, IItem.class, Item.class,
						IShoppingCart.class, ShoppingCart.class,
						ISalesPersistenceBean.class,
						SalesPersistenceException.class,
						SalesPersistenceBean.class,
						sales.persistence.entities.ShoppingCart.class,
						sales.persistence.entities.Item.class,
						sales.persistence.entities.PurchaseLogPK.class,
						sales.persistence.entities.PurchaseLog.class,
						IShoppingCartLogicBean.class,
						ShoppingCartLogicBean.class,
						ShoppingCartLogicException.class,
						IPurchaseHistoryLogicBean.class,
						PurchaseHistoryLogicBean.class,
						PurchaseHistoryLogicException.class)
				.addAsManifestResource("META-INF/test-persistence.xml",
						"persistence.xml");
	}

	/**
	 * Test method for
	 * {@link shoppingcart.logic.beans.ShoppingCartLogicBean#addItem(sales.dtos.IItem, users.dtos.IUser)}
	 * .
	 */
	@Test
	public void testAddItem() {
		try {
			// Test item (song type)
			IItem item = new Item();
			item.setName("Painkiller");
			item.setAlbum(7);
			item.setArtist("Judas Priest");
			item.setSong(16);
			item.setType(IItem.SONG_TYPE);

			// Test user
			IUser user = new User();
			user.setEmail("admin");

			// Attempt to add the item to the user's shopping cart
			shoppingCartLogicBean.addItem(item, user);

			// Get all the items in the user's shopping cart
			List<IItem> shoppingCartItems = shoppingCartLogicBean
					.getAllItems(user);

			// If the shopping cart contains the newly added item the test is
			// passed
			assertTrue(shoppingCartItems.contains(item));
		} catch (ShoppingCartLogicException e) {
			// Fail the test if any exception is thrown.
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link shoppingcart.logic.beans.ShoppingCartLogicBean#removeItem(sales.dtos.IItem, users.dtos.IUser)}
	 * .
	 */
	@Test
	public void testRemoveItem() {
		try {
			// Test item (song type)
			IItem item = new Item();
			item.setName("Painkiller");
			item.setAlbum(7);
			item.setArtist("Judas Priest");
			item.setSong(16);
			item.setType(IItem.SONG_TYPE);

			// Test user
			IUser user = new User();
			user.setEmail("admin");

			// Attempt to remove the item from the user's shopping cart
			shoppingCartLogicBean.removeItem(item, user);

			// Get all the items in the user's shopping cart
			List<IItem> shoppingCartItems = shoppingCartLogicBean
					.getAllItems(user);

			// If the shopping cart doesn't contain the item the test is passed
			assertTrue(!shoppingCartItems.contains(item));
		} catch (ShoppingCartLogicException e) {
			// Fail the test if any exception is thrown.
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link shoppingcart.logic.beans.ShoppingCartLogicBean#empty(users.dtos.IUser)}
	 * .
	 */
	@Test
	public void testEmpty() {
		try {
			// Test item (song type)
			IItem item = new Item();
			item.setName("Painkiller");
			item.setAlbum(7);
			item.setArtist("Judas Priest");
			item.setSong(16);
			item.setType(IItem.SONG_TYPE);

			// Test user
			IUser user = new User();
			user.setEmail("admin");

			// Attempt to add the item to the user's shopping cart
			shoppingCartLogicBean.addItem(item, user);

			// Attempt to clear the user's shopping cart
			shoppingCartLogicBean.empty(user);

			// Get all the items in the user's shopping cart
			List<IItem> shoppingCartItems = shoppingCartLogicBean
					.getAllItems(user);

			// If the shopping cart doesn't contain any item the test is passed
			assertTrue(shoppingCartItems.size() == 0);
		} catch (ShoppingCartLogicException e) {
			// Fail the test if any exception is thrown.
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link shoppingcart.logic.beans.ShoppingCartLogicBean#checkoutAllItems(users.dtos.IUser)}
	 * .
	 */
	@Test
	public void testCheckoutAllItems() {
		try {
			// Test item (song type)
			IItem item = new Item();
			item.setName("Painkiller");
			item.setAlbum(7);
			item.setArtist("Judas Priest");
			item.setSong(16);
			item.setType(IItem.SONG_TYPE);

			// Test user
			IUser user = new User();
			user.setEmail("admin");

			// Attempt to add the item to the user's shopping cart
			shoppingCartLogicBean.addItem(item, user);

			// Checkout all the items in the user's shopping cart
			List<IItem> shoppingCartItems = shoppingCartLogicBean
					.checkoutAllItems(user);

			// If the checked out items contain the item the test is passed
			assertTrue(shoppingCartItems.contains(item));
		} catch (ShoppingCartLogicException e) {
			// Fail the test if any exception is thrown.
			fail(e.getMessage());
		}
	}

}
