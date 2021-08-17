package com.oidc.movie.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oidc.movie.dto.MovieDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ApiScheduler {

    String serverIp = "http://118.67.132.152:8083";

    @Scheduled(cron = "0 * * * * *")
    public void Analaysis(){
        System.out.println("-------------------------------");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String currentDate = simpleDateFormat.format(now);
        System.out.println(currentDate);
        System.out.println("-------------------------------");
        System.out.println("심박수 MIN, MAX 추출");
        System.out.println(">> "+mdata());
        System.out.println("데이터 가져오는중");
        System.out.println(">> "+calaver());
        System.out.println("클러스터링 진행중");
        System.out.println(">> "+cluster());
        System.out.println("점수 산출");
        System.out.println(">> "+rating());
        System.out.println("-------------------------------\n");
    }

    public String mdata(){

        String linkUrl = serverIp + "/valuerate/mda";
        String resultString = "";

        try{
            Document document = Jsoup.connect(linkUrl).timeout(3600000).get();
            Thread.sleep(1000);
            resultString = document.body().text();

        }catch(Exception e){
            e.printStackTrace();
        }
        return resultString;
    }

    public String calaver(){

        String linkUrl = serverIp + "/valuerate/cal";
        String resultString = "";

        try{
            Document document = Jsoup.connect(linkUrl).timeout(3600000).get();
            Thread.sleep(1000);
            resultString = document.body().text();

        }catch(Exception e){
            e.printStackTrace();
        }
        return resultString;
    }

    public String cluster(){

        String linkUrl = serverIp + "/valuerate/clu";
        String resultString = "";

        try{
            Document document = Jsoup.connect(linkUrl).timeout(3600000).get();
            Thread.sleep(1000);
            resultString = document.body().text();

        }catch(Exception e){
            e.printStackTrace();
        }
        return resultString;
    }

    public String rating(){

        String linkUrl = serverIp + "/valuerate/rat";
        String resultString = "";

        try{
            Document document = Jsoup.connect(linkUrl).timeout(3600000).get();
            Thread.sleep(1000);
            resultString = document.body().text();

        }catch(Exception e){
            e.printStackTrace();
        }
        return resultString;
    }
}
