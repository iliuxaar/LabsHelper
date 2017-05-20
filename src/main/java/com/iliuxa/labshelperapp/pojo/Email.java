package com.iliuxa.labshelperapp.pojo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

@DatabaseTable(tableName = "Email")
public class Email {

    public static final String FIELD_EMAIL = "email";
    public static final String FIELD_PASSWORD = "password";

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = FIELD_EMAIL, canBeNull = false)
    private String email;

    @DatabaseField(columnName = FIELD_PASSWORD, canBeNull = false)
    private String password;

    Email(){}

    public Email(String email, String password) throws UnsupportedEncodingException {
        this.email = email;
        this.password = Base64.getEncoder().encodeToString(password.getBytes());
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        int result = email.hashCode();
        return result += password.hashCode() * 31;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        Email email = (Email) obj;
        return password.equals(email.password) && this.email.equals(email.email);
    }
}
