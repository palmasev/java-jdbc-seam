package com.fjavierpalma.costura_test;

public class NotFoundUserException extends Exception {
    public NotFoundUserException(String username) {
        super("No se ha encontrado el nombre del usuario " + username);
    }
}
