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

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import searching.basic.logic.beans.IBasicSearchLogicBean;

import catalog.dtos.IAlbum;
import catalog.dtos.ISong;
import catalog.persistence.beans.ICatalogPersistenceBean;

/**
 * Session Bean that implements the IBasicSearchLogicBean Services
 * 
 * @author daviddurangiraldo
 */
@Stateless
public class BasicSearchLogicBean implements IBasicSearchLogicBean {

	/**
	 * Bean that contains the basic persistence services
	 */
	@EJB
	private ICatalogPersistenceBean iCatalogPersistenceBean;

	/**
	 * Default constructor.
	 */
	public BasicSearchLogicBean() {

	}

	@Override
	public List<IAlbum> findAlbumsByKeywords(String keyword) {
		List<IAlbum> foundAlbums = new ArrayList<>();
		List<IAlbum> albumsByKeyword = iCatalogPersistenceBean
				.getAlbumsWithNameContaining(keyword);
		if (albumsByKeyword != null) {
			for (IAlbum iAlbum : albumsByKeyword) {
				foundAlbums.add(iAlbum);
			}
		}
		List<IAlbum> albumsByArtist = iCatalogPersistenceBean
				.getAlbumsFromArtist(keyword);
		if (albumsByArtist != null) {
			for (IAlbum iAlbum : albumsByArtist) {
				if(foundAlbums != null && !foundAlbums.contains(iAlbum))
					foundAlbums.add(iAlbum);
			}
		}
		return foundAlbums.isEmpty() ? null : foundAlbums;
	}

	@Override
	public List<ISong> findSongsByKeywords(String keyword) {
		List<ISong> foundSongs = new ArrayList<>();
		List<ISong> songsByKeyword = iCatalogPersistenceBean
				.getSongsWithNameContaining(keyword);
		if (songsByKeyword != null) {
			for (ISong iSong : songsByKeyword) {
				foundSongs.add(iSong);
			}
		}
		List<ISong> songsByArtist = iCatalogPersistenceBean
				.getSongsFromArtist(keyword);
		if (songsByArtist != null) {
			for (ISong iSong : songsByArtist) {
				if(foundSongs != null && !foundSongs.contains(iSong))
					foundSongs.add(iSong);
			}
		}
		return foundSongs.isEmpty() ? null : foundSongs;
	}

}
