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

import javax.ejb.EJB;
import javax.ejb.Stateless;

import login.basic.logic.exceptions.LoginLogicException;

import users.dtos.IUser;
import users.persistence.beans.IUserPersistenceBean;
import users.persistence.exceptions.UserPersistenceException;

/**
 * Session Bean implementation class BasicLoginLogicBean
 */
@Stateless
public class BasicLoginLogicBean implements IBasicLoginLogicBean {

	/**
	 * Persistence bean instance to retrieve a user by its email
	 */
	@EJB
	private IUserPersistenceBean userPersistenceBean;
	
    /**
     * Default constructor. 
     */
    public BasicLoginLogicBean() {
    }

    /* (non-Javadoc)
	 * @see login.basic.logic.beans.IBasicLoginLogicBean#login()
	 */
	@Override
	public IUser login(IUser iUser) throws LoginLogicException {
		try {
			IUser user = userPersistenceBean.getUserByEmail(iUser);
			if(user == null)
				throw new LoginLogicException("Wrong user.");
			if (!user.getPassword().equals(iUser.getPassword())) {
				throw new LoginLogicException("Wrong password.");
			}
			return user;
		} catch (UserPersistenceException e) {
			throw new LoginLogicException("Wrong user.");
		}
	}

}
