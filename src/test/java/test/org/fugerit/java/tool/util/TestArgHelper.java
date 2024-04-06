package test.org.fugerit.java.tool.util;

import java.util.Properties;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.util.PropsIO;
import org.fugerit.java.tool.util.ArgHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestArgHelper {

	@Test
	void testArgRequiredOk() {
		Properties params = PropsIO.loadFromClassLoaderSafe( "params/test-params1.properties" );
		Assertions.assertThrows( ConfigRuntimeException.class , () -> ArgHelper.checkAllRequiredThrowRuntimeEx(params, "arg1", "not-exists" ) );
	}
	
	@Test
	void testArgRequiredEx() {
		Properties params = PropsIO.loadFromClassLoaderSafe( "params/test-params1.properties" );
		Assertions.assertTrue( ArgHelper.checkAllRequiredThrowRuntimeEx(params, "arg2", "arg3" ) );
	}
	
}
