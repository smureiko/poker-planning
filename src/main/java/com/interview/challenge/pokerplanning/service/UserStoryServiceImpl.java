package com.interview.challenge.pokerplanning.service;

import com.interview.challenge.pokerplanning.entity.UserStory;
import com.interview.challenge.pokerplanning.entity.UserStoryStatus;
import com.interview.challenge.pokerplanning.repository.SessionRepository;
import com.interview.challenge.pokerplanning.repository.UserStoryRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserStoryServiceImpl implements UserStoryService {

    private static final String USER_STORY_NOT_EXIST_MESSAGE = "UserStory with id %s doesn't exist";

    private final UserStoryRepository userStoryRepository;
    private final SessionRepository sessionRepository;

    @Override
    public List<UserStory> findAllBySessionId(UUID sessionId) {
        if (sessionRepository.existsById(sessionId)) {
            return userStoryRepository.findAll();
        } else {
            throw new NoSuchElementException(String.format(SessionServiceImpl.SESSION_NOT_EXIST_MESSAGE, sessionId));
        }
    }

    @Override
    public UserStory create(UserStory userStory, UUID sessionId) {
        if (sessionRepository.existsById(sessionId)) {
            userStory.setStatus(UserStoryStatus.PENDING);
            return userStoryRepository.save(userStory);
        } else {
            throw new NoSuchElementException(String.format(SessionServiceImpl.SESSION_NOT_EXIST_MESSAGE, sessionId));
        }
    }

    @Override
    public UserStory deleteBySessionIdAndUserStoryId(UUID sessionId, UUID memberId) {
        UserStory userStory;
        if (sessionRepository.existsById(sessionId)) {
            userStory = userStoryRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException(String.format(USER_STORY_NOT_EXIST_MESSAGE, sessionId)));
            if (userStory.getStatus().equals(UserStoryStatus.PENDING)) {
                userStoryRepository.deleteById(memberId);
            } else {
                throw new IllegalArgumentException(String.format(
                    "User story with status %s can not be deleted",
                    userStory.getStatus()));
            }
        } else {
            throw new NoSuchElementException(String.format(SessionServiceImpl.SESSION_NOT_EXIST_MESSAGE, sessionId));
        }
        return userStory;
    }

    @Override
    public UserStory updateUserStoryBySessionId(UUID sessionId, UserStory userStoryToUpdate, UUID userStoryId) {
        UserStory userStory = null;
        if (sessionRepository.existsById(sessionId)) {
            userStory = userStoryRepository.findById(userStoryId)
                .orElseThrow(() -> new NoSuchElementException(String.format(
                    USER_STORY_NOT_EXIST_MESSAGE,
                    userStoryId)));
            userStory.setStatus(userStoryToUpdate.getStatus());
            userStory.setDescription(userStoryToUpdate.getDescription());
            userStory.setVotes(userStoryToUpdate.getVotes());
            userStoryRepository.save(userStory);
        }
        return userStory;
    }
}
