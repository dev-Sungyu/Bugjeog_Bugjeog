package com.bugjeogbugjeog.app.bugjeogbugjeog.service;

import com.bugjeogbugjeog.app.bugjeogbugjeog.domain.dao.ReplyDAO;
import com.bugjeogbugjeog.app.bugjeogbugjeog.domain.dto.BoardReplyDTO;
import com.bugjeogbugjeog.app.bugjeogbugjeog.domain.vo.Criteria;
import com.bugjeogbugjeog.app.bugjeogbugjeog.domain.vo.FreeReplyVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyDAO replyDAO;
    //    댓글 전체 조회
    public List<BoardReplyDTO> getList(Criteria criteria, Long boardId){
//        criteria.create(getTotal(boardId));
        return replyDAO.findAll(criteria, boardId);
    }

    //    댓글 전체 개수
    public int getTotal(Long boardId){
        return replyDAO.findCountAll(boardId);
    }
}
