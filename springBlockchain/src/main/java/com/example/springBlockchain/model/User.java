package com.example.springBlockchain.model;
import jakarta.persistence.*;


@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    public void setId(Long id){
        this.id = id;
    }
    public Long getId(){
        return id;
    }
    public void setUsername(String username){
        this.username = username;
    }
    private String getUsername(){
        return username;
    }
    public void setPassword(String password){
        this.password = password;
    }
    private String getPassword(){
        return password;
    }

}
