# 📂 Recommendation-Service

## 서비스 개요
`Recommendation-Service`는 사용자에게 맞춤형 추천을 제공하는 서비스입니다. 채팅 기반의 노래 추천 시스템을 포함하고 있습니다.

## 디렉터리 구조
```
📦domain
 ┣ 📂chat
 ┃ ┣ 📂api
 ┃ ┃ ┗ 📜ChatController.java
 ┃ ┣ 📂application
 ┃ ┃ ┣ 📜ChatService.java
 ┃ ┃ ┗ 📜PromptGenerator.java
 ┃ ┗ 📂dto
 ┃ ┃ ┣ 📂request
 ┃ ┃ ┃ ┗ 📜ChatRequest.java
 ┃ ┃ ┗ 📂response
 ┃ ┃ ┃ ┣ 📜ChatResponse.java
 ┃ ┃ ┃ ┗ 📜GenreRecommendationResponse.java
 ┣ 📂common
 ┃ ┗ 📂model
 ┃ ┃ ┗ 📜BaseTimeEntity.java
 ┗ 📂spotify
 ┃ ┣ 📂api
 ┃ ┃ ┗ 📜SpotifyController.java
 ┃ ┣ 📂application
 ┃ ┃ ┗ 📜SpotifyService.java
 ┃ ┗ 📂dto
 ┃ ┃ ┗ 📂response
 ┃ ┃ ┃ ┗ 📜SpotifySearchResponse.java
```

### 📂 `domain`
- **`chat`**: AI 기반의 채팅을 활용한 추천 기능을 제공하는 영역입니다.
  - `ChatController.java`: 채팅 추천 API를 담당하는 컨트롤러
  - `ChatService.java`: 채팅 기반 추천 로직을 처리하는 서비스
  - `PromptGenerator.java`: AI 추천을 위한 프롬프트 생성기
  - `dto/request`: 클라이언트에서 요청하는 데이터 모델
  - `dto/response`: 응답 데이터를 위한 모델

- **`spotify`**: Spotify 데이터를 활용한 음악 추천 기능을 담당하는 영역입니다.
  - `SpotifyController.java`: Spotify 관련 API를 제공하는 컨트롤러
  - `SpotifyService.java`: Spotify API와 통신하는 서비스
  - `dto/response`: Spotify 검색 결과 데이터를 담는 DTO
