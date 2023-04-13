package com.interview.challenge.pokerplanning.repository;

import com.interview.challenge.pokerplanning.entity.Session;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, UUID> {
}
