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
package creditcard.persistence.beans;

import javax.ejb.Remote;

import payments.dtos.ICreditCard;
import payments.dtos.IInvoice;
import users.dtos.IUser;

/**
 * Interface definition that contains the CreditCardPersistenceBean services
 * 
 * @author daviddurangiraldo
 * 
 */
@Remote
public interface ICreditCardPersistenceBean {

	/**
	 * Associates the specified credit card to the specified invoice
	 * 
	 * @param creditCard
	 *            ICreditCard containing the credit card information
	 * @param invoice
	 *            IInvoice containing the invoice information
	 */
	public void logPayment(ICreditCard creditCard, IInvoice invoice);

	/**
	 * Gets the credit card associated to the specified user
	 * 
	 * @param user
	 *            IUser containing the user's id to search for
	 * @return ICreditCard containing the user's credit card. Null if there is
	 *         no card associated
	 */
	public ICreditCard getUserCreditCard(IUser user);

	/**
	 * Updates the information of the credit card related to the specified user.
	 * If the user does not have a credit card, a new one is created
	 * 
	 * @param creditCard
	 *            ICreditCard containing the updated information
	 * @param user
	 *            IUser containing the user that owns the credit card
	 */
	public void updateUserCreditCard(ICreditCard creditCard, IUser user);

	/**
	 * Deletes all the credit cards stored in the database
	 */
	public void deleteAllCreditCards();
}
