package com.radait.mariosample.web.api.member.controller;

import com.radait.mariosample.web.api.common.entity.PageResponse;
import com.radait.mariosample.web.api.member.dto.MemberDetailDto;
import com.radait.mariosample.web.api.member.dto.MemberFormDto;
import com.radait.mariosample.web.api.member.dto.MemberSearchDto;
import com.radait.mariosample.web.api.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원 목록 조회
     * @return
     */
    @GetMapping("/api/members")
    public ResponseEntity<PageResponse<MemberDetailDto>> getMembers(MemberSearchDto condition) {

        PageResponse<MemberDetailDto> pageMember = memberService.selectMemberList(condition);
        return ResponseEntity.ok(pageMember);
    }

    /**
     * 회원 조회
     * @param id
     * @return
     */
    @GetMapping("/api/members/{id}")
    public ResponseEntity<MemberDetailDto> getMemberById(@PathVariable long id) {

        MemberDetailDto member = memberService.selectMemberById(id);
        return ResponseEntity.ok(member);
    }

    /**
     * 회원 등록
     */
    @PostMapping("/api/members")
    public ResponseEntity<MemberDetailDto> insertMember(@RequestBody MemberFormDto formDto) {

        MemberDetailDto detailDto = memberService.insertMember(formDto);
        URI location = URI.create("/api/members/" + detailDto.getMemberId());
        return ResponseEntity
                .created(location)
                .body(detailDto);
    }

    /**
     * 회원 수정
     * @param id
     * @param formDto
     * @return
     */
    @PutMapping("/api/members/{id}")
    public ResponseEntity<String> updateMember(@PathVariable long id, @RequestBody MemberFormDto formDto) {

        int rtnCnt = memberService.updateMember(id, formDto);
        return ResponseEntity.ok(rtnCnt + "건의 회원 정보가 수정 되었습니다");
    }

    /**
     * 회원 삭제
     * @param id
     * @return
     */
    @DeleteMapping("/api/members/{id}")
    public ResponseEntity<String> deleteMember(@PathVariable long id) {

        int rtnCnt = memberService.deleteMemberById(id);
        return ResponseEntity.ok(rtnCnt + "건의 회원 정보가 삭제 되었습니다");
    }
}
