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
package login.basic.logic.beans;

import javax.ejb.Remote;

import login.basic.logic.exceptions.LoginLogicException;

import users.dtos.IUser;

/***
 * Interface defining the BasiLoginLogicBean services
 * 
 * @author Andrés Paz
 */
@Remote
public interface IBasicLoginLogicBean {

	/***
	 * Logs in the user into the system.
	 * @param iUser with the email and password of the user to log in
	 * @return The user's details
	 * @throws UserLogicException if the email or password are wrong
	 */
	public IUser login(IUser iUser) throws LoginLogicException;
	
}
