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

import java.util.HashMap;
import java.util.Map;

/**
 * Registry implementation class.
 * 
 * @author Andrés Paz
 */
public class Registry {

	/**
	 * Default constructor.
	 */
	private Registry() {
	}
	
	/**
	 * Map where the objects are stored.
	 */
	private static Map<String, Object> map = new HashMap<>();

	/**
	 * Returns the object with the given id.
	 * 
	 * @param key
	 *            the identifier
	 * @return the object or <code>null</code> if no match
	 */
	@SuppressWarnings("unchecked")
	public static <X> X get(String key) {
		return (X) map.get(key);
	}

	/**
	 * Returns a map of all registered objects.
	 * 
	 * @return the object map
	 */
	public static Map<String, Object> getAll() {
		return map;
	}

	/**
	 * Registers an object.
	 * 
	 * @param key
	 *            the identifier
	 * @param value
	 *            the object to be registered
	 */
	public static void register(String key, Object value) {
		map.put(key, value);
	}

	/**
	 * Unregisters an object.
	 * 
	 * @param key
	 *            the identifier
	 */
	public static void unregister(String key) {
		map.remove(key);
	}

	/**
	 * Unregisters all registered objects.
	 */
	public static void unregisterAll() {
		map.clear();
	}

}
