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
package users.dtos;

/**
 * Interface defining the User services
 */
public interface IUser {

	/***
	 * Gets the user's email.
	 * @return string containing the user's email
	 */
	public String getEmail();
	
	/***
	 * Sets the user's email.
	 * @param email of the user
	 */
	public void setEmail(String email);
	
	/***
	 * Gets the user's name.
	 * @return string containing the user's name
	 */
	public String getName();
	
	/***
	 * Sets the user's name.
	 * @param name of the user
	 */
	public void setName(String name);
	
	/***
	 * Gets the user's password
	 * @return string containing the user's password
	 */
	public String getPassword();
	
	/***
	 * Sets the user's password
	 * @param password of the user.
	 */
	public void setPassword(String password);
	
}
