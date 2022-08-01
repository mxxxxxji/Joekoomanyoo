package com.project.common.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Table(name="tb_group")
public class GroupEntity {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="group_seq")
    private Long groupSeq;
    
    @Column(name="attach_seq")
	private String attachSeq;
   
    @Column(name="group_name",nullable=false)
    private String groupName;

    @Column(name= "group_description", nullable=false)
    private String groupDescription;
    
    @Column(name="group_access_type",length = 1, nullable = false)
    private char groupAccessType;
    
    @Column(name="group_pwd",length = 100, nullable = false)
    private String groupPassword;
    
    @Column(name="group_status",nullable=false)
    private String groupStatus;
    
    @Column(name="group_is_active",length = 1, nullable = false)
    private char groupIsActive;
    
    @Column(name="group_max",nullable=false)
	private int groupMaxCount;
    
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date groupCreatedAt;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date groupUpdatedAt;
    
    @OneToOne(mappedBy = "groupEntity",cascade = CascadeType.ALL)
    private GroupAttributeEntity groupAttributeEntity;
    
    @OneToOne
    @JoinColumn(name="group_maker")
    private UserEntity groupMaker;

    @Builder
	public GroupEntity(Long groupSeq, String attachSeq, String groupName, String groupDescription, char groupAccessType,
			String groupPassword, String groupStatus, char groupIsActive, int groupMaxCount, Date groupCreatedAt,
			Date groupUpdatedAt, GroupAttributeEntity groupAttributeEntity, UserEntity groupMaker) {
		super();
		this.groupSeq = groupSeq;
		this.attachSeq = attachSeq;
		this.groupName = groupName;
		this.groupDescription = groupDescription;
		this.groupAccessType = groupAccessType;
		this.groupPassword = groupPassword;
		this.groupStatus = groupStatus;
		this.groupIsActive = groupIsActive;
		this.groupMaxCount = groupMaxCount;
		this.groupCreatedAt = groupCreatedAt;
		this.groupUpdatedAt = groupUpdatedAt;
		this.groupAttributeEntity = groupAttributeEntity;
		this.groupMaker = groupMaker;
	}

    
    

    

}
