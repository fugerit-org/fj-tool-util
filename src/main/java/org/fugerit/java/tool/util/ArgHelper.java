package org.fugerit.java.tool.util;

import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.lang.helpers.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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

	public static boolean checkAllRequiredThrowRuntimeEx( Properties params, ParamHolder holder ) {
		List<String> missingParams = new ArrayList<>();
		checkRequired( params, holder, missingParams );
		if ( !missingParams.isEmpty() ) {
			throw new ConfigRuntimeException(
					String.format( "At least one required parameter missing : %s", StringUtils.concat( ", ", missingParams ) ),
					MainHelper.FAIL_MISSING_REQUIRED_PARAM );
		}
		return missingParams.isEmpty();
	}

	public static boolean checkRequired( Properties params, ParamHolder holder, List<String> missingParams ) {
		if ( holder.isAndHolder() ) {
			for ( ParamHolder kid : holder.getParams() ) {
				if( !checkRequired( params, kid, missingParams ) ) {
					return Boolean.FALSE.booleanValue();
				}
			}
			return Boolean.TRUE.booleanValue();
		} else if ( holder.isOrHolder() ) {
			List<String> missingParamsOr = new ArrayList<>();
			for ( ParamHolder kid : holder.getParams() ) {
				if( checkRequired( params, kid, missingParamsOr ) ) {
					return Boolean.TRUE.booleanValue();
				}
			}
			missingParams.addAll( missingParamsOr );
			return Boolean.FALSE.booleanValue();
		} else {
			boolean checkParam = params.containsKey( holder.getName() );
			if ( !checkParam ) {
				missingParams.add( holder.getName() );
			}
			return checkParam;
		}
	}

	public static boolean checkAllRequiredThrowRuntimeEx( Properties params, String... keys ) {
		return checkAllRequiredThrowRuntimeEx( params, ParamHolder.newAndHolder( keys ) );
	}
	
}
