package com.project.common.temp;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.project.common.entity.Group.GroupEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Data
@Getter @Setter 
@Builder @AllArgsConstructor @NoArgsConstructor
@Table(name="tb_group_daily_memo")
public class GroupDailyMemoEntity {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gdm_seq")
	private int gdmSeq;
	
	@Column(name = "gdm_content")
    private String gdmContent;

	@Temporal(TemporalType.DATE)
	@Column(name = "gdm_date")
	private Date gdmDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="gdm_created_at")
	private Date gdmCreatedAt;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="gdm_updated_at")
    private Date gdmUpdatedAt;
		
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="group_seq")
	private GroupEntity group;

}
