package com.ll.demo03.mock;

import com.ll.demo03.global.port.MessageProducer;
import com.ll.demo03.imageTask.controller.request.ImageQueueRequest;
import com.ll.demo03.videoTask.controller.request.VideoQueueRequest;

import java.util.ArrayList;
import java.util.List;

public class FakeMessageProducer implements MessageProducer {

    public List<ImageQueueRequest> imageMessages = new ArrayList<>();
    public List<VideoQueueRequest> videoMessages = new ArrayList<>();

    @Override
    public void sendImageCreationMessage(ImageQueueRequest message) {
        imageMessages.add(message);
    }

    @Override
    public void sendVideoCreationMessage(VideoQueueRequest message) {
        videoMessages.add(message);
    }
}

