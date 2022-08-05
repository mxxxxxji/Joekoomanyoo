package com.project.common.service.Group;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.common.dto.Group.GroupDailyMemoDto;
import com.project.common.entity.Group.GroupDailyMemoEntity;
import com.project.common.entity.Group.GroupEntity;
import com.project.common.repository.Group.GroupDailyMemoRepository;
import com.project.common.repository.Group.GroupRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupDailyMemoService{
	private final GroupRepository groupRepository;
	private final GroupDailyMemoRepository groupDailyMemoRepository;
	private final GroupService groupService;

	
	//그륩 메모 찾기(그륩 번호로)
	public List<GroupDailyMemoEntity> findMemo(long groupSeq) {
		List<GroupDailyMemoEntity> findMemo = new ArrayList<>();
		for(GroupDailyMemoEntity entity: groupDailyMemoRepository.findAll()) {
			if(entity.getGroup().getGroupSeq()==groupSeq) {
				findMemo.add(entity);
			}
		}
		return findMemo;
	}
	
	//메모 조회
	public List<GroupDailyMemoDto> getMemoList(long groupSeq){
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
	
	//메모 등록
	@Transactional
	public GroupDailyMemoDto createGroupMemo(long groupSeq, GroupDailyMemoDto gdmDto) {
		GroupEntity group = groupService.findGroup(groupSeq);
		for(GroupDailyMemoEntity entity:group.getMemos()) {
			if(entity.getGdmDate()==gdmDto.getGdmDate()) {
				throw new IllegalArgumentException("해당 날짜에 등록한 메모가 있습니다");
			}
		}
		group.addGroupMemo(GroupDailyMemoEntity.builder().gdmContent(gdmDto.getGdmContent()).gdmDate(gdmDto.getGdmDate()).build());
		groupRepository.save(group);
		return gdmDto;
	}
	
	//메모 삭제
	@Transactional
	public void deleteGroupMemo(long groupSeq, GroupDailyMemoDto gdmDto) {
		List<GroupDailyMemoEntity> memos= findMemo(groupSeq);
		GroupEntity group = groupService.findGroup(groupSeq);
		for(GroupDailyMemoEntity entity : memos) {
			if(entity.getGdmDate()==gdmDto.getGdmDate()) {
				groupDailyMemoRepository.deleteByGdmDate(gdmDto.getGdmDate());
				group.removeGroupMemo(gdmDto.getGdmDate());
			}
		}
	}
	
	//메모 수정
	@Transactional
	public GroupDailyMemoDto modifyGroupMemo(long groupSeq, GroupDailyMemoDto gdmDto) {
		for(GroupDailyMemoEntity entity: findMemo(groupSeq)) {
			if(entity !=null && entity.getGdmDate()==gdmDto.getGdmDate()) {
				entity.setGdmContent(gdmDto.getGdmContent());
				groupDailyMemoRepository.save(entity);
				break;
			}
		}
		return gdmDto;
	}

}
