package com.example.expensetracker.dtos;

import com.googlecode.jmapper.annotations.JGlobalMap;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@JGlobalMap
public class SignUpDto {
    @NotBlank
    @Size(max = 40)
    private String fullName;

    @NaturalId(mutable=true)
    @NotBlank
    @Size(max = 40)
    @Email
    @Column(unique = true)
    private String email;

    private String password;

    @Pattern(regexp = "(^$|[0-9]{10})")
    private String phoneNumber;

    private String avatarUri;

    public SignUpDto() { }

    public SignUpDto(String fullName, String email, String password, String phoneNumber, String avatarUri) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.avatarUri = avatarUri;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAvatarUri() {
        return avatarUri;
    }

    public void setAvatarUri(String avatarUri) {
        this.avatarUri = avatarUri;
    }
}
