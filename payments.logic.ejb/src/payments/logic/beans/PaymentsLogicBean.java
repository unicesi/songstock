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
package payments.logic.beans;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import payments.dtos.IInvoice;
import payments.dtos.IAdditionalCharge;
import payments.dtos.impl.Invoice;
import payments.persistence.beans.IPaymentsPersistenceBean;
import sales.dtos.IItem;
import users.dtos.IUser;

/**
 * Session Bean that implements the IPaymentsLogicBean Services
 * 
 * @author daviddurangiraldo
 */
@Stateless
public class PaymentsLogicBean implements IPaymentsLogicBean {

	/**
	 * Bean that contains the basic persistence services
	 */
	@EJB
	private IPaymentsPersistenceBean iPaymentsPersistenceBean;

	/**
	 * Default constructor.
	 */
	public PaymentsLogicBean() {

	}

	@Override
	public IInvoice generateInvoice(List<IItem> items,
			List<IAdditionalCharge> additionalCharges, IUser user) {
		IInvoice invoice = null;
		List<IInvoice> foundInvoices = iPaymentsPersistenceBean.findAllPendingInvoices();
		for (IInvoice i : foundInvoices) {
			if(items.containsAll(i.getItems()))
				invoice = i;
		}
		
		if(invoice == null){
			invoice = new Invoice();
			invoice.setId(iPaymentsPersistenceBean.generateInvoiceId());
			invoice.setDate(new Date());
			invoice.setUser(user.getEmail());
			invoice.setPaymentState(IInvoice.PENDING_STATE);
			invoice.setPaymentMethod("UNDEFINED YET");
		}
		invoice.setValue(calculateInvoiceValue(items, additionalCharges));
		invoice.setAdditionalCharges(additionalCharges);
		invoice.setItems(items);
		updateInvoice(invoice, user);
		return invoice;
	}

	/**
	 * Add all the additional charges to the sum of the item's prices
	 * 
	 * @param items
	 *            List<IItem> containing the items related to the invoice
	 * @param additionalCharges
	 *            List<IAdditionalCharge> containing the additional charges to
	 *            be applied to the invoice
	 * @return double containing the value of the invoice with all its charges
	 */
	private double calculateInvoiceValue(List<IItem> items,
			List<IAdditionalCharge> additionalCharges) {
		double totalWithCharges = 0;
		for (IItem iItem : items) {
			totalWithCharges += iItem.getPrice();
		}
		for (IAdditionalCharge iAdditionalCharge : additionalCharges) {
			totalWithCharges += iAdditionalCharge.getValue();
		}
		return totalWithCharges;
	}

	@Override
	public void updateInvoice(IInvoice iInvoice, IUser user) {
		iPaymentsPersistenceBean.updateInvoice(iInvoice);
	}

}
