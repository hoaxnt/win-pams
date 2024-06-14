package com.winpams.core.model;

import com.winpams.data.annotations.Column;
import com.winpams.data.annotations.Entity;
import com.winpams.data.model.BaseModel;

@Entity(name = "pet_photos")
public class PetPhoto extends BaseModel {
    @Column(name = "pet_id")
    public Integer petId;

    @Column(name = "photo_url")
    public String url;

    public final static QueryBuilder<PetPhoto> query = new QueryBuilder<>(PetPhoto.class);
}
