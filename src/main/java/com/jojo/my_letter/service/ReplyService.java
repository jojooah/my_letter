package com.jojo.my_letter.service;

import com.jojo.my_letter.controller.service.LoginService;
import com.jojo.my_letter.mapper.ReplyMapper;
import com.jojo.my_letter.model.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyMapper replyMapper;
    private final LoginService loginService;

    public void saveReply(Reply reply) {
        Member member = loginService.getMember();
        reply.setMemberSeq(member.getMemberSeq());

        if (reply.getSaveStatus().equals(SaveStatus.I)) {
            replyMapper.insertReply(reply);
        } else if (reply.getSaveStatus().equals(SaveStatus.U)) {
            replyMapper.updateReply(reply);
        } else if (reply.getSaveStatus().equals(SaveStatus.D)) {
            replyMapper.deleteReply(reply);
        }
    }

    public List<Reply> selectReplyListByNewsLetterSeq(int seq) {
        Reply reply = new Reply();
        reply.setNewsLetterSeq(seq);
        return replyMapper.selectReplyListByNewsLetterSeq(reply);
    }

}
