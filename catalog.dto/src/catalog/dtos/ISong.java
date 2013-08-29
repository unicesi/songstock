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
package catalog.dtos;

/**
 * Interface definition for DTO: Song
 * @author daviddurangiraldo
 *
 */
public interface ISong {

	/**
	 * Gets the song's album id
	 * @return Integer containing the song's album id
	 */
	public Integer getAlbumId();
	/**
	 * Sets the song's album id
	 * @param albumId Integer containing the new song's album id
	 */
	public void setAlbumId(Integer albumId);
	/**
	 * Gets the song's id
	 * @return Integer containing the song's id
	 */
	public Integer getId();
	/**
	 * Sets the song's id
	 * @param id Integer containing the new song's id
	 */
	public void setId(Integer id);
	/**
	 * Gets the song's name
	 * @return String containing the song's name
	 */
	public String getName();
	/**
	 * Sets the song's name
	 * @param name String containing the new song's name
	 */
	public void setName(String name);
	/**
	 * Gets the song's time in minutes (MM:SS)
	 * @return String containing the song's time in minutes
	 */
	public String getTime();
	/**
	 * Sets the song's time
	 * @param time String containing the new song's time
	 * @throws Exception if the time param does not meet the format (MM:SS)
	 */
	public void setTime(String time) throws Exception;
	/**
	 * Gets the song's artist
	 * @return String containing the song's artist
	 */
	public String getArtist();
	/**
	 * Sets the song's artist
	 * @param artist String containing the new song's artist
	 */
	public void setArtist(String artist);
	/**
	 * Gets the song's price
	 * @return double containing the song's price
	 */
	public double getPrice();
	/**
	 * Sets the song's price
	 * @param price double containing the new song's price
	 */
	public void setPrice(double price);
	/**
	 * Gets the song's size in MegaBytes
	 * @return double containing the song's size in MegaBytes
	 */
	public double getSize();
	/**
	 * Sets the song's size
	 * @param size double containing the new song's size in MegaBytes
	 */
	public void setSize(double size);
	/**
	 * Gets the song's quality in Kbps
	 * @return Integer containing the song's quality in Kbps
	 */
	public Integer getQuality();
	/**
	 * Sets the song's quality
	 * @param quality Integer containing the new song's quality in Kbps
	 */
	public void setQuality(Integer quality);
	
	/**
	 * Compares this object with the param
	 * @param obj ISong to be compared with
	 * @return true if the two objects are equal, false otherwise
	 */
	@Override
	public boolean equals(Object obj);
}
