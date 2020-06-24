package com.topband.bluetooth.common.enums;

public interface IErrorMessage {
    
     String getMessage();
    
     int getStatus();
    
     IErrorMessage getError(int code);
     String getMsgCode() ;

}
