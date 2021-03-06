package com.web.kokoa.controller;

import com.web.kokoa.model.dictionary;
import com.web.kokoa.model.subtitles;
import com.web.kokoa.model.video;
import com.web.kokoa.repository.CategoryRepo;
import com.web.kokoa.repository.MemberRepo;
import com.web.kokoa.repository.VideoRepo;
import com.web.kokoa.service.SearchService;
import com.web.kokoa.service.TranslateService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@ApiResponses(value = {
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "Failure"),
})

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private MemberRepo memberRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private SearchService searchService;

    @Autowired
    private TranslateService translateService;
    
    @Autowired
    private VideoRepo videoRepo;


    @GetMapping("/idol/{page}")
    @ApiOperation(value = "좋아하는 가수 검색", notes = "category num과 일치하는 video 반환")
    private Object SearchIdolVideo(@RequestParam String groupname, @PathVariable int page) {
        int GroupId = categoryRepo.findIdByGroupname(groupname);
        Page<video> list = searchService.getVideo(page, GroupId);
        return new ResponseEntity<Page<video>>(list, HttpStatus.OK);
    }

    @GetMapping("/member/{page}")
    @ApiOperation(value = "좋아하는 가수 클릭", notes = "category num과 일치하는 video 반환")
    private Object SearchIdolMemberVideo(@RequestParam String membername, @PathVariable int page) {
        int Memberid = memberRepo.findIdByName(membername);
        Page<video> list = searchService.getVideoMember(page, Memberid);
        return new ResponseEntity<Page<video>>(list, HttpStatus.OK);
    }


    @GetMapping("/video")
    @ApiOperation(value = "영상 자막 정보", notes = "해당 영상번호와 일치하는 자막 정보 모두 제공")
    private Object SearchVideoSubtitles(@RequestParam String id) {
        int videoid = Integer.parseInt(id);
        List<subtitles> list = searchService.getSubtitles(videoid);
        List<subtitles> korean = new ArrayList<>();
        List<subtitles> english = new ArrayList<>();
        String url = videoRepo.findById(Integer.parseInt(id)).getUrl();
        for (subtitles s : list) {
            if (s.getType() == 0)
                korean.add(s);
            else {
                english.add(s);
            }
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("Korean",korean);
        map.put("English", english);
        map.put("url",url);

        return new ResponseEntity<HashMap<String,Object>>(map, HttpStatus.OK);
    }
}