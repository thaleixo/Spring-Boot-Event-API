package br.com.nlw.events.Exceptions;

public class EventNotFoundException extends RuntimeException{

    public EventNotFoundException(String msg){
        super(msg);
    }
}
