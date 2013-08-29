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
package users.persistence.beans;

import javax.ejb.Remote;

import users.dtos.IUser;
import users.persistence.exceptions.UserPersistenceException;

/***
 * Interface definition that contains the UserPersistenceBean services
 * 
 * @author daviddurangiraldo
 * @author andrespaz
 */
@Remote
public interface IUserPersistenceBean {

	/***
	 * Gets the specified user's details by its email.
	 * @param user holding the email to be queried
	 * @return the user that matches the email
	 * @throws UserPersistenceException if the user doesn't exist or couldn't be retrieved
	 */
	public IUser getUserByEmail(IUser user) throws UserPersistenceException;

	/**
	 * Adds the specified user to the database
	 * 
	 * @param user
	 *            IUser containing the information of the new user to be added
	 * @throws UserPersistenceException
	 *             if the user cannot be added
	 */
	public void addUser(IUser user) throws UserPersistenceException;

	/**
	 * Updates the specified user information. The user's id remains intact, but
	 * the remaining fields are modified
	 * 
	 * @param user IUser containing the user's id and the information to be updated
	 * @throws UserPersistenceException if the user cannot be updated
	 */
	public void updateUser(IUser user) throws UserPersistenceException;

	/**
	 * Deletes the specified user from the database
	 * @param iUser IUser that contains the id user to delete
	 */
	public void deleteUser(IUser iUser);

}