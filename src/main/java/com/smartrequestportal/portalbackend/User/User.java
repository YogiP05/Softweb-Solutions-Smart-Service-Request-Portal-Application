package com.smartrequestportal.portalbackend.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name="users")
public class User {
    @Id
    private long id;
    private String username;

    @Column(unique=true)
    private String email;

    @JsonIgnore
    private String hashedPassword;

    @Enumerated(EnumType.STRING)
    private userRole role;
    private Boolean isActive;

    @Column(nullable = false)
    public String securityQuestion;


    @JsonIgnore
    @Column(nullable = false)
    public String securityAnswerHashed;

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswerHashed() {
        return securityAnswerHashed;
    }

    public void setSecurityAnswerHashed(String securityAnswerHashed) {
        this.securityAnswerHashed = securityAnswerHashed;
    }

    public User() {
    }

    public User(long id, String username, String email, String hashedPassword,
                userRole role, Boolean isActive) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.role = role;
        this.isActive = isActive;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public userRole getRole() {
        return role;
    }

    public void setRole(userRole role) {
        this.role = role;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

}
