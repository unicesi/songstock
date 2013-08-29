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
package catalog.persistence.entities;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.String;
import javax.persistence.*;

import catalog.dtos.IAlbum;

/**
 * Entity implementation class for Entity: Album
 * 
 * @author daviddurangiraldo
 */
@Entity
@Table(name = "Albums")
@NamedQueries({
		@NamedQuery(name = "catalog.getAlbumsWithNameContaining", query = "SELECT a FROM Album a WHERE a.name LIKE :keyword"),
		@NamedQuery(name = "catalog.getAlbumsFromArtist", query = "SELECT a FROM Album a WHERE a.artist LIKE :artist"),
		@NamedQuery(name = "catalog.getAllAlbums", query = "SELECT a FROM Album a"),
		@NamedQuery(name = "catalog.getAlbumDetails", query = "SELECT a FROM Album a WHERE a.id=:albumId") })
public class Album implements Serializable, IAlbum {

	/**
	 * Integer that represents the album's id
	 */
	@Id
	@Column(name = "id")
	private Integer id;
	/**
	 * String that represents the album's name
	 */
	@Column(name = "name")
	private String name;
	/**
	 * String that represents the album's artist
	 */
	@Column(name = "artist")
	private String artist;
	/**
	 * Integer that represents the album's release year
	 */
	@Column(name = "releaseYear")
	private Integer releaseYear;
	/**
	 * String that represents the album's genre
	 */
	@Column(name = "genre")
	private String genre;
	/**
	 * String that represents the album's artwork
	 */
	@Column(name = "artwork")
	private String artwork;
	/**
	 * double that represents the album's price
	 */
	@Column(name = "price")
	private double price;
	private static final long serialVersionUID = 1L;

	/**
	 * Default album's constructor
	 */
	public Album() {
		super();
	}

	@Override
	public boolean equals(Object obj) {
		Album album = (Album) obj;
		return album.artist.equals(artist) && album.name.equals(name);
	}

	@Override
	public int hashCode() {
		return (name + artist).hashCode();
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

	/**
	 * Wraps this Object into its DTO representation (IAlbum)
	 * 
	 * @return IAlbum containing the information attached to this object
	 */
	public IAlbum toBO() {
		IAlbum iAlbum = new catalog.dtos.impl.Album();
		iAlbum.setArtist(artist);
		iAlbum.setArtwork(artwork);
		iAlbum.setGenre(genre);
		iAlbum.setId(id);
		iAlbum.setName(name);
		iAlbum.setPrice(price);
		iAlbum.setReleaseYear(releaseYear);
		return iAlbum;
	}
}
