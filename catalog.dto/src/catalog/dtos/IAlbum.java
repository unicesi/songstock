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
 * Interface definition for DTO: Album
 * @author daviddurangiraldo
 *
 */
public interface IAlbum {

	/**
	 * Gets the album's id
	 * @return Integer containing the album's id
	 */
	public Integer getId();
	/**
	 * Sets the album's id
	 * @param id Integer containing the new album's id
	 */
	public void setId(Integer id);
	/**
	 * Gets the album's name
	 * @return String containing the album's name
	 */
	public String getName();

	/**
	 * Sets the album's name
	 * @param name String containing the new album's name
	 */
	public void setName(String name);
	
	/**
	 * Gets the album's artist
	 * @return String containing the album's artist
	 */
	public String getArtist();

	/**
	 * Sets the album's artist
	 * @param artist String containing the new album's artist
	 */
	public void setArtist(String artist);
	
	/**
	 * Gets the album's release year
	 * @return Integer containing the album's release year
	 */
	public Integer getReleaseYear();

	/**
	 * Sets the album's release year
	 * @param releaseYear Integer containing the new album's release year
	 */
	public void setReleaseYear(Integer releaseYear);
	
	/**
	 * Gets the album's genre
	 * @return String containing the album's genre
	 */
	public String getGenre();

	/**
	 * Sets the album's genre
	 * @param genre String containing the new album's genre
	 */
	public void setGenre(String genre);
	
	/**
	 * Gets the album's artwork
	 * @return String containing the album's artwork
	 */
	public String getArtwork();

	/**
	 * Sets the album's artwork
	 * @param artwork String containing the new album's artwork
	 */
	public void setArtwork(String artwork);
	
	/**
	 * Gets the album's price
	 * @return double containing the album's price
	 */
	public double getPrice();

	/**
	 * Sets the album's price
	 * @param price double containing the new album's price
	 */
	public void setPrice(double price);
	
	/**
	 * Compares this object with the param
	 * @param obj IAlbum to be compared with
	 * @return true if the two objects are equal, false otherwise
	 */
	@Override
	public boolean equals(Object obj);
}
