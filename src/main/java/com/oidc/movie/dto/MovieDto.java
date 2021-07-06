package com.oidc.movie.dto;

public class MovieDto {

    private String title;
    private String image;
    private String subtitle;
    private String pubTitle;
    private String director;
    private String actor;
    private String summary;
    private String open;
    private String grade;
    private String genre;
    private String country;
    private String runningtime;

    public MovieDto(){}

    // 리스트 추출
    public MovieDto(String title, String subtitle, String image, String pubTitle, String director, String actor){
        this.title = title;
        this.subtitle = subtitle;
        this.image = image;
        this.pubTitle = pubTitle;
        this.director = director;
        this.actor = actor;
    }

    // 상세정보 추출
    public MovieDto(String title,String subtitle, String image, String pubTitle, String director, String actor, String open, String grade, String genre, String country, String runningtime, String summary){
        this.title = title;
        this.subtitle = subtitle;
        this.image = image;
        this.pubTitle = pubTitle;
        this.director = director;
        this.actor = actor;
        this.open = open;
        this.grade = grade;
        this.genre = genre;
        this.country = country;
        this.runningtime = runningtime;
        this.summary = summary;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public void setSubtitle(String subtitle){
        this.subtitle = subtitle;
    }

    public String getSubtitle(){
        return subtitle;
    }

    public void setImage(String image){
        this.image = image;
    }

    public String getImage(){
        return image;
    }

    public void setPubTitle(String pubTitle){
        this.pubTitle = pubTitle;
    }

    public String getPubTitle(){
        return pubTitle;
    }

    public void setDirector(String director){
        this.director = director;
    }

    public String getDirector(){
        return director;
    }

    public void setActor(String actor){
        this.actor = actor;
    }

    public String getActor(){
        return actor;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public void setRunningtime(String runningtime) {
        this.runningtime = runningtime;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCountry() {
        return country;
    }

    public String getGenre() {
        return genre;
    }

    public String getGrade() {
        return grade;
    }

    public String getOpen() {
        return open;
    }

    public String getRunningtime() {
        return runningtime;
    }

    public String getSummary() {
        return summary;
    }
}
