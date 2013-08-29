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
package payments.logic.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import payments.logic.beans.IPaymentsLogicBean;
import payments.logic.beans.PaymentsLogicBean;
import payments.logic.exceptions.PaymentsLogicException;
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
public class PaymentsLogicBeanTest {

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
				.create(JavaArchive.class, "PaymentsLogicBeanTest.jar")
				.addClasses(IUser.class, User.class, IItem.class, Item.class,
						IAdditionalCharge.class, ICreditCard.class,
						IInvoice.class, AdditionalCharge.class,
						CreditCard.class, Invoice.class,
						IPaymentsLogicBean.class, PaymentsLogicException.class,
						PaymentsLogicBean.class,
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
	 * PaymentsLogicBean instance
	 */
	@EJB
	private IPaymentsLogicBean paymentsLogicBean;
	/**
	 * PaymentsPersistenceBean instance
	 */
	@EJB
	private IPaymentsPersistenceBean paymentsPersistenceBean;

	/**
	 * Test method for
	 * {@link payments.logic.beans.PaymentsLogicBean#generateInvoice(java.util.List, java.util.List, users.dtos.IUser)}
	 * .
	 */
	@Test
	public void testGenerateInvoice() {
		IUser user = new User();
		user.setEmail("admin");
		user.setName("admin");
		List<IItem> items = new ArrayList<>();
		List<IAdditionalCharge> additionalCharges = new ArrayList<>();

		paymentsLogicBean.generateInvoice(items, additionalCharges, user);
		IInvoice foundInvoice = paymentsPersistenceBean.findAllInvoices()
				.get(0);
		assertEquals(true, foundInvoice.getId().equals(new Integer(1)));
		assertEquals(true, foundInvoice.getValue() == 0);
		assertEquals(true,
				foundInvoice.getPaymentMethod().equals("UNDEFINED YET"));
		assertEquals(true,
				foundInvoice.getPaymentState().equals(IInvoice.PENDING_STATE));
		
		paymentsPersistenceBean.deleteAllInvoices();
	}

	/**
	 * Test method for
	 * {@link payments.logic.beans.PaymentsLogicBean#updateInvoice(payments.dtos.IInvoice, users.dtos.IUser)}
	 * .
	 */
	@Test
	public void testUpdateInvoice() {
		IUser user = new User();
		user.setEmail("admin");
		user.setName("admin");
		List<IItem> items = new ArrayList<>();
		List<IAdditionalCharge> additionalCharges = new ArrayList<>();

		paymentsLogicBean.generateInvoice(items, additionalCharges, user);
		
		IInvoice invoice = new Invoice();
		invoice.setAdditionalCharges(additionalCharges);
		invoice.setItems(items);
		invoice.setDate(new Date());
		invoice.setId(1);
		invoice.setUser(user.getEmail());
		invoice.setValue(200450);
		invoice.setPaymentMethod("UNDEFINED YET");
		invoice.setPaymentState(IInvoice.PENDING_STATE);
		paymentsLogicBean.updateInvoice(invoice, user);
		
		invoice.setPaymentMethod(IInvoice.CREDIT_CARD_METHOD);
		invoice.setPaymentState(IInvoice.APPROVED_STATE);
		paymentsLogicBean.updateInvoice(invoice, user);
		IInvoice foundInvoice = paymentsPersistenceBean.findAllInvoices().get(0);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		String expectedDate = cal.get(Calendar.YEAR)+"/"+cal.get(Calendar.MONTH)+"/"+cal.get(Calendar.DATE);
		cal.setTime(foundInvoice.getDate());
		String foundDate = cal.get(Calendar.YEAR)+"/"+cal.get(Calendar.MONTH)+"/"+cal.get(Calendar.DATE);
		assertEquals(true, expectedDate.equals(foundDate));
		assertEquals(true, foundInvoice.getId().equals(1));
		assertEquals(true, foundInvoice.getUser().equals(user.getEmail()));
		assertEquals(true, foundInvoice.getValue() == 200450);
		assertEquals(
				true,
				foundInvoice.getPaymentMethod().equals(
						IInvoice.CREDIT_CARD_METHOD));
		assertEquals(true,
				foundInvoice.getPaymentState().equals(IInvoice.APPROVED_STATE));
		
		paymentsPersistenceBean.deleteAllInvoices();
	}

}
