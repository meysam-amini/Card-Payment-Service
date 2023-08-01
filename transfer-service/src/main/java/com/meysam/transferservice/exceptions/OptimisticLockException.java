package com.meysam.transferservice.exceptions;

public class OptimisticLockException extends RuntimeException{

    public OptimisticLockException(String msg){
        super(msg);
    }
}
