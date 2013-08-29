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
package songstock.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.vaadin.ui.Component;

/**
 * Abstract controller class. Defines the common behavior of a SongStock controller.
 * Provides services for UI, content and process contributions.
 * @author Andrés Paz
 *
 */
public abstract class AbstractController {

	/**
	 * List of UI contributors.
	 */
	private List<UIContributor> uiContributors;
	/**
	 * List of content contributors.
	 */
	private List<ContentContributor> contentContributors;
	/**
	 * List of process contributors.
	 */
	private HashMap<String, ProcessContributor> processContributors;
	
	/**
	 * Default constructor. Initializes the contributors' lists.
	 */
	public AbstractController() {
		uiContributors = new ArrayList<>();
		contentContributors = new ArrayList<>();
		processContributors = new HashMap<>();
	}

	// UI Contributors
	
	/**
	 * Adds the specified UI contributor.
	 * @param contributor to be added
	 */
	public void addContributor(UIContributor contributor) {
		uiContributors.add(contributor);
	}
	
	/**
	 * Removes the specified UI contributor.
	 * @param contributor to be removed
	 */
	public void removeContributor(UIContributor contributor) {
		uiContributors.remove(contributor);
	}
	
	/**
	 * Gets the registered UI contributors.
	 * @return list of UI contributors.
	 */
	public List<UIContributor> getUIContributors() {
		return uiContributors;
	}

	/**
	 * Calls the contributeUITo method of the UI contributors.
	 * 
	 * @param component to be extended with additional UI components
	 * @param data required for the contribution (optional)
	 * @throws Exception if there was a problem extending the component
	 */
	public void extendUI(Component component, Object data) throws Exception {
		for (UIContributor contributor : uiContributors) {
			contributor.contributeUITo(component, data);
		}
	}
	
	// Content Contributors
	
	/**
	 * Adds the specified content contributor.
	 * @param contributor to be added
	 */
	public void addContributor(ContentContributor contributor) {
		contentContributors.add(contributor);
	}
	
	/**
	 * Removes the specified content contributor.
	 * @param contributor to be removed
	 */
	public void removeContributor(ContentContributor contributor) {
		contentContributors.remove(contributor);
	}
	
	/**
	 * Gets the registered content contributors.
	 * @return list of content contributors
	 */
	public List<ContentContributor> getContentContributors() {
		return contentContributors;
	}
	
	/**
	 * Calls the contributeContentTo method of the content contributors.
	 * @param component to be extended with content
	 * @param data required for the contribution (optional)
	 * @throws Exception if there was a problem extending the component
	 */
	public void extendContent(Component component, Object data) throws Exception {
		for (ContentContributor contributor : contentContributors) {
			contributor.contributeContentTo(component, data);
		}
	}
	
	// Process Contributors
	
	/**
	 * Adds the specified process contributor.
	 * @param key the key of the process contributor to be added
	 * @param contributor to be added 
	 */
	public void addContributor(String key, ProcessContributor contributor) {
		processContributors.put(key, contributor);
	}
	
	/**
	 * Removes the specified content contributor.
	 * @param contributor to be removed
	 */
	public void removeContributor(ProcessContributor contributor) {
		processContributors.remove(contributor);
	}
	
	/**
	 * Gets the registered process contributors.
	 * @return HashMap<String, ProcessContributor> containing a list of all process contributors
	 */
	public HashMap<String, ProcessContributor> getProcessContributors() {
		return processContributors;
	}
	
	/**
	 * Calls the contributeProcess method of the process contributors.
	 * @param data required by the extension process
	 * @throws Exception if there was a problem extending the process
	 */
	public void extendProcess(String key, Object data) throws Exception {
//		for (ProcessContributor contributor : processContributors) {
//			contributor.contributeProcess(data);
//		}
		processContributors.get(key).contributeProcess(data);
	}

}
