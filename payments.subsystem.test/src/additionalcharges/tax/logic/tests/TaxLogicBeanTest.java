package additionalcharges.tax.logic.tests;

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

import additionalcharges.tax.logic.beans.ITaxLogicBean;
import additionalcharges.tax.logic.beans.TaxLogicBean;
import additionalcharges.tax.logic.exceptions.TaxLogicException;

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
public class TaxLogicBeanTest {

	/**
	 * TaxLogicBean instance
	 */
	@EJB
	private ITaxLogicBean taxLogicBean;
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
				.create(JavaArchive.class, "TaxLogicBeanTest.jar")
				.addClasses(IUser.class, User.class, IItem.class, Item.class,
						IAdditionalCharge.class, ICreditCard.class,
						IInvoice.class, AdditionalCharge.class,
						CreditCard.class, Invoice.class, ITaxLogicBean.class,
						TaxLogicException.class, TaxLogicBean.class,
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
	 * Test method for {@link
	 * additionalcharges.tax.logic.beans.TaxLogicBean#calculateTax(java.util.
	 * List<sales.dtos.IItem>)} .
	 */
	@Test
	public void testCalculateTax() {
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

		// Creating a new item
		item = new Item();
		item.setAlbum(3);
		item.setArtist("Survivor");
		item.setName("Is This Love");
		item.setPrice(1.59);
		item.setType(IItem.SONG_TYPE);
		itemsList.add(item);

		// Creating a new item
		item = new Item();
		item.setAlbum(6);
		item.setArtist("The Police");
		item.setName("Every Breath You Take");
		item.setPrice(1.32);
		item.setType(IItem.SONG_TYPE);
		itemsList.add(item);

		IAdditionalCharge taxRate = taxLogicBean.calculateTax(itemsList);

		assertEquals(3, taxRate.getValue(), 76);

		item = new Item();
		item.setName("Painkiller");
		item.setAlbum(7);
		item.setPrice(2.02);
		item.setArtist("Judas Priest");
		item.setSong(16);
		item.setType(IItem.SONG_TYPE);
		itemsList.add(item);

		taxRate = taxLogicBean.calculateTax(itemsList);

		assertEquals(4, taxRate.getValue(), 832);

		paymentsPersistenceBean.deleteAllAdditionalCharges();
	}

}
