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
package payments.persistence.tests;

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
public class PaymentPersistenceBeanTest {

	/**
	 * PaymentsPersistenceBean instance
	 */
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
				.create(JavaArchive.class, "PaymentsPersistenceBeanTest.jar")
				.addClasses(IUser.class, User.class, IItem.class, Item.class,
						IAdditionalCharge.class, ICreditCard.class,
						IInvoice.class, AdditionalCharge.class,
						CreditCard.class, Invoice.class,
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
	 * {@link payments.persistence.beans.PaymentPersistenceBean#updateInvoice(payments.dtos.IInvoice)}
	 * .
	 */
	@Test
	public void testUpdateInvoice() {
		IUser user = new User();
		user.setEmail("admin");
		user.setName("admin");
		List<IInvoice> invoices = new ArrayList<>();
		List<IInvoice> foundInvoices;

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
		invoices.add(invoice);
		foundInvoices = paymentsPersistenceBean.findAllInvoices();
		assertNotNull(foundInvoices);
		assertEquals(
				"The invoice wasn't updated sucessfully",
				true,
				invoices.get(0).getPaymentMethod().equals("UNDEFINED YET")
						&& invoices.get(0).getPaymentState()
								.equals(IInvoice.PENDING_STATE));

		invoice.setPaymentMethod(IInvoice.CREDIT_CARD_METHOD);
		invoice.setPaymentState(IInvoice.APPROVED_STATE);

		paymentsPersistenceBean.updateInvoice(invoice);
		foundInvoices = paymentsPersistenceBean.findAllInvoices();
		assertNotNull(foundInvoices);
		assertEquals(
				"The invoice wasn't updated sucessfully",
				true,
				invoices.get(0).getPaymentMethod()
						.equals(IInvoice.CREDIT_CARD_METHOD)
						&& invoices.get(0).getPaymentState()
								.equals(IInvoice.APPROVED_STATE));

		paymentsPersistenceBean.deleteAllInvoices();
		foundInvoices = paymentsPersistenceBean.findAllInvoices();
		assertEquals(true, foundInvoices.size() == 0);
	}

	/**
	 * Test method for
	 * {@link payments.persistence.beans.PaymentPersistenceBean#generateInvoiceId()}
	 * .
	 */
	@Test
	public void testGenerateInvoiceId() {
		IUser user = new User();
		user.setEmail("admin");
		user.setName("admin");
		List<IInvoice> foundInvoices;

		IInvoice invoice = new Invoice();
		invoice.setAdditionalCharges(new ArrayList<IAdditionalCharge>());
		invoice.setItems(new ArrayList<IItem>());
		invoice.setDate(new Date());
		invoice.setId(paymentsPersistenceBean.generateInvoiceId());
		invoice.setUser(user.getEmail());
		invoice.setValue(200450);
		invoice.setPaymentMethod("UNDEFINED YET");
		invoice.setPaymentState(IInvoice.PENDING_STATE);
		paymentsPersistenceBean.updateInvoice(invoice);
		foundInvoices = paymentsPersistenceBean.findAllInvoices();
		assertEquals(true, foundInvoices.get(0).getId().equals(new Integer(1)));

		assertEquals(true, paymentsPersistenceBean.generateInvoiceId() == 2);

		invoice.setPaymentMethod(IInvoice.CREDIT_CARD_METHOD);
		invoice.setPaymentState(IInvoice.APPROVED_STATE);
		paymentsPersistenceBean.updateInvoice(invoice);

		invoice.setId(paymentsPersistenceBean.generateInvoiceId());
		paymentsPersistenceBean.updateInvoice(invoice);

		assertEquals(true, paymentsPersistenceBean.generateInvoiceId() == 3);
		paymentsPersistenceBean.deleteAllInvoices();
	}

	/**
	 * Test method for
	 * {@link payments.persistence.beans.PaymentPersistenceBean#findAllInvoices()}
	 * .
	 */
	@Test
	public void testFindAllInvoices() {
		IUser user = new User();
		user.setEmail("admin");
		user.setName("admin");
		List<IInvoice> foundInvoices;
		List<IInvoice> invoices = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			IInvoice invoice = new Invoice();
			invoice.setAdditionalCharges(new ArrayList<IAdditionalCharge>());
			invoice.setItems(new ArrayList<IItem>());
			invoice.setDate(new Date());
			invoice.setId(paymentsPersistenceBean.generateInvoiceId());
			invoice.setUser(user.getEmail());
			invoice.setValue(200450);
			invoice.setPaymentMethod(IInvoice.CREDIT_CARD_METHOD);
			invoice.setPaymentState(IInvoice.APPROVED_STATE);
			paymentsPersistenceBean.updateInvoice(invoice);
			invoices.add(invoice);
		}
		foundInvoices = paymentsPersistenceBean.findAllInvoices();
		int c = 1;
		for (IInvoice iInvoice : foundInvoices) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			String expectedDate = cal.get(Calendar.YEAR)+"/"+cal.get(Calendar.MONTH)+"/"+cal.get(Calendar.DATE);
			cal.setTime(iInvoice.getDate());
			String foundDate = cal.get(Calendar.YEAR)+"/"+cal.get(Calendar.MONTH)+"/"+cal.get(Calendar.DATE);
			assertEquals(true, expectedDate.equals(foundDate));
			assertEquals(true, iInvoice.getId().equals(c));
			assertEquals(true, iInvoice.getUser().equals(user.getEmail()));
			assertEquals(true, iInvoice.getValue() == 200450);
			assertEquals(
					true,
					iInvoice.getPaymentMethod().equals(
							IInvoice.CREDIT_CARD_METHOD));
			assertEquals(true,
					iInvoice.getPaymentState().equals(IInvoice.APPROVED_STATE));
			c++;
		}
		paymentsPersistenceBean.deleteAllInvoices();
	}

	/**
	 * Test method for
	 * {@link payments.persistence.beans.PaymentPersistenceBean#persistAdditionalCharge(payments.dtos.IAdditionalCharge)}
	 * .
	 */
	@Test
	public void testPersistAdditionalCharge(){
		IAdditionalCharge additionalCharge = new AdditionalCharge();
		additionalCharge.setDescription("16% per item");
		additionalCharge.setId("Tax Rate");
		additionalCharge.setRate(0.16);
		additionalCharge.setValue(0);
		
		paymentsPersistenceBean.persistAdditionalCharge(additionalCharge);
		
		IAdditionalCharge additionalChargeFound = paymentsPersistenceBean.findAllAdditionalCharges().get(0);
		
		assertNotNull(additionalCharge);
		assertEquals(additionalCharge.getDescription(), additionalChargeFound.getDescription());
		assertEquals(additionalCharge.getId(), additionalChargeFound.getId());
		assertEquals(additionalCharge.getRate(), additionalChargeFound.getRate());	
		
		paymentsPersistenceBean.deleteAllAdditionalCharges();
	}
}
