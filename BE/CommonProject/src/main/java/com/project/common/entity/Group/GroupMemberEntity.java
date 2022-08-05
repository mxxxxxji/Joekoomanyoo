package com.project.common.entity.Group;



import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.*;
import lombok.*;

@Entity
@Getter @Setter 
@Builder @AllArgsConstructor @NoArgsConstructor
@Table(name="tb_group_member")
public class GroupMemberEntity {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_seq")
	private long memberSeq;
	
	@Column(name = "member_status")
	private int memberStatus;
	
	@Column(name = "member_appeal")
	private String memberAppeal;
	
	@Column(name = "member_is_evaluated")
	private char memberIsEvaluated;
	
	@Column(name = "member_in_at")
	private Date memberInAt;
	
	@Column(name = "member_created_at")
	private LocalDateTime createdTime;
	
	@Column(name = "member_updated_at")
    private LocalDateTime updatedTime;
	
    @Column(name = "user_seq")
    private long userSeq;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="group_seq")
	private GroupEntity group;

}
