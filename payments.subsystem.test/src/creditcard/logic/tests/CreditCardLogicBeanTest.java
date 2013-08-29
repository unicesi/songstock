package creditcard.logic.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import payments.dtos.IAdditionalCharge;
import payments.dtos.ICreditCard;
import payments.dtos.IInvoice;
import payments.dtos.impl.AdditionalCharge;
import payments.dtos.impl.CreditCard;
import payments.dtos.impl.Invoice;
import payments.persistence.beans.IPaymentsPersistenceBean;
import payments.persistence.beans.PaymentPersistenceBean;
import payments.persistence.entities.AdditionalCharge_Invoice;
import payments.persistence.entities.AdditionalCharge_InvoicePK;
import payments.persistence.exceptions.PaymentsPersistenceException;
import sales.dtos.IItem;
import sales.dtos.impl.Item;
import users.dtos.IUser;
import users.dtos.impl.User;
import creditcard.logic.beans.CreditCardLogicBean;
import creditcard.logic.beans.ICreditCardLogicBean;
import creditcard.logic.exceptions.CreditCardLogicException;
import creditcard.persistence.beans.CreditCardPersistenceBean;
import creditcard.persistence.beans.ICreditCardPersistenceBean;
import creditcard.persistence.exceptions.CreditCardPersistenceException;

/**
 * 
 * @author daviddurangiraldo
 *
 */
@RunWith(Arquillian.class)
public class CreditCardLogicBeanTest {

	@EJB
	private ICreditCardLogicBean creditCardLogicBean;
	@EJB
	private ICreditCardPersistenceBean creditCardPersistenceBean;
	/**
	 * PaymentsPersistenceBean instance
	 */
	@EJB
	private IPaymentsPersistenceBean paymentsPersistenceBean;
	
	/**
	 * Creates a test archive with the classes and configuration files needed to run the test cases.
	 * 
	 * @return .jar file containing the classes and configuration files needed to run the test cases 
	 */
	@Deployment
	public static JavaArchive createTestArchive() {
		return ShrinkWrap.create(JavaArchive.class, "CreditCardPersistenceBeanTest.jar")
				.addClasses(  
						IUser.class,
						User.class,
						IItem.class,
						Item.class,
						IAdditionalCharge.class,
						ICreditCard.class,
						IInvoice.class,
						AdditionalCharge.class,
						CreditCard.class,
						Invoice.class,
						ICreditCardLogicBean.class,
						CreditCardLogicException.class,
						CreditCardLogicBean.class,
						ICreditCardPersistenceBean.class,
						CreditCardPersistenceBean.class,
						CreditCardPersistenceException.class,
						creditcard.persistence.entities.CreditCard.class,
						creditcard.persistence.entities.CreditCard_Invoice.class,
						IPaymentsPersistenceBean.class,
						PaymentsPersistenceException.class,
						PaymentPersistenceBean.class,
						AdditionalCharge_Invoice.class,
						AdditionalCharge_InvoicePK.class,
						payments.persistence.entities.AdditionalCharge.class,
						payments.persistence.entities.Invoice.class,
						payments.persistence.entities.Item.class)
				.addAsManifestResource("META-INF/test-persistence.xml", "persistence.xml");
	}
	
	/**
	 * Test method for
	 * {@link creditcard.logic.beans.CreditCardLogicBean#authorizeAndCapture(payments.dtos.ICreditCard, payments.dtos.IInvoice)}
	 * .
	 */
	@Test
	public void testAuthorizeAndCapture() {
		User user = new User();
		user.setEmail("admin");
		user.setName("admin");

		IInvoice invoice = new Invoice();
		invoice.setAdditionalCharges(new ArrayList<IAdditionalCharge>());
		invoice.setItems(new ArrayList<IItem>());
		invoice.setDate(new Date());
		invoice.setId(1);
		invoice.setUser(user.getEmail());
		invoice.setValue(200450);
		invoice.setPaymentMethod("UNDEFINED YET");
		invoice.setPaymentState(IInvoice.PENDING_STATE);
		paymentsPersistenceBean.updateInvoice(invoice);
		
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
		
		creditCardLogicBean.authorizeAndCapture(creditCard, invoice);
		
		IInvoice invoiceFound = paymentsPersistenceBean.findAllInvoices().get(0);
		assertEquals(IInvoice.APPROVED_STATE, invoiceFound.getPaymentState());
		assertEquals(IInvoice.CREDIT_CARD_METHOD, invoiceFound.getPaymentMethod());
		
		paymentsPersistenceBean.deleteAllAdditionalCharges();
		creditCardPersistenceBean.deleteAllCreditCards();
		paymentsPersistenceBean.deleteAllInvoices();
	}

	/**
	 * Test method for
	 * {@link creditcard.logic.beans.CreditCardLogicBean#getUserCreditCard(users.dtos.IUser)}
	 * .
	 */
	@Test
	public void testGetUserCreditCard() {
		User user = new User();
		user.setEmail("admin");
		
		ICreditCard creditCard = null;
		try {
			creditCard = creditCardLogicBean.getUserCreditCard(user);
		} catch (CreditCardLogicException e) {
			assertEquals("The User does not have a credit card", e.getMessage());
		}
		assertNull(creditCard);
		
		creditCard = new CreditCard();
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
		
		try {
			ICreditCard creditCardFound = creditCardLogicBean.getUserCreditCard(user);
			
			assertEquals(creditCard.getNumber(), creditCardFound.getNumber());
			assertEquals(creditCard.getBrandName(), creditCardFound.getBrandName());
			assertEquals(creditCard.getCardHolderName(), creditCardFound.getCardHolderName());
			
			creditCardPersistenceBean.deleteAllCreditCards();
		} catch (CreditCardLogicException e) {
			fail("The user's credit card was not found");
		}
	}

	/**
	 * Test method for
	 * {@link creditcard.logic.beans.CreditCardLogicBean#updateUserCreditCard(users.dtos.IUser)}
	 * .
	 */
	@Test
	public void testUpdateUserCreditCard() {
		User user = new User();
		user.setEmail("admin");
		
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
		
		try {
			ICreditCard creditCardFound = creditCardLogicBean.getUserCreditCard(user);
			
			assertEquals(creditCard.getNumber(), creditCardFound.getNumber());
			assertEquals(creditCard.getBrandName(), creditCardFound.getBrandName());
			assertEquals(creditCard.getCardHolderName(), creditCardFound.getCardHolderName());
			
			creditCard.setNumber("11114322768765");
			creditCard.setBrandName("Visa");
			creditCard.setCardHolderName("AvVillas");
			creditCardPersistenceBean.updateUserCreditCard(creditCard, user);
			
			creditCardFound = creditCardLogicBean.getUserCreditCard(user);
			assertEquals(creditCard.getNumber(), creditCardFound.getNumber());
			assertEquals(creditCard.getBrandName(), creditCardFound.getBrandName());
			assertEquals(creditCard.getCardHolderName(), creditCardFound.getCardHolderName());
			
			creditCardPersistenceBean.deleteAllCreditCards();
		} catch (CreditCardLogicException e) {
			fail("The user's credit card was not found");
		}
	}

}
