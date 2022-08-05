package com.project.common.service;

import com.project.common.dto.Heritage.*;
import com.project.common.entity.Heritage.HeritageEntity;
import com.project.common.entity.Heritage.HeritageReviewEntity;
import com.project.common.entity.Heritage.HeritageScrapEntity;
import com.project.common.repository.Heritage.HeritageRepository;
import com.project.common.repository.Heritage.HeritageReviewRepository;
import com.project.common.repository.Heritage.HeritageScrapRepository;
import com.project.common.repository.Heritage.HeritageScrapRepositoryCustom;
import com.project.common.repository.User.UserRepository;
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

    private final UserRepository userRepository;

    // 문화유산 리스트
    @Transactional
    public List<HeritageDto> listInfo() throws Exception {
        List<HeritageDto> list = new ArrayList<>();
        for (HeritageEntity entity : heritageRepository.findAll()) {
            list.add(HeritageMapper.MAPPER.toDto(entity));
        }
        return list;
    }

    // 리뷰 등록
    @Transactional
    public boolean createReview(HeritageReivewDto heritageReivewDto) {
        String nickname = userRepository.findByUserSeq(heritageReivewDto.getUserSeq()).getUserNickname();
        HeritageReviewEntity heritageReviewEntity = HeritageReviewMapper.MAPPER.toEntity(heritageReivewDto);
        heritageReviewEntity.setUserNickname(nickname);
        heritageReviewRepository.save(heritageReviewEntity);
        if (heritageReviewEntity == null) {
            return false;
        } else {
            return true;
        }
    }

    // 리뷰 삭제
    @Transactional
    public boolean deleteReview(int heritageReviewSeq, int heritageSeq) {
        // 리뷰가 없으면 false
        if(heritageReviewRepository.findByHeritageReviewSeq(heritageReviewSeq) == null){
            return false;
        }else{
            // 리뷰 개수 줄이기 ( 문화유산에 )
            HeritageEntity heritageEntity = heritageRepository.findByHeritageSeq(heritageSeq);
            
            // 리뷰 개수 하나 줄이기
            int cnt = heritageEntity.getHeritageReviewCnt() - 1;

            // 개수 entity에 세팅하기
            heritageEntity.setHeritageReviewCnt(cnt);

            // 개수 heritage DB에 저장하기
            heritageRepository.save(heritageEntity);

            // 리뷰 삭제하기
            heritageReviewRepository.deleteByHeritageReviewSeq(heritageReviewSeq);
            return true;
        }
    }

    // 리뷰 리스트 반환
    @Transactional
    public List<HeritageReivewDto> reviewList(int heritageSeq) {
        List<HeritageReviewEntity> list = heritageReviewRepository.findAllByHeritageSeq(heritageSeq);
        List<HeritageReivewDto> listDto= new ArrayList<>();
        for(HeritageReviewEntity heritageReviewEntity : list){
            listDto.add(HeritageReviewMapper.MAPPER.toDto(heritageReviewEntity));
        }
        return listDto;
    }



    // 스크랩 등록
    @Transactional
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
    @Transactional
    public List<HeritageDto> myScrapList(int userSeq) {
        // 유저 번호로 스크랩 목록들 가져오기
        // 그 목록들의 번호로 문화유산 DTO에 담아주기
        // 그 리스트를 반환
        List<HeritageScrapEntity> list = heritageScrapRepository.findAllByUserSeq(userSeq);
        List<HeritageDto> listHeritageDto = new ArrayList<>();
        for(HeritageScrapEntity heritageScrapEntity : list){
            listHeritageDto.add(HeritageMapper.MAPPER.toDto(heritageRepository.findByHeritageSeq(heritageScrapEntity.getHeritageSeq())));
        }
        return listHeritageDto;
    }
    
    // 유저 scrap 삭제
    @Transactional
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
