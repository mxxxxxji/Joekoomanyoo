package com.project.common.dto.Chat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDto{
	
	
	public ChatMessageDto() {
		super();
	}

	public enum MessageType {
        ENTER, TALK
    }

    private MessageType type;
    
    //방 번호 (그륩 번호?)
    private String roomId;
    
    //메세지 보낸 사람 (닉네임)
    private String sender;

    //메시지 내용
    private String message;

    //유저 이미지
    private String img;
    
    //유저 아이디
    private long userSeq;

    @Builder
	public ChatMessageDto(MessageType type, String roomId, String sender, String message, String img, long userSeq) {
		super();
		this.type = type;
		this.roomId = roomId;
		this.sender = sender;
		this.message = message;
		this.img = img;
		this.userSeq = userSeq;
	} 
       
    
   
}