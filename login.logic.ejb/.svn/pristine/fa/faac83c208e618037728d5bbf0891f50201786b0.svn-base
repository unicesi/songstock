package co.edu.uniandes.songstore.login.logic.dto;

import java.io.Serializable;

import co.edu.uniandes.songstore.login.logic.api.ILoginDTO;

/**
 * UserDTO
 */
public class LoginDTO implements Serializable, ILoginDTO {

	/**
	 * id for serialization 
	 */
	private static final long serialVersionUID = 1L;

	// -------------------------------------------------------------
	// CONSTRUCTORS
	// -------------------------------------------------------------

	/**
	 * Default constructor
	 */
	public LoginDTO() {
		
	}
	
	/**
	 * Full constructor
	 */
	public LoginDTO(Long iD, String login, String name) {
		super();
		ID = iD;
		this.login = login;
		this.name = name;
	}
		
	// -------------------------------------------------------------
	// ATTRIBUTES
	// -------------------------------------------------------------
	
	/**
	 * Attribute ID
	 **/
	private Long ID;

	/**
	 * name attribute
	 **/
	private String name;

	/**
	 * login attribute
	 **/
	private String login;

	/**
	 * password attribute
	 **/
	private String password;
	
	
	// -------------------------------------------------------------
	// METHODS
	// -------------------------------------------------------------
	
	/* (non-Javadoc)
	 * @see co.edu.uniandes.songStore.user.service.dto.IUserDTO#getID()
	 */
	@Override
	public Long getID() {
		return ID;
	}

	/* (non-Javadoc)
	 * @see co.edu.uniandes.songStore.user.service.dto.IUserDTO#setID(java.lang.Long)
	 */
	@Override
	public void setID(Long ID) {
		this.ID = ID;
	}

	/* (non-Javadoc)
	 * @see co.edu.uniandes.songStore.user.service.dto.IUserDTO#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see co.edu.uniandes.songStore.user.service.dto.IUserDTO#getLogin()
	 */
	@Override
	public String getLogin() {
		return login;
	}

	/* (non-Javadoc)
	 * @see co.edu.uniandes.songStore.user.service.dto.IUserDTO#setName(java.lang.String)
	 */
	@Override
	public void setName(String nombre) {
		this.name = nombre;
	}

	/* (non-Javadoc)
	 * @see co.edu.uniandes.songStore.user.service.dto.IUserDTO#setLogin(java.lang.String)
	 */
	@Override
	public void setLogin(String usuario) {
		this.login = usuario;
	}

	/* (non-Javadoc)
	 * @see co.edu.uniandes.songstore.login.logic.api.ILoginDTO#getPassword()
	 */
	@Override
	public String getPassword() {
		return this.password;
	}

	/* (non-Javadoc)
	 * @see co.edu.uniandes.songstore.login.logic.api.ILoginDTO#setPassword(java.lang.String)
	 */
	@Override
	public void setPassword(String password) {
		this.password = password;
	}

}
