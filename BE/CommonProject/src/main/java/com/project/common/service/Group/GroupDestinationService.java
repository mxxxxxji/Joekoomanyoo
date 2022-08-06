package com.project.common.service.Group;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.common.dto.Group.GroupDestinationDto;
import com.project.common.dto.Group.GroupDestinationMapDto;
import com.project.common.entity.Group.GroupDestinationEntity;
import com.project.common.entity.Group.GroupEntity;
import com.project.common.entity.Heritage.HeritageEntity;
import com.project.common.repository.Group.GroupDestinationRepository;
import com.project.common.repository.Group.GroupRepository;
import com.project.common.repository.Heritage.HeritageRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupDestinationService{
	private final GroupRepository groupRepository;
	private final HeritageRepository heritageRepository;
	private final GroupDestinationRepository groupDestinationRepository;
	private final GroupService groupService;

	
	//내 모임 목적지 조회
		public List<GroupDestinationMapDto> getMyDestinationList (int userSeq){
			List<GroupEntity> groupList= new ArrayList<>();
			for(GroupEntity entity : groupRepository.findAll()) {
				if(entity.getUser().getUserSeq()==userSeq) {
					groupList.add(entity);
				}
			}
			if(groupList.size()==0)
				throw new IllegalArgumentException("가입한 모임이 없습니다");
			List<GroupDestinationMapDto> destinationList= new ArrayList<>();
			for(GroupEntity entity : groupList) {
				for(int i=0;i<entity.getDestinations().size();i++) {
					HeritageEntity herritage=heritageRepository.findByHeritageSeq(entity.getDestinations().get(i).getHeritageSeq());
					if(herritage!=null)
						destinationList.add(new GroupDestinationMapDto(entity.getDestinations().get(i),herritage.getHeritageLat(),herritage.getHeritageLng()));
				}
			}return destinationList;
		}
	
	//모임 목적지 조회
	public List<GroupDestinationMapDto> getGroupDestinationList(int groupSeq){
		List<GroupDestinationMapDto> list = new ArrayList<>();
		HeritageEntity herritage;
		for(GroupDestinationEntity entity : groupDestinationRepository.findAll()) {
			if(entity.getGroup()!=null&&entity.getGroup().getGroupSeq()==groupSeq) {
				herritage=heritageRepository.findByHeritageSeq(entity.getHeritageSeq());
				if(herritage!=null)
					list.add(new GroupDestinationMapDto(entity,herritage.getHeritageLat(),herritage.getHeritageLng()));
			}
		}
		if(list.size()==0)
			throw new IllegalArgumentException("등록된 메모가 없습니다");
		return list;
	}
	
	//모임 목적지 추가
	@Transactional
	public String addGroupDestination(int groupSeq, GroupDestinationDto gdDto) {
		System.out.println(gdDto.getHeritageSeq());
		GroupEntity group = groupService.findGroup(groupSeq);
		for(GroupDestinationEntity entity:group.getDestinations()) {
			if(entity!=null&&entity.getHeritageSeq()==gdDto.getHeritageSeq()) {
				throw new IllegalArgumentException("이미 등록한 목적지입니다");
			}
		}
		group.addGroupDestination(GroupDestinationEntity.builder().gdCompleted("N").heritageSeq(gdDto.getHeritageSeq()).build());
		groupRepository.save(group);
		return "Success";
	}
	
	//모임 목적지 삭제
	@Transactional
	public String deleteGroupDestination(int groupSeq, int heritageSeq) {
		List<GroupDestinationEntity> destinations= findDestination(groupSeq);
		if(destinations==null) 
			throw new IllegalArgumentException("등록된 목적지가 없습니다");
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
	public String modifyGroupDestination(int groupSeq, GroupDestinationDto gdDto) {
		for(GroupDestinationEntity entity: findDestination(groupSeq)) {
			if(entity !=null && entity.getHeritageSeq()==gdDto.getHeritageSeq()) {
				entity.setGdCompleted("Y");
				groupDestinationRepository.save(entity);
				return "Success";
			}
		}
		throw new IllegalArgumentException("유산 번호가 일치하지 없습니다");
		
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
