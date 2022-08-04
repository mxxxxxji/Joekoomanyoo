package com.project.common.dto.Group;



import java.time.LocalDateTime;

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

	private Long groupSeq;  
	
	// 스터디 기본 정보 //
    private String name;
    private String themaImg;
    private String master;
    private String description;
    private char accessType;
    private String password;
    private int maxCount;
   	private String region;
   	private int startDate;
   	private int endDate;
   	private int ageRange;
   	private char withChild;
   	private char withGlobal;
   	private char active;
   	private char status;
	
   	// 모임 설정 정보 //
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
	//private boolean recruiting;
	//private boolean published;
	//private boolean closed;
	//private LocalDateTime publishedTime;
	//private LocalDateTime recruitUpdatedTime;
	//private LocalDateTime closedTime;

	@Builder
	public GroupDto(Long groupSeq, String name, String themaImg, String master, String description, char accessType,
			String password,  int maxCount, String region, int startDate, int endDate, int ageRange,
			char withChild, char withGlobal, char active, char status, LocalDateTime createdTime,
			LocalDateTime updatedTime) {
		super();
		this.groupSeq = groupSeq;
		this.name = name;
		this.themaImg = themaImg;
		this.master = master;
		this.description = description;
		this.accessType = accessType;
		this.password = password;
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
                .maxCount(maxCount)
                .region(region)
                .startDate(startDate)
                .endDate(endDate)
                .ageRange(ageRange)
                .withChild(withChild)
                .withGlobal(withGlobal)
                .active(active)
                .status(status)
                .build();
    }














}
