package com.winpams.core.model;

import com.winpams.core.annotations.Column;
import com.winpams.core.annotations.Entity;

@Entity(name = "pet_photos")
public class PetPhoto extends BaseModel {
    @Column(name = "pet_id")
    public Integer petId;

    @Column(name = "photo_url")
    public String url;
}
