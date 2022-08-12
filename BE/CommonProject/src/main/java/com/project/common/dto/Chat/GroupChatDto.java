package com.project.common.dto.Chat;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupChatDto{

	//메시지 번호
	private int chatSeq;
	    
	//방 번호
	private int groupSeq;
	
	//유저 번호
	private int userSeq;

	//메시지 내용
	private String chatContent;
	    
	//메시지 이미지
	private String chatImgUrl;

	//메세지 보낸 사람 (닉네임)
	private String sender;

	//유저 이미지
	private String userImg;
    
    //메세지 전송시간
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date createdTime;

    
    @Builder
	public GroupChatDto(int chatSeq, int groupSeq, int userSeq, String chatContent, String chatImgUrl, String sender,
			String userImg) {
		super();
		this.chatSeq = chatSeq;
		this.groupSeq = groupSeq;
		this.userSeq = userSeq;
		this.chatContent = chatContent;
		this.chatImgUrl = chatImgUrl;
		this.sender = sender;
		this.userImg = userImg;
	}



    
   
}