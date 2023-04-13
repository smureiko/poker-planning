package com.interview.challenge.pokerplanning.repository;

import com.interview.challenge.pokerplanning.entity.UserStory;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStoryRepository extends JpaRepository<UserStory, UUID> {
}
