package com.winpams.core.model;

import com.winpams.data.QueryBuilder;
import com.winpams.data.annotations.Column;
import com.winpams.data.annotations.Entity;
import com.winpams.data.model.BaseModel;


// TODO: Add relation to photos
@Entity(name = "pets")
public class Pet extends BaseModel {
    @Column(name = "name")
    public String name;

    @Column(name = "breed")
    public String breed;

    @Column(name = "age")
    public Integer age;

    @Column(name = "description")
    public String description;

    public final static QueryBuilder<Pet> query = new QueryBuilder<>(Pet.class);
}
