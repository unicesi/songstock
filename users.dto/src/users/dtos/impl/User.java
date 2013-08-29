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
package users.dtos.impl;

import java.io.Serializable;

import users.dtos.IUser;

/**
 * User class implementation
 */
public class User implements IUser, Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * User's email.
	 */
	private String email;
	/**
	 * User's name.
	 */
	private String name;
	/**
	 * User's password.
	 */
	private String password;
	
	/**
	 * Default constructor.
	 */
	public User() {
	}

	/* (non-Javadoc)
	 * @see users.dtos.IUser#getEmail()
	 */
	@Override
	public String getEmail() {
		return this.email;
	}

	/* (non-Javadoc)
	 * @see users.dtos.IUser#setEmail(java.lang.String)
	 */
	@Override
	public void setEmail(String email) {
		this.email = email;
	}

	/* (non-Javadoc)
	 * @see users.dtos.IUser#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/* (non-Javadoc)
	 * @see users.dtos.IUser#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see users.dtos.IUser#getPassword()
	 */
	@Override
	public String getPassword() {
		return this.password;
	}

	/* (non-Javadoc)
	 * @see users.dtos.IUser#setPassword(java.lang.String)
	 */
	@Override
	public void setPassword(String password) {
		this.password = password;
	}

}
