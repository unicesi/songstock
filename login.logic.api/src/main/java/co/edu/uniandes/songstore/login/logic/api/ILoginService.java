package co.edu.uniandes.songstore.login.logic.api;

import javax.ejb.Local;
import javax.ejb.Remote;

/**
 * ILoginService
 * interface for user management services
 */
@Local @Remote
public interface ILoginService {

	
	/**
	 * perform a login validation
	 * @param login user´s login
	 * @param password user´s password
	 * @return the name of the user if the login and password is correct, null if there is an error
	 */
	public ILoginDTO login( ILoginDTO loginData ) throws Exception; 		

}
