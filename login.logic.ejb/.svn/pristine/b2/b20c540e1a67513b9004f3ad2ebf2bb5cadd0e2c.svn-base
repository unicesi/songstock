package co.edu.uniandes.songstore.login.logic.ejb;

import co.edu.uniandes.songstore.login.persistence.api.IUserDataDTO;
import co.edu.uniandes.songstore.login.persistence.api.IUserPersistenceService;
import co.edu.uniandes.songstore.login.persistence.exceptions.UserPersistenceServiceException;
import co.edu.uniandes.songstore.login.logic.annotation.Log;
import co.edu.uniandes.songstore.login.logic.api.ILoginService;
import co.edu.uniandes.songstore.login.logic.api.ILoginDTO;
import co.edu.uniandes.songstore.login.logic.dto.LoginDTO;
import co.edu.uniandes.songstore.login.logic.exceptions.LoginServiceException;

import javax.ejb.Stateless;
import javax.ejb.EJB;
import javax.ejb.EJB;

import org.apache.commons.beanutils.BeanUtils;

import java.util.List;
import java.util.ArrayList;

/**
 * LoginService Session Bean
 */
@Stateless
@SuppressWarnings( "unused" )
public class LoginService implements ILoginService {

	// -------------------------------------------------------------
	// ATTRIBUTES
	// -------------------------------------------------------------
	
	/**
	 * UserPersistenceService 
	 */
	@EJB
	private IUserPersistenceService userPersistenceService;

	// -------------------------------------------------------------
	// METHODS
	// -------------------------------------------------------------
	@Log
	public ILoginDTO login( ILoginDTO loginData ) throws Exception{
		try {
			
			// authenticates the user
			userPersistenceService.login( loginData.getLogin(), loginData.getPassword() );
			
			// obtains the user data and returns them
			IUserDataDTO userDataDTO = userPersistenceService.getUserByLogin( loginData.getLogin() );
			
			// convert to a UserDTO and returns it
			LoginDTO user = new LoginDTO();
			BeanUtils.copyProperties( user, userDataDTO );
			return user;
		
		} catch (UserPersistenceServiceException e) {
			throw new Exception( "Login failed" );
		} 		
	}
}