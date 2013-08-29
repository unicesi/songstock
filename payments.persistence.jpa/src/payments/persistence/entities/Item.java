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
package payments.persistence.entities;

import java.io.Serializable;
import javax.persistence.*;

import sales.dtos.IItem;

import java.util.List;


/**
 * The persistent class for the Items database table.
 * 
 */
@Entity
@Table(name="Items")
public class Item implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name = "album", nullable = true)
	private Integer album;

	private String artist;

	private String name;

	private double price;

	@Column(name = "song", nullable = true)
	private Integer song;

	private String type;

	//bi-directional many-to-many association to Invoice
	@ManyToMany(mappedBy="items")
	private List<Invoice> invoices;

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

	public List<Invoice> getInvoices() {
		return this.invoices;
	}

	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
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