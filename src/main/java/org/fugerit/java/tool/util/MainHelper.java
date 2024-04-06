package org.fugerit.java.tool.util;

import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.core.function.SafeFunction;
import org.fugerit.java.core.function.UnsafeConsumer;
import org.fugerit.java.core.function.UnsafeVoid;
import org.fugerit.java.core.lang.ex.CodeEx;
import org.fugerit.java.core.util.result.Result;

import java.util.function.IntConsumer;

@Slf4j
public class MainHelper {

    private MainHelper() {}

    /**
     * Default result for exit OK : <code>0</code>
     */
    public static final int OK_DEFAULT = Result.RESULT_CODE_OK;

    /**
     * Default result for exit FAIL : <code>1</code>
     */
    public static final int FAIL_DEFAULT = 1;

    /**
     * Default result for exit FAIL_MISSING_REQUIRED_PARAM : <code>2</code>
     */
    public static final int FAIL_MISSING_REQUIRED_PARAM = 2;


    public static final IntConsumer DEFAULT_EXIT_ACTION = System::exit;

    public static final UnsafeConsumer<Throwable, Exception> DEFAULT_ERROR_ACTION = ex -> log.error( String.format( "Error : %s", ex ), ex );

    private static IntConsumer defaultExitAction = DEFAULT_EXIT_ACTION;

    private static UnsafeConsumer<Throwable, Exception> defaultErrorAction = DEFAULT_ERROR_ACTION;

    public static IntConsumer getDefaultExitAction() {
        return defaultExitAction;
    }

    public static void setDefaultExitAction(IntConsumer defaultExitAction) {
        MainHelper.defaultExitAction = defaultExitAction;
    }

    public static UnsafeConsumer<Throwable, Exception> getDefaultErrorAction() {
        return defaultErrorAction;
    }

    public static void setDefaultErrorAction(UnsafeConsumer<Throwable, Exception> defaultErrorAction) {
        MainHelper.defaultErrorAction = defaultErrorAction;
    }

    public static int evaluateExitCode(Throwable ex ) {
        if ( ex instanceof CodeEx) {
            CodeEx codeEx = (CodeEx) ex;
            return codeEx.getCode();
        } else if ( ex != null ) {
            return FAIL_DEFAULT;
        } else {
            return OK_DEFAULT;
        }
    }

    public static void handleMain( UnsafeVoid<Exception> mainAction ) {
        handleMain( mainAction, getDefaultErrorAction() );
    }

    public static void handleMain(UnsafeVoid<Exception> mainAction, UnsafeConsumer<Throwable, Exception> errorAction ) {
        handleMain( mainAction, errorAction, getDefaultExitAction() );
    }

    public static void handleMain(UnsafeVoid<Exception> mainAction, UnsafeConsumer<Throwable, Exception> errorAction, IntConsumer exitAction ) {
        try {
            mainAction.apply();
            exitAction.accept( OK_DEFAULT );
        } catch (Throwable ex) {
            SafeFunction.applySilent( () -> errorAction.accept( ex ) );
            exitAction.accept( evaluateExitCode( ex ) );
        }
    }

}
