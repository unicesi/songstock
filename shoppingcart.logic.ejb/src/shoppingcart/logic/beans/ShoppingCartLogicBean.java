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

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import sales.dtos.IItem;
import sales.dtos.IShoppingCart;
import sales.persistence.beans.ISalesPersistenceBean;
import sales.persistence.exceptions.SalesPersistenceException;
import shoppingcart.logic.exceptions.ShoppingCartLogicException;
import users.dtos.IUser;

/**
 * Session Bean implementation class ShoppingCartLogicBean
 */
@Stateless
public class ShoppingCartLogicBean implements IShoppingCartLogicBean {

	@EJB
	private ISalesPersistenceBean shoppingCartPersistenceBean;

	/**
	 * Default constructor.
	 */
	public ShoppingCartLogicBean() {
	}

	@Override
	public IShoppingCart getUserShoppingCart(IUser user)
			throws ShoppingCartLogicException {
		IShoppingCart shoppingCart;
		try {
			shoppingCart = shoppingCartPersistenceBean
					.getUserShoppingCart(user);
		} catch (SalesPersistenceException e) {
			throw new ShoppingCartLogicException(
					"There was a problem retrieving your shopping cart. "
							+ e.getMessage(), e);
		}
		return shoppingCart;
	}

	private void updateUserShoppingCart(IShoppingCart shoppingCart, IUser user)
			throws ShoppingCartLogicException {
		try {
			shoppingCartPersistenceBean.updateUserShoppingCart(shoppingCart,
					user);
		} catch (SalesPersistenceException e) {
			throw new ShoppingCartLogicException(
					"There was a problem saving your shopping cart. "
							+ e.getMessage(), e);
		}
	}

	@Override
	public void addItem(IItem item, IUser user)
			throws ShoppingCartLogicException {
		IShoppingCart shoppingCart = getUserShoppingCart(user);

		if (!shoppingCart.getItems().contains(item)) {
			shoppingCart.getItems().add(item);
		} else {
			throw new ShoppingCartLogicException(
					"Item is already in your shopping cart.");
		}

		updateUserShoppingCart(shoppingCart, user);
	}

	@Override
	public void removeItem(IItem item, IUser user)
			throws ShoppingCartLogicException {
		IShoppingCart shoppingCart = getUserShoppingCart(user);

		if (shoppingCart.getItems().contains(item)) {
			shoppingCart.getItems().remove(item);
		} else {
			throw new ShoppingCartLogicException(
					"Item is not in your shopping cart.");
		}

		updateUserShoppingCart(shoppingCart, user);
	}

	@Override
	public void empty(IUser user) throws ShoppingCartLogicException {
		IShoppingCart shoppingCart = getUserShoppingCart(user);

		if (!shoppingCart.getItems().isEmpty()) {
			shoppingCart.getItems().clear();
		} else {
			throw new ShoppingCartLogicException(
					"Your shopping cart is already empty.");
		}

		updateUserShoppingCart(shoppingCart, user);
	}

	@Override
	public List<IItem> checkoutAllItems(IUser user)
			throws ShoppingCartLogicException {
		IShoppingCart shoppingCart = getUserShoppingCart(user);

		List<IItem> items = new ArrayList<>();
		if (!shoppingCart.getItems().isEmpty()) {
			for (IItem iItem : shoppingCart.getItems()) {
//				iItem.setShoppingCart(shoppingCart.getId());
				items.add(iItem);
			}
			shoppingCart.getItems().clear();
		} else {
			throw new ShoppingCartLogicException(
					"Your shopping cart has no items to checkout.");
		}

		updateUserShoppingCart(shoppingCart, user);

		return items;
	}

	@Override
	public List<IItem> getAllItems(IUser user)
			throws ShoppingCartLogicException {
		IShoppingCart shoppingCart = getUserShoppingCart(user);
		return shoppingCart.getItems();
	}

}
