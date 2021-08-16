package com.oidc.movie.parse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oidc.movie.dto.MovieDto;
import org.json.simple.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class ParseMovie {

    String clientId= "gqQSDSXctJYArTsqvLae";
    String clientSecret = "aRwkVaPsfo";

    String query = null;
    int min = 0, max = 0, count = 0, love = 0;
    List<MovieDto> list = null;

    String title = "", summary = "", open = "", genre = "", country = "", grade = ""
            , runningtime = "",subtitle = "",image = "",pubTitle = "",director = "",actor = "";

    // 생성자
    public ParseMovie(String query){
        this.query = query;
    }

    public ParseMovie(String title, int min, int max, int count, int love){
        this.query = title;
        this.min = min;
        this.max = max;
        this.count = count;
        this.love = love;
    }

    public ParseMovie(List<MovieDto> list){
        this.list = list;
    }

    // 영화 순위 데이터
    public List<MovieDto> getHotMovieRank(){

        for(int i = 0; i<list.size(); i++){

            try {
                query = list.get(i).getTitle();

                String json = parse();

                JSONParser parser = new JSONParser();
                JSONObject obj = null;
                obj = (JSONObject)parser.parse(json);

                JSONArray item = (JSONArray)obj.get("items");

                JSONObject tmp = (JSONObject) item.get(0);
                image = (String) tmp.get("image");

                list.get(i).setImage(image);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    // 협업필터링으로 분석되어 나온 영화 파싱
    public String getCollaborateMovie(){
        String uid = query;
        System.out.println(uid);
        String linkUrl = "http://118.67.132.152:8083/valuerate/rec/?uid=" + uid;
        String resultString = "";

        try{
            Document document = Jsoup.connect(linkUrl).get();
            Thread.sleep(1000);
            resultString = document.body().text();
            resultString = resultString.replaceAll("'", "\"");

            ObjectMapper objectMapper = new ObjectMapper();
            this.list = Arrays.asList(objectMapper.readValue(resultString, MovieDto[].class));

        }catch(Exception e){
            e.printStackTrace();
        }
        return resultString;
    }

    // 영화 데이터 parse
    public String parse(){

        // 검색어 UTF-8 변환
        try{
            query = URLEncoder.encode(query, "UTF-8");
        }catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }

        // API 주소 정의 및 실행
        String apiURL = "https://openapi.naver.com/v1/search/movie.json?query="+query;
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);

        // 파싱 결과
        String responseBody = get(apiURL, requestHeaders);

        // 결과 반환
        return responseBody;
    }

    // 데이터 처리(영화 상세정보 추출)
    public List<MovieDto> getInfo() {

        query = query.replaceAll(" ","");

        String url = "https://search.naver.com/search.naver?where=nexearch&query=" + query + "+정보";

        // parse 수행
        String json = parse();

        // 최종 반환할 리스트
        List<MovieDto> list = null;
        list = new ArrayList<MovieDto>();

        try {
            Document document = Jsoup.connect(url).get();
            if(document.body().getElementsByClass("text _content_text").size()==0){
                summary = document.body().getElementsByClass("desc _text").get(0).text();
            }
            else{
                summary = document.body().getElementsByClass("text _content_text").get(0).text();
            }

            Elements data = document.body().getElementsByTag("dt");

            String[] array = data.text().split(" ");

            for(int i = 0; i<array.length; i++){
                String value = document.getElementsByTag("dd").get(i).text();
                System.out.println(array[i]);
                System.out.println(value);
                System.out.println("----------");

                if(array[i].equals("개봉")){
                    open = value;
                }
                else if(array[i].equals("등급")){
                    grade = value;
                }
                else if(array[i].equals("장르")){
                    genre = value;
                }
                else if(array[i].equals("국가")){
                    country = value;
                }
                else if(array[i].equals("러닝타임")){
                    runningtime = value;
                }
                else if(array[i].equals("개요")){
                    runningtime = value;
                }
            }

            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject)parser.parse(json);
            JSONArray item = (JSONArray)obj.get("items");

            for(int i = 0; i<item.size(); i++) {
                JSONObject tmp = (JSONObject) item.get(i);

                title = (String) tmp.get("title");
                title = title.replaceAll("<b>", "");
                title = title.replaceAll("</b>", "");

                subtitle = (String) tmp.get("subtitle");
                image = (String) tmp.get("image");
                pubTitle = (String) tmp.get("pubTitle");

                director = (String) tmp.get("director");
                if (director.length() != 0) {
                    director = director.substring(0, director.length() - 1);
                }

                actor = (String) tmp.get("actor");
                if (actor.length() != 0) {
                    actor = actor.substring(0, actor.length() - 1);
                }
            }

            MovieDto movie = new MovieDto(title,subtitle,image,pubTitle,director,actor,open,grade,genre,country,runningtime,summary, max, min ,count, love);
            System.out.println(movie);
            list.add(movie);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }

        return list;
    }

    // 데이터 처리(영화 리스트 추출)
    public List<MovieDto> getList(){

        // 최종 반환할 리스트
        List<MovieDto> list = null;

        // parse 수행
        String json = parse();

        try {
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject)parser.parse(json);
            JSONArray item = (JSONArray)obj.get("items");

            list = new ArrayList<MovieDto>();

            System.out.println(item);

            for(int i = 0; i<item.size(); i++){
                JSONObject tmp = (JSONObject)item.get(i);

                String title = (String)tmp.get("title");
                title = title.replaceAll("<b>","");
                title = title.replaceAll("</b>","");

                String subtitle = (String)tmp.get("subtitle");
                String image = (String)tmp.get("image");
                String pubTitle = (String)tmp.get("pubTitle");

                String director = (String)tmp.get("director");
                if(director.length() != 0){
                    director = director.substring(0, director.length()-1);
                }

                String actor = (String)tmp.get("actor");
                if(actor.length() != 0){
                    actor = actor.substring(0, actor.length()-1);
                }

                MovieDto movie = new MovieDto(title, subtitle, image, pubTitle, director, actor);

                if(movie != null)
                    list.add(movie);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }

    // http 통신
    private String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 에러 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    // 연결
    private HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    // 데이터 읽기
    private String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body, StandardCharsets.UTF_8);

        try (
                BufferedReader lineReader = new BufferedReader(streamReader)
        ) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }
}