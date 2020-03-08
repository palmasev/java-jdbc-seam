package com.fjavierpalma.costura_test;

import java.io.IOException;
import java.net.URL;

import java.sql.*;
import java.util.Properties;

public class HelloMessageProvider {
    private String lang;

    public HelloMessageProvider() {
        lang = "es";
    }

    public HelloMessageProvider(String lang) {
        this.lang = lang;
    }

    public String getMessage(String name) throws IOException {
        Properties properties = new Properties();
        URL resource = getClass().getClassLoader().getResource("translations/greet-translation.properties");

        properties.load(resource.openStream());

        return properties.get(lang) + " " + name;
    }

    public String greetUser(String username) throws NotFoundUserException, SQLException, IOException {
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        Connection conn = DriverManager.getConnection("url-database", "username", "password");

        PreparedStatement stmt = conn.prepareStatement("SELECT name FROM users where username = ?");
        stmt.setString(0, username);
        ResultSet rs = stmt.executeQuery();

        String name = null;

        if (!rs.first()) {
            throw new NotFoundUserException(username);
        }

        name = rs.getString("name");

        conn.close();
        return getMessage(name);
    }

}
