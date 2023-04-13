package com.interview.challenge.pokerplanning.service;

import com.interview.challenge.pokerplanning.entity.Vote;
import com.interview.challenge.pokerplanning.repository.SessionRepository;
import com.interview.challenge.pokerplanning.repository.VoteRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final SessionRepository sessionRepository;

    @Override
    public List<Vote> findAllBySessionId(UUID sessionId) {
        if (sessionRepository.existsById(sessionId)) {
            return voteRepository.findAll();
        } else {
            throw new NoSuchElementException(String.format(SessionServiceImpl.SESSION_NOT_EXIST_MESSAGE, sessionId));
        }
    }

    @Override
    public Vote create(Vote vote, UUID sessionId) {
        if (sessionRepository.existsById(sessionId)) {
            return voteRepository.save(vote);
        } else {
            throw new NoSuchElementException(String.format(SessionServiceImpl.SESSION_NOT_EXIST_MESSAGE, sessionId));
        }
    }
}
