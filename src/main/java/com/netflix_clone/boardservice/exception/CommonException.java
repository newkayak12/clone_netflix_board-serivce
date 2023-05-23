package com.netflix_clone.boardservice.exception;

public class CommonException extends Exception{

    public CommonException(BecauseOf reason){
        super(reason.getMsg());
    }
}
