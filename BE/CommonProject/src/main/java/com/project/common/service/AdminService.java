package com.project.common.service;

import com.project.common.controller.Feed.FeedController;
import com.project.common.controller.HeritageController;
import com.project.common.dto.Admin.AdminReportDto;
import com.project.common.entity.Admin.AdminReportEntity;
import com.project.common.entity.Feed.FeedEntity;
import com.project.common.entity.Heritage.HeritageReviewEntity;
import com.project.common.entity.User.UserEntity;
import com.project.common.mapper.Admin.AdminReportMapper;
import com.project.common.repository.Admin.AdminRepository;
import com.project.common.repository.Feed.FeedRepository;
import com.project.common.repository.Heritage.HeritageReviewRepository;
import com.project.common.repository.User.UserRepository;
import com.project.common.service.Feed.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {
    private final AdminRepository adminRepository;
    private final HeritageReviewRepository heritageReviewRepository;
    private final FeedRepository feedRepository;
    private final UserRepository userRepository;
    private final HeritageService heritageService;
    private final FeedService feedService;
    private final UserService userService;

    


    // 신고 접수 받기
    public boolean reportCreate(AdminReportDto adminReportDto) {
        int type = adminReportDto.getReportType();
        // 신고받은 정보 번호 -> 사용자의 아이디 추출
        int reportTypeSeq = adminReportDto.getReportTypeSeq();

        // 타입을 이상하게 가져올 경우
        if(type > 2) return false;

        AdminReportEntity adminReportEntity = AdminReportMapper.MAPPER.toEntity(adminReportDto);
        adminReportEntity.setIsSolved('N');

        switch (type){
            // 리뷰 신고
            case 0:
                // 리뷰 정보 가져오기
                HeritageReviewEntity heritageReviewEntity = heritageReviewRepository.findByHeritageReviewSeq(reportTypeSeq);

                // 리뷰 있는지 체크
                if(heritageReviewEntity == null){
                    return false;
                }
                // 리뷰 작성자 아이디
                String reviewUserId = userRepository.findByUserSeq(heritageReviewEntity.getUserSeq()).getUserId();

                // 사용자 있는지 체크
                if(userRepository.findByUserId(reviewUserId).getIsDeleted() == 'Y'){
                    return false;
                }

                adminReportEntity.setUserId(reviewUserId);
                break;

            // 피드 신고
            case 1:
                // 피드 정보 가져오기
                FeedEntity feedEntity = feedRepository.findByFeedSeq(reportTypeSeq);

                // 피드 있는지 체크
                if(feedEntity == null){
                    return false;
                }
                // 피드 작성자 아이디
                String feedUserId = feedEntity.getUser().getUserId();

                // 사용자 있는지 체크
                if(feedEntity.getUser().getIsDeleted() == 'Y'){
                    return false;
                }

                adminReportEntity.setUserId(feedUserId);
                break;

            // 사용자 신고
            case 2:
                // 사용자 정보 가져오기
                UserEntity userEntity = userRepository.findByUserSeq(reportTypeSeq);
                
                // 사용자 있는지 체크
                if(userEntity == null || userEntity.getIsDeleted() == 'Y'){
                    return false;
                }
                // 사용자 아이디
                String userId = userEntity.getUserId();
                adminReportEntity.setUserId(userId);
                break;
        }
        // 저장하기
        adminRepository.save(adminReportEntity);
        return true;
    }

    // 신고 리스트
    public List<AdminReportDto> listReport() {
        List<AdminReportEntity> list = adminRepository.findAll();
        List<AdminReportDto> listDto = AdminReportMapper.MAPPER.toDtoList(list);
        return listDto;
    }

    
    // 신고에 따라 리뷰, 피드, 사용자 탈퇴시키기
    public String deleteReport(int reportSeq) {
        AdminReportEntity adminReportEntity = adminRepository.findByReportSeq(reportSeq);

        // 이미 신고가 처리 된 경우 false
        if(adminReportEntity.getIsSolved() == 'Y'){
            return "Fail - Solved";
        }


        // 신고 종류
        int type = adminReportEntity.getReportType();

        // 신고 번호
        int reportTypeSeq = adminReportEntity.getReportTypeSeq();

        // 올바르지 않은 경우 false
        if(adminReportEntity == null){
            return "Fail - no data";
        }

        switch (type){
            // 리뷰 삭제
            case 0:

                // 리뷰 찾기
                HeritageReviewEntity heritageReviewEntity = heritageReviewRepository.findByHeritageReviewSeq(reportTypeSeq);

                // 만약 리뷰가 없다면 false
                if(heritageReviewEntity == null){
                    return "Fail - no Review Data";
                }

                // 리뷰 번호, 문화유산 번호
                int heritageReviewSeq = heritageReviewEntity.getHeritageReviewSeq();
                int heritageSeq = heritageReviewEntity.getHeritageSeq();

                // 리뷰 삭제
                heritageService.deleteReview(heritageReviewSeq,heritageSeq);
                break;
            // 피드 삭제    
            case 1:

                // 피드 찾기
                FeedEntity feedEntity = feedRepository.findByFeedSeq(reportTypeSeq);

                // 만약 피드가 없다면 false
                if(feedEntity == null){
                    return "Fail - no Feed Data";
                }

                // 피드 삭제
                feedService.deleteFeed(feedEntity.getUser().getUserId(), reportTypeSeq);
                break;
            // 사용자 삭제
            case 2:

                // 사용자 찾기
                UserEntity userEntity = userRepository.findByUserSeq(reportTypeSeq);

                // 만약 사용자가 없다면 false
                if(userEntity == null){
                    return "Fail - no User Data";
                }

                // 사용자 탈퇴 처리
                userService.resignUser(userEntity.getUserId());
                break;
        }

        // 신고 해결 처리
        adminReportEntity.setIsSolved('Y');
        adminRepository.save(adminReportEntity);

        return "Success";
    }
}
