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
import java.util.List;

import javax.persistence.*;

import sales.dtos.IItem;

/**
 * The persistent class for the Items database table.
 * 
 * @author daviddurangiraldo
 */
@Entity
@Table(name = "Items")
@NamedQueries({
// @NamedQuery(name = "sales.deleteItemsFromShoppingCart", query =
// "DELETE FROM Item i WHERE i.id.shoppingCart=:scId"),
// @NamedQuery(name = "sales.getShoppingCartItems", query =
// "SELECT i FROM Item i WHERE i.shoppingCartBean.id=:scId")
})
public class Item implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "album", nullable = true)
	private Integer album;

	@Column(name = "artist")
	private String artist;

	@Column(name = "name")
	private String name;

	@Column(name = "price")
	private double price;

	@Column(name = "song", nullable = true)
	private Integer song;

	@Column(name = "type")
	private String type;

	// bi-directional many-to-one association to PurchaseLog
//	@OneToMany(mappedBy = "itemBean")
//	private List<PurchaseLog> purchaseLogs;

	// bi-directional many-to-many association to ShoppingCart
	@ManyToMany(mappedBy = "items")
	private List<ShoppingCart> shoppingCarts;

	public Item() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getAlbum() {
		return this.album;
	}

	public void setAlbum(Integer album) {
		this.album = album;
	}

	public String getArtist() {
		return this.artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Integer getSong() {
		return this.song;
	}

	public void setSong(Integer song) {
		this.song = song;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

//	public List<PurchaseLog> getPurchaseLogs() {
//		return this.purchaseLogs;
//	}
//
//	public void setPurchaseLogs(List<PurchaseLog> purchaseLogs) {
//		this.purchaseLogs = purchaseLogs;
//	}

	public List<ShoppingCart> getShoppingCarts() {
		return this.shoppingCarts;
	}

	public void setShoppingCarts(List<ShoppingCart> shoppingCarts) {
		this.shoppingCarts = shoppingCarts;
	}

	/**
	 * Wraps this Object into its DTO representation (IItem)
	 * 
	 * @return IItem containing the information attached to this object
	 */
	public IItem toBO() {
		IItem item = new sales.dtos.impl.Item();
		item.setId(id);
		item.setAlbum(album);
		item.setName(name);
		item.setSong(song);
		item.setType(type);
		item.setArtist(artist);
		item.setPrice(price);
		return item;
	}
}