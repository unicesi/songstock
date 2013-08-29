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
package sales.persistence.beans;

import java.util.List;

import javax.ejb.Remote;

import sales.dtos.IItem;
import sales.dtos.IShoppingCart;
import sales.persistence.exceptions.SalesPersistenceException;
import users.dtos.IUser;

/**
 * Interface definition that contains the SalesPersistenceBean services
 * 
 * @author daviddurangiraldo
 * 
 */
@Remote
public interface ISalesPersistenceBean {

	/**
	 * Gets the shopping cart related to the specified user. If there isn't such
	 * a relationship, a new shopping cart is assigned to the user
	 * 
	 * @param iUser
	 *            IUser containing the id of the user to search for
	 * @return IShoppingCart containing the shopping cart found. Null if there
	 *         is no match found
	 * @exception if
	 *                the shopping cart cannot be allocated
	 */
	public IShoppingCart getUserShoppingCart(IUser iUser)
			throws SalesPersistenceException;

	/**
	 * Updates the shopping cart related to the specified user with a new
	 * shopping cart reference
	 * 
	 * @param iShoppingCart
	 *            IShoppingCart containing the information of the new shopping
	 *            cart
	 * @param iUser
	 *            IUser containing the id of the user that owns the shopping
	 *            cart
	 * @exception if
	 *                the shopping cart cannot be updated
	 */
	public void updateUserShoppingCart(IShoppingCart iShoppingCart, IUser iUser)
			throws SalesPersistenceException;

	/**
	 * Keeps a log of a purchase made by and user for a particular item
	 * 
	 * @param iItem
	 *            IItem containing the item involved in the purchase
	 * @param iUser
	 *            IUser containing the user involved in the purchase
	 * @exception if
	 *                the log cannot be saved
	 */
	public void logUserPurchase(IItem iItem, IUser iUser)
			throws SalesPersistenceException;

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

	/**
	 * Clears all the user history logs
	 * 
	 * @param iUser
	 *            IUser containing the user id to search for
	 */
	public void deleteUserPurchaseHistory(IUser iUser);
}
