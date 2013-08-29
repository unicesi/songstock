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

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import users.dtos.IUser;
import users.persistence.entities.User;
import users.persistence.exceptions.UserPersistenceException;

/**
 * Session Bean that implements the IUserPersistenceBean Services
 * 
 * @author daviddurangiraldo
 * @author andrespaz
 */
@Stateless
public class UserPersistenceBean implements IUserPersistenceBean {

	/***
	 * 
	 */
	@PersistenceContext(unitName = "users.persistence.jpa")
	private EntityManager entityManager;

	/**
	 * Default constructor.
	 */
	public UserPersistenceBean() {
	}

	@Override
	public IUser getUserByEmail(IUser user) throws UserPersistenceException {
		try {
			User userEntity = entityManager.find(User.class, user.getEmail());
			if (userEntity != null) {
				IUser u = new users.dtos.impl.User();
				u.setEmail(userEntity.getEmail());
				u.setName(userEntity.getName());
				u.setPassword(userEntity.getPassword());
				return u;
			}
			return null;
		} catch (Exception e) {
			throw new UserPersistenceException("Wrong user.", e);
		}
	}

	@Override
	public void addUser(IUser user) throws UserPersistenceException {
		try {
			User newUser = new User();
			newUser.setEmail(user.getEmail());
			newUser.setName(user.getName());
			newUser.setPassword(user.getPassword());

			entityManager.persist(newUser);
			entityManager.flush();
		} catch (Exception e) {
			throw new UserPersistenceException(
					"There was a problem creating your user.", e);
		}
	}

	@Override
	public void updateUser(IUser iUser) throws UserPersistenceException {
		User newUser = new User();
		newUser.setEmail(iUser.getEmail());
		newUser.setName(iUser.getName());
		newUser.setPassword(iUser.getPassword());
		entityManager.merge(newUser);
		entityManager.flush();
	}

	@Override
	public void deleteUser(IUser iUser) {
		TypedQuery<User> query = entityManager.createNamedQuery(
				"users.deleteUser", User.class);
		query.setParameter("email", iUser.getEmail());
		query.executeUpdate();
		entityManager.flush();
	}
}
