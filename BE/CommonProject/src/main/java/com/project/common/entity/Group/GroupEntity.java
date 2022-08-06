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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.project.common.entity.User.UserEntity;

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
	private int groupSeq;
    
	// 모임 기본 정보 //
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

    @ManyToOne
	@JoinColumn(name="user_seq",updatable=false)
	private UserEntity user;
    

    // 모임 멤버 //
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    @Builder.Default
    private List<GroupMemberEntity> members = new ArrayList<>();
    
    // 모임 멤버 Method //
    public void addGroupMember(GroupMemberEntity groupMember) {
    	this.members.add(groupMember);
    	groupMember.setGroup(this);
    }

    public void removeGroupMember(int userSeq) {
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
    
    // 모임 일정 //
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    @Builder.Default
    private List<GroupScheduleEntity> schedules = new ArrayList<>();
    
    // 모임 일정 Method //
    public void addGroupSchedule(GroupScheduleEntity groupSchedule) {
    	this.schedules.add(groupSchedule);
    	groupSchedule.setGroup(this);
    }

    public void removeGroupSchedule(long gsDateTime) {
        this.schedules.removeIf(groupSchedule ->
        groupSchedule.getGsDateTime()==gsDateTime);
    }
    
    // 모임 목적지 //
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    @Builder.Default
    private List<GroupDestinationEntity> destinations = new ArrayList<>();
    
    // 모임 목적지  Method //
    public void addGroupDestination(GroupDestinationEntity groupDestination) {
    	this.destinations.add(groupDestination);
    	groupDestination.setGroup(this);
    }

    public void removeGroupDestination(int heritageSeq) {
        this.destinations.removeIf(groupDestination ->
        groupDestination.getHeritageSeq()==heritageSeq);
    }
    
    
}
