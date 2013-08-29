/**
 * 
 */
package experiment.performance.purchases;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;

import junit.framework.TestCase;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import additionalcharges.tax.logic.beans.ITaxLogicBean;
import additionalcharges.tax.logic.beans.TaxLogicBean;
import additionalcharges.tax.logic.exceptions.TaxLogicException;

import creditcard.logic.beans.CreditCardLogicBean;
import creditcard.logic.beans.ICreditCardLogicBean;
import creditcard.logic.exceptions.CreditCardLogicException;
import creditcard.persistence.beans.CreditCardPersistenceBean;
import creditcard.persistence.beans.ICreditCardPersistenceBean;
import creditcard.persistence.exceptions.CreditCardPersistenceException;

import payments.dtos.IAdditionalCharge;
import payments.dtos.ICreditCard;
import payments.dtos.IInvoice;
import payments.dtos.impl.AdditionalCharge;
import payments.dtos.impl.CreditCard;
import payments.dtos.impl.Invoice;
import payments.logic.beans.IPaymentsLogicBean;
import payments.logic.beans.PaymentsLogicBean;
import payments.logic.exceptions.PaymentsLogicException;
import payments.persistence.beans.IPaymentsPersistenceBean;
import payments.persistence.beans.PaymentPersistenceBean;
import payments.persistence.entities.AdditionalCharge_Invoice;
import payments.persistence.entities.AdditionalCharge_InvoicePK;
import payments.persistence.exceptions.PaymentsPersistenceException;
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
 * @author Andrés Paz
 *
 */
@RunWith(Arquillian.class)
public class PurchaseTest extends TestCase {

	@Deployment
	public static JavaArchive createTestArchive() {
		return ShrinkWrap.create(JavaArchive.class, "PurchaseTest.jar")
				.addClasses(
						IUser.class, User.class,
						IItem.class, Item.class, payments.persistence.entities.Item.class, sales.persistence.entities.Item.class,
						IAdditionalCharge.class, AdditionalCharge.class, payments.persistence.entities.AdditionalCharge.class, AdditionalCharge_Invoice.class, AdditionalCharge_InvoicePK.class,
						ICreditCard.class, CreditCard.class,
						IInvoice.class, Invoice.class, payments.persistence.entities.Invoice.class,
						IPaymentsLogicBean.class, PaymentsLogicBean.class, PaymentsLogicException.class,
						IPaymentsPersistenceBean.class, PaymentsPersistenceException.class, PaymentPersistenceBean.class,
						IShoppingCart.class, ShoppingCart.class,
						ISalesPersistenceBean.class, SalesPersistenceException.class, SalesPersistenceBean.class,
						sales.persistence.entities.ShoppingCart.class,
						sales.persistence.entities.PurchaseLogPK.class, sales.persistence.entities.PurchaseLog.class,
						IShoppingCartLogicBean.class, ShoppingCartLogicBean.class, ShoppingCartLogicException.class,
						IPurchaseHistoryLogicBean.class, PurchaseHistoryLogicBean.class, PurchaseHistoryLogicException.class,
						ICreditCard.class, CreditCard.class, creditcard.persistence.entities.CreditCard.class, creditcard.persistence.entities.CreditCard_Invoice.class,
						ICreditCardLogicBean.class, CreditCardLogicException.class, CreditCardLogicBean.class,
						ICreditCardPersistenceBean.class, CreditCardPersistenceBean.class, CreditCardPersistenceException.class,
						ITaxLogicBean.class, TaxLogicException.class, TaxLogicBean.class)
				.addAsManifestResource("META-INF/payments-test-persistence.xml", "p-persistence.xml")
				.addAsManifestResource("META-INF/sales-test-persistence.xml", "s-persistence.xml");
	}
	
	@EJB
	private IShoppingCartLogicBean shoppingCartLogicBean;
	
	@EJB
	private IPaymentsLogicBean paymentsLogicBean;
	
	@EJB
	private IPaymentsPersistenceBean paymentsPersistenceBean;
	
	@EJB
	private ITaxLogicBean taxLogicBean;
	
	@EJB
	private ICreditCardLogicBean creditCardLogicBean;
	
	@EJB
	private ICreditCardPersistenceBean creditCardPersistenceBean;
	
	@Test
	public void testPurchase() {
		try {
			// Create a test user
			IUser user = new User();
			user.setEmail("admin");
			
			// Add a test item to the test shopping cart
			IItem item = addTestItemToTestShoppingCart(user);
			if (item != null) {
				// Get the test items from the test shopping cart
				List<IItem> items = shoppingCartLogicBean.getUserShoppingCart(user).getItems();
				
				// Calculate tax additional charge
				IAdditionalCharge tax = taxLogicBean.calculateTax(items);
				List<IAdditionalCharge> additionalCharges = new ArrayList<>();
				additionalCharges.add(tax);
				
				// Generate purchase invoice
				IInvoice invoice = paymentsLogicBean.generateInvoice(items, additionalCharges, user);
				// Set credit card as payment method
				invoice.setPaymentMethod(IInvoice.CREDIT_CARD_METHOD);
				// Update the invoice
				paymentsLogicBean.updateInvoice(invoice, user);
				
				// Create test credit card and associate it with the test user
				ICreditCard creditCard = addTestCreditCardToTestUser(user);
				
				// Authorize purchase and capture funds
				creditCardLogicBean.authorizeAndCapture(creditCard, invoice);
				
				// Set invoice state to approved
				invoice.setPaymentState(IInvoice.APPROVED_STATE);
				// Update the invoice
				paymentsLogicBean.updateInvoice(invoice, user);
				
				IInvoice foundInvoice = paymentsPersistenceBean.findAllInvoices().get(0);
				assertEquals(true, foundInvoice.getPaymentState().equals(IInvoice.APPROVED_STATE));
			
				// Checkout the item
				checkoutItemFromShoppingCart(user, item);
				
				clean();
			} else {
				// Fail the test if there is no test item.
				fail("Test item is null.");
			}
		} catch (ShoppingCartLogicException e) {
			// Fail the test if an exception is thrown.
			fail(e.getMessage());
		}
	}
	
	private IItem addTestItemToTestShoppingCart(IUser user) {
		IItem item = null;
		try {
			// Test item (song type)
			item = new Item();
			item.setName("Painkiller");
			item.setAlbum(7);
			item.setArtist("Judas Priest");
			item.setSong(16);
			item.setType(IItem.SONG_TYPE);

			// Add the item to the user's shopping cart
			shoppingCartLogicBean.addItem(item, user);
		} catch (ShoppingCartLogicException e) {
			// Fail the test if an exception is thrown.
			fail(e.getMessage());
		}
		return item;
	}

	private ICreditCard addTestCreditCardToTestUser(IUser user) {
		ICreditCard creditCard = new CreditCard();
		creditCard.setNumber("23454322768765");
		creditCard.setExpirationDate(new Date());
		creditCard.setPhoneNumber("3458751");
		creditCard.setBillingAddress("Billing Address");
		creditCard.setBrandName("Master Card");
		creditCard.setCardHolderName("Bancolombia");
		creditCard.setCity("Cali");
		creditCard.setCountry("Colombia");
		creditCard.setState("Valle del Cauca");
		creditCard.setUser(user.getEmail());
		creditCard.setZipCode("0000");
		creditCardPersistenceBean.updateUserCreditCard(creditCard, user);
		return creditCard;
	}
	
	private void checkoutItemFromShoppingCart(IUser user, IItem item) {
		try {
			// Checkout all the items in the user's shopping cart
			List<IItem> shoppingCartItems = shoppingCartLogicBean.checkoutAllItems(user);
			// If the checked out items contain the item the test is passed
			assertTrue(shoppingCartItems.contains(item));
		} catch (ShoppingCartLogicException e) {
			// Fail the test if an exception is thrown.
			fail(e.getMessage());
		}
	}

	private void clean() {
		// Delete all additional charges
		paymentsPersistenceBean.deleteAllAdditionalCharges();
		// Delete all invoices
		paymentsPersistenceBean.deleteAllInvoices();
		// Delete all credit cards
		creditCardPersistenceBean.deleteAllCreditCards();
	}
}
