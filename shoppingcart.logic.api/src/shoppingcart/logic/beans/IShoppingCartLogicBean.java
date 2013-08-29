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

package shoppingcart.logic.beans;

import java.util.List;

import javax.ejb.Remote;

import sales.dtos.IItem;
import sales.dtos.IShoppingCart;
import shoppingcart.logic.exceptions.ShoppingCartLogicException;
import users.dtos.IUser;

/**
 * Interface defining the ShoppingCartLogicBean services
 * 
 * @author Andrés Paz
 * 
 */
@Remote
public interface IShoppingCartLogicBean {

	/**
	 * Adds the specified item to the specified user's shopping cart.
	 * 
	 * @param item
	 *            to be added
	 * @param user
	 *            of the shopping cart
	 * @throws ShoppingCartLogicException
	 *             if the item is already in the shopping cart or there was a
	 *             problem saving the shopping cart
	 */
	public void addItem(IItem item, IUser user)
			throws ShoppingCartLogicException;

	/**
	 * Removes the specified item from the specified user's shopping cart.
	 * 
	 * @param item
	 *            to be removed
	 * @param user
	 *            of the shopping cart
	 * @throws ShoppingCartLogicException
	 *             if the item is not in the shopping cart or there was a
	 *             problem saving the shopping cart
	 */
	public void removeItem(IItem item, IUser user)
			throws ShoppingCartLogicException;

	/**
	 * Empties the specified user's shopping cart.
	 * 
	 * @param user
	 *            of the shopping cart
	 * @throws ShoppingCartLogicException
	 *             if the shopping cart is already empty or there was a problem
	 *             saving the shopping cart
	 */
	public void empty(IUser user) throws ShoppingCartLogicException;

	/**
	 * Checks out the items in the specified user's shopping cart. The shopping
	 * cart is emptied after using this method.
	 * 
	 * @param user
	 *            of the shopping cart
	 * @return the list of checked out items
	 * @throws ShoppingCartLogicException
	 *             if the shopping cart has no items to checkout or there was a
	 *             problem saving the shopping cart
	 */
	public List<IItem> checkoutAllItems(IUser user)
			throws ShoppingCartLogicException;

	/**
	 * Retrieves the items in the specified user's shopping cart. The shopping
	 * cart is not emptied after using this method.
	 * 
	 * @param user
	 *            of the shopping cart
	 * @return the list of checked out items
	 * @throws ShoppingCartLogicException
	 *             if the shopping cart has no items to checkout or there was a
	 *             problem saving the shopping cart
	 */
	public List<IItem> getAllItems(IUser user)
			throws ShoppingCartLogicException;

	public IShoppingCart getUserShoppingCart(IUser user)
			throws ShoppingCartLogicException;
}
