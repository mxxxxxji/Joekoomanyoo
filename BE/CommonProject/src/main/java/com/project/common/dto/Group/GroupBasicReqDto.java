package com.project.common.dto.Group;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GroupBasicReqDto {

	private int userSeq;
	private int groupSeq;	
	@Builder
	public GroupBasicReqDto(int userSeq, int groupSeq) {
		super();
		this.userSeq = userSeq;
		this.groupSeq = groupSeq;
	}
}
