package com.example.model;

import lombok.Data;

import java.util.Objects;
import java.util.regex.Pattern;

@Data
public class EmailAddress {
    private String address;
    private String isVerified;
    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public EmailAddress(String address) {
        if (isInvalid(address)) {
            throw new IllegalArgumentException("Invalid email address: " + address);
        }
        this.address = address;
    }

    public EmailAddress(String address, String isVerified) {
        if (isInvalid(address)) {
            throw new IllegalArgumentException("Invalid email address: " + address);
        }
        this.address = address;
        this.isVerified = isVerified;
    }

    public static boolean isInvalid(String email) {
        return email == null || email.isEmpty() || !EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

    public String getValue() {
        return address;
    }

    @Override
    public String toString() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        EmailAddress that = (EmailAddress) o;
        return Objects.equals(this.address, that.address);
    }

    @Override
    public int hashCode() {
        return this.address != null ? Objects.hash(this.address) : 0;
    }

}
