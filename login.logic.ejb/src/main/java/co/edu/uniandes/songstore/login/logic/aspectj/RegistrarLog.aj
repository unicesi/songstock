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

public aspect RegistrarLog {

	
	public pointcut registrarLog() : execution(@co.edu.uniandes.songstore.login.logic.annotation.Log * *(..));
	
	before() : registrarLog() {
		Log log = LogFactory.getLog(thisJoinPoint.getThis().getClass());
		log.info("Start Method: "+thisJoinPoint.getThis().getClass()+
				"."+thisJoinPoint.getSignature().getName()+
				" at "+new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a").format(new Date()));
	}
	
	after() : registrarLog() {
		Log log = LogFactory.getLog(thisJoinPoint.getClass());
		log.info("End Method: "+thisJoinPoint.getClass()+
				"."+thisJoinPoint.getSignature().getName()+
				" at "+new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a").format(new Date()));
	}
}
