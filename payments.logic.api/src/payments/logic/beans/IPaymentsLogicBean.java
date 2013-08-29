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

import java.util.List;

import javax.ejb.Remote;

import payments.dtos.IInvoice;
import payments.dtos.IAdditionalCharge;

import sales.dtos.IItem;
import users.dtos.IUser;

/***
 * Interface definition that contains the PaymentsLogicBean services
 * 
 * @author daviddurangiraldo
 * 
 */
@Remote
public interface IPaymentsLogicBean {

	/**
	 * Generates an invoice for the specified user, depending on the specified
	 * items and the additional charges
	 * 
	 * @param items
	 *            List<IItem> containing the items involved in the sale
	 * @param additionalCharges
	 *            List<IAdditionalCharge> containing the additional charges
	 *            related to the sale
	 * @param user
	 *            IUser containing the user who is buying the specified items
	 * @return IInvoice containing the invoice with the information derived from
	 *         the received data
	 */
	public IInvoice generateInvoice(List<IItem> items,
			List<IAdditionalCharge> additionalCharges, IUser user);

	/**
	 * Updates the information related to the invoice
	 * 
	 * @param iInvoice
	 *            iInvoice containing the new updated information
	 */
	public void updateInvoice(IInvoice iInvoice, IUser user);
}
