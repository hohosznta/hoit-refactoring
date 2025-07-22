package com.ll.demo03.mock;

import com.ll.demo03.global.port.Network;

public class FakeNetwork implements Network {

    @Override
    public String modifyPrompt(String lora, String prompt) {
        return "[FAKE_MODIFIED] " + prompt;
    }

    @Override
    public String createImage(Long taskId, String lora, String prompt, String webhook) {
        return String.format("{\"status\":\"ok\",\"type\":\"image\",\"task_id\":%d}", taskId);
    }

    @Override
    public String createVideo(Long taskId, String lora, String prompt, String webhook) {
        return String.format("{\"status\":\"ok\",\"type\":\"video\",\"task_id\":%d}", taskId);
    }
}
