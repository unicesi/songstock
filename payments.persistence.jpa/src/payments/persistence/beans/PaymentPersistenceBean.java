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
package payments.persistence.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import payments.dtos.IAdditionalCharge;
import payments.dtos.IInvoice;
import payments.persistence.entities.AdditionalCharge;
import payments.persistence.entities.AdditionalCharge_Invoice;
import payments.persistence.entities.AdditionalCharge_InvoicePK;
import payments.persistence.entities.Invoice;
import payments.persistence.entities.Item;
import sales.dtos.IItem;

/**
 * Session Bean that implements the IPaymentsPersistenceBean Services
 * 
 * @author daviddurangiraldo
 * 
 */
@Stateless
public class PaymentPersistenceBean implements IPaymentsPersistenceBean {

	@PersistenceContext(unitName = "payments.persistence.jpa")
	private EntityManager entityManager;

	public PaymentPersistenceBean() {

	}

	@Override
	public void updateInvoice(IInvoice iInvoice) {
		Invoice foundInvoice = entityManager.find(Invoice.class,
				iInvoice.getId());
		if (foundInvoice == null) {
			foundInvoice = new Invoice();
			setInvoiceInfo(foundInvoice, iInvoice, false);
		} else {
			updateInvoiceInfo(foundInvoice, iInvoice);
		}
	}

	private void setInvoiceInfo(Invoice invoice, IInvoice iInvoice,
			boolean exists) {
		invoice.setDate(iInvoice.getDate());
		invoice.setId(iInvoice.getId());
		invoice.setPaymentMethod(iInvoice.getPaymentMethod());
		invoice.setPaymentState(iInvoice.getPaymentState());
		invoice.setUser(iInvoice.getUser());
		invoice.setValue(iInvoice.getValue());

		if (!exists) {

			entityManager.persist(invoice);
			// Calendar cal = Calendar.getInstance();
			// cal.setTime(iInvoice.getDate());
			// entityManager
			// .createNativeQuery(
			// "INSERT INTO Invoices (ID, DATE, PAYMENTMETHOD, PAYMENTSTATE, USER, VALUE) VALUES "
			// +
			// "("+iInvoice.getId().intValue()+", " +
			// "'"+cal.get(Calendar.YEAR)+"/"+cal.get(Calendar.MONTH)+"/"+cal.get(Calendar.DATE)+"', "
			// +
			// "'"+iInvoice.getPaymentMethod()+"', " +
			// "'"+iInvoice.getPaymentState()+"', " +
			// "'"+iInvoice.getUser()+"', " +
			// ""+iInvoice.getValue()+")")
			// .executeUpdate();
			entityManager.flush();
		}

		List<AdditionalCharge> additionalCharges = new ArrayList<>();
		for (IAdditionalCharge iAC : iInvoice.getAdditionalCharges()) {
			AdditionalCharge_Invoice aCI = new AdditionalCharge_Invoice();
			AdditionalCharge_InvoicePK aCIPK = new AdditionalCharge_InvoicePK();
			aCIPK.setAdditionalCharge(iAC.getId());
			aCIPK.setInvoice(invoice.getId());
			aCI.setId(aCIPK);
			aCI.setValue(iAC.getValue());
			entityManager.persist(aCI);
			entityManager.flush();
		}
		invoice.setAdditionalCharges(additionalCharges);
		entityManager.merge(invoice);
		entityManager.flush();

		List<Item> items = new ArrayList<>();
		for (IItem iItem : iInvoice.getItems()) {
			Item item = (Item) entityManager.createQuery(
					"SELECT i FROM Item i WHERE i.artist='"
							+ iItem.getArtist().replace("'", "")
							+ "' AND i.name='"
							+ iItem.getName().replace("'", "")
							+ "' AND i.type='" + iItem.getType() + "'")
					.getSingleResult();
			items.add(item);
		}
		invoice.setItems(items);
		entityManager.merge(invoice);
		entityManager.flush();
	}

	private void updateInvoiceInfo(Invoice invoice, IInvoice iInvoice) {
		invoice.setPaymentMethod(iInvoice.getPaymentMethod());
		invoice.setPaymentState(iInvoice.getPaymentState());
		invoice.setValue(iInvoice.getValue());
		invoice.getAdditionalCharges().clear();
		invoice.getItems().clear();
		entityManager.merge(invoice);
		entityManager.flush();

		List<AdditionalCharge> additionalCharges = new ArrayList<>();
		for (IAdditionalCharge iAC : iInvoice.getAdditionalCharges()) {
			AdditionalCharge_Invoice aCI = new AdditionalCharge_Invoice();
			AdditionalCharge_InvoicePK aCIPK = new AdditionalCharge_InvoicePK();
			aCIPK.setAdditionalCharge(iAC.getId());
			aCIPK.setInvoice(invoice.getId());
			aCI.setId(aCIPK);
			aCI.setValue(iAC.getValue());
			if (entityManager.find(AdditionalCharge_Invoice.class, aCIPK) == null) {
				entityManager.persist(aCI);
			} else
				entityManager.merge(aCI);
			entityManager.flush();
		}
		invoice.setAdditionalCharges(additionalCharges);
		entityManager.merge(invoice);
		entityManager.flush();

		List<Item> items = new ArrayList<>();
		for (IItem iItem : iInvoice.getItems()) {
			Item item = (Item) entityManager.createQuery(
					"SELECT i FROM Item i WHERE i.artist='"
							+ iItem.getArtist().replace("'", "")
							+ "' AND i.name='"
							+ iItem.getName().replace("'", "")
							+ "' AND i.type='" + iItem.getType() + "'")
					.getSingleResult();
			items.add(item);
		}
		invoice.setItems(items);
		entityManager.merge(invoice);
		entityManager.flush();
	}

	@Override
	public int generateInvoiceId() {
		long id = (long) entityManager.createQuery(
				"SELECT COUNT(i.id) FROM Invoice i").getSingleResult();
		return (int) id + 1;
	}

	@Override
	public List<IInvoice> findAllPendingInvoices() {
		TypedQuery<Invoice> query = entityManager.createNamedQuery(
				"payments.getAllPendingInvoices", Invoice.class);
		List<Invoice> invoicesFound = query.getResultList();
		List<IInvoice> invoices = new ArrayList<>();
		for (Invoice invoice : invoicesFound) {
			invoices.add(invoice.toBO());
		}
		return invoices;
	}
	
	@Override
	public List<IInvoice> findAllInvoices() {
		TypedQuery<Invoice> query = entityManager.createNamedQuery(
				"payments.getAllInvoices", Invoice.class);
		List<Invoice> invoicesFound = query.getResultList();
		List<IInvoice> invoices = new ArrayList<>();
		for (Invoice invoice : invoicesFound) {
			invoices.add(invoice.toBO());
		}
		return invoices;
	}
	
	@Override
	public void persistAdditionalCharge(IAdditionalCharge additionalCharge) {
		AdditionalCharge aCFound = entityManager.find(AdditionalCharge.class,
				additionalCharge.getId());
		if (aCFound == null) {
			AdditionalCharge aC = new AdditionalCharge();
			aC.setDescription(additionalCharge.getDescription());
			aC.setId(additionalCharge.getId());
			aC.setRate(additionalCharge.getRate());
			entityManager.persist(aC);
			entityManager.flush();
		}
	}

	@Override
	public void deleteAllInvoices() {
		TypedQuery<Invoice> query = entityManager.createNamedQuery(
				"payments.getAllInvoices", Invoice.class);
		List<Invoice> invoicesFound = query.getResultList();

		for (Invoice invoice : invoicesFound) {
			invoice.getAdditionalCharges().clear();
			invoice.getItems().clear();
			entityManager.merge(invoice);
			entityManager.flush();
		}

		query = entityManager.createNamedQuery("payments.deleteAllInvoices",
				Invoice.class);
		query.executeUpdate();
		entityManager.flush();
	}

	@Override
	public List<IAdditionalCharge> findAllAdditionalCharges() {
		TypedQuery<AdditionalCharge> query = entityManager.createNamedQuery(
				"payments.getAllAdditionalCharges", AdditionalCharge.class);
		List<AdditionalCharge> additionalChargesFound = query.getResultList();
		List<IAdditionalCharge> additionalCharges = new ArrayList<>();
		for (AdditionalCharge aC : additionalChargesFound) {
			additionalCharges.add(aC.toBO());
		}
		return additionalCharges;
	}

	@Override
	public void deleteAllAdditionalCharges() {
		TypedQuery<AdditionalCharge_Invoice> queryOne = entityManager
				.createNamedQuery("payments.deleteAllAdditionalChargeInvoice",
						AdditionalCharge_Invoice.class);
		queryOne.executeUpdate();
		entityManager.flush();

		TypedQuery<AdditionalCharge> queryTwo = entityManager.createNamedQuery(
				"payments.deleteAllAdditionalCharges", AdditionalCharge.class);
		queryTwo.executeUpdate();
		entityManager.flush();
	}

}
