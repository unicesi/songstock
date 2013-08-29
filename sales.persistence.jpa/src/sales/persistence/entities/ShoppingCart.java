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
package sales.persistence.entities;

import java.io.Serializable;
import javax.persistence.*;

import sales.dtos.IItem;
import sales.dtos.IShoppingCart;

import java.util.ArrayList;
import java.util.List;

/**
 * The persistent class for the ShoppingCarts database table.
 * 
 * @author daviddurangiraldo
 */
@Entity
@Table(name = "ShoppingCarts")
@NamedQueries({ @NamedQuery(name = "sales.getUserShoppingCart", query = "SELECT sc FROM ShoppingCart sc WHERE sc.user=:userId") })
public class ShoppingCart implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "user")
	private String user;

	// bi-directional many-to-many association to Item
//	@ManyToMany(fetch = FetchType.EAGER, targetEntity = Item.class, cascade = {
//			CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH })
	@ManyToMany
	@JoinTable(name = "ShoppingCarts_Items", joinColumns = { @JoinColumn(name = "shoppingCart", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "item", referencedColumnName = "id") })
	private List<Item> items;

	public ShoppingCart() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public List<Item> getItems() {
		return this.items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	/**
	 * Wraps this Object into its DTO representation (IShoppingCart)
	 * 
	 * @return IShoppingCart containing the information attached to this object
	 */
	public IShoppingCart toBO() {
		IShoppingCart sC = new sales.dtos.impl.ShoppingCart();
		sC.setId(id);
		sC.setUser(user);
		ArrayList<IItem> iItems = new ArrayList<>();
		if (items != null) {
			for (Item item : items) {
				iItems.add(item.toBO());
			}
		}
		sC.setItems(iItems);
		return sC;
	}
}