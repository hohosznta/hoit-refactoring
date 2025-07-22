package com.ll.demo03.imageTask.service;

import com.ll.demo03.global.error.ErrorCode;
import com.ll.demo03.global.exception.CustomException;
import com.ll.demo03.imageTask.controller.port.ImageTaskService;
import com.ll.demo03.imageTask.controller.request.ImageQueueRequest;
import com.ll.demo03.imageTask.controller.request.ImageTaskRequest;
import com.ll.demo03.member.domain.Member;
import com.ll.demo03.member.service.port.MemberRepository;
import com.ll.demo03.mock.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ImageTaskServiceTest {

    private TestContainer testContainer;
    private ImageTaskService imageTaskService;
    private MemberRepository memberRepository;
    private FakeMessageProducer messageProducer;

    @BeforeEach
    void setUp() {
        this.testContainer = TestContainer.builder().build();
        this.imageTaskService = testContainer.imageTaskService;
        this.memberRepository = testContainer.memberRepository;
        this.messageProducer = testContainer.fakeMessageProducer;

        Member member1 = Member.builder().id(1L).credit(5).build();
        Member member2 = Member.builder().id(2L).credit(0).build();
        memberRepository.save(member1);
        memberRepository.save(member2);
    }

    @Test
    void 프롬프트_변환하고_크레딧_차감하고_메시지_전송한다() {
        // given
        Member member = memberRepository.findById(1L).get();

        ImageTaskRequest request = ImageTaskRequest.builder()
                .prompt("고양이 사진 그려줘")
                .lora("test-model")
                .build();

        // when
        imageTaskService.initate(request, member);

        // then
        Member updated = memberRepository.findById(1L).get();
        assertEquals(4, updated.getCredit());

        assertTrue(messageProducer.imageMessages.size() == 1);
        ImageQueueRequest sentMessage = messageProducer.imageMessages.get(0);
        assertTrue(sentMessage.getPrompt().contains("[FAKE_MODIFIED]"));
    }

    @Test
    void 크레딧이_0이면_에러를_발생시킨다() {
        // given
        Member member = memberRepository.findById(2L).get();

        ImageTaskRequest request = ImageTaskRequest.builder()
                .prompt("고양이 사진 그려줘")
                .lora("test-model")
                .build();

        // when
        //then
        assertThatThrownBy(() -> {
            imageTaskService.initate(request, member);
        }).isInstanceOf(CustomException.class)
                .hasMessageContaining(ErrorCode.NO_CREDIT.getMessage());

    }
}