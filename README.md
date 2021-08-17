# OIDC_User_Management_Service (영화 정보 서비스)
심박수 기반 영화 순위 제공서비스 'Movie by Heart'의 영화 정보 서비스를 구성하는 레포지토리

## Controller
- 영화 인기 순위(GET)  **/hotmovierank**
  - parameter : none
  
  <br>
  
- 영화 사용자별 협업필터링 결과(GET) **/usermovierank**
  - parameter : userId
  
  <br>

- 영화 검색(GET)  **/searchmovie**
  - parameter : query
  
  <br>
  
- 영화 상세정보(GET) **/movieinfo**
  - parameter : title, userId
  
  <br>
  
- 영화 좋아요 상태 변경(GET) **/changelove**
  - parameter : userId, title, state
  
  <br>

- 좋아요 영화 목록 가져오기(GET)  **/getlovemovie**
  - parameter : userId
  
  <br>

- 시청한 영화 목록 가져오기(GET)  **/getwatchmovie**
  - parameter : userId
  
  <br>

## Interface
- getMovieRank() : 로그인을 위해 사용자 정보를 DB로부터 비교하기 위한 기능

- getBpm() : 회원가입 과정에서 ID의 중복검사를 위한 기능

- getUid() : 회원가입을 위해, 회원 기입 정보를 DB에 Insert하기 위한 기능

- checkTitle() : 잃어버림 ID/PW를 찾기 위한 기능

- getCount() : 로그인을 위해 사용자 정보를 DB로부터 비교하기 위한 기능

- userLoveCheck() : 회원가입 과정에서 ID의 중복검사를 위한 기능

- userLoveDelete() : 회원가입을 위해, 회원 기입 정보를 DB에 Insert하기 위한 기능

- userLoveInsert() : 잃어버림 ID/PW를 찾기 위한 기능

- insertMovieInfo() : 로그인을 위해 사용자 정보를 DB로부터 비교하기 위한 기능

- getLoveMoive() : 회원가입 과정에서 ID의 중복검사를 위한 기능

- getWatchMovie() : 회원가입을 위해, 회원 기입 정보를 DB에 Insert하기 위한 기능

<br>

## ParseMovie
영화 데이터 파싱 및 크롤링, 데이터 처리의 기능을 수행하는 클래스

- getHotMovieRank() : 영화 순위를 썸네일과 함께 제공해주기 위해 리스트를 반환하는 함수

- getCollaborateMovie() : 협업필터링으로 분석되어 나온 영화를 파싱하는 함수

- parse() : 영화 제목을 토대로 상세 정보를 파싱 및 크롤링 하는 함수

- getInfo() : 파싱 및 크롤링 된 영화 데이터를 추출하여 데이터를 가공하는 함수

- getList() : 파싱 및 크롤링 된 영화를 리스트로 추출하는 함수

- get() : 외부 사이트와의 GET 통신 구현 함수

- connect() : HttpURLConnection 함수

- readBody() : 요청되어 나온 결과를 읽는 
