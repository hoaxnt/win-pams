package com.winpams.core.model;

import com.winpams.data.QueryBuilder;
import com.winpams.data.annotations.Column;
import com.winpams.data.annotations.Entity;
import com.winpams.data.model.BaseModel;

import java.sql.Timestamp;

@Entity(name = "interviews")
public class Interview extends BaseModel {
    @Column(name = "adoption_request_id")
    public Integer adoptionRequestId;

    @Column(name = "interviewer_id")
    public Integer interviewerId;

    @Column(name = "start")
    public Timestamp start;

    @Column(name = "end")
    public Timestamp end;

    public final static QueryBuilder<Interview> query = new QueryBuilder<>(Interview.class);
}
