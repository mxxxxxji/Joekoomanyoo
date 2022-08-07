package com.project.common.service.Group;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.common.dto.Group.GroupDestinationMapDto;
import com.project.common.entity.Group.GroupDestinationEntity;
import com.project.common.entity.Group.GroupEntity;
import com.project.common.entity.Group.GroupMemberEntity;
import com.project.common.entity.Heritage.HeritageEntity;
import com.project.common.repository.Group.GroupDestinationRepository;
import com.project.common.repository.Group.GroupMemberRepository;
import com.project.common.repository.Group.GroupRepository;
import com.project.common.repository.Heritage.HeritageRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupDestinationService{

	private final GroupMemberRepository groupMemberRepository;
	private final GroupRepository groupRepository;
	private final HeritageRepository heritageRepository;
	private final GroupDestinationRepository groupDestinationRepository;
	private final GroupService groupService;


	//내 모임 목적지 조회
	public List<GroupDestinationMapDto> getMyDestinationList(int userSeq){
		List<GroupEntity> groupList= new ArrayList<>();
		for(GroupMemberEntity entity : groupMemberRepository.findAll()) {
			if(entity.getUserSeq()==userSeq) {
					groupList.add(entity.getGroup());
				}
			}
			List<GroupDestinationMapDto> destinationList= new ArrayList<>();
			
			for(GroupEntity entity : groupList) {
				for(int i=0;i<entity.getDestinations().size();i++) {
					HeritageEntity heritage=heritageRepository.findByHeritageSeq(entity.getDestinations().get(i).getHeritageSeq());
					if(heritage!=null)
						destinationList.add(new GroupDestinationMapDto(entity.getDestinations().get(i),heritage));
				}
			}
			return destinationList;
	}
	
	//모임 목적지 조회
	public List<GroupDestinationMapDto> getGroupDestinationList(int groupSeq){
		List<GroupDestinationMapDto> list = new ArrayList<>();
		HeritageEntity herritage;
		for(GroupDestinationEntity entity : groupDestinationRepository.findAll()) {
			if(entity.getGroup()!=null&&entity.getGroup().getGroupSeq()==groupSeq) {
				herritage=heritageRepository.findByHeritageSeq(entity.getHeritageSeq());
				if(herritage!=null)

					list.add(new GroupDestinationMapDto(entity,herritage));
			}
		}
		return list;
	}
	
	//모임 목적지 추가
	@Transactional
	public String addGroupDestination(int groupSeq, int heritageSeq) {
		System.out.println(heritageSeq);
		GroupEntity group = groupService.findGroup(groupSeq);
		for(GroupDestinationEntity entity:group.getDestinations()) {
			if(entity!=null&&entity.getHeritageSeq()==heritageSeq) {
				return "Fail";
			}
		}
		group.addGroupDestination(GroupDestinationEntity.builder().gdCompleted('N').heritageSeq(heritageSeq).build());
		groupRepository.save(group);
		return "Success";
	}
	
	//모임 목적지 삭제
	@Transactional
	public String deleteGroupDestination(int groupSeq, int heritageSeq) {
		List<GroupDestinationEntity> destinations= findDestination(groupSeq);
		if(destinations==null) 
			return "Fail";
		GroupEntity group = groupService.findGroup(groupSeq);
		for(GroupDestinationEntity entity : destinations) {
			if(entity.getHeritageSeq()==heritageSeq) {
				groupDestinationRepository.deleteByHeritageSeq(heritageSeq);
				group.removeGroupDestination(heritageSeq);
			}
		}
		return "Success";
	}
	
	//모임 목적지 완료 표시
	@Transactional
	public String modifyGroupDestination(int groupSeq,int heritageSeq) {
		for(GroupDestinationEntity entity: findDestination(groupSeq)) {
			if(entity !=null && entity.getHeritageSeq()==heritageSeq) {
				entity.setGdCompleted('Y');
				groupDestinationRepository.save(entity);
				return "Success";
			}
		}
		return "Fail";
		
	}
	
	//해당 모임 메모 찾기
	public List<GroupDestinationEntity> findDestination(int groupSeq) {
		List<GroupDestinationEntity> findDestination = new ArrayList<>();
		for(GroupDestinationEntity entity: groupDestinationRepository.findAll()) {
			if(entity.getGroup().getGroupSeq()==groupSeq) {
				findDestination.add(entity);
			}
		}
		return findDestination;
	}

}
