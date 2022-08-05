package com.project.common.entity.Group;



import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter 
@Builder @AllArgsConstructor @NoArgsConstructor
@Table(name="tb_group_daily_memo")
public class GroupDailyMemoEntity {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gdm_seq")
	private long gdmSeq;
	
	@Column(name = "gdm_content")
    private String gdmContent;

	@Column(name = "gdm_date")
	private int gdmDate;
	
	@Column(name = "gdm_created_at")
	private LocalDateTime gdmCreatedAt;
	
	@Column(name= "gdm_updated_at")
    private LocalDateTime gdmUpdatedAt;
		
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="group_seq")
	private GroupEntity group;


}
