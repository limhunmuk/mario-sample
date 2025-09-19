package com.radait.mariosample.web.api.member.dto;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
public class MemberSearchDto {

    private Long id;
    private String loginId;
    private String username;
    private String nickNm;
    private String memNm;
    private String memType;

    private String searchType;  // 검색 타입 (id, name, loginId)
    private String searchText;  // 검색어

    // 페이징/정렬 기본값
    private Integer page = 0;     // 0-based
    private Integer size = 10;    // 페이지 크기
    private String sort = "reg_dt";      // DB 컬럼 기준으로 지정
    private String direction = "desc";   // asc|desc

    public Pageable toPageable() {
        return PageRequest.of(page, size, Sort.by(sort));
    }

    /** 안전한 page 반환 */
    public int getPageSafe() {
        return page == null || page < 0 ? 0 : page;
    }

    /** 안전한 size 반환 (상한선 100 권장) */
    public int getSizeSafe() {
        int s = (size == null ? 10 : size);
        if (s < 1) s = 10;
        if (s > 100) s = 100;
        return s;
    }

    /** OFFSET 계산 (page*size) */
    public int getOffset() {
        return getPageSafe() * getSizeSafe();
    }

    /**
     * ORDER BY 컬럼 화이트리스트 매핑
     * - 클라이언트가 sort에 뭘 넣어도 여기서 안전한 실제 컬럼명으로 변환
     */
    public String getSafeSortColumn() {
        String key = (sort == null ? "" : sort);
        return switch (key) {
            // 프론트/백 어느 쪽에서 와도 허용할 별칭들
            case "id", "member_id" -> "member_id";
            case "loginId", "login_id" -> "login_id";
            case "name", "mem_nm" -> "mem_nm";
            case "regDt", "reg_dt" -> "reg_dt";
            default -> "reg_dt";
        };
    }

    /** ORDER BY 방향 화이트리스트 */
    public String getSafeDirection() {
        return "asc".equalsIgnoreCase(direction) ? "asc" : "desc";
    }

    /** LIKE 검색용 파라미터 (DB 독립적으로 쓰기 좋음) */
    public String getNameLike() {
        return (memNm == null || memNm.isBlank()) ? null : "%" + memNm + "%";
    }

}
