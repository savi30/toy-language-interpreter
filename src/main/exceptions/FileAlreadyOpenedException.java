package main.exceptions;

public class FileAlreadyOpenedException extends RuntimeException {
    public FileAlreadyOpenedException(String message) {
        super(message);
    }

    public FileAlreadyOpenedException() {
        super();
    }
}
