package sales.persistence.tests;

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
 * Test case for the SalesPersistenceBean class
 * 
 * @author daviddurangiraldo
 * 
 */
@RunWith(Arquillian.class)
public class SalesPersistenceBeanTest {

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
				.create(JavaArchive.class, "SalesPersistenceBeanTest.jar")
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
	 * {@link sales.persistence.beans.SalesPersistenceBean#getUserShoppingCart(users.dtos.IUser)}
	 * .
	 * 
	 * @throws SalesPersistenceException
	 *             if the shopping cart cannot be allocated
	 */
	@Test
	public void testGetUserShoppingCart() throws SalesPersistenceException {
		IUser user = new User();
		user.setEmail("admin");
		IShoppingCart shoppingCart = iSalesPersistenceBean
				.getUserShoppingCart(user);
		assertNotNull("The shopping cart is not being created", shoppingCart);
		assertEquals("The shopping cart id is different from the expected",
				new Integer(0), shoppingCart.getId());

		user.setEmail("dduran@icesi.edu.co");
		shoppingCart = iSalesPersistenceBean.getUserShoppingCart(user);
		assertNotNull("The shopping cart is not being created", shoppingCart);
		assertEquals("The shopping cart id is different from the expected",
				new Integer(1), shoppingCart.getId());

		user.setEmail("admin");
		shoppingCart = iSalesPersistenceBean.getUserShoppingCart(user);
		assertNotNull("The shopping cart is not being created", shoppingCart);
		assertEquals("The shopping cart id is different from the expected",
				new Integer(0), shoppingCart.getId());

		user.setEmail("afpaz@icesi.edu.co");
		shoppingCart = iSalesPersistenceBean.getUserShoppingCart(user);
		assertNotNull("The shopping cart is not being created", shoppingCart);
		assertEquals("The shopping cart id is different from the expected",
				new Integer(2), shoppingCart.getId());

		user.setEmail("dduran@icesi.edu.co");
		shoppingCart = iSalesPersistenceBean.getUserShoppingCart(user);
		assertNotNull("The shopping cart is not being created", shoppingCart);
		assertEquals("The shopping cart id is different from the expected",
				new Integer(1), shoppingCart.getId());
	}

	/**
	 * Test method for
	 * {@link sales.persistence.beans.SalesPersistenceBean#updateUserShoppingCart(sales.dtos.IShoppingCart, users.dtos.IUser)}
	 * .
	 * 
	 * @throws Exception
	 *             if there is an error updating the user shopping cart
	 */
	@Test
	public void testUpdateUserShoppingCart() throws Exception {
		IUser user = new User();
		user.setEmail("admin");
		IShoppingCart shoppingCart = iSalesPersistenceBean
				.getUserShoppingCart(user);
		assertNotNull("The shopping cart is not being created", shoppingCart);
		assertEquals("The shopping cart id is different from the expected",
				new Integer(0), shoppingCart.getId());
		assertEquals(
				"The shopping cart has different items from the expected ones",
				0, shoppingCart.getItems().size());

		// List that contains all the items added to the shopping cart
		List<IItem> itemsList = new ArrayList<>();

		// Creating a new item
		IItem item = new Item();
		item.setAlbum(1);
		item.setArtist("Survivor");
		item.setName("Too Hot To Sleep");
		item.setPrice(20.59);
		item.setType(IItem.ALBUM_TYPE);
		itemsList.add(item);

		// Adding the item to the shopping cart
		shoppingCart.getItems().add(item);
		iSalesPersistenceBean.updateUserShoppingCart(shoppingCart, user);

		// Getting the updated shopping cart
		shoppingCart = iSalesPersistenceBean.getUserShoppingCart(user);

		assertEquals("The shopping cart id is different from the expected",
				new Integer(0), shoppingCart.getId());
		assertEquals(
				"The shopping cart has different items from the expected ones",
				1, shoppingCart.getItems().size());
		assertEquals(
				"The shopping cart has different items from the expected ones",
				true, shoppingCart.getItems().contains(item));

		// Creating a new item
		item = new Item();
		item.setAlbum(3);
		item.setArtist("Survivor");
		item.setName("Is This Love");
		item.setPrice(1.59);
		item.setType(IItem.SONG_TYPE);
		itemsList.add(item);
		// Adding the item to the shopping cart
		shoppingCart.getItems().add(item);
		iSalesPersistenceBean.updateUserShoppingCart(shoppingCart, user);

		// Creating a new item
		item = new Item();
		item.setAlbum(6);
		item.setArtist("The Police");
		item.setName("Every Breath You Take");
		item.setPrice(1.32);
		item.setType(IItem.SONG_TYPE);
		itemsList.add(item);
		// Adding the item to the shopping cart
		shoppingCart.getItems().add(item);
		iSalesPersistenceBean.updateUserShoppingCart(shoppingCart, user);

		// Getting the updated shopping cart
		shoppingCart = iSalesPersistenceBean.getUserShoppingCart(user);

		assertEquals("The shopping cart id is different from the expected",
				new Integer(0), shoppingCart.getId());
		assertEquals(
				"The shopping cart has different items from the expected ones",
				3, shoppingCart.getItems().size());
		assertEquals(
				"The shopping cart has different items from the expected ones",
				true, shoppingCart.getItems().containsAll(itemsList));

		user.setEmail("dduran@icesi.edu.co");
		shoppingCart = iSalesPersistenceBean.getUserShoppingCart(user);

		// Adding all the items to another user's shopping cart
		for (IItem iItem : itemsList) {
			shoppingCart.getItems().add(iItem);
		}
		// Adding the items to the shopping cart
		iSalesPersistenceBean.updateUserShoppingCart(shoppingCart, user);

		// Getting the updated shopping cart
		shoppingCart = iSalesPersistenceBean.getUserShoppingCart(user);

		assertEquals("The shopping cart id is different from the expected",
				new Integer(1), shoppingCart.getId());
		assertEquals(
				"The shopping cart has different items from the expected ones",
				3, shoppingCart.getItems().size());
		assertEquals(
				"The shopping cart has different items from the expected ones",
				true, shoppingCart.getItems().containsAll(itemsList));

		// Cleaning the users shopping carts for future testing
		iShoppingCartLogicBean.checkoutAllItems(user);
		user.setEmail("admin");
		iShoppingCartLogicBean.checkoutAllItems(user);
	}

	/**
	 * Test method for
	 * {@link sales.persistence.beans.SalesPersistenceBean#logUserPurchase(sales.dtos.IItem, users.dtos.IUser)}
	 * .
	 */
	@Test
	public void testLogUserPurchase() {
		IUser user = new User();
		user.setEmail("admin");

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

		// Creating a log for all the items in the list
		for (IItem iItem : itemsList) {
			try {
				iSalesPersistenceBean.logUserPurchase(iItem, user);
			} catch (SalesPersistenceException e) {
			}
		}

		// Creating a log for all the items in the list
		for (IItem iItem : itemsList) {
			try {
				iSalesPersistenceBean.logUserPurchase(iItem, user);
			} catch (SalesPersistenceException e) {
				assertEquals("The message is different from the expected",
						"Duplicate entry for key: " + iItem.getId() + "-"
								+ user.getEmail(), e.getMessage());
			}
		}

		// Cleaning the user history logs for future testing
		iSalesPersistenceBean.deleteUserPurchaseHistory(user);
	}

	/**
	 * Test method for
	 * {@link sales.persistence.beans.SalesPersistenceBean#getUserPurchaseHistory(users.dtos.IUser)}
	 * .
	 */
	@Test
	public void testGetUserPurchaseHistory() {
		IUser user = new User();
		user.setEmail("admin");

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

		// Creating a log for all the items in the list
		for (IItem iItem : itemsList) {
			try {
				iSalesPersistenceBean.logUserPurchase(iItem, user);
			} catch (SalesPersistenceException e) {
			}
		}

		// Getting the user history logs
		List<IItem> history = iSalesPersistenceBean
				.getUserPurchaseHistory(user);
		assertNotNull("The query found a null list of items", history);
		assertEquals("The items found are different from the expected ones",
				true, history.containsAll(itemsList));

		// Cleaning the user history logs for future testing
		iSalesPersistenceBean.deleteUserPurchaseHistory(user);

		// Getting the user history logs
		history = iSalesPersistenceBean.getUserPurchaseHistory(user);
		assertNull("The query found a list of items that contains elements",
				history);

		// Testing with another user
		user.setEmail("dduran@icesi.edu.co");
		history = iSalesPersistenceBean.getUserPurchaseHistory(user);
		assertNull("The query found a list of items that contains elements",
				history);
	}

}
