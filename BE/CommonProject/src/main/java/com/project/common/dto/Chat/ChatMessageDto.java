package com.project.common.dto.Chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDto{

    private String roomId;
    private String writer;
    private String message;
}