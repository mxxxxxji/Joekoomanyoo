package com.project.common.service;

import com.project.common.dto.AR.MyStampDto;
import com.project.common.dto.AR.MyStampMapper;
import com.project.common.dto.AR.StampDto;
import com.project.common.dto.AR.StampMapper;
import com.project.common.entity.AR.MyStampEntity;
import com.project.common.entity.AR.StampEntity;
import com.project.common.repository.AR.ARRepository;
import com.project.common.repository.AR.MyARRepository;
import com.project.common.repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ARService {
    private final ARRepository arRepository;

    private final UserRepository userRepository;

    private final MyARRepository myARRepository;


    // 스탬프 리스트 반환
    public List<StampDto> listStamp() {
        List<StampEntity> list = arRepository.findAll();

        // 리스트가 하나도 없으면 null 리턴
        if (list.size() == 0) {
            return null;
        } else {
            List<StampDto> listDto = new ArrayList<>();
            for (StampEntity stmStampEntity : list) {
                listDto.add(StampMapper.MAPPER.toDto(stmStampEntity));
            }
            return listDto;
        }
    }

    // 내 스탬프 리스트 받아오기
    public List<StampDto> listMyStamp(int userSeq) {
        // 내 스탬프 리스트 받아옴
        List<MyStampEntity> list = myARRepository.findAllByUserSeq(userSeq);
        if (list.size() == 0) {
            return null;
        }

        // 내 스탬프 리스트의 스탬프 번호를 통해 스탬프Dto 받아오기
        List<StampDto> listDto = new ArrayList<>();
        for (MyStampEntity myStampEntity : list) {
            listDto.add(StampMapper.MAPPER.toDto(arRepository.findByStampSeq(myStampEntity.getStampSeq())));
        }
        return listDto;
    }

    public boolean plusMyStamp(int userSeq, int stampSeq) {
        // 사용자가 없다면 false
        if (userRepository.findByUserSeq(userSeq) == null) {
            return false;
        }
        // 스탬프가 없다면 false
        StampEntity stampEntity = arRepository.findByStampSeq(stampSeq);
        if (stampEntity == null) {
            return false;
        }

        // 문화유산 번호
        int heritageSeq = stampEntity.getHeritageSeq();

        MyStampDto myStampDto = MyStampDto.builder()
                 .myStampSeq(0)
                .stampSeq(stampSeq)
                .userSeq(userSeq)
                .heritageSeq(heritageSeq)
                .myStampRegistedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).build();

        myARRepository.save(MyStampMapper.MAPPER.toEntity(myStampDto));
        return true;
    }
}
