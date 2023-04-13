package com.interview.challenge.pokerplanning.service;

import com.interview.challenge.pokerplanning.entity.Session;
import java.util.List;
import java.util.UUID;

public interface SessionService {

    List<Session> findAll();

    Session create(Session session);

    Session findById(UUID id);

    Session deleteById(UUID id);
}
