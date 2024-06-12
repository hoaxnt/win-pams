package com.winpams.core.model;

import com.winpams.core.annotations.Entity;
import com.winpams.core.annotations.Column;

@Entity(name = "adoption_requests")
public class AdoptionRequest extends BaseModel {
    public enum Status {
        PENDING,
        APPROVED,
        REJECTED
    }

    @Column(name = "user_id")
    public Integer userId;

    @Column(name = "pet_id")
    public Integer petId;

    @Column(name = "status")
    public Status status = Status.PENDING;

    @Column(name = "additional_info")
    public String additionalInfo;
}
