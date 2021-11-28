package server;

import java.io.Serializable;

public class Message implements Serializable{
    private final String msg;
    
    public Message(String msg){
        this.msg = msg;
    }
    
    public String getMsg(){
        return this.msg;
    }
}
