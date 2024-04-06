package test.org.fugerit.java.tool.util;

import java.util.Properties;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.cli.ArgUtils;
import org.fugerit.java.core.util.PropsIO;
import org.fugerit.java.tool.util.ArgHelper;
import org.fugerit.java.tool.util.ParamHolder;
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

	@Test
	void testParamHolderOk() {
		Properties params = PropsIO.loadFromClassLoaderSafe( "params/test-params1.properties" );
		ParamHolder holder = ParamHolder.newAndHolder(
				ParamHolder.newHolder( "arg1" ),
				ParamHolder.newOrHolder( "arg2", "arg3" ) );
		boolean ok = ArgHelper.checkAllRequiredThrowRuntimeEx( params, holder );
		Assertions.assertTrue( ok );
		Assertions.assertFalse( holder.isNameHolder() );
		Assertions.assertTrue( ParamHolder.newHolder( "test" ).isNameHolder() );
	}

	@Test
	void testParamHolderKo() {
		Properties params = PropsIO.loadFromClassLoaderSafe( "params/test-params1.properties" );
		ParamHolder holderKo1 = ParamHolder.newAndHolder(
				ParamHolder.newHolder( "arg1" ),
				ParamHolder.newOrHolder( "arg4", "arg5" ) );
		Assertions.assertThrows( ConfigRuntimeException.class, () -> ArgHelper.checkAllRequiredThrowRuntimeEx( params, holderKo1 ) );
		ParamHolder holderKo2 = ParamHolder.newOrHolder(
				ParamHolder.newHolder( "arg9" ),
				ParamHolder.newAndHolder( "arg6", "arg7" ) );
		Assertions.assertThrows( ConfigRuntimeException.class, () -> ArgHelper.checkAllRequiredThrowRuntimeEx( params, holderKo2 ) );

	}

}
