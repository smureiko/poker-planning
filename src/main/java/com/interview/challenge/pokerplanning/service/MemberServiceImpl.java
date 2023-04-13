package com.interview.challenge.pokerplanning.service;

import com.interview.challenge.pokerplanning.entity.Member;
import com.interview.challenge.pokerplanning.repository.MemberRepository;
import com.interview.challenge.pokerplanning.repository.SessionRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final SessionRepository sessionRepository;

    @Override
    public List<Member> findAllBySessionId(UUID sessionId) {
        if (sessionRepository.existsById(sessionId)) {
            return memberRepository.findAll();
        } else {
            throw new NoSuchElementException(String.format(SessionServiceImpl.SESSION_NOT_EXIST_MESSAGE, sessionId));
        }
    }

    @Override
    public Member create(Member member, UUID sessionId) {
        if (sessionRepository.existsById(sessionId)) {
            return memberRepository.save(member);
        } else {
            throw new NoSuchElementException(String.format(SessionServiceImpl.SESSION_NOT_EXIST_MESSAGE, sessionId));
        }
    }

    @Override
    public Member deleteBySessionIdAndMemberId(UUID sessionId, UUID memberId) {
        Member member;
        if (sessionRepository.existsById(sessionId)) {
            member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException(String.format(
                    "Member with id %s does not exist",
                    sessionId)));
            memberRepository.deleteById(memberId);
        } else {
            throw new NoSuchElementException(String.format(SessionServiceImpl.SESSION_NOT_EXIST_MESSAGE, sessionId));
        }
        return member;
    }
}
