package test.org.fugerit.java.tool.util;

import java.util.Properties;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.util.PropsIO;
import org.fugerit.java.tool.util.ArgHelper;
import org.junit.Assert;
import org.junit.Test;

public class TestArgHelper {

	@Test
	public void testArgRequiredOk() {
		Properties params = PropsIO.loadFromClassLoaderSafe( "params/test-params1.properties" );
		Assert.assertThrows( ConfigRuntimeException.class , () -> ArgHelper.checkAllRequiredThrowRuntimeEx(params, "arg1", "not-exists" ) );
	}
	
	@Test
	public void testArgRequiredEx() {
		Properties params = PropsIO.loadFromClassLoaderSafe( "params/test-params1.properties" );
		Assert.assertTrue( ArgHelper.checkAllRequiredThrowRuntimeEx(params, "arg2", "arg3" ) );
	}
	
}
