package com.qianwang.mymusic.bean;

/**
 * Created by sky on 2017/4/4.
 */

public class LineInfo
{
    private String song_content;
    private long song_time;

    public LineInfo(String song_content, long song_time) {
        this.song_content = song_content;
        this.song_time = song_time;
    }

    public LineInfo() {

    }

    public String getSong_content() {
        return song_content;
    }

    public void setSong_content(String song_content) {
        this.song_content = song_content;
    }

    public long getSong_time() {
        return song_time;
    }

    public void setSong_time(long song_time) {
        this.song_time = song_time;
    }

    @Override
    public String toString() {
        return "LineInfo{" +
                "song_content='" + song_content + '\'' +
                ", song_time=" + song_time +
                '}';
    }
}
