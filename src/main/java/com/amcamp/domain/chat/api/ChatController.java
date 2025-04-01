package com.amcamp.domain.chat.api;

import com.amcamp.domain.chat.application.ChatService;
import com.amcamp.domain.chat.dto.response.GenreRecommendationResponse;
import com.amcamp.domain.spotify.dto.response.SpotifySearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/music")
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/recommendation")
    public GenreRecommendationResponse getRecommendation(@RequestBody Map<String, String> request) {
        String input = request.get("input");

        // 1. ChatService에서 장르 추천을 받음
        List<SpotifySearchResponse> recommendedTracks = chatService.getRecommendGenre(input);

        // 2. GenreRecommendationResponse에 추천된 음악 목록을 담아 반환
        return new GenreRecommendationResponse(recommendedTracks);
    }
}
