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

import com.vaadin.ui.Component;

/**
 * Interface defining the content contributor services.
 * 
 * @author Andrés Paz
 *
 */
public interface ContentContributor {

	/**
	 * Contributes content to the specified component using the specified data (optional).
	 * @param component to be extended with content
	 * @param data required for the contribution (optional)
	 * @throws Exception if there was a problem extending the component
	 */
	public void contributeContentTo(Component component, Object data) throws Exception;
}
