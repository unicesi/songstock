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
package songstock.web.extensions.download;

import java.util.HashMap;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import download.logic.beans.IDownloadLogicBean;
import download.logic.exceptions.DownloadLogicException;

import sales.dtos.IItem;
import songstock.web.AbstractController;
import songstock.web.ProcessContributor;
import songstock.web.Registry;
import songstock.web.SongStockUI;
import songstock.web.client.ContentPanel;

/**
 * Download controller singleton class.
 * Extends AbstractController.
 * Implements ProcessContributor to contribute in the shopping cart's checkout process.
 * 
 * @author Andrés Paz
 */
public class DownloadController extends AbstractController implements ProcessContributor {

	/**
	 * Download controller instance
	 */
	private static DownloadController downloadController;
	
	/**
	 * DownloadLogicBean instance
	 */
	private IDownloadLogicBean downloadLogicBean;
	
	/**
	 * Default constructor. Performs components' lookup.
	 */
	private DownloadController() {
		doLookup();
	}

	/**
	 * Lookup components.
	 */
	private void doLookup() {
		try {
			InitialContext context = new InitialContext();
			downloadLogicBean = (IDownloadLogicBean) context.lookup("java:global/catalog.subsystem/download.logic.ejb/DownloadLogicBean!download.logic.beans.IDownloadLogicBean");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the DownloadController instance.
	 * 
	 * @return The DownloadController instance
	 */
	public static DownloadController getInstance() {
		if (downloadController == null) {
			downloadController = new DownloadController();
		}
		return downloadController;
	}

	/**
	 * Creates a new DownloadsView component, adds it to the content panel and
	 * loads the download URL links.
	 * @param items to download
	 */
	protected void downloadItems(List<IItem> items) {
		DownloadsView downloadsView = new DownloadsView();
		
		ContentPanel contentPanel = Registry.get(SongStockUI.CONTENT_PANEL);
		contentPanel.removeContent();
		contentPanel.setContent(downloadsView);
		
		Registry.register(DownloadsView.DOWNLOADS_VIEW, downloadsView);
		
		HashMap<Object[], String> links = new HashMap<Object[], String>();
		String url;
		List<String> urls;
		int c = 0;
		Object[] key;
		for (IItem item : items) {
			if (item.getType().equals(IItem.ALBUM_TYPE)) {
				urls = downloadLogicBean.downloadItemsIn(item);
				for (String string : urls) {
					key = new Object[2];
					key[0] = item;
					key[1] = c;
					links.put(key, string);
					c++;
				}
			} else {
				try {
					url = downloadLogicBean.downloadItem(item);
					key = new Object[2];
					key[0] = item;
					key[1] = c;
					links.put(key, url);
					c++;
				} catch (DownloadLogicException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		downloadsView.loadLinks(links);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void contributeProcess(Object data) throws Exception {
		if (data instanceof List) {
			List<IItem> items = (List<IItem>) data;
			downloadItems(items);
		}
	}
}
