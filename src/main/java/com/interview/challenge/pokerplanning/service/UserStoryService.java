package com.interview.challenge.pokerplanning.service;

import com.interview.challenge.pokerplanning.entity.UserStory;
import java.util.List;
import java.util.UUID;

public interface UserStoryService {

    List<UserStory> findAllBySessionId(UUID sessionId);

    UserStory create(UserStory userStory, UUID id);

    UserStory deleteBySessionIdAndUserStoryId(UUID id, UUID sessionId);

    UserStory updateUserStoryBySessionId(UUID id, UserStory userStory, UUID userStoryId);
}
