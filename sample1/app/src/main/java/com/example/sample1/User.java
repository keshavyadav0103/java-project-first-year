package com.example.sample1;

public class User {

    private String username;
    private String name;
    private String password;
    private String cpassword;

    public User() {

    }

    public User(String username, String name, String password, String cpassword) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.cpassword = cpassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCpassword() {
        return cpassword;
    }

    public void setCpassword(String cpassword) {
        this.cpassword = cpassword;
    }
}
