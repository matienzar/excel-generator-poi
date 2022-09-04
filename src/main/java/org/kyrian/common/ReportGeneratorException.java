package org.kyrian.common;

public class ReportGeneratorException extends RuntimeException{

    public ReportGeneratorException(String message, Throwable e){
        super(message,e);
    }
}
