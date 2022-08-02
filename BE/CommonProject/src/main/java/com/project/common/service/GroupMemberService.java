package com.project.common.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.common.dto.UserDto;
import com.project.common.repository.GroupMemberRepository;
import com.project.common.repository.GroupRepository;
import com.project.common.service.exception.GroupMemberNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupMemberService{
	private final GroupRepository groupRepository;
	private final GroupMemberRepository groupMemberRepository;
	private final UserService userService;
	
	@Transactional
    public void withdrawGroup(long groupSeq, UserDto userSignupDto) {
        long userSeq = userSignupDto.getUserSeq();

    //    groupMemberRepository.findByGroupIdAndMemberId(groupSeq, userSeq)
  //              .orElseThrow(GroupMemberNotFoundException::new);
      //          .checkPresenter(userId);

        //가입명단에서 삭제
     //   groupMemberRepository.deleteByGroupIdAndMemberId(groupSeq, userSeq);
    }
}
