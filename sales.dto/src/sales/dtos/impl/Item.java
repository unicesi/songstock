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

import sales.dtos.IItem;

/**
 * DTO implementation class for DTO: Item
 * 
 * @author daviddurangiraldo
 * 
 */
public class Item implements IItem, Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * String that represents the item's name
	 */
	private String name;
	/**
	 * String that represents the item's artist
	 */
	private String artist;
	/**
	 * String that represents the item's type
	 */
	private String type;
	/**
	 * double that represents the item's rice
	 */
	private double price;
	/**
	 * Integer that represents the item's id
	 */
	private Integer id;
	/**
	 * Integer that represents the item's album id
	 */
	private Integer album;
	/**
	 * Integer that represents the item's song id
	 */
	private Integer song;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getArtist() {
		return artist;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public double getPrice() {
		return price;
	}

	@Override
	public Integer getSong() {
		return song;
	}

	@Override
	public Integer getAlbum() {
		return album;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setArtist(String artist) {
		this.artist = artist;
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public void setSong(Integer song) {
		this.song = song;
	}

	@Override
	public void setAlbum(Integer album) {
		this.album = album;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null) {
			if (this == obj) {
				return true;
			}
			if (!(obj instanceof Item)) {
				return false;
			}
			Item item = (Item) obj;
			return item.getName().equals(name)
					&& item.getArtist().equals(artist)
					&& item.getType().equals(type);
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.artist.hashCode();
		hash = hash * prime + this.name.hashCode();
		hash = hash * prime + this.type.hashCode();

		return hash;
	}
}
