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
import java.util.Map;

import sales.dtos.IItem;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Table;

/**
 * 
 * @author Andrés Paz
 *
 */
public class DownloadsView extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;

	@AutoGenerated
	private Table tableDownloadLinks;

	@AutoGenerated
	private Label labelTitle;

	private static final long serialVersionUID = 1L;

	/**
	 *  Constant for use in the registry
	 */
	public static final String DOWNLOADS_VIEW = "downloadsView";
	
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public DownloadsView() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// User code
		
		// Table for the download links.
		tableDownloadLinks.addContainerProperty("Name", String.class, null);
		tableDownloadLinks.addContainerProperty("Artist", String.class, null);
		tableDownloadLinks.addContainerProperty("Download", Link.class, null);

		// Send changes in selection immediately to server.
		tableDownloadLinks.setImmediate(true);
	}
	
	/**
	 * Loads and displays the download links.
	 * @param links to display
	 */
	public void loadLinks(HashMap<Object[], String> links) {
		tableDownloadLinks.removeAllItems();
		
		for (Map.Entry<Object[], String> entry : links.entrySet()) {
			Link link = new Link("Download", new ExternalResource(entry.getValue()));
			int length = 35; //BASE_PATH length
			String songName = entry.getValue().substring(length+1).split("[/.]")[2];
			tableDownloadLinks.addItem(new Object[] { songName, ((IItem) entry.getKey()[0]).getArtist(), link }, entry.getKey());
		}
	}

	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");
		
		// labelTitle
		labelTitle = new Label();
		labelTitle.setImmediate(false);
		labelTitle.setWidth("-1px");
		labelTitle.setHeight("-1px");
		labelTitle.setValue("<b>Downloads</b>");
		labelTitle.setContentMode(com.vaadin.shared.ui.label.ContentMode.HTML);
		mainLayout.addComponent(labelTitle, "top:20.0px;left:20.0px;");
		
		// tableDownloadLinks
		tableDownloadLinks = new Table();
		tableDownloadLinks.setImmediate(false);
		tableDownloadLinks.setWidth("100.0%");
		tableDownloadLinks.setHeight("100.0%");
		mainLayout.addComponent(tableDownloadLinks,
				"top:60.0px;right:20.0px;bottom:20.0px;left:20.0px;");
		
		return mainLayout;
	}

}
