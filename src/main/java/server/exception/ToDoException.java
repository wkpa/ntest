package server.exception;

public class ToDoException extends RuntimeException {

    public ToDoException(final String message) {
        super(message);
    }
}
