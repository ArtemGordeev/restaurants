package com.util.exception;

public class NotFoundException extends ApplicationException {
    public static final String NOT_FOUND_EXCEPTION = "Not found entity with {0}";

    //  http://stackoverflow.com/a/22358422/548473
    public NotFoundException(String arg) {
        super(ErrorType.DATA_NOT_FOUND, NOT_FOUND_EXCEPTION, arg);
    }
}

////  http://stackoverflow.com/a/22358422/548473
//@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "No data found")  // 422
//public class NotFoundException extends RuntimeException{
//    public NotFoundException(String message) {
//        super(message);
//    }
//}