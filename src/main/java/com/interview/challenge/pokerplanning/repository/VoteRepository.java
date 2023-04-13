package com.interview.challenge.pokerplanning.repository;

import com.interview.challenge.pokerplanning.entity.Vote;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, UUID> {
}
