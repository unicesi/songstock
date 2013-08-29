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
package catalog.dtos.impl;

import java.io.Serializable;

import sales.dtos.IItem;
import sales.dtos.impl.Item;

import catalog.dtos.IAlbum;

/**
 * DTO implementation class for DTO: Album
 * 
 * @author daviddurangiraldo
 * 
 */
public class Album extends Item implements IAlbum, Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Integer that represents the album's id
	 */
	private Integer id;
	/**
	 * String that represents the album's name
	 */
	private String name;
	/**
	 * String that represents the album's artist
	 */
	private String artist;
	/**
	 * Integer that represents the album's release year
	 */
	private Integer releaseYear;
	/**
	 * String that represents the album's genre
	 */
	private String genre;
	/**
	 * String that represents the album's artwork
	 */
	private String artwork;
	/**
	 * double that represents the album's price
	 */
	private double price;

	/**
	 * Default album's constructor
	 */
	public Album() {

	}

	@Override
	public Integer getId() {
		return this.id;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getArtist() {
		return this.artist;
	}

	@Override
	public void setArtist(String artist) {
		this.artist = artist;
	}

	@Override
	public Integer getReleaseYear() {
		return this.releaseYear;
	}

	@Override
	public void setReleaseYear(Integer releaseYear) {
		this.releaseYear = releaseYear;
	}

	@Override
	public String getGenre() {
		return this.genre;
	}

	@Override
	public void setGenre(String genre) {
		this.genre = genre;
	}

	@Override
	public String getArtwork() {
		return this.artwork;
	}

	@Override
	public void setArtwork(String artwork) {
		this.artwork = artwork;
	}

	@Override
	public double getPrice() {
		return this.price;
	}

	@Override
	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		IAlbum album = (IAlbum) obj;
		return album != null ? album.getArtist().equals(artist) && album.getName().equals(name) : false;
	}
	
	@Override
	public int hashCode() {
		return (name+artist).hashCode();
	}
	
	public Item toItem(){
		Item item = new Item();
		item.setAlbum(id);
		item.setArtist(artist);
		item.setName(name);
		item.setPrice(price);
		item.setSong(super.getSong());
		item.setType(IItem.ALBUM_TYPE);
		return item;
	}
}
