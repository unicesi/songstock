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
package creditcard.persistence.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import creditcard.persistence.beans.CreditCardPersistenceBean;
import creditcard.persistence.beans.ICreditCardPersistenceBean;
import creditcard.persistence.exceptions.CreditCardPersistenceException;

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

/**
 * 
 * @author daviddurangiraldo
 * 
 */
@RunWith(Arquillian.class)
public class CreditCardPersistenceBeanTest {

	@EJB
	private ICreditCardPersistenceBean creditCardPersistenceBean;
	@EJB
	private IPaymentsPersistenceBean paymentsPersistenceBean;

	/**
	 * Creates a test archive with the classes and configuration files needed to
	 * run the test cases.
	 * 
	 * @return .jar file containing the classes and configuration files needed
	 *         to run the test cases
	 */
	@Deployment
	public static JavaArchive createTestArchive() {
		return ShrinkWrap
				.create(JavaArchive.class, "CreditCardPersistenceBeanTest.jar")
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
				.addAsManifestResource("META-INF/test-persistence.xml",
						"persistence.xml");
	}

	/**
	 * Test method for
	 * {@link creditcard.persistence.beans.CreditCardPersistenceBean#logPayment(payments.dtos.ICreditCard, payments.dtos.IInvoice)}
	 * .
	 */
	@Test
	public void testLogPayment() {
		IUser user = new User();
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

		List<IItem> items = new ArrayList<>();
		List<IAdditionalCharge> additionalCharges = new ArrayList<>();
		IInvoice invoice = new Invoice();
		invoice.setAdditionalCharges(additionalCharges);
		invoice.setItems(items);
		invoice.setDate(new Date());
		invoice.setId(1);
		invoice.setUser(user.getEmail());
		invoice.setValue(200450);
		invoice.setPaymentMethod("UNDEFINED YET");
		invoice.setPaymentState(IInvoice.PENDING_STATE);
		paymentsPersistenceBean.updateInvoice(invoice);

		creditCardPersistenceBean.logPayment(creditCard, invoice);
		invoice.setPaymentState(IInvoice.APPROVED_STATE);
		invoice.setPaymentMethod(IInvoice.CREDIT_CARD_METHOD);
		paymentsPersistenceBean.updateInvoice(invoice);
		invoice = paymentsPersistenceBean.findAllInvoices().get(0);

		assertEquals(IInvoice.CREDIT_CARD_METHOD, invoice.getPaymentMethod());
		assertEquals(IInvoice.APPROVED_STATE, invoice.getPaymentState());

		creditCardPersistenceBean.deleteAllCreditCards();
		paymentsPersistenceBean.deleteAllInvoices();
	}

	/**
	 * Test method for
	 * {@link creditcard.persistence.beans.CreditCardPersistenceBean#getUserCreditCard(users.dtos.IUser)}
	 * .
	 */
	@Test
	public void testGetUserCreditCard() {
		IUser user = new User();
		user.setEmail("admin");

		ICreditCard creditCard = creditCardPersistenceBean
				.getUserCreditCard(user);
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

		ICreditCard foundCreditCard = creditCardPersistenceBean
				.getUserCreditCard(user);
		assertNotNull(foundCreditCard);
		assertEquals(creditCard.getNumber(), foundCreditCard.getNumber());
		assertEquals(creditCard.getBrandName(), foundCreditCard.getBrandName());

		creditCard.setNumber("11114322768765");
		creditCard.setBrandName("Visa");
		creditCardPersistenceBean.updateUserCreditCard(creditCard, user);

		foundCreditCard = creditCardPersistenceBean.getUserCreditCard(user);
		assertEquals(creditCard.getNumber(), foundCreditCard.getNumber());
		assertEquals(creditCard.getBrandName(), foundCreditCard.getBrandName());

		creditCardPersistenceBean.deleteAllCreditCards();
	}

	/**
	 * Test method for
	 * {@link creditcard.persistence.beans.CreditCardPersistenceBean#updateUserCreditCard(users.dtos.IUser)}
	 * .
	 */
	@Test
	public void testUpdateUserCreditCard() {
		testGetUserCreditCard();
	}

}
