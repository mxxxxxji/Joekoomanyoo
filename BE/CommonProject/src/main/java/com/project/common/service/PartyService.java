//package com.project.common.service;
//
//import java.util.List;
//
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.core.io.ResourceLoader;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.project.common.entity.PartyMember;
//import com.project.common.entity.UserEntity;
//import com.project.common.entity.Party;
//import com.project.common.repository.GroupRepository;
//import com.project.common.repository.PartyRepository;
//import com.project.common.repository.PartyMemberRepository;
//
//import lombok.RequiredArgsConstructor;
//
//@Service
//@RequiredArgsConstructor
//@Transactional(readOnly = true)
//
//public class PartyService{
//    private final PartyRepository partyRepository;
//    private final PartyMemberRepository partyMemberRepository;
//    
//    @Transactional
//    public Party createNewParty(Party newPartyInfo, UserEntity user) {
//        Party newParty = partyRepository.save(newPartyInfo);
//
//        newParty.addPartyMember(
//                partyMemberRepository.save(createPartyMember(newParty))
//        );
//        return newParty;
//    }
//    
//    public List<PartyMember> getPartyMembers(Party party) {
//        return partyMemberRepository.searchMembersByParty(party.getGroupSeq());
//    }
//    
//    @Transactional
//    public void joinToParty(Party party, UserEntity user) {
//        party.addPartyMember(
//                partyMemberRepository.save(createPartyMember(party))
//        );
//    }
//
//    @Transactional
//    public void leaveFromParty(Party party, UserEntity user) {
//        if (!partyMemberRepository.existsByUserAndStudy(user, party)) {
//        	throw new IllegalArgumentException("스터디에서 해당 사용자를 찾을 수 없습니다.");
//        }
//
//        partyMemberRepository.deleteByAccountAndStudy(user, party);
//        party.removePartyMember(user);
//
//    }
//
//}
