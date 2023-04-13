package com.interview.challenge.pokerplanning.service;

import com.interview.challenge.pokerplanning.entity.Session;
import com.interview.challenge.pokerplanning.repository.SessionRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    public static final String SESSION_NOT_EXIST_MESSAGE = "Session with id %s does not exist";

    private final SessionRepository sessionRepository;

    @Override
    public List<Session> findAll() {
        return sessionRepository.findAll();
    }

    @Override
    public Session create(Session session) {
        return sessionRepository.save(session);
    }

    @Override
    public Session findById(UUID id) {
        return sessionRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException(String.format(SESSION_NOT_EXIST_MESSAGE, id)));
    }

    @Override
    public Session deleteById(UUID id) {
        Session session = sessionRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException(String.format(SESSION_NOT_EXIST_MESSAGE, id)));
        sessionRepository.deleteById(id);
        return session;
    }
}
