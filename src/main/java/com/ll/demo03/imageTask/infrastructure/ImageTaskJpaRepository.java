package com.ll.demo03.imageTask.infrastructure;

import com.ll.demo03.imageTask.domain.ImageTask;
import com.ll.demo03.member.infrastructure.MemberEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


@Repository
public interface ImageTaskJpaRepository extends JpaRepository<ImageTaskEntity, Long>, JpaSpecificationExecutor<ImageTaskEntity> {

    void deleteByMemberId(Long memberId);

    Slice<ImageTaskEntity> findByMember(MemberEntity creator, PageRequest pageRequest);

    boolean existsByMemberAndCreatedAtGreaterThan(MemberEntity creator, LocalDateTime createdAt);

    boolean existsByMemberAndCreatedAtLessThan(MemberEntity creator, LocalDateTime createdAt);

}
