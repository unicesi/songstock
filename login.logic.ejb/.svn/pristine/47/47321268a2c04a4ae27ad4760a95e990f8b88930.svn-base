package co.edu.uniandes.songstore.login.logic.aspectj;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import co.edu.uniandes.songstore.login.logic.api.ILoginDTO;

public aspect RegistrarLogin {

	public pointcut registrarLogin() : call(public * co.edu.uniandes.songstore.login.logic.api.ILoginService.login(..));
	
	before() : registrarLogin() {
		Log log = LogFactory.getLog(thisJoinPoint.getClass());
		log.info("Access attemp: "+((ILoginDTO)thisJoinPoint.getArgs()[0]).getLogin());
		
		try {
		    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("./logins", true)));
		    out.println(((ILoginDTO)thisJoinPoint.getArgs()[0]).getLogin()+";"+new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
		    out.close();
		} catch (IOException e) {
			log.info("Error in file: "+e.getMessage());
		}

		//intentar guardar en un archivo
	}
}
