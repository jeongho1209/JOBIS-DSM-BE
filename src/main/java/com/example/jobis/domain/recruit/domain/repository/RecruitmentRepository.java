package com.example.jobis.domain.recruit.domain.repository;

import com.example.jobis.domain.code.domain.enums.CodeType;
import com.example.jobis.domain.recruit.domain.RecruitArea;
import com.example.jobis.domain.recruit.domain.Recruitment;
import com.example.jobis.domain.recruit.domain.repository.vo.QQueryRecruitAreaCodeVO;
import com.example.jobis.domain.recruit.domain.repository.vo.QueryRecruitAreaCodeVO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

import static com.example.jobis.domain.code.domain.QRecruitAreaCode.recruitAreaCode;
import static com.example.jobis.domain.code.domain.QCode.code1;

@Repository
@RequiredArgsConstructor
public class RecruitmentRepository {
    private final JPAQueryFactory queryFactory;
    private final RecruitmentJpaRepository recruitmentJpaRepository;

    public List<QueryRecruitAreaCodeVO> queryKeywordListByRecruitArea(RecruitArea recruitArea) {
        return queryFactory
                .select(new QQueryRecruitAreaCodeVO(code1.keyword))
                .from(recruitAreaCode)
                .join(recruitAreaCode.codeId, code1)
                .where(recruitAreaCode.recruitAreaId.eq(recruitArea),
                        code1.codeType.eq(CodeType.JOB))
                .fetch();
    }

    public List<Recruitment> findAll() {
        return recruitmentJpaRepository.findAll();
    }
}
