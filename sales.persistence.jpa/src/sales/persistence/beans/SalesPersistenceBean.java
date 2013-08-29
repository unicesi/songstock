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

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import sales.dtos.IItem;
import sales.dtos.IShoppingCart;
import sales.persistence.beans.ISalesPersistenceBean;
import sales.persistence.entities.Item;
import sales.persistence.entities.PurchaseLog;
import sales.persistence.entities.PurchaseLogPK;
import sales.persistence.entities.ShoppingCart;
import sales.persistence.exceptions.SalesPersistenceException;
import users.dtos.IUser;

/**
 * Session Bean that implements the ISalesPersistenceBean Services
 * 
 * @author daviddurangiraldo
 * 
 */
@Stateless
public class SalesPersistenceBean implements ISalesPersistenceBean {

	@PersistenceContext(unitName = "sales.persistence.jpa")
	private EntityManager entityManager;

	public SalesPersistenceBean() {

	}

	@Override
	public IShoppingCart getUserShoppingCart(IUser iUser) {
		TypedQuery<ShoppingCart> query = entityManager.createNamedQuery(
				"sales.getUserShoppingCart", ShoppingCart.class);
		query.setParameter("userId", iUser.getEmail());
		ShoppingCart foundShoppingCart = null;
		try {
			foundShoppingCart = query.getSingleResult();
			List<Item> items = foundShoppingCart.getItems();
			foundShoppingCart.setItems(items);
		} catch (Exception e) {
			foundShoppingCart = null;
		}
		IShoppingCart iFoundShoppingCart = null;
		if (foundShoppingCart != null)
			iFoundShoppingCart = foundShoppingCart.toBO();
		else {
			foundShoppingCart = new ShoppingCart();
			long max = (long) entityManager.createQuery(
					"SELECT COUNT(sc.id) FROM ShoppingCart sc")
					.getSingleResult();
			foundShoppingCart.setId((int) max);
			foundShoppingCart.setUser(iUser.getEmail());
			iFoundShoppingCart = foundShoppingCart.toBO();
			// entityManager.persist(foundShoppingCart);
			entityManager.createNativeQuery(
					"INSERT INTO ShoppingCarts (id, user) VALUES (" + (int) max
							+ ", '" + iUser.getEmail() + "')").executeUpdate();
			entityManager.flush();
		}
		return iFoundShoppingCart;
	}

	@Override
	public void updateUserShoppingCart(IShoppingCart iShoppingCart, IUser iUser)
			throws SalesPersistenceException {
		try {
			ShoppingCart sC = entityManager.find(ShoppingCart.class,
					iShoppingCart.getId());
			sC.getItems().clear();
			entityManager.merge(sC);
			entityManager.flush();

			Item item = null;
			for (IItem iItem : iShoppingCart.getItems()) {
				try {
					item = (Item) entityManager.createQuery(
							"SELECT i FROM Item i WHERE i.artist='"
									+ iItem.getArtist().replace("'", "")
									+ "' AND i.name='"
									+ iItem.getName().replace("'", "")
									+ "' AND i.type='" + iItem.getType() + "'")
							.getSingleResult();
				} catch (Exception e) {
					item = null;
				}
				if (item == null) {
					item = new Item();
					long max = (long) entityManager.createQuery(
							"SELECT COUNT(i.id) FROM Item i").getSingleResult();
					item.setAlbum(iItem.getAlbum());
					item.setName(iItem.getName().replace("'", ""));
					item.setSong(iItem.getSong());
					item.setType(iItem.getType());
					item.setArtist(iItem.getArtist().replace("'", ""));
					item.setId((int) max);
					item.setPrice(iItem.getPrice());
					entityManager.persist(item);
					entityManager.flush();
				}
				sC.getItems().add(item);
			}
			entityManager.merge(sC);
			entityManager.flush();
		} catch (Exception e) {
			throw new SalesPersistenceException(e.getMessage());
		}
	}

	@Override
	public void logUserPurchase(IItem iItem, IUser iUser)
			throws SalesPersistenceException {
		PurchaseLogPK pk = new PurchaseLogPK();
		pk.setItem(iItem.getId());
		pk.setUser(iUser.getEmail());

		PurchaseLog pL = new PurchaseLog();
		pL.setId(pk);
		pL.setItemBean(entityManager.find(Item.class, iItem.getId()));

		PurchaseLog pLFound = entityManager.find(PurchaseLog.class, pk);
		if (pLFound == null)
			entityManager.persist(pL);
		else {
			throw new SalesPersistenceException("Duplicate entry for key: "
					+ pk.getItem() + "-" + pk.getUser());
		}
	}

	@Override
	public List<IItem> getUserPurchaseHistory(IUser iUser) {
		TypedQuery<PurchaseLog> itemsQuery = entityManager.createNamedQuery(
				"sales.getUserPurchaseHistory", PurchaseLog.class);
		itemsQuery.setParameter("userId", iUser.getEmail());
		List<PurchaseLog> items = itemsQuery.getResultList();
		List<IItem> iItems = new ArrayList<>();
		IItem iItem;
		for (PurchaseLog purchaseLog : items) {
			iItem = new sales.dtos.impl.Item();
			iItem.setAlbum(purchaseLog.getItemBean().getAlbum());
			iItem.setArtist(purchaseLog.getItemBean().getArtist());
			iItem.setName(purchaseLog.getItemBean().getName());
			iItem.setPrice(purchaseLog.getItemBean().getPrice());
			iItem.setSong(purchaseLog.getItemBean().getSong());
			iItem.setType(purchaseLog.getItemBean().getType());
			iItem.setId(purchaseLog.getItemBean().getId());
			iItems.add(iItem);
		}
		return iItems.isEmpty() ? null : iItems;
	}

	@Override
	public void deleteUserPurchaseHistory(IUser iUser) {
		TypedQuery<PurchaseLog> query = entityManager.createNamedQuery(
				"sales.deleteUserPurchaseHistory", PurchaseLog.class);
		query.setParameter("userId", iUser.getEmail());
		query.executeUpdate();
		entityManager.flush();
	}
}
