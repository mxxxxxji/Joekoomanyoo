package com.ssafy.heritage.data.dto

data class Feed(
    val feedSeq: Int,               // 게시글 번호
    val userSeq: Int,               // 사용자 번호
<<<<<<< .merge_file_a23008
    val attachSeq: Int,             // 피드 첨부파일 번호
=======
    val feedImgUrl: String,         // 피드 첨부파일 Url
>>>>>>> .merge_file_a16236
    val feedTitle: String,          // 제목
    val feedContent: String,        // 본문
    val feedOpen: Char,             // 공개 여부 (N,Y)
    val feedCreatedAt: String,      // 등록 시간
    val feedUpdatedAt: String       // 수정 시간
)