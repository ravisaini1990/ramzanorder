package com.ravi.ramzanorder.modal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String pinCode;
    //private Set<Role> roles; // Assuming Role class exists

    // Default Constructor
    public UserDTO() {}

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", pinCode='" + pinCode + '\'' +
                '}';
    }
}
