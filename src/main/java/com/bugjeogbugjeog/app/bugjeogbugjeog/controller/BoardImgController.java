package com.bugjeogbugjeog.app.bugjeogbugjeog.controller;

import com.bugjeogbugjeog.app.bugjeogbugjeog.domain.vo.BoardBusinessImgVO;
import com.bugjeogbugjeog.app.bugjeogbugjeog.service.BoardBusinessImgService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
//@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class BoardImgController {
    private final BoardBusinessImgService businessBoardImgService;

    //    파일 업로드
    @PostMapping("/imgs/business/upload")
    @ResponseBody
    public List<String> businessUpload(@RequestParam("file") List<MultipartFile> multipartFiles) throws IOException {
        List<String> uuids = new ArrayList<>();
        String path = "/usr/project/upload/" + getPath();
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }

        for (int i = 0; i < multipartFiles.size(); i++) {
            uuids.add(UUID.randomUUID().toString());
            multipartFiles.get(i).transferTo(new File(path, uuids.get(i) + "_" + multipartFiles.get(i).getOriginalFilename()));

            if (multipartFiles.get(i).getContentType().startsWith("image")) {
                FileOutputStream out = new FileOutputStream(new File(path, "t_" + uuids.get(i) + "_" + multipartFiles.get(i).getOriginalFilename()));
                Thumbnailator.createThumbnail(multipartFiles.get(i).getInputStream(), out, 250, 175);
                out.close();
            }
        }
        return uuids;
    }

    //    파일 저장
    @PostMapping("/imgs/business/save")
    @ResponseBody
    public RedirectView businessSave(@RequestBody List<BoardBusinessImgVO> boardBusinessImgVOs) {
        log.info(boardBusinessImgVOs.toArray().toString());
        businessBoardImgService.writeList(boardBusinessImgVOs);
        return new RedirectView("/board/business/list");
    }

    //    파일 불러오기
    @GetMapping("/imgs/business/display")
    @ResponseBody
    public byte[] businessDisplay(String fileName) throws Exception {
        System.out.println(fileName);
        System.out.println(fileName);
        System.out.println(fileName);
        System.out.println(fileName);
        System.out.println(fileName);
        System.out.println(fileName);
//        fileName.split("_")
        try {
            return fileName.contentEquals("null") || fileName.isBlank() ? null : FileCopyUtils.copyToByteArray(new File("/usr/project/upload", fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/imgs/business/download")
    public ResponseEntity<Resource> download(String fileName) throws UnsupportedEncodingException {
        System.out.println(fileName);
        Resource resource = new FileSystemResource("/usr/project/upload/" + fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment;filename=" + new String(fileName.substring(fileName.indexOf("_") + 1).getBytes("UTF-8"), "ISO-8859-1"));
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    //    현재 날짜 경로 구하기
    private String getPath() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }
}
