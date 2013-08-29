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
package songstock.web.extensions.browsing;

import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import browsing.logic.beans.IBrowsingLogicBean;

import catalog.dtos.IAlbum;
import catalog.dtos.ISong;

import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

import songstock.web.AbstractController;
import songstock.web.Registry;
import songstock.web.SongStockUI;
import songstock.web.client.ContentPanel;

/**
 * Browsing controller singleton class. Extends AbstractController.
 * 
 * @author Andrés Paz
 */
public class BrowsingController extends AbstractController {

	/**
	 * Browsing controller instance
	 */
	private static BrowsingController browsingController;

	/**
	 * BrowsingLogicBean instance
	 */
	private IBrowsingLogicBean browsingLogicBean;

	/**
	 * Default constructor. Performs components' lookup.
	 */
	private BrowsingController() {
		doLookup();
	}

	/**
	 * Lookup components.
	 */
	private void doLookup() {
		try {
			InitialContext context = new InitialContext();
			browsingLogicBean = (IBrowsingLogicBean) context
					.lookup("java:global/catalog.subsystem/browsing.logic.ejb/BrowsingLogicBean!browsing.logic.beans.IBrowsingLogicBean");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the BrowsingController instance.
	 * 
	 * @return The BrowsingController instance
	 */
	public static BrowsingController getInstance() {
		if (browsingController == null) {
			browsingController = new BrowsingController();
		}
		return browsingController;
	}

	/**
	 * Creates a new AlbumsListView component, adds it to the content panel and
	 * loads the albums. Calls extendUI method from AbstractController.
	 */
	protected void showAlbumListView() {
		AlbumsListView albumsListView = new AlbumsListView();

		ContentPanel contentPanel = Registry.get(SongStockUI.CONTENT_PANEL);
		contentPanel.removeContent();
		contentPanel.setContent(albumsListView);

		Registry.register(AlbumsListView.ALBUMS_LIST_VIEW, albumsListView);

		List<IAlbum> albums = browsingLogicBean.getAllAlbums();
		albumsListView.loadAlbums(albums);

		try {
			extendUI(albumsListView.getTableAlbums(), albums);
		} catch (Exception e) {
			SongStockUI songStockUI = Registry.get(SongStockUI.UI);
			songStockUI.showNotification("SongStock", e.getMessage());
		}
	}

	/**
	 * Gets the command for the browsing menu item. The command shows the
	 * albums' list view component.
	 * 
	 * @return Command for the browsing menu item
	 */
	public Command getBrowsingCommand() {
		return new Command() {

			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				showAlbumListView();
			}
		};
	}

	/**
	 * Retrieves the given album's details and song list. Shows the album
	 * details view component with this information.
	 * 
	 * @param album to retrieve its details and song list
	 */
	public void viewAlbum(IAlbum album) {
		IAlbum iAlbum = browsingLogicBean.getAlbumDetails(album);
		List<ISong> albumSongs = browsingLogicBean.getAlbumSongs(album);
		showAlbumDetailsView(iAlbum, albumSongs);
	}

	/**
	 * Creates a new AlbumDetailsView component, adds it to the content panel
	 * and loads the album information. Calls extendUI method from
	 * AbstractController.
	 *
	 * @param album to display
	 * @param albumSongs to display
	 */
	protected void showAlbumDetailsView(IAlbum album, List<ISong> albumSongs) {
		AlbumDetailsView albumDetailsView = new AlbumDetailsView();

		ContentPanel contentPanel = Registry.get(SongStockUI.CONTENT_PANEL);
		contentPanel.removeContent();
		contentPanel.setContent(albumDetailsView);

		Registry.register(AlbumDetailsView.ALBUM_DETAILS_VIEW, albumDetailsView);

		albumDetailsView.loadAlbum(album, albumSongs);

		try {
			extendUI(albumDetailsView.getTableAlbums(), albumSongs);
		} catch (Exception e) {
			SongStockUI songStockUI = Registry.get(SongStockUI.UI);
			songStockUI.showNotification("SongStock", e.getMessage());
		}
	}

	/**
	 * [Disabled]
	 * Retrieves the given song's details. Shows the song
	 * details view component with this information.
	 * 
	 * @param song to retrieve its details
	 */
	public void viewSong(ISong song) {
		ISong iSong = browsingLogicBean.getSongDetails(song);
		showSongDetailsView(iSong);
	}

	/**
	 * [Disabled]
	 * Creates a new SongDetailsView component, adds it to the content panel
	 * and loads the song information. Calls extendUI method from
	 * AbstractController.
	 * 
	 * @param song to display
	 */
	protected void showSongDetailsView(ISong song) {
		// SongDetailsView songDetailsView = new SongDetailsView();
		//
		// ContentPanel contentPanel = Registry.get(SongStockUI.CONTENT_PANEL);
		// contentPanel.removeContent();
		// contentPanel.setContent(songDetailsView);
		//
		// Registry.register(SongDetailsView.SONG_DETAILS_VIEW,
		// songDetailsView);
		//
		// songDetailsView.loadSong(song);
	}

}
