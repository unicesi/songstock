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
package users.persistence.entities;

import java.io.Serializable;

import javax.persistence.*;

import users.dtos.IUser;

/**
 * Entity implementation class for Entity: User
 */
@Entity
@Table(name="Users")
@NamedQueries({
	@NamedQuery(name="users.getUserByEmail", query="SELECT u FROM User u WHERE u.email=:email"),
	@NamedQuery(name="users.deleteUser", query="DELETE FROM User u WHERE u.email=:email")
})
public class User implements Serializable, IUser {

	private static final long serialVersionUID = 1L;

	/***
	 * String representing the user's email.
	 */
	@Id
	@Column(name="email")
	private String email;
	
	/***
	 * String representing the user's name.
	 */
	@Column(name="name")
	private String name;
	
	/***
	 * String representing the user's password.
	 */
	@Column(name="password")
	private String password;
	
	/**
	 * Default constructor.
	 */
	public User() {
		super();
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
