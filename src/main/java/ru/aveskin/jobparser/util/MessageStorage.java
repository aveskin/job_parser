package ru.aveskin.jobparser.util;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MessageStorage {
    private final Map<Long, List<Long>> chatMessages = new HashMap<>();

    public void addMessage(long chatId, long messageId) {
        chatMessages.computeIfAbsent(chatId, k -> new ArrayList<>()).add(messageId);
    }

    public List<Long> getMessages(long chatId) {
        return chatMessages.getOrDefault(chatId, new ArrayList<>());
    }

    public void clearMessages(long chatId) {
        chatMessages.remove(chatId);
    }
}
