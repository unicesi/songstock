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

import java.util.List;

import javax.ejb.Remote;

import payments.dtos.IAdditionalCharge;
import payments.dtos.IInvoice;

/**
 * Interface definition that contains the PaymentsPersistenceBean services
 * 
 * @author daviddurangiraldo
 * 
 */
@Remote
public interface IPaymentsPersistenceBean {

	/**
	 * Updates the information related to the invoice
	 * 
	 * @param iInvoice
	 *            iInvoice containing the new updated information
	 */
	public void updateInvoice(IInvoice iInvoice);

	/**
	 * Generates the invoice's id based on the number of items stored in the
	 * database
	 * 
	 * @return int containing the invoice's id
	 */
	public int generateInvoiceId();

	/**
	 * Register a new additional charge with the given information
	 * 
	 * @param additionalCharge
	 *            IAdditionalCharge containing the information to store
	 */
	public void persistAdditionalCharge(IAdditionalCharge additionalCharge);

	/**
	 * Gets all the invoices that have the payment state as pending
	 * 
	 * @return List<IInvoice> containing the invoices that match the criteria
	 */
	public List<IInvoice> findAllPendingInvoices();

	/**
	 * Gets all the invoices registered in the database
	 * 
	 * @return List<IInvoice> containing all the invoices stored in the database
	 */
	public List<IInvoice> findAllInvoices();

	/**
	 * Deletes all the invoices stored in the database
	 */
	public void deleteAllInvoices();
	
	/**
	 * Gets all the additional charges registered in the database
	 * 
	 * @return List<IAdditionalCharge> containing all the additional charges stored in the database
	 */
	public List<IAdditionalCharge> findAllAdditionalCharges();
	
	/**
	 * Deletes all the invoices stored in the database
	 */
	public void deleteAllAdditionalCharges();
}
