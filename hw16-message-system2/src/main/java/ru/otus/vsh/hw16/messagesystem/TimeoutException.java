package ru.otus.vsh.hw16.messagesystem;

public class TimeoutException extends RuntimeException{
    public TimeoutException(){
        super();
    }

    public TimeoutException(String message){
        super(message);
    }
}
