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
import javax.persistence.*;

import catalog.dtos.ISong;
import catalog.persistence.exceptions.CatalogPersistenceException;

/**
 * Entity implementation class for Entity: Song
 * 
 */
@Entity
@Table(name = "Songs")
@NamedQueries({
		@NamedQuery(name = "catalog.getSongsWithNameContaining", query = "SELECT s FROM Song s WHERE s.name LIKE :keyword"),
		@NamedQuery(name = "catalog.getSongsFromArtist", query = "SELECT s FROM Song s WHERE s.artist LIKE :artist"),
		@NamedQuery(name = "catalog.getAlbumSongs", query = "SELECT s FROM Song s WHERE s.albumId=:albumId"),
		@NamedQuery(name = "catalog.getSongDetails", query = "SELECT s FROM Song s WHERE s.id=:songId")})
public class Song implements Serializable, ISong {

	private static final long serialVersionUID = 1L;
	/**
	 * Integer that represents the song's id
	 */
	@Id
	@Column(name = "id")
	private Integer id;
	/**
	 * String that represents the song's name
	 */
	@Column(name = "name")
	private String name;
	/**
	 * String that represents the song's time (MM:SS)
	 */
	@Column(name = "time")
	private String time;
	/**
	 * String that represents the song's artist
	 */
	@Column(name = "artist")
	private String artist;
	/**
	 * double that represents the song's price
	 */
	@Column(name = "price")
	private double price;
	/**
	 * double that represents the song's size in MegaBytes
	 */
	@Column(name = "size")
	private double size;
	/**
	 * Integer that represents the song's quality in Kbps
	 */
	@Column(name = "quality")
	private Integer quality;
	/**
	 * Integer that represents the song's album id
	 */
	@Column(name = "album")
	private Integer albumId;

	/**
	 * Default song constructor
	 */
	public Song() {
		super();
	}
	
	@Override
	public boolean equals(Object obj) {
		Song song = (Song) obj;
		return song.name.equals(name) && song.artist.equals(artist);
	}
	
	@Override
	public int hashCode() {
		return (name+artist).hashCode();
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
	public void setTime(String time) throws CatalogPersistenceException {
		if (time.matches("\\d\\d:\\d\\d"))
			this.time = time;
		else
			throw new CatalogPersistenceException(
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

	/**
	 * Wraps this Object into its DTO representation (ISong)
	 * 
	 * @return ISong containing the information attached to this object
	 */
	public ISong toBO() {
		ISong iSong = new catalog.dtos.impl.Song();
		iSong.setArtist(artist);
		iSong.setName(name);
		iSong.setPrice(price);
		iSong.setQuality(quality);
		iSong.setSize(size);
		iSong.setId(id);
		iSong.setAlbumId(albumId);
		try {
			iSong.setTime(time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return iSong;
	}

}
