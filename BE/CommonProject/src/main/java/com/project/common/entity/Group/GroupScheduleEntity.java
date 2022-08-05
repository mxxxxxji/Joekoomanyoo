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
@Table(name="tb_group_schedule")
public class GroupScheduleEntity {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gs_seq")
	private int gsSeq;
	
	@Column(name = "gs_content")
    private String gsContent;

	@Column(name = "gs_date_time")
	private long gsDateTime;
	
	@Column(name = "gs_registered_at")
	private LocalDateTime gsRegisteredAt;
	
	@Column(name= "gs_updated_at")
    private LocalDateTime gsUpdatedAt;
		
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="group_seq")
	private GroupEntity group;


}
