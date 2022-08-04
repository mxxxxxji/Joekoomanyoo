package com.project.common.service;

import com.project.common.dto.UserKeywordDto;
import com.project.common.dto.UserKeywordMapper;
import com.project.common.entity.UserKeywordEntity;
import com.project.common.repository.UserKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MyPageService {
    private final UserKeywordRepository userKeywordRepository;
    public boolean createKeyword(UserKeywordDto userKeywordDto) {
        UserKeywordEntity userKeywordEntity = UserKeywordMapper.MAPPER.toEntity(userKeywordDto);

        // 만약 이미 키워드가 존재하는 경우
        if(userKeywordEntity == null){
            return false;
        }else{
            // 현재 날짜 , 인덱스 주기
            userKeywordEntity.setMyKeywordRegistedAt(LocalDateTime.now());
            userKeywordEntity.setMyKeywordSeq(0);
            userKeywordRepository.save(userKeywordEntity);
            return true;
        }
    }

    public List<UserKeywordDto> listKeyword(int userSeq) {
        List<UserKeywordDto> listDto = new ArrayList<>();

        List<UserKeywordEntity> list = userKeywordRepository.findAllByUserSeq(userSeq);
        for(UserKeywordEntity userKeywordEntity : list){
            listDto.add(UserKeywordMapper.MAPPER.toDto(userKeywordEntity));
        }

        return listDto;
    }

    public boolean deleteKeyword(int myKeywordSeq) {
        // 키워드가 없는 경우
        if(userKeywordRepository.findByMyKeywordSeq(myKeywordSeq)==null){
            return false;
        }else {
            userKeywordRepository.deleteByMyKeywordSeq(myKeywordSeq);
            return true;
        }
    }
}