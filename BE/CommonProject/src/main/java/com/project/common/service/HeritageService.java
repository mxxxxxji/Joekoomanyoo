package com.project.common.service;

import com.project.common.dto.Heritage.*;
import com.project.common.entity.Heritage.HeritageEntity;
import com.project.common.entity.Heritage.HeritageReviewEntity;
import com.project.common.entity.Heritage.HeritageScrapEntity;
import com.project.common.repository.Heritage.HeritageRepository;
import com.project.common.repository.Heritage.HeritageReviewRepository;
import com.project.common.repository.Heritage.HeritageScrapRepository;
import com.project.common.repository.Heritage.HeritageScrapRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HeritageService {

    private final HeritageReviewRepository heritageReviewRepository;
    private final HeritageRepository heritageRepository;
    private final HeritageScrapRepository heritageScrapRepository;
    private final HeritageScrapRepositoryCustom heritageScrapRepositoryCustom;

    @Transactional
    public List<HeritageDto> listInfo() throws Exception {
        List<HeritageDto> list = new ArrayList<>();
        for (HeritageEntity entity : heritageRepository.findAll()) {
            list.add(HeritageMapper.MAPPER.toDto(entity));
        }
        return list;
    }

    @Transactional
    public boolean createReview(HeritageReivewDto heritageReivewDto) {
        HeritageReviewEntity heritageReviewEntity = HeritageReviewMapper.MAPPER.toEntity(heritageReivewDto);
        heritageReviewRepository.save(heritageReviewEntity);
        if (heritageReviewEntity == null) {
            return false;
        } else {
            return true;
        }
    }

    // 리뷰 리스트 반환
    public List<HeritageReivewDto> reviewList() {
        List<HeritageReviewEntity> list = heritageReviewRepository.findAll();
        List<HeritageReivewDto> listDto= new ArrayList<>();
        for(HeritageReviewEntity heritageReviewEntity : list){
            listDto.add(HeritageReviewMapper.MAPPER.toDto(heritageReviewEntity));
        }
        return listDto;
    }

    // 스크랩 등록
    public boolean createScrap(HeritageScrapDto heritageScrapDto){
        HeritageScrapEntity heritageScrapEntity = HeritageScrapMapper.MAPPER.toEntity(heritageScrapDto);

        // 스크랩 개수 추가
        HeritageEntity heritageEntity = heritageRepository.findByHeritageSeq(heritageScrapEntity.getHeritageSeq());
        // 그전 스크랩 개수에서 하나 더해주기
        int cnt = heritageEntity.getHeritageScrapCnt()+1;
        
        // 개수 바꿔주기
        heritageEntity.setHeritageScrapCnt(cnt);

        // 스크랩 개수 Heritage DB에 저장
        heritageRepository.save(heritageEntity);

        // 스크랩 DB에 저장
        heritageScrapRepository.save(heritageScrapEntity);

        // 올바르게 들어갔으면 true
        if(heritageScrapEntity != null){
            return true;
        }else{
            return false;
        }
    }

    // 유저 scrap 리스트 반환
    public List<HeritageScrapDto> myScrapList(int userSeq) {
        List<HeritageScrapEntity> list = heritageScrapRepository.findAllByUserSeq(userSeq);
        List<HeritageScrapDto> listDto = new ArrayList<>();
        for(HeritageScrapEntity heritageScrapEntity : list){
            listDto.add(HeritageScrapMapper.MAPPER.toDto(heritageScrapEntity));
        }
        return listDto;
    }
    
    // 유저 scrap 삭제
    public boolean deleteScrap(int userSeq, int heritageSeq) {
        // 값이 없으면 false
        if(heritageScrapRepositoryCustom.findByUserSeqAndHeritageSeq(userSeq, heritageSeq) == null){
            return false;
        }
        else{
            // 스크랩 개수 하나 줄이기
            HeritageEntity heritageEntity = heritageRepository.findByHeritageSeq(heritageSeq);
            // 그전 스크랩 개수에서 하나 빼주기
            int cnt = heritageEntity.getHeritageScrapCnt()-1;

            // 개수 바꿔주기
            heritageEntity.setHeritageScrapCnt(cnt);

            // 스크랩 개수 Heritage DB에 저장
            heritageRepository.save(heritageEntity);

            return heritageScrapRepositoryCustom.deleteByUserSeqAndHeritageSeq(userSeq, heritageSeq);
        }
    }
}
