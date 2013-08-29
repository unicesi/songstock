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
package songstock.web.client;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

/**
 * Menu panel component.
 * @author Andrés Paz
 *
 */
public class MenuPanel extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	private static final long serialVersionUID = 1L;
	@AutoGenerated
	private AbsoluteLayout mainLayout;
	
	/**
	 * The application's menu bar.
	 */
	@AutoGenerated
	private MenuBar menuBar;
	
	/**
	 * The application's extension menu item.
	 */
	private MenuItem modulesMenu;

	/**
	 * List of disabled menu item names.
	 */
	private List<String> disabledMenus;
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public MenuPanel() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// User code
		disabledMenus = new ArrayList<String>();
		
		// Create the File Menu
		MenuItem fileMenu = menuBar.addItem("File", null);
		MenuItem exitMenuItem = fileMenu.addItem("Exit", null);

		// Create the Modules Menu
		this.modulesMenu = menuBar.addItem("Modules", null);
	}
	
	/**
	 * Adds a new menu item to the modules menu with the specified caption, command and options.
	 * @param caption for the menu item
	 * @param command for the menu item's action
	 * @param isEnabled indicates if the menu item is enabled when added
	 * @return the menu item added
	 */
	public MenuItem addMenu(String caption, Command command, boolean isEnabled) {
		MenuItem menuItem = this.modulesMenu.addItem(caption, command);
		menuItem.setEnabled(isEnabled);
		return menuItem;
	}
	
	/**
	 * Enables the disabled menu items.
	 */
	public void enableMenus() {
		disabledMenus.clear();
		List<MenuItem> menuItems = this.modulesMenu.getChildren();
		for (MenuItem menuItem : menuItems) {
			if (!menuItem.isEnabled()) {
				disabledMenus.add(menuItem.getText());
				menuItem.setEnabled(true);
			}
		}
	}
	
	/**
	 * Re-disables the disabled menu items.
	 */
	public void disableMenus() {
		List<MenuItem> menuItems = this.modulesMenu.getChildren();
		for (MenuItem menuItem : menuItems) {
			if (disabledMenus.contains(menuItem.getText())) {
				menuItem.setEnabled(false);
			}
		}
	}
	
	/**
	 * Sets the caption of a menu item. 
	 * @param caption of the menu item to set
	 * @param newCaption for the menu item
	 */
	public void setMenuCaption(String caption, String newCaption){
		if (getItem(caption) != null) {
			getItem(caption).setText(newCaption);
		}
	}
	
	/**
	 * Sets the caption and command of a menu item.
	 * @param caption of the menu item to set
	 * @param newCaption for the menu item
	 * @param newCommand for the menu item
	 */
	public void setMenuCaptionAndCommand(String caption, String newCaption, Command newCommand){
		if (getItem(caption) != null) {
			getItem(caption).setCommand(newCommand);
			getItem(caption).setText(newCaption);
		}
	}
	
	/**
	 * Gets the menu item for the given caption.
	 * @param caption of the menu item to find
	 * @return the menu item with the given caption, null if there is no menu item with that caption
	 */
	private MenuItem getItem(String caption){
		List<MenuItem> menuItems = modulesMenu.getChildren();
		MenuItem item = null;
		boolean go = true;
		for (int i = 0; i < menuItems.size() && go; i++) {
			if(menuItems.get(i).getText().equals(caption)){
				item = menuItems.get(i);
				go = false;
			}
		}
		return item;
	}

	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("23px");
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("23px");
		
		// menuBar
		menuBar = new MenuBar();
		menuBar.setImmediate(false);
		menuBar.setWidth("100.0%");
		menuBar.setHeight("-1px");
		mainLayout.addComponent(menuBar, "top:0.0px;left:0.0px;");
		
		return mainLayout;
	}

}
