package com.interview.challenge.pokerplanning.service;

import com.interview.challenge.pokerplanning.entity.Member;
import java.util.List;
import java.util.UUID;

public interface MemberService {

    List<Member> findAllBySessionId(UUID sessionId);

    Member create(Member member, UUID id);

    Member deleteBySessionIdAndMemberId(UUID sessionId, UUID memberId);
}
