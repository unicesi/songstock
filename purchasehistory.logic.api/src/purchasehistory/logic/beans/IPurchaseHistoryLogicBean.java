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
package purchasehistory.logic.beans;

import java.util.List;

import javax.ejb.Remote;

import purchasehistory.logic.exceptions.PurchaseHistoryLogicException;

import sales.dtos.IItem;
import users.dtos.IUser;

/***
 * Interface definition that contains the PurchaseHistoryLogicBean services
 * 
 * @author daviddurangiraldo
 * 
 */
@Remote
public interface IPurchaseHistoryLogicBean {

	/**
	 * Keeps a log of every purchase made by and user for a list of items
	 * 
	 * @param iItem
	 *            IItem containing the item involved in the purchase
	 * @param iUser
	 *            IUser containing the user involved in the purchase
	 * @exception when the log cannot be saved
	 */
	public void logUserPurchase(List<IItem> items, IUser iUser) throws PurchaseHistoryLogicException;

	/**
	 * Gets a list of items that contains all the items that have been purchased
	 * by the specified user
	 * 
	 * @param iUser
	 *            IUser containing the user id to search for
	 * @return List<IItem> a list containing all the items purchased by the
	 *         user, null if there are no matches found
	 */
	public List<IItem> getUserPurchaseHistory(IUser iUser);
}
