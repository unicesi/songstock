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
package songstock.web.extensions.searching.basic;

import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;


import catalog.dtos.IAlbum;
import catalog.dtos.ISong;

import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

import searching.basic.logic.beans.IBasicSearchLogicBean;
import songstock.web.AbstractController;
import songstock.web.Registry;
import songstock.web.SongStockUI;
import songstock.web.client.ContentPanel;

/**
 * 
 * @author Andrés Paz
 *
 */
public class SearchController extends AbstractController {

	/**
	 * 
	 */
	private static SearchController searchController;

	/**
	 * 
	 */
	private IBasicSearchLogicBean basicSearchLogicBean;

	/**
	 * 
	 */
	public SearchController() {
		doLookup();
	}

	/**
	 * 
	 */
	private void doLookup() {
		try {
			InitialContext context = new InitialContext();
			basicSearchLogicBean = (IBasicSearchLogicBean) context
					.lookup("java:global/catalog.subsystem/searching.basic.logic.ejb/BasicSearchLogicBean!searching.basic.logic.beans.IBasicSearchLogicBean");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the singleton instance of the controller
	 * 
	 * @return SearchController containing the unique instance of the controller
	 */
	public static SearchController getInstance() {
		if (searchController == null) {
			searchController = new SearchController();
		}
		return searchController;
	}

	/**
	 * 
	 * @return
	 */
	public Command getBasicSearchCommand() {
		return new Command() {

			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				showBasicSearchForm();
			}
		};
	}

	/**
	 * 
	 */
	protected void showBasicSearchForm() {
		BasicSearchForm basicSearchForm = new BasicSearchForm();

		ContentPanel contentPanel = Registry.get(SongStockUI.CONTENT_PANEL);
		contentPanel.removeContent();
		contentPanel.setContent(basicSearchForm);

		Registry.register(BasicSearchForm.BASIC_SEARCH_FORM, basicSearchForm);
	}

	/**
	 * 
	 * @param query
	 */
	public void doBasicSearch(String query) {
		try {
			 List<IAlbum> albums = basicSearchLogicBean.findAlbumsByKeywords(query);
			 List<ISong> songs = basicSearchLogicBean.findSongsByKeywords(query);

			BasicSearchForm basicSearchForm = Registry
					.get(BasicSearchForm.BASIC_SEARCH_FORM);
			basicSearchForm.clear();

			ContentPanel contentPanel = Registry.get(SongStockUI.CONTENT_PANEL);
			contentPanel.removeContent();

			showBasicResultsView(albums, songs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void showBasicResultsView(List<IAlbum> albums, List<ISong> songs) {
		BasicResultsView basicResultsView = new BasicResultsView();

		ContentPanel contentPanel = Registry.get(SongStockUI.CONTENT_PANEL);
		contentPanel.removeContent();
		contentPanel.setContent(basicResultsView);

		Registry.register(BasicResultsView.BASIC_RESULTS_VIEW, basicResultsView);

		basicResultsView.loadAlbums(albums);
 
		try {
			extendUI(basicResultsView.getTableAlbums(), albums);
		} catch (Exception e) {
			SongStockUI songStockUI = Registry.get(SongStockUI.UI);
			songStockUI.showNotification("SongStock", e.getMessage());
		}
		
		basicResultsView.loadSongs(songs);
		
		try {
			extendUI(basicResultsView.getTableSongs(), songs);
		} catch (Exception e) {
			SongStockUI songStockUI = Registry.get(SongStockUI.UI);
			songStockUI.showNotification("SongStock", e.getMessage());
		}
	}

}
