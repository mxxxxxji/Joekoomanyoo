package com.project.common.service;

import com.project.common.dto.HeritageDto;
import com.project.common.dto.HeritageMapper;
import com.project.common.dto.HeritageReivewDto;
import com.project.common.dto.HeritageReviewMapper;
import com.project.common.entity.HeritageEntity;
import com.project.common.entity.HeritageReviewEntity;
import com.project.common.repository.HeritageRepository;
import com.project.common.repository.HeritageReviewRepository;
import com.project.common.repository.UserRepository;
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
    private final UserRepository userRepository;
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
}
