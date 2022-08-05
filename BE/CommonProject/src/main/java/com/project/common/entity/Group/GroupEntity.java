package com.project.common.entity.Group;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder @AllArgsConstructor @NoArgsConstructor
@Entity
@Getter @Setter
@ToString
@Table(name="tb_group")
public class GroupEntity {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="group_seq")
	private Long groupSeq;
    
	// 스터디 기본 정보 //
	@Column(name="group_name")
    private String name;
    
    @Column(name="attach_seq")
    private String themaImg;
 
    @Column(name="group_maker")
    private String master;
    
    @Column(name= "group_description")
    private String description;
    
    @Column(name="group_access_type")
    private char accessType;
    
    @Column(name="group_pwd")
    private String password;
    
    @Column(name="group_total_count")
    private int maxCount;
    
    @Column(name="group_region")
   	private String region;
       
    @Column(name="group_start_date")
   	private int startDate;
       
    @Column(name="group_end_date")
   	private int endDate;
       
    @Column(name="group_age_range")
   	private int ageRange;

    @Column(name="group_child")
   	private char withChild;
       
    @Column(name="group_global")
   	private char withGlobal;
    
    @Column(name="group_is_active")
   	private char active;

    @Column(name="group_status")
   	private char status;

    // 모임 설정 정보 //
    @Column(name="group_created_at")
    private LocalDateTime createdTime;
    
    @Column(name="group_updated_at")
    private LocalDateTime updatedTime;
    
    
    // 참여 멤버 //
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    @Builder.Default
    private List<GroupMemberEntity> members = new ArrayList<>();
    
    // 참여 멤버 Method //
    public void addGroupMember(GroupMemberEntity groupMember) {
    	this.members.add(groupMember);
    	groupMember.setGroup(this);
    }

    public void removeGroupMember(long	userSeq) {
        this.members.removeIf(groupMember ->
                groupMember.getUserSeq()==userSeq);
    }
    
    // 모임 데일리 메모 //
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    @Builder.Default
    private List<GroupDailyMemoEntity> memos = new ArrayList<>();
    
    // 모임 데일리 메모 Method //
    public void addGroupMemo(GroupDailyMemoEntity groupMemo) {
    	this.memos.add(groupMemo);
    	groupMemo.setGroup(this);
    }

    public void removeGroupMemo(int gdmDate) {
        memos.removeIf(groupMemo ->
                groupMemo.getGdmDate()==gdmDate);
    }
    
}
