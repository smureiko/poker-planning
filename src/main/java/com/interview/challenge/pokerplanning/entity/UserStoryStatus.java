package com.interview.challenge.pokerplanning.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum UserStoryStatus {

    PENDING, VOTING, VOTED
}