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

import javax.ejb.EJB;
import javax.ejb.Stateless;

import catalog.dtos.IAlbum;
import catalog.dtos.ISong;
import catalog.persistence.beans.ICatalogPersistenceBean;

/**
 * Session Bean that implements the IBrowsingLogicBean Services
 * 
 * @author daviddurangiraldo
 */
@Stateless
public class BrowsingLogicBean implements IBrowsingLogicBean{

	/**
	 * Bean that contains the basic persistence services
	 */
	@EJB
	private ICatalogPersistenceBean iCatalogPersistenceBean;
	
	/**
	 * Default constructor.
	 */
	public BrowsingLogicBean(){
		
	}
	
	@Override
	public List<IAlbum> getAllAlbums() {
		return iCatalogPersistenceBean.getAllAlbums();
	}

	@Override
	public IAlbum getAlbumDetails(IAlbum iAlbum) {
		return iCatalogPersistenceBean.getAlbumDetails(iAlbum);
	}

	@Override
	public List<ISong> getAlbumSongs(IAlbum iAlbum) {
		return iCatalogPersistenceBean.getAlbumSongs(iAlbum);
	}

	@Override
	public ISong getSongDetails(ISong iSong) {
		return iCatalogPersistenceBean.getSongDetails(iSong);
	}

}
