package com.project.common.dto.Group;

import com.project.common.entity.Group.GroupDestinationEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GroupDestinationMapDto {
    
	private int heritageSeq;
    private String gdCompleted;
    private String heritageLng;
    private String heritageLat;
    
    public GroupDestinationMapDto(GroupDestinationEntity destination,String heritageLat, String heritageLng) {
    	super();
    	this.heritageSeq = destination.getHeritageSeq();
    	this.gdCompleted = destination.getGdCompleted();
    	this.heritageLng = heritageLng;
    	this.heritageLat = heritageLat;
    }
    
  




}

