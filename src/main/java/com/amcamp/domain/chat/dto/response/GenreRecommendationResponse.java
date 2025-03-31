package com.amcamp.domain.chat.dto.response;

import com.amcamp.domain.spotify.dto.response.SpotifySearchResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
public class GenreRecommendationResponse {
    private List<SpotifySearchResponse> tracks;  // 추천된 음악 목록
}