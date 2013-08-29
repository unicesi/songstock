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
package browsing.logic.beans;

import java.util.List;

import javax.ejb.Remote;

import catalog.dtos.IAlbum;
import catalog.dtos.ISong;
/***
 * Interface definition that contains the BrowsingLogicBean services
 * 
 * @author daviddurangiraldo
 * 
 */
@Remote
public interface IBrowsingLogicBean {

	/**
	 * Gets all the albums stored in the database
	 * 
	 * @return List<IAlbum> containing all the albums stored in the database.
	 *         Null if there are no match found
	 */
	public List<IAlbum> getAllAlbums();

	/**
	 * Gets the information of the specified album
	 * 
	 * @param iAlbum
	 *            IAlbum containing the id of the album to search for
	 * @return IAlbum containing the information of the album sought. Null if
	 *         there is no match found
	 */
	public IAlbum getAlbumDetails(IAlbum iAlbum);

	/**
	 * Gets all the songs related to the specified album
	 * 
	 * @param iAlbum
	 *            IAlbum containing the id of the album to search for
	 * @return List<ISong> containing all the songs related to the specified
	 *         album. Null if there are no match found
	 */
	public List<ISong> getAlbumSongs(IAlbum iAlbum);

	/**
	 * Gets the information of the specified song
	 * 
	 * @param iSong
	 *            ISong containing the id of the song to search for
	 * @return ISong containing the information of the song sought. Null if
	 *         there is no match found
	 */
	public ISong getSongDetails(ISong iSong);
}
