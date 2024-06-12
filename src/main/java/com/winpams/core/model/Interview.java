package com.winpams.core.model;

import com.winpams.core.annotations.Column;
import com.winpams.core.annotations.Entity;

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
}
