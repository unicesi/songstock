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
package sales.dtos;

import java.util.List;

/**
 * Interface definition for DTO: ShoppingCart
 * @author daviddurangiraldo
 *
 */
public interface IShoppingCart {

	/**
	 * Gets the shopping cart id
	 * @return Integer containing the shopping cart id
	 */
	public Integer getId();
	
	/**
	 * Sets the shopping cart id
	 * @param id Integer containing the new shopping cart id
	 */
	public void setId(Integer id);
	
	/**
	 * Gets the shopping cart user
	 * @return String containing the shopping cart user
	 */
	public String getUser();
	
	/**
	 * Sets the shopping cart user
	 * @param user String containing the new shopping cart user
	 */
	public void setUser(String user);
	
	/**
	 * Gets the items stored in the shopping cart
	 * @return List<IItem> containing all the items stored in the shopping cart
	 */
	public List<IItem> getItems();
	
	/**
	 * Sets the shopping cart items
	 * @param items List<IItem> containing the new shopping cart items
	 */
	public void setItems(List<IItem> items);
}
