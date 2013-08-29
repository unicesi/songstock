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
package sales.dtos.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import sales.dtos.IItem;
import sales.dtos.IShoppingCart;

/**
 * DTO implementation class for DTO: ShoppingCart
 * 
 * @author daviddurangiraldo
 * 
 */
public class ShoppingCart implements IShoppingCart, Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * Integer that represents the shopping cart id
	 */
	private Integer id;
	
	/**
	 * String that represents the shopping cart user
	 */
	private String user;
	
	/**
	 * List containing the items stored in the shopping cart
	 */
	private List<IItem> items;
	
	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String getUser() {
		return user;
	}

	@Override
	public void setUser(String user) {
		this.user = user;
	}
	
	@Override
	public List<IItem> getItems(){
		return items;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof ShoppingCart)) {
			return false;
		}
		ShoppingCart sC = (ShoppingCart) obj;
		return sC.getId() == id
				&& sC.getUser().equals(user);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.id;
		hash = hash * prime + this.user.hashCode();
		
		return hash;
	}

	@Override
	public void setItems(List<IItem> items) {
		this.items = new ArrayList<>(items);
	}
}
