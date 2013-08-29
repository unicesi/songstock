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
package purchasehistory.logic.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
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
 * PurchaseHistoryLogicBean test class
 * 
 * @author daviddurangiraldo
 * 
 */
@RunWith(Arquillian.class)
public class PurchaseHistoryLogicBeanTest {

	/**
	 * PurchaseHistoryLogicBean instance
	 */
	@EJB
	private IPurchaseHistoryLogicBean iPurchaseHistoryLogicBean;
	/**
	 * SalesPersistenceBean instance
	 */
	@EJB
	private ISalesPersistenceBean iSalesPersistenceBean;
	/**
	 * ShoppingCartLogicBean instance
	 */
	@EJB
	private IShoppingCartLogicBean iShoppingCartLogicBean;

	/**
	 * Creates a test archive with the classes and configuration files needed to run the test cases.
	 * 
	 * @return .jar file containing the classes and configuration files needed to run the test cases 
	 */
	@Deployment
	public static JavaArchive createTestArchive() {
		return ShrinkWrap
				.create(JavaArchive.class, "PurchaseHistoryLogicBeanTest.jar")
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
	 * {@link purchasehistory.logic.beans.PurchaseHistoryLogicBean#logUserPurchase(java.util.List, users.dtos.IUser)}
	 * .
	 * 
	 * @throws Exception
	 */
	@Test
	public void testLogUserPurchase() throws Exception {
		IUser user = new User();
		user.setEmail("admin");
		IShoppingCart shoppingCart = iSalesPersistenceBean
				.getUserShoppingCart(user);

		// List that contains all the items added to the shopping cart
		List<IItem> itemsList = new ArrayList<>();

		// Creating a new item
		IItem item = new Item();
		item.setAlbum(1);
		item.setArtist("Survivor");
		item.setName("Too Hot To Sleep");
		item.setPrice(20.59);
		item.setType(IItem.ALBUM_TYPE);
		item.setId(0);
		itemsList.add(item);

		// Creating a new item
		item = new Item();
		item.setAlbum(3);
		item.setArtist("Survivor");
		item.setName("Is This Love");
		item.setPrice(1.59);
		item.setType(IItem.SONG_TYPE);
		item.setId(1);
		itemsList.add(item);

		// Creating a new item
		item = new Item();
		item.setAlbum(6);
		item.setArtist("The Police");
		item.setName("Every Breath You Take");
		item.setPrice(1.32);
		item.setType(IItem.SONG_TYPE);
		item.setId(2);
		itemsList.add(item);

		// Adding all the items to another user's shopping cart in case they are
		// missing
		for (IItem iItem : itemsList) {
			shoppingCart.getItems().add(iItem);
		}
		// Adding the items to the shopping cart
		iSalesPersistenceBean.updateUserShoppingCart(shoppingCart, user);

		try {
			iPurchaseHistoryLogicBean.logUserPurchase(itemsList, user);
		} catch (PurchaseHistoryLogicException e) {

		}

		try {
			iPurchaseHistoryLogicBean.logUserPurchase(itemsList, user);
		} catch (PurchaseHistoryLogicException e) {
			assertEquals("One of the selected items has been alredy bought",
					e.getMessage());
		}

		// Cleaning the user history logs for future testing
		iSalesPersistenceBean.deleteUserPurchaseHistory(user);
		iShoppingCartLogicBean.checkoutAllItems(user);
	}

	/**
	 * Test method for
	 * {@link purchasehistory.logic.beans.PurchaseHistoryLogicBean#getUserPurchaseHistory(users.dtos.IUser)}
	 * .
	 * @throws Exception 
	 */
	@Test
	public void testGetUserPurchaseHistory() throws Exception {
		IUser user = new User();
		user.setEmail("admin");
		IShoppingCart shoppingCart = iSalesPersistenceBean
				.getUserShoppingCart(user);

		// List that contains all the items added to the shopping cart
		List<IItem> itemsList = new ArrayList<>();

		// Creating a new item
		IItem item = new Item();
		item.setAlbum(1);
		item.setArtist("Survivor");
		item.setName("Too Hot To Sleep");
		item.setPrice(20.59);
		item.setType(IItem.ALBUM_TYPE);
		item.setId(0);
		itemsList.add(item);

		// Creating a new item
		item = new Item();
		item.setAlbum(3);
		item.setArtist("Survivor");
		item.setName("Is This Love");
		item.setPrice(1.59);
		item.setType(IItem.SONG_TYPE);
		item.setId(1);
		itemsList.add(item);

		// Creating a new item
		item = new Item();
		item.setAlbum(6);
		item.setArtist("The Police");
		item.setName("Every Breath You Take");
		item.setPrice(1.32);
		item.setType(IItem.SONG_TYPE);
		item.setId(2);
		itemsList.add(item);

		// Adding all the items to another user's shopping cart in case they are
		// missing
		for (IItem iItem : itemsList) {
			shoppingCart.getItems().add(iItem);
		}
		// Adding the items to the shopping cart
		iSalesPersistenceBean.updateUserShoppingCart(shoppingCart, user);

		// Creating a log for all the items in the list
		try {
			iPurchaseHistoryLogicBean.logUserPurchase(itemsList, user);
		} catch (PurchaseHistoryLogicException e) {

		}

		// Getting the user history logs
		List<IItem> history = iPurchaseHistoryLogicBean
				.getUserPurchaseHistory(user);
		assertNotNull("The query found a null list of items", history);
		assertEquals("The items found are different from the expected ones",
				true, history.containsAll(itemsList));

		// Cleaning the user history logs for future testing
		iSalesPersistenceBean.deleteUserPurchaseHistory(user);
		iShoppingCartLogicBean.checkoutAllItems(user);

		// Getting the user history logs
		history = iPurchaseHistoryLogicBean.getUserPurchaseHistory(user);
		assertNull("The query found a list of items that contains elements",
				history);

		// Testing with another user
		user.setEmail("dduran@icesi.edu.co");
		history = iPurchaseHistoryLogicBean.getUserPurchaseHistory(user);
		assertNull("The query found a list of items that contains elements",
				history);
	}

}
