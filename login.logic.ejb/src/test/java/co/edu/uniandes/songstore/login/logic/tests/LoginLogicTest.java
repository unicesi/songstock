package co.edu.uniandes.songstore.login.logic.tests;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import co.edu.uniandes.commons.maven.MavenArtifactResolver;
import co.edu.uniandes.songstore.login.logic.dto.LoginDTO;
import co.edu.uniandes.songstore.login.logic.ejb.LoginService;
import co.edu.uniandes.songstore.login.persistence.api.IUserPersistenceService;
import co.edu.uniandes.songstore.login.persistence.dto.UserDataDTO;
import co.edu.uniandes.songstore.login.persistence.model.User;
import co.edu.uniandes.songstore.login.logic.api.ILoginService;
import co.edu.uniandes.songstore.login.logic.api.ILoginDTO;


@RunWith(Arquillian.class)
public class LoginLogicTest {

	/**
	 * Deploy persistence class as a war-file
	 * @return
	 */
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap
        	
        	// use a WAR archive for testing
        	.create( WebArchive.class, "LoginLogicTest.war" )
        	
        	// including the UserService classes
            .addPackage( LoginService.class.getPackage() )
            .addPackage( LoginDTO.class.getPackage() )
            .addPackage( UserDataDTO.class.getPackage() )
            
            // add dependencies
            .addAsLibraries( MavenArtifactResolver.resolve(
            		"co.edu.uniandes.songstore:user.persistence.jpa:0.0.1-SNAPSHOT" ))
            .addAsLibraries( MavenArtifactResolver.resolve(
            		"co.edu.uniandes.songstore:user.persistence.api:0.0.1-SNAPSHOT" ))
            .addAsLibraries( MavenArtifactResolver.resolve(
            		"co.edu.uniandes.songstore:login.logic.api:0.0.1-SNAPSHOT"
            ))
            
            // add log4j.properties
            .addAsResource( "log4j.properties", "log4j.properties")
            .addAsWebInfResource( "log4j.properties" )
            // add web.xml to WEB-INF
            .addAsWebInfResource("web.xml")
            // add glassfish-resources to WEB-INF
            .addAsWebInfResource("glassfish-resources.xml")  
            // including empty beans.xml to WEB-INF
            .addAsWebInfResource( EmptyAsset.INSTANCE, "beans.xml")
            ;
        
    }	
	
    // Injected resources
    // ----------------------------------------

    @EJB ( mappedName="java:global/LoginLogicTest/LoginService" ) 
    ILoginService service;
    
    @EJB ( mappedName="java:global/LoginLogicTest/UserPersistenceService" ) 
    IUserPersistenceService persistenceService;
    
    @PersistenceContext( unitName="user" ) 
    EntityManager em;
    
    @Inject
    UserTransaction utx;    
    
    // Test preparation
    // ----------------------------------------
    
    @Before
    public void preparePersistenceTest() throws Exception {
        clearData();
        insertData();
    }    
    private void clearData() throws Exception {
        utx.begin();
        em.joinTransaction();
        em.createQuery("delete from User").executeUpdate();
        utx.commit();
    }

    private void insertData() throws Exception {
    	
    	User testUser = new User(1L, "joe", "password", "Joe sample user");
    	
        utx.begin();
        em.joinTransaction();

        em.persist( testUser );
        
        utx.commit();
        // clear the persistence context (first-level cache)
        em.clear();
    }

    // Tests
    // ----------------------------------------
    
    @Test
    public void readData()  {
    	try {
			User user = em.find( User.class, 1L);
			if (user.getID() != 1L) {
				fail( "returned user with wrong id" );
			}
		} catch (Exception e) {
			System.out.println( "user not found" );
			fail ( " - user not found " + e.getMessage() );
		}
    }
    
    @Test
    public void shouldLogin() {
    	try {
    		ILoginDTO loginData = new LoginDTO();
    		loginData.setLogin( "joe" );
    		loginData.setPassword( "password" );
    		
			ILoginDTO res = service.login( loginData );
			if ( res == null ) {
				fail( "Login error with correct values - using JPA" );
			}
		} catch (Exception e) {
			System.out.println( " - Error in the service " + e.getMessage() ); 
			fail ( " - Error in the service " + e.getMessage() );
		}
    }

    @Test
    public void shouldNotLogin() throws Exception {
    	try {
    		ILoginDTO loginData = new LoginDTO();
    		loginData.setName( "joe" );
    		loginData.setPassword( "other" );
    		
			ILoginDTO res = service.login( loginData );
        	if ( res != null ) {
        		fail( "Login with wrong password - using JPA" );
        	}
        	
		} catch (Exception e) {
			// must fail
		}    
    	
    }

}
