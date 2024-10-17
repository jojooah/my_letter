package com.jojo.my_letter.mapper;

import com.jojo.my_letter.model.entity.Reply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReplyMapper {
    /**
     * 댓글 등록
     * @param reply
     */
    void insertReply(@Param("param")Reply reply);

    /**
     * 댓글 수정
     * @param reply
     */
    void updateReply(@Param("param")Reply reply);

    /**
     * 댓글 삭제
     * @param reply
     */
    void deleteReply(@Param("param")Reply reply);

    /**
     * 댓글 조회
     * @param reply
     * @return
     */
    List<Reply> selectReplyListByNewsLetterSeq(@Param("param")Reply reply);
}