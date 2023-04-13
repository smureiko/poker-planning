package com.interview.challenge.pokerplanning.controller;

import com.interview.challenge.pokerplanning.entity.Member;
import com.interview.challenge.pokerplanning.entity.Session;
import com.interview.challenge.pokerplanning.entity.UserStory;
import com.interview.challenge.pokerplanning.entity.Vote;
import com.interview.challenge.pokerplanning.service.MemberService;
import com.interview.challenge.pokerplanning.service.SessionService;
import com.interview.challenge.pokerplanning.service.UserStoryService;
import com.interview.challenge.pokerplanning.service.VoteService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sessions")
public class SessionController {

    private final SessionService sessionService;
    private final UserStoryService userStoryService;
    private final MemberService memberService;
    private final VoteService voteService;

    @GetMapping
    public List<Session> getAllSessions() {
        return sessionService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Session createSession(@RequestBody Session session) {
        return sessionService.create(session);
    }

    @GetMapping(value = "/{id}")
    public Session getSessionById(@PathVariable UUID id) {
        return sessionService.findById(id);
    }

    @DeleteMapping(value = "/{id}")
    public Session deleteSessionById(@PathVariable UUID id) {
        return sessionService.deleteById(id);
    }

    @PostMapping(value = "/{id}/user-stories")
    @ResponseStatus(HttpStatus.CREATED)
    public UserStory createUserStoryBySession(@PathVariable UUID id,
                                              @RequestBody UserStory userStory) {
        return userStoryService.create(userStory, id);
    }

    @GetMapping(value = "/{id}/user-stories")
    public List<UserStory> getUserStoriesBySession(@PathVariable UUID id) {
        return userStoryService.findAllBySessionId(id);
    }

    @DeleteMapping(value = "/{id}/user-stories/{userStoryId}")
    public UserStory deleteUserStoryBySession(@PathVariable UUID id, @PathVariable UUID userStoryId) {
        return userStoryService.deleteBySessionIdAndUserStoryId(id, userStoryId);
    }

    @PutMapping(value = "/{id}/user-stories/{userStoryId}")
    public UserStory updateUserStory(@PathVariable UUID id,
                                     @RequestBody UserStory userStory,
                                     @PathVariable UUID userStoryId) {
        return userStoryService.updateUserStoryBySessionId(id, userStory, userStoryId);
    }

    @PostMapping(value = "/{id}/members", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Member createMemberBySession(@PathVariable UUID id,
                                        @RequestBody Member member) {
        return memberService.create(member, id);
    }

    @GetMapping(value = "/{id}/members")
    public List<Member> getMembersBySession(@PathVariable UUID id) {
        return memberService.findAllBySessionId(id);
    }

    @DeleteMapping(value = "/{id}/members/{memberId}")
    public Member logoutMember(@PathVariable UUID id, @PathVariable UUID memberId) {
        return memberService.deleteBySessionIdAndMemberId(id, memberId);
    }

    @GetMapping(value = "/{id}/votes")
    public List<Vote> getVotesBySession(@PathVariable UUID id) {
        return voteService.findAllBySessionId(id);
    }

    @PostMapping(value = "/{id}/votes", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Vote createVoteBySession(@RequestBody Vote vote, @PathVariable UUID id) {
        return voteService.create(vote, id);
    }
}
