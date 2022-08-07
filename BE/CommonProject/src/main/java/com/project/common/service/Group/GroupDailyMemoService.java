package com.project.common.service.Group;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.common.dto.Group.GroupDailyMemoDto;
import com.project.common.entity.Group.GroupDailyMemoEntity;
import com.project.common.entity.Group.GroupEntity;
import com.project.common.entity.User.UserEntity;
import com.project.common.mapper.GroupDailyMemoMapper;
import com.project.common.repository.Group.GroupDailyMemoRepository;
import com.project.common.repository.Group.GroupRepository;
import com.project.common.repository.User.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupDailyMemoService{
	private final GroupRepository groupRepository;
	private final GroupDailyMemoRepository groupDailyMemoRepository;
	private final GroupService groupService;
	private final UserRepository userRepository;
	
	//내 모임 데일리 메모 조회
	public List<GroupDailyMemoDto> getMyGroupMemoList(int userSeq){
		List<GroupEntity> groupList= new ArrayList<>();
		for(UserEntity entity : userRepository.findAll()) {
			if(entity.getUserSeq()==userSeq) {
				for(GroupEntity group : entity.getGroups()) {
					groupList.add(group);
				}
			}
		}
		if(groupList.size()==0)
			throw new IllegalArgumentException("가입한 모임이 없습니다");
		
			List<GroupDailyMemoDto> memoList= new ArrayList<>();
			
			for(GroupEntity entity : groupList) {
				for(int i=0;i<entity.getMemos().size();i++)
					memoList.add(new GroupDailyMemoDto(entity.getMemos().get(i)));
			}
			return memoList;
	}
	
	//데일리 메모 조회
	public List<GroupDailyMemoDto> getMemoList(int groupSeq){
		List<GroupDailyMemoDto> list = new ArrayList<>();
		for(GroupDailyMemoEntity entity : groupDailyMemoRepository.findAll()) {
			if(entity.getGroup()!=null&&entity.getGroup().getGroupSeq()==groupSeq) {
				list.add(new GroupDailyMemoDto(entity));
			}
		}
		if(list.size()==0)
			throw new IllegalArgumentException("등록된 메모가 없습니다");
		return list;
	}
	
	//데일리 메모 등록
	@Transactional
	public String createGroupMemo(int groupSeq, GroupDailyMemoDto gdmDto) {
		GroupEntity group = groupService.findGroup(groupSeq);
		for(GroupDailyMemoEntity entity:group.getMemos()) {
			if(entity.getGdmDate()==gdmDto.getGdmDate()) {
				throw new IllegalArgumentException("해당 날짜에 등록한 메모가 있습니다");
			}
		}
		group.addGroupMemo(GroupDailyMemoEntity.builder()
				.gdmContent(gdmDto.getGdmContent())
				.gdmDate(gdmDto.getGdmDate())
				.gdmCreatedAt(new Date())
				.gdmUpdatedAt(new Date()).build());
		groupRepository.save(group);
		return "Success";
	}
	
	//데일리 메모 삭제
	@Transactional
	public String deleteGroupMemo(int groupSeq, int gdmDate) {
		List<GroupDailyMemoEntity> memos= findMemo(groupSeq);
		if(memos==null) 
			throw new IllegalArgumentException("등록된 메모가 없습니다");
		GroupEntity group = groupService.findGroup(groupSeq);
		for(GroupDailyMemoEntity entity : memos) {
			if(entity.getGdmDate()==gdmDate) {
				groupDailyMemoRepository.deleteByGdmDate(gdmDate);
				group.removeGroupMemo(gdmDate);
			}
		}
		return "Success";
	}
	
	//데일리 메모 수정
	@Transactional
	public GroupDailyMemoDto modifyGroupMemo(int groupSeq, GroupDailyMemoDto gdmDto) {
		for(GroupDailyMemoEntity entity: findMemo(groupSeq)) {
			if(entity !=null && entity.getGdmDate()==gdmDto.getGdmDate()) {
				entity.setGdmContent(gdmDto.getGdmContent());
				entity.setGdmUpdatedAt(new Date());
				groupDailyMemoRepository.save(entity);
				break;
			}
		}
		return gdmDto;
	}
	
	//해당 모임 메모 찾기
	public List<GroupDailyMemoEntity> findMemo(int groupSeq) {
		List<GroupDailyMemoEntity> findMemo = new ArrayList<>();
		for(GroupDailyMemoEntity entity: groupDailyMemoRepository.findAll()) {
			if(entity.getGroup().getGroupSeq()==groupSeq) {
				findMemo.add(entity);
			}
		}
		return findMemo;
	}

}
