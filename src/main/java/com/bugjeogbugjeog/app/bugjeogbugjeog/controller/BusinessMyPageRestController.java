package com.bugjeogbugjeog.app.bugjeogbugjeog.controller;

import com.bugjeogbugjeog.app.bugjeogbugjeog.domain.vo.BusinessVO;
import com.bugjeogbugjeog.app.bugjeogbugjeog.domain.vo.FreeReplyVO;
import com.bugjeogbugjeog.app.bugjeogbugjeog.service.BusinessMyPageService;
import com.bugjeogbugjeog.app.bugjeogbugjeog.service.MyPageService;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/myPages/business/*")
public class BusinessMyPageRestController {

    private final HttpServletRequest req;
    private final BusinessMyPageService businessMyPageService;
    private final MyPageService myPageService;

    // 파일 업로드
    @PostMapping("upload-file")
    public String memberUpload(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        String path = "/usr/project/upload/" + getPath();
        File file = new File(path);
        if(!file.exists()) {file.mkdirs();}

        String uuid = UUID.randomUUID().toString();
        multipartFile.transferTo(new File(path, uuid + "_" + multipartFile.getOriginalFilename()));

        if(multipartFile.getContentType().startsWith("image")){
            FileOutputStream out = new FileOutputStream(new File(path, "t_" + uuid + "_" + multipartFile.getOriginalFilename()));
            Thumbnailator.createThumbnail(multipartFile.getInputStream(), out, 400, 400);
            out.close();
        }
        return uuid;
    }

    //    파일 불러오기
    @GetMapping("display")
    public byte[] display(String fileName) throws IOException {
        return FileCopyUtils.copyToByteArray(new File("/usr/project/upload", fileName));
    }

    //    파일 저장
    @PatchMapping("file-business-save")
    public void fileSave(@RequestBody BusinessVO business){
        businessMyPageService.businessFileSave(business);
    }

    //    이름 변경
    @PatchMapping("updateBusinessCeoName")
    public String updateName(@RequestParam("businessCeoName") String businessCeoName) {
        HttpSession session = req.getSession();
        Long businessId = (Long) session.getAttribute("businessId");

        businessMyPageService.updateBusinessCeoName(businessId, businessCeoName);
        return businessCeoName;
    }

    //    회원정보 가져오기
    @GetMapping("businessVO")
    public BusinessVO memberVO(){
        HttpSession session = req.getSession();
        Long businessId = (Long) session.getAttribute("businessId");

        return businessMyPageService.businessInfo(businessId);
    }

    @GetMapping("businessInfo")
    public BusinessVO businessInfo(@RequestParam("businessId") Long businessId){
        return businessMyPageService.businessInfo(businessId);
    }

    //    핸드폰 중복검사
    @GetMapping("businessPhoneCheck")
    public Boolean businessPhoneCheck(@RequestParam("businessPhoneNumber") String businessPhoneNumber){
        return myPageService.phoneNumberCheck(businessPhoneNumber);
    }

    //    핸드폰 번호 변경
    @PatchMapping("phoneNumberUpdate")
    public String phoneNumberUpdate(@RequestParam("businessPhoneNumber") String businessPhoneNumber){
        HttpSession session = req.getSession();
        Long businessId = (Long) session.getAttribute("businessId");

        businessMyPageService.updateBusinessPhoneNumber(businessId, businessPhoneNumber);
        return businessPhoneNumber;
    }

    // 회사명 변경
    @PatchMapping("companyName-update")
    public String updateBusinessCompanyName(@RequestParam("businessCompanyName") String businessCompanyName){
        HttpSession session = req.getSession();
        Long businessId = (Long) session.getAttribute("businessId");

        businessMyPageService.updateBusinessCompanyName(businessId, businessCompanyName);
        return businessCompanyName;
    }

    // 사업자 번호 중복 검사
    @GetMapping("businessNumber-check")
    public Boolean businessNumberCheck(@RequestParam("businessNumber") String businessNumber){
        return businessMyPageService.businessNumberCheck(businessNumber);
    }

    // 사업자 번호 변경
    @PatchMapping("businessNumber-update")
    public String businessNumberUpdate(@RequestParam("businessNumber") String businessNumber){
        HttpSession session = req.getSession();
        Long businessId = (Long) session.getAttribute("businessId");

        businessMyPageService.businessNumberUpdate(businessId, businessNumber);
        return businessNumber;
    }

    // 비밀번호 변경
    @PatchMapping("businessPassword-update")
    public void businessPasswordUpdate(@RequestParam("businessPassword") String businessPassword){
        HttpSession session = req.getSession();
        Long businessId = (Long) session.getAttribute("businessId");

        businessMyPageService.businessPasswordUpdate(businessId, businessPassword);
    }

    // 각 게시물 작성 갯수
    @GetMapping("count")
    public Map<String, Object> businessAllCount(){
        HttpSession session = req.getSession();
        Long businessId = (Long) session.getAttribute("businessId");

        return businessMyPageService.businessAllCount(businessId);
    }

    // 유통업자가 작성한  댓글 목록
    @GetMapping("replyList")
    public List<FreeReplyVO> reply(@RequestParam("boardFreeId") Long boardFreeId){
        HttpSession session = req.getSession();
        Long businessId = (Long) session.getAttribute("businessId");

        return businessMyPageService.businessReplyList(businessId, boardFreeId);
    }

    //    현재 날짜 경로 구하기
    private String getPath(){
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }

}
