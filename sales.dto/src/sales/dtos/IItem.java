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

/**
 * Interface definition for DTO: Item
 * @author daviddurangiraldo
 *
 */
public interface IItem {

	/**
	 * Constant that defines the type album
	 */
	public static final String ALBUM_TYPE = "Album";
	/**
	 * Constant that defines the type song
	 */
	public static final String SONG_TYPE = "Song";
	
	/**
	 * Gets the item's id
	 * @return Integer containing the item's id
	 */
	public Integer getId();
	
	/**
	 * Sets the item's id
	 * @param id Integer containing the new item's id
	 */
	public void setId(Integer id);
	
	/**
	 * Gets the item's name
	 * @return String containing the item's name
	 */
	public String getName();
	
	/**
	 * Sets the item's name
	 * @param name String containing the new item's name
	 */
	public void setName(String name);
	
	/**
	 * Gets the item's artist
	 * @return String containing the item's artist
	 */
	public String getArtist();
	
	/**
	 * Sets the item's artist
	 * @param artist String containing the new item's artist
	 */
	public void setArtist(String artist);
	
	/**
	 * Gets the item's type
	 * @return String containing the item's type
	 */
	public String getType();
	
	/**
	 * Sets the item's type
	 * @param type String containing the new item's type
	 */
	public void setType(String type);
	
	/**
	 * Gets the item's price
	 * @return double containing the item's price
	 */
	public double getPrice();
	
	/**
	 * Sets the item's price
	 * @param price double containing the new item's price
	 */
	public void setPrice(double price);
	
	/**
	 * Gets the item's song id
	 * @return Integer containing the item's song id
	 */
	public Integer getSong();
	
	/**
	 * Sets the item's song id
	 * @param song Integer containing the new item's song id
	 */
	public void setSong(Integer song);
	
	/**
	 * Gets the item's album id
	 * @return Integer containing the item's album id
	 */
	public Integer getAlbum();
	
	/**
	 * Sets the item's album id
	 * @param album Integer containing the new item's album id
	 */
	public void setAlbum(Integer album);
	
	@Override
	public boolean equals(Object obj);
	
	@Override
	public int hashCode();
}
