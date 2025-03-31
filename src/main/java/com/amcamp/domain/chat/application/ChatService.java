package com.amcamp.domain.chat.application;

import com.amcamp.domain.chat.dto.request.ChatRequest;
import com.amcamp.domain.chat.dto.response.ChatResponse;
import com.amcamp.domain.spotify.application.SpotifyService;
import com.amcamp.domain.spotify.dto.response.SpotifySearchResponse;
import com.amcamp.infra.config.chat.ChatInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

//    private final MemberUtil memberUtil;
    private final ChatInterface chatInterface;
    private final PromptGenerator promptGenerator;
    private final SpotifyService spotifyService;

    private static final String MODEL = "gemini-2.0-flash-lite";

    private ChatResponse getCompletion(ChatRequest request) {
        return chatInterface.getCompletion(MODEL, request);
    }

    private String getCompletion(String text) {
        ChatResponse response = getCompletion(new ChatRequest(text));

        return response.getCandidates()
                .stream()
                .findFirst().flatMap(candidate -> candidate.getContent().getParts()
                        .stream()
                        .findFirst()
                        .map(ChatResponse.TextPart::getText))
                .orElse(null);
    }


    public List<SpotifySearchResponse> getRecommendGenre(String input) {
//        memberUtil.getCurrentMember();
        // ChatService에서 장르 추천을 받음
        String message = promptGenerator.generatePrompt(input);
        String recommendedGenre = getCompletion(message);

        // 추천된 장르를 기반으로 SpotifyService에서 음악 검색
        List<String> genres = List.of(recommendedGenre.split(","));  // 쉼표로 구분된 장르 처리
        return spotifyService.searchByGenre(genres);
    }
}
