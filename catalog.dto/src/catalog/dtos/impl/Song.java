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

import catalog.dtos.ISong;

/**
 * DTO implementation class for DTO: Song
 * 
 * @author daviddurangiraldo
 * 
 */
public class Song extends Item implements ISong, Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * Integer that represents the song's id
	 */
	private Integer id;
	/**
	 * String that represents the song's name
	 */
	private String name;
	/**
	 * String that represents the song's time (MM:SS)
	 */
	private String time;
	/**
	 * String that represents the song's artist
	 */
	private String artist;
	/**
	 * double that represents the song's price
	 */
	private double price;
	/**
	 * double that represents the song's size in MegaBytes
	 */
	private double size;
	/**
	 * Integer that represents the song's quality in Kbps
	 */
	private Integer quality;
	/**
	 * Integer that represents the song's album id
	 */
	private Integer albumId;

	/**
	 * Default song constructor
	 */
	public Song() {

	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getTime() {
		return time;
	}

	@Override
	public void setTime(String time) throws Exception {
		if (time.matches("\\d\\d:\\d\\d"))
			this.time = time;
		else
			throw new Exception(
					"The specified time does not meet the format (MM:SS)");
	}

	@Override
	public String getArtist() {
		return artist;
	}

	@Override
	public void setArtist(String artist) {
		this.artist = artist;
	}

	@Override
	public double getPrice() {
		return price;
	}

	@Override
	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public double getSize() {
		return size;
	}

	@Override
	public void setSize(double size) {
		this.size = size;
	}

	@Override
	public Integer getQuality() {
		return quality;
	}

	@Override
	public void setQuality(Integer quality) {
		this.quality = quality;
	}

	@Override
	public Integer getAlbumId() {
		return albumId;
	}

	@Override
	public void setAlbumId(Integer albumId) {
		this.albumId = albumId;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		ISong song = (ISong) obj;
		return song != null ? song.getName().equals(name) && song.getArtist().equals(artist) : false;
	}
	
	@Override
	public int hashCode() {
		return (name+artist).hashCode();
	}
	
	public Item toItem(){
		Item item = new Item();
		item.setAlbum(albumId);
		item.setArtist(artist);
		item.setName(name);
		item.setPrice(price);
		item.setSong(id);
		item.setType(IItem.SONG_TYPE);
		return item;
	}
}
