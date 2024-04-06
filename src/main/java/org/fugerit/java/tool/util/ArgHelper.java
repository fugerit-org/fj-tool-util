package org.fugerit.java.tool.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.lang.helpers.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ArgHelper {

	private ArgHelper() {}
	
	public static String checkRequired( Properties params, String key ) throws ConfigException {
		return checkRequired(params, key, null);
	}
	
	public static String checkRequired( Properties params, String key, String defaultValue ) throws ConfigException {
		String value = params.getProperty(key,defaultValue);
		if ( value != null ) {
			log.trace( "required parem '{}' found '{}'" );
		} else {
			throw new ConfigException( String.format( "Missing required parameter : %s", key ), MainHelper.FAIL_MISSING_REQUIRED_PARAM );
		}
		return value;
	}
	
	public static boolean checkAllRequiredThrowRuntimeEx( Properties params, String... keys ) {
		List<String> notFound = new ArrayList<>(); 
		for ( int k=0; k<keys.length; k++ ) {
			String currentKey = keys[k];
			try {
				checkRequired(params, currentKey);
			} catch (ConfigException e) {
				notFound.add( currentKey );
				log.info( e.getMessage() );
			}
		}
		if ( !notFound.isEmpty() ) {
			throw new ConfigRuntimeException( String.format( "Missing parameters : %s", StringUtils.concat("," , notFound) ), MainHelper.FAIL_MISSING_REQUIRED_PARAM );
		}
		return notFound.isEmpty();
	}
	
}
