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

import javax.ejb.EJB;
import javax.ejb.Stateless;

import register.logic.exceptions.RegisterLogicException;

import users.dtos.IUser;
import users.persistence.beans.IUserPersistenceBean;
import users.persistence.exceptions.UserPersistenceException;

/**
 * Session Bean that implements the IRegisterLogicBean Services
 * 
 * @author daviddurangiraldo
 */
@Stateless
public class RegisterLogicBean implements IRegisterLogicBean {

	/**
	 * Bean that contains the basic persistence services
	 */
	@EJB
	private IUserPersistenceBean userPersistenceBean;

	/**
	 * Default constructor.
	 */
	public RegisterLogicBean() {
	}

	@Override
	public boolean registerNewUser(IUser iUser) throws RegisterLogicException {
		try {
			if (userPersistenceBean.getUserByEmail(iUser) != null) {
				throw new RegisterLogicException("User already exists.");
			}
			userPersistenceBean.addUser(iUser);
		} catch (UserPersistenceException e) {
			throw new RegisterLogicException(e.getMessage());
		}
		return true;
	}

	@Override
	public void updateUser(IUser iUser) throws RegisterLogicException {
		try {
			userPersistenceBean.updateUser(iUser);
		} catch (UserPersistenceException e) {
			throw new RegisterLogicException(e.getMessage(), e);
		}
	}
}
