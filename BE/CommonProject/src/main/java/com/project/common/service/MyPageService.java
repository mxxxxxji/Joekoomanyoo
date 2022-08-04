package com.project.common.service;

import com.project.common.dto.MyDailyMemo.MyDailyMemoDto;
import com.project.common.dto.MyDailyMemo.MyDailyMemoMapper;
import com.project.common.dto.User.UserKeywordDto;
import com.project.common.dto.User.UserKeywordMapper;
import com.project.common.entity.MyDailyMemoEntity;
import com.project.common.entity.User.UserKeywordEntity;
import com.project.common.repository.MyDailyMemoRepository;
import com.project.common.repository.MyDailyMemoRepositoryCustom;
import com.project.common.repository.User.UserKeywordRepository;
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

    private final MyDailyMemoRepositoryCustom myDailyMemoRepositoryCustom;

    private final MyDailyMemoRepository myDailyMemoRepository;


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

    public boolean createDailyMemo(MyDailyMemoDto myDailyMemoDto) {
        // 이미 메모가 있으면
        if(myDailyMemoRepositoryCustom.findByUserSeqAndMyDailyMemoDate(myDailyMemoDto.getUserSeq(), myDailyMemoDto.getMyDailyMemoDate()) != null){
            return false;
        }else {
            myDailyMemoDto.setMyDailyMemoRegistedAt(LocalDateTime.now());
            myDailyMemoDto.setMyDailyMemoUpdatedAt(LocalDateTime.now());

            MyDailyMemoEntity myDailyMemoEntity = MyDailyMemoMapper.MAPPER.toEntity(myDailyMemoDto);
            myDailyMemoRepository.save(myDailyMemoEntity);
            return true;
        }
    }

    public MyDailyMemoDto showDailyMemo(MyDailyMemoDto myDailyMemoDto) {
        MyDailyMemoEntity myDailyMemoEntity = myDailyMemoRepositoryCustom.findByUserSeqAndMyDailyMemoDate(myDailyMemoDto.getUserSeq(), myDailyMemoDto.getMyDailyMemoDate());
        // 데일리 메모가 없는 경우
        if(myDailyMemoEntity == null){
            return null;
        }else{
            return MyDailyMemoMapper.MAPPER.toDto(myDailyMemoEntity);
        }
    }

    public boolean modifyDailyMemo(MyDailyMemoDto myDailyMemoDto) {
        MyDailyMemoEntity myDailyMemoEntity =myDailyMemoRepositoryCustom.findByUserSeqAndMyDailyMemoDate(myDailyMemoDto.getUserSeq(), myDailyMemoDto.getMyDailyMemoDate());
        // 메모가 없으면
        if(myDailyMemoEntity == null){
            return false;
        }else {
            // 메모 내용 수정
            myDailyMemoEntity.setMyDailyMemo(myDailyMemoDto.getMyDailyMemo());
            myDailyMemoEntity.setMyDailyMemoUpdatedAt(LocalDateTime.now());

            myDailyMemoRepository.save(myDailyMemoEntity);
            return true;
        }
    }

    public boolean deleteDailyMemo(int myDailyMemoSeq) {
        // 만약 메모가 없는 경우
        if(myDailyMemoRepository.findByMyDailyMemoSeq(myDailyMemoSeq) == null){
            return false;
        }else {
            myDailyMemoRepository.deleteByMyDailyMemoSeq(myDailyMemoSeq);
            return true;
        }
    }
}