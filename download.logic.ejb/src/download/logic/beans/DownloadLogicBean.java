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
package download.logic.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import browsing.logic.beans.IBrowsingLogicBean;

import catalog.dtos.IAlbum;
import catalog.dtos.ISong;
import catalog.dtos.impl.Album;
import catalog.persistence.beans.ICatalogPersistenceBean;

import sales.dtos.IItem;
import sales.dtos.impl.Item;

/**
 * Session Bean implementation class DownloadLogicBean
 */
@Stateless
public class DownloadLogicBean implements IDownloadLogicBean {

	/**
	 * Base path of the server where the files are located. 
	 */
	private static final String BASE_PATH = "http://200.3.193.23/songstock/files";
	/**
	 * Supported file extension.
	 */
	private static final String FILE_EXTENSION = ".mp3";
	
	/**
	 * BrowsingLogicBean instance to lookup an album's songs.
	 */
	@EJB
	private IBrowsingLogicBean browsingLogicBean;
	
	/**
	 * CatalogPersistenceBean instance to lookup an album's details.
	 */
	@EJB
	private ICatalogPersistenceBean catalogPersistenceBean;
	
    /**
     * Default constructor. 
     */
    public DownloadLogicBean() {
    }

	@Override
	public String downloadItem(IItem item) {
		IAlbum album = new Album();
		album.setId(item.getAlbum());
		album = catalogPersistenceBean.getAlbumDetails(album);
		String url = BASE_PATH + "/" + item.getArtist() + "/" + album.getName() + "/" + item.getName() + FILE_EXTENSION;
		url.replaceAll("\\s", "%20");
		return url;
	}
	
	@Override
	public List<String> downloadItemsIn(IItem item) {
		IAlbum album = new Album();
		album.setId(item.getAlbum());
		List<ISong> songs = browsingLogicBean.getAlbumSongs(album);
		List<String> urls = new ArrayList<String>();
		IItem iItem;
		for (ISong song : songs) {
			iItem = new Item();
			iItem.setArtist(song.getArtist());
			iItem.setName(song.getName());
			iItem.setAlbum(item.getAlbum());
			urls.add(downloadItem(iItem));
		}
		return urls;
	}

}
