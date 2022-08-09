//package com.project.common.controller;
//import java.util.List;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import com.project.common.dto.Chat.ChatRoomDto;
//import com.project.common.repository.ChatRoomRepository;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//
//@Controller
//@RequiredArgsConstructor
//@RequestMapping(value = "/chat")
//public class ChatRoomController {
//
//    private final ChatRoomRepository chatRoomRepository;
//
//    //채팅방 목록 조회
//    @GetMapping("/room")
//    public ResponseEntity room() {
//        List<ChatRoomDto> chatRooms = chatRoomRepository.findAllRoom();
//        chatRooms.stream().forEach(room -> room.setUserCount(chatRoomRepository.getUserCount(room.getRoomId())));
//        return ResponseEntity.ok().body(chatRooms);
//    }
//
//    // 채팅방 생성
//    @PostMapping("/room")
//    public ChatRoomDto createRoom(@RequestParam String name) {
//        return chatRoomRepository.createChatRoom(name);
//    }
//
//    // 채팅방 생성
//    @PostMapping("/room")
//    public ChatRoomDto createRoom(@RequestParam String name) {
//        return chatRoomRepository.createChatRoom(name);
//    }
//
//    // 채팅방 파괴
//    @DeleteMapping("/room/{roomId}")
//    public ResponseEntity deleteRoom(@PathVariable String roomId) {
//        chatRoomRepository.deleteChatRoom(roomId);
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
//    }
//    
//    // 특정 채팅방 들어갔을때 채팅방 관련 정보를 전달
//    @GetMapping("/room/{roomId}")
//    public ChatRoomDto roomInfo(@PathVariable String roomId) {
//        return chatRoomRepository.findRoomById(roomId);
//    }
//
//    // 해당 채팅방에 저장된 최신 메시지 받기
//    @GetMapping("/room/message/{roomId}")
//    @ResponseBody
//    public List<ChatMessageDto> getMessages(@PathVariable String roomId) {
//        return chatRoomRepository.getMessages(roomId);
//    }
//}