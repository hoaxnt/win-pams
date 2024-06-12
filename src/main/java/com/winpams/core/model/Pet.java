package com.winpams.core.model;

import com.winpams.core.annotations.Column;
import com.winpams.core.annotations.Entity;


// TODO: Add relation to photos
@Entity(name = "pets")
public class Pet extends BaseModel {
    @Column(name = "name")
    public String name;

    @Column(name = "species")
    public String species;

    @Column(name = "breed")
    public String breed;

    @Column(name = "age")
    public Integer age;

    @Column(name = "description")
    public String description;
}
