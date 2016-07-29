package com.lskj.gx.rrhr.main.bean;

import java.util.List;

public class SubjectsBean {
    /**
     * max : 10
     * average : 9.4
     * stars : 50
     * min : 0
     */

    private RatingBean rating;
    private String title;
    private int collect_count;
    private String original_title;
    private String subtype;
    private String year;
    /**
     * small : https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p511118051.jpg
     * large : https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p511118051.jpg
     * medium : https://img3.doubanio.com/view/movie_poster_cover/spst/public/p511118051.jpg
     */

    private ImagesBean images;
    private String alt;
    private String id;
    private List<String> genres;
    /**
     * alt : https://movie.douban.com/celebrity/1025182/
     * avatars : {"small":"https://img3.doubanio.com/img/celebrity/small/8833.jpg","large":"https://img3.doubanio.com/img/celebrity/large/8833.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/8833.jpg"}
     * name : 让·雷诺
     * id : 1025182
     */

    private List<CastsBean> casts;
    /**
     * alt : https://movie.douban.com/celebrity/1031876/
     * avatars : {"small":"https://img3.doubanio.com/img/celebrity/small/33301.jpg","large":"https://img3.doubanio.com/img/celebrity/large/33301.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/33301.jpg"}
     * name : 吕克·贝松
     * id : 1031876
     */

    private List<DirectorsBean> directors;

    public RatingBean getRating() {
        return rating;
    }

    public void setRating(RatingBean rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCollect_count() {
        return collect_count;
    }

    public void setCollect_count(int collect_count) {
        this.collect_count = collect_count;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public ImagesBean getImages() {
        return images;
    }

    public void setImages(ImagesBean images) {
        this.images = images;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<CastsBean> getCasts() {
        return casts;
    }

    public void setCasts(List<CastsBean> casts) {
        this.casts = casts;
    }

    public List<DirectorsBean> getDirectors() {
        return directors;
    }

    public void setDirectors(List<DirectorsBean> directors) {
        this.directors = directors;
    }

    @Override
    public String toString() {
        return "SubjectsBean{" +
                "rating=" + rating.toString() +
                ", title='" + title + '\'' +
                ", collect_count=" + collect_count +
                ", original_title='" + original_title + '\'' +
                ", subtype='" + subtype.toString() + '\'' +
                ", year='" + year + '\'' +
                ", images=" + images.toString() +
                ", alt='" + alt + '\'' +
                ", id='" + id + '\'' +
                ", genres=" + genres.toString() +
                ", casts=" + casts +
                ", directors=" + directors.toString() +
                '}';
    }
}