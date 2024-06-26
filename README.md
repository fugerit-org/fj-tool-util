# fj-tool-util

Runtime tool utilities

[![Keep a Changelog v1.1.0 badge](https://img.shields.io/badge/changelog-Keep%20a%20Changelog%20v1.1.0-%23E05735)](CHANGELOG.md) 
[![Maven Central](https://img.shields.io/maven-central/v/org.fugerit.java/fj-tool-util.svg)](https://mvnrepository.com/artifact/org.fugerit.java/fj-tool-util) 
[![license](https://img.shields.io/badge/License-Apache%20License%202.0-teal.svg)](https://opensource.org/licenses/Apache-2.0) 
[![code of conduct](https://img.shields.io/badge/conduct-Contributor%20Covenant-purple.svg)](https://github.com/fugerit-org/fj-universe/blob/main/CODE_OF_CONDUCT.md)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=fugerit-org_fj-tool-util&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=fugerit-org_fj-tool-util)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=fugerit-org_fj-tool-util&metric=coverage)](https://sonarcloud.io/summary/new_code?id=fugerit-org_fj-tool-util)

[![Java runtime version](https://img.shields.io/badge/run%20on-java%208+-%23113366.svg?style=for-the-badge&logo=openjdk&logoColor=white)](https://universe.fugerit.org/src/docs/versions/java11.html)
[![Java build version](https://img.shields.io/badge/build%20on-java%2011+-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)](https://universe.fugerit.org/src/docs/versions/java11.html)
[![Apache Maven](https://img.shields.io/badge/Apache%20Maven-3.9.0+-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)](https://universe.fugerit.org/src/docs/versions/maven3_9.html)
[![Fugerit Github Project Conventions](https://img.shields.io/badge/Fugerit%20Org-Project%20Conventions-1A36C7?style=for-the-badge&logo=Onlinect%20Playground&logoColor=white)](https://universe.fugerit.org/src/docs/conventions/index.html)

Sample helpers class to build java standalone tools

## 1. ArgHelper

Check if all the parameters needed are set. If not a ConfigRuntimeException with code

```java
try {
    ParamHolder holder = ParamHolder.newAndHolder(
            ParamHolder.newHolder("arg1"),
            ParamHolder.newOrHolder("arg2", "arg3"));
    if ( ArgHelper.checkAllRequiredThrowRuntimeEx(params, holder) ) {
        // code
    }
} catch (ConfigRuntimeException e) {
    if ( e.getCode() == MainHelper.FAIL_MISSING_REQUIRED_PARAM ) {
        log.info( "Missing parameters : {}", e.getMessage() );    
    } else {
        // code
    }
}
```

## 2. MainHelper

Handle main method

```java
public static void main( String[] args ) {
    MainHelper.handleMain( () -> {
        // code    
    } ); 
}
```

By default any exception will be evaluated as follow in an exit code

```java
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
```

Behaviours may be customized, for instance this way exit code will be printed with no System.exit() call : 

```java
MainHelper.setDefaultExitAction( ec -> log.warn( "Exit : {}", ec ) );
```