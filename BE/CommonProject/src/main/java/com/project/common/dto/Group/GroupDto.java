package com.project.common.dto.Group;

import java.util.Date;

import com.project.common.entity.Group.GroupEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GroupDto {

	private int groupSeq;  
	
	// 모임 기본 정보 //
    private String groupName;
    private String bannerImgUrl;
    private String groupMaster;
    private String groupDescription;
    private char groupAccessType;
    private String groupPassword;
    private int groupMaxCount;
   	private String groupRegion;
   	private int groupStartDate;
   	private int groupEndDate;
   	private int groupAgeRange;
   	private char groupWithChild;
   	private char groupWithGlobal;
   	private char groupActive;
   	private char groupStatus;
	
   	// 모임 설정 정보 //
    private Date createdTime;
    private Date updatedTime;
   // private UserEntity user;

    @Builder
    public GroupDto(int groupSeq, String groupName, String bannerImgUrl, String groupMaster, String groupDescription,
    		char groupAccessType, String groupPassword, int groupMaxCount, String groupRegion, int groupStartDate,
    		int groupEndDate, int groupAgeRange, char groupWithChild, char groupWithGlobal, char groupActive,
    		char groupStatus, Date createdTime, Date updatedTime) {
    	super();
    	this.groupSeq = groupSeq;
    	this.groupName = groupName;
    	this.bannerImgUrl = bannerImgUrl;
    	this.groupMaster = groupMaster;
    	this.groupDescription = groupDescription;
    	this.groupAccessType = groupAccessType;
    	this.groupPassword = groupPassword;
    	this.groupMaxCount = groupMaxCount;
    	this.groupRegion = groupRegion;
    	this.groupStartDate = groupStartDate;
    	this.groupEndDate = groupEndDate;
    	this.groupAgeRange = groupAgeRange;
    	this.groupWithChild = groupWithChild;
    	this.groupWithGlobal = groupWithGlobal;
    	this.groupActive = groupActive;
    	this.groupStatus = groupStatus;
    	this.createdTime = createdTime;
    	this.updatedTime = updatedTime;
    }
    
    @Builder
    public GroupDto(GroupEntity group) {
    	super();
    	this.groupSeq = group.getGroupSeq();
    	this.groupName = group.getGroupName();
    	this.bannerImgUrl = group.getBannerImgUrl();
    	this.groupMaster = group.getGroupMaster();
    	this.groupDescription = group.getGroupDescription();
    	this.groupAccessType = group.getGroupAccessType();
    	this.groupPassword = group.getGroupPassword();
    	this.groupMaxCount = group.getGroupMaxCount();
    	this.groupRegion = group.getGroupRegion();
    	this.groupStartDate = group.getGroupStartDate();
    	this.groupEndDate = group.getGroupEndDate();
    	this.groupAgeRange = group.getGroupAgeRange();
    	this.groupWithChild = group.getGroupWithChild();
    	this.groupWithGlobal = group.getGroupWithGlobal();
    	this.groupActive = group.getGroupActive();
    	this.groupStatus = group.getGroupStatus();
    	this.createdTime = group.getCreatedTime();
    	this.updatedTime = group.getUpdatedTime();
    }


    public GroupEntity toEntity(){
        return GroupEntity.builder()
                .groupSeq(groupSeq)
                .groupName(groupName)
                .bannerImgUrl(bannerImgUrl)
                .groupMaster(groupMaster)
                .groupDescription(groupDescription)
                .groupAccessType(groupAccessType)
                .groupPassword(groupPassword)
                .groupMaxCount(groupMaxCount)
                .groupRegion(groupRegion)
                .groupStartDate(groupStartDate)
                .groupEndDate(groupEndDate)
                .groupAgeRange(groupAgeRange)
                .groupWithChild(groupWithChild)
                .groupWithGlobal(groupWithGlobal)
                .groupActive(groupActive)
                .groupStatus(groupStatus)
                .build();
    }

}
