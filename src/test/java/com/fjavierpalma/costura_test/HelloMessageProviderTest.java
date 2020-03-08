package com.fjavierpalma.costura_test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HelloMessageProviderTest {



    private HelloMessageProvider helloMessageProvider;

    @BeforeEach
    void setUp() {
        helloMessageProvider = new HelloMessageProvider();
    }

    @Test
    void ShowSpanishMessage() throws IOException {
        assertEquals("Hola Juan", helloMessageProvider.getMessage("Juan"));
    }

    @Test
    void ShowEnglishMessage() throws IOException {
        helloMessageProvider = new HelloMessageProvider("en");
        assertEquals("Hello Juan", helloMessageProvider.getMessage("Juan"));
    }

    @Test
    void greetsByUsernameFoundUserOnDatabase() throws Exception {

        helloMessageProvider = getMessageProviderForGreetsByUsernameTest(true);

        assertEquals("Hola Francisco Javier", helloMessageProvider.greetUser("fjavierpalma"));
    }

    @Test
    void greetsByUsernameNotFoundUserOnDatabase() {

        helloMessageProvider = getMessageProviderForGreetsByUsernameTest(false);

        assertThrows(
                NotFoundUserException.class,
                () -> helloMessageProvider.greetUser("fjavierpalma"),
                "No se ha encontrado el nombre del usuario fjavierpalma");
    }

    private HelloMessageProvider getMessageProviderForGreetsByUsernameTest(boolean founded) {
        return new HelloMessageProvider() {
            @Override
            protected Connection getConnection() throws SQLException {
                Connection conn = mock(Connection.class);
                PreparedStatement stmt = mock(PreparedStatement.class);
                ResultSet rs = mock(ResultSet.class);
                when(conn.prepareStatement("SELECT name FROM users where username = ?")).thenReturn(stmt);
                when(stmt.executeQuery()).thenReturn(rs);
                when(rs.first()).thenReturn(founded);
                when(rs.getString(eq("name"))).thenReturn("Francisco Javier");
                return conn;
            }
        };
    }
}
