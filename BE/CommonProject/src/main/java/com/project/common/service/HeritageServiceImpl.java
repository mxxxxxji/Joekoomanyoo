package com.project.common.service;

import com.project.common.entity.HeritageEntity;
import com.project.common.repository.HeritageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HeritageServiceImpl implements HeritageService{

    private final HeritageRepository heritageRepository;

    @Transactional
    @Override
    public List<HeritageEntity> listInfo() throws Exception{
        List<HeritageEntity> list = new ArrayList<>();
        for(HeritageEntity entity : heritageRepository.findAll()){
            list.add(entity);
        }
        return list;
    }
}
