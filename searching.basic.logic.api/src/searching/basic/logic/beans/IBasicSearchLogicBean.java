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

package searching.basic.logic.beans;

import java.util.List;

import javax.ejb.Remote;

import catalog.dtos.IAlbum;
import catalog.dtos.ISong;

/***
 * Interface definition that contains the BasicSearchLogicBean services
 * 
 * @author daviddurangiraldo
 * 
 */
@Remote
public interface IBasicSearchLogicBean {

	/**
	 * Finds all the albums that contain the specified keyword, comparing their
	 * names and their artist names to match the information given.
	 * 
	 * @param keyword
	 *            String containing the keyword to search for
	 * @return List<IAlbum> containing all the albums that match the keyword.
	 *         Null if there are no match found
	 */
	public List<IAlbum> findAlbumsByKeywords(String keyword);

	/**
	 * Finds all the songs that contain the specified keyword, comparing their
	 * names and their artist names to match the information given.
	 * 
	 * @param keyword
	 *            String containing the keyword to search for
	 * @return List<ISong> containing all the songs that match the keyword. Null
	 *         if there are no match found
	 */
	public List<ISong> findSongsByKeywords(String keyword);
}
