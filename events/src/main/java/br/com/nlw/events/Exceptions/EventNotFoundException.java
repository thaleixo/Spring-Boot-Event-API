package br.com.nlw.events.Exceptions;

public class EventNotFound extends RuntimeException{

    public EventNotFound(String msg){
        super(msg);
    }
}
