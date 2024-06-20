package com.winpams.core.model;

import com.winpams.core.utils.Hasher;
import com.winpams.data.QueryBuilder;
import com.winpams.data.annotations.Column;
import com.winpams.data.annotations.Entity;
import com.winpams.data.model.BaseModel;

@Entity(name = "users")
public class User extends BaseModel {
    @Column(name = "first_name")
    public String firstName;

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

    public final static QueryBuilder<User> query = new QueryBuilder<>(User.class);

    public boolean verify(String password) {
        return Hasher.verifyPassword(password, this.password);
    }
}
