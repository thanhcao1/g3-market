package net.blogsv.shop.model;

/**
 * Created by quang on 11/6/2017.
 */

public class User {
    private int id;



    private String username;
    private String password;
    private String email;
    private String phone;
    private String diachi;
    private int chucvu;

    public User() {
    }

    public User(int id,String username, String password, String email, String phone, String diachi, int chucvu) {
        this.id= id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.diachi = diachi;
        this.chucvu = chucvu;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public int getChucvu() {
        return chucvu;
    }

    public void setChucvu(int chucvu) {
        this.chucvu = chucvu;
    }
}

