package com.winpams.core.model;

import com.winpams.core.annotations.Column;
import com.winpams.core.annotations.Entity;

@Entity(name = "users")
public class User extends BaseModel {
    @Column(name = "given_name")
    public String givenName;

    @Column(name = "last_name")
    public String lastName;

    @Column(name = "contact_number")
    public String contactNumber;

    @Column(name = "email")
    public String email;

    @Column(name = "username")
    public String username;

    @Column(name = "password")
    public String password;

    @Column(name = "is_admin")
    public Boolean isAdmin = false;
}
