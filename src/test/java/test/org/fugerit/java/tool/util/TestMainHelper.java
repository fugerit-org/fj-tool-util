package test.org.fugerit.java.tool.util;

import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.function.SafeFunction;
import org.fugerit.java.core.function.SimpleValue;
import org.fugerit.java.core.lang.ex.CodeException;
import org.fugerit.java.core.util.result.Result;
import org.fugerit.java.tool.util.MainHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
class TestMainHelper {

    @Test
    void testMainHelper() {
        // main action
        SafeFunction.applySilent( () -> MainHelper.DEFAULT_ERROR_ACTION.accept( new ConfigRuntimeException( "Test exit" ) ) );
        // set default exit action
        MainHelper.setDefaultExitAction( ec -> log.info( "Exit : {}", ec ) );
        // set default exception action
        MainHelper.setDefaultErrorAction( ex -> {
            log.warn( "print my tool help..." );
            MainHelper.DEFAULT_ERROR_ACTION.accept( ex );
        } );
        // ok scenario
        MainHelper.handleMain( () -> log.info( "Ok operation" ) );
        Assertions.assertEquals( MainHelper.OK_DEFAULT, MainHelper.evaluateExitCode( null ) );
        // ko scenario with code exception
        MainHelper.handleMain( () -> { if (true) throw new CodeException( "test" ); } );
        // ko scenario with normale exception
        MainHelper.handleMain( () -> { if (true) throw new Exception( "test" ); } );
        // ok scenario do op
        final SimpleValue<Integer> value = new SimpleValue<>( 1 );
        MainHelper.handleMain( () -> value.setValue(Result.RESULT_CODE_OK) );
        Assertions.assertEquals(  Result.RESULT_CODE_OK, value.getValue().intValue() );
    }

}
