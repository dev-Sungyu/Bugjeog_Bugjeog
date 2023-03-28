package com.bugjeogbugjeog.app.bugjeogbugjeog.domain.dao;

import com.bugjeogbugjeog.app.bugjeogbugjeog.domain.vo.BusinessVO;
import com.bugjeogbugjeog.app.bugjeogbugjeog.domain.vo.MemberVO;
import com.bugjeogbugjeog.app.bugjeogbugjeog.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberDAO {
    private final MemberMapper memberMapper;

//    자영업자 회원가입
    public void register(MemberVO memberVO) {
        memberMapper.insertMember(memberVO);
    }

//    이메일 중복검사
    public Long findByEmail(String memberEmail) {
        return memberMapper.selectByEmail(memberEmail);
    }

//    휴대폰번호 중복검사
    public Long findByPhoneNumber(String memberPhoneNumber) {
        return memberMapper.selectByPhoneNumber(memberPhoneNumber);
    }

//    자영업자 로그인
    public Long findByMemberEmailAndMemberPassword(MemberVO memberVO) {
        return memberMapper.selectByMemberIdAndMemberPassword(memberVO);
    }

//    유통업체 회원가입
    public void registerBusiness(BusinessVO businessVO) {
        memberMapper.insertBusiness(businessVO);
    }

//    유통업체 이메일 중복검사
    public Long businessFindByEmail(String businessEmail) {
        return memberMapper.businessSelectByEmail(businessEmail);
    }

//    유통업체 휴대폰 중복검사
    public Long businessFindByPhoneNumber(String businessPhoneNumber) {
        return memberMapper.businessSelectByPhoneNumber(businessPhoneNumber);
    }
}
