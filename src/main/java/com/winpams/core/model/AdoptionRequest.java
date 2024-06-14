package com.winpams.core.model;

import com.winpams.data.QueryBuilder;
import com.winpams.data.annotations.Column;
import com.winpams.data.annotations.Entity;
import com.winpams.data.model.BaseModel;

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

    public final static QueryBuilder<AdoptionRequest> query = new QueryBuilder<>(AdoptionRequest.class);
}
