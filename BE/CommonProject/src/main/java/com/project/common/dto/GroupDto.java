package com.project.common.dto;



import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import com.project.common.entity.GroupEntity;

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

	private Long groupSeq;
    
	// 스터디 기본 정보 //
    private String name;
    private String themaImg;
    private String master;
    private String description;
    private int accessType;
    private String password;
   // private int memberCount = 0;
    private int maxCount;
   	private String region;
   	private int startDate;
   	private int endDate;
   	private int ageRange;
   	private boolean withChild;
   	private boolean withGlobal;
   	private boolean active;
   	private String status;
	
 // 모임 설정 정보 //
//   	private boolean recruiting;
//   	private boolean published;
//   	private boolean closed;
//    private LocalDateTime publishedTime;
//    private LocalDateTime recruitUpdatedTime;
//    private LocalDateTime closedTime;

    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

	@Builder
	public GroupDto(Long groupSeq, String name, String themaImg, String master, String description, int accessType,
			String password,  int maxCount, String region, int startDate, int endDate, int ageRange,
			boolean withChild, boolean withGlobal, boolean active, String status, LocalDateTime createdTime,
			LocalDateTime updatedTime) {
		super();
		this.groupSeq = groupSeq;
		this.name = name;
		this.themaImg = themaImg;
		this.master = master;
		this.description = description;
		this.accessType = accessType;
		this.password = password;
	//	this.memberCount = memberCount;
		this.maxCount = maxCount;
		this.region = region;
		this.startDate = startDate;
		this.endDate = endDate;
		this.ageRange = ageRange;
		this.withChild = withChild;
		this.withGlobal = withGlobal;
		this.active = active;
		this.status = status;
		this.createdTime = createdTime;
		this.updatedTime = updatedTime;
	}

	

    public GroupEntity toEntity(){
        return GroupEntity.builder()
                .groupSeq(groupSeq)
                .name(name)
                .themaImg(themaImg)
                .master(master)
                .description(description)
                .accessType(accessType)
                .password(password)
           //     .memberCount(memberCount)
                .maxCount(maxCount)
                .region(region)
                .startDate(startDate)
                .endDate(endDate)
                .ageRange(ageRange)
                .withChild(withChild)
                .withGlobal(withGlobal)
                .active(active)
                .status(status)
//                .recruiting(recruiting)
//                .published(published)
//                .closed(closed)
//                .publishedTime(publishedTime)
//                .recruitUpdatedTime(recruitUpdatedTime)
//                .closedTime(closedTime)
                .build();

    }














}
