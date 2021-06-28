package com.oidc.movie.parse;

import com.oidc.movie.dto.MovieDto;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParseMovie {

    String clientId= "gqQSDSXctJYArTsqvLae";
    String clientSecret = "aRwkVaPsfo";
    String query = null;

    // 생성자
    public ParseMovie(String query){
        this.query = query;
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

    // 데이터 처리
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
                director = director.substring(0, director.length()-1);
                String actor = (String)tmp.get("actor");
                actor = actor.substring(0, actor.length()-1);

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
