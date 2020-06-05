package scs.iss.agentie.exceptions;

public class UserNotFound extends Exception{
    public UserNotFound() {
        super();
    }

    public UserNotFound(String message) {
        super(message);
    }
}
