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
package register.logic.beans;

import javax.ejb.Remote;

import register.logic.exceptions.RegisterLogicException;
import users.dtos.IUser;

/***
 * Interface definition that contains the RegisterLogicBean services
 * 
 * @author daviddurangiraldo
 * 
 */
@Remote
public interface IRegisterLogicBean {

	/**
	 * Verifies if the specified user is already registered. If false, then the
	 * user is registered, throws Exception otherwise
	 * 
	 * @param iUser
	 *            IUser containing the new user to be registered
	 * @return boolean true if the user was added successfully, false otherwise
	 * @throws RegisterLogicException
	 *             if the user already exists or if the user cannot be added
	 */
	public boolean registerNewUser(IUser iUser) throws RegisterLogicException;

	/**
	 * Updates the specified user information. The user's id remains intact, but
	 * the remaining fields are modified
	 * 
	 * @param user IUser containing the user's id and the information to be updated
	 * @throws RegisterLogicException 
	 */
	public void updateUser(IUser iUser) throws RegisterLogicException;

}
