package ru.aveskin.jobparser.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.aveskin.jobparser.model.VacancyEntity;
import ru.aveskin.jobparser.repository.VacancyRepository;
import ru.aveskin.jobparser.util.MessageStorage;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {
    private final VacancyRepository vacancyRepository;
    private final MessageStorage messageStorage;

    @Value("${bot.name}")
    private String botName;

    private static final String START = "/start";
    private static final String GET = "/get";
    private static final String CLEAR = "/clear";

    public TelegramBot(@Value("${bot.token}") String botToken, VacancyRepository vacancyRepository, MessageStorage messageStorage) {
        super(botToken);
        this.vacancyRepository = vacancyRepository;
        this.messageStorage = messageStorage;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }
        String message = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        switch (message) {
            case START -> {
                String userName = update.getMessage().getChat().getUserName();
                startCommand(chatId, userName);
            }
            case GET -> getCommand(chatId);
            case CLEAR -> clearCommand(chatId);
            default -> unknownCommand(chatId);

        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }


    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }


    @Override
    public void onRegister() {
        super.onRegister();
    }


    private void startCommand(Long chatId, String userName) {
        var text = """
                Добро пожаловать в бот, %s!
                
                Здесь Вы сможете узнать вакансии java junior за сегодня.
                
                Для этого воспользуйтесь командами:
                /get - получить вакансии
                
                /clear - очистить экран
                
                Дополнительные команды:
                /help - получение справки
                """;
        var formattedText = String.format(text, userName);
        sendMessage(chatId, formattedText);
    }

    private void getCommand(Long chatId) {
        List<VacancyEntity> vacancies = vacancyRepository.findAllByToday(LocalDate.now());
        for (VacancyEntity vacancy : vacancies) {
            String message =
                    String
                            .format("🔹 %s\n💰 Зарплата: %s\n🏢 Компания: %s\n🌍 Город: %s\n🧑‍🎓 Опыт: %s\n🔗 Ссылка: %s",
                                    vacancy.getTitle(),
                                    vacancy.getSalary(),
                                    vacancy.getCompany(),
                                    vacancy.getCity(),
                                    vacancy.getExperience(),
                                    vacancy.getLink());

            sendMessage(chatId, message);
        }
    }

    private void clearCommand(Long chatId) {
        List<Long> messageIds = messageStorage.getMessages(chatId);
        for (Long messageId : messageIds) {
            try {
                var chatIdStr = String.valueOf(chatId);
                Integer messageIdInt = Math.toIntExact(messageId);
                execute(new DeleteMessage(chatIdStr, messageIdInt));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        messageStorage.clearMessages(chatId);
        sendMessage(chatId, "Экран очищен!");
    }

    private void unknownCommand(Long chatId) {
        var text = "Не удалось распознать команду!";
        sendMessage(chatId, text);
    }


    private void sendMessage(Long chatId, String text) {
        var chatIdStr = String.valueOf(chatId);
        var sendMessage = new SendMessage(chatIdStr, text);
        try {
            Message sentMessage = execute(sendMessage);
            var msgId = sentMessage.getMessageId();
            messageStorage.addMessage(chatId, msgId);
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки сообщения", e);
        }
    }


}
