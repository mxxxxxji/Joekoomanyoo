package com.project.common.entity.Group;

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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter @Setter 
@Builder @AllArgsConstructor @NoArgsConstructor
@Table(name="tb_group_chat")
public class GroupChatEntity{

	//메시지 번호
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_seq")
	private int chatSeq;
	    
	//유저 아이디
	@Column(name = "user_seq")
	private int userSeq;

	//메시지 내용
	@Column(name = "chat_content")	
	private String chatContent;
	    
	//메시지 이미지
	@Column(name = "chat_img_url")
	private String chatImgUrl;
    
    //메세지 전송시간
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="chat_created_at")
    private Date createdTime;

    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="group_seq")
	private GroupEntity group;
   
}