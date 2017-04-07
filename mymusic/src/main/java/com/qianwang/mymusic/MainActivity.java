package com.qianwang.mymusic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.qianwang.mymusic.bean.LineInfo;
import com.qianwang.mymusic.bean.MusicInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private TextView tv_show;
    private MusicInfo musiInfo;
    private LineInfo lineInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_show = (TextView) findViewById(R.id.tv_show);

        try {
            InputStream inputStream = getAssets().open("music1.lrc");

            String content = getSongContent(inputStream, "GBK");

            tv_show.setText(content);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getSongContent(InputStream input, String chartset) throws IOException {


        musiInfo = new MusicInfo();
        musiInfo.song_line = new ArrayList<LineInfo>();
        InputStreamReader re = new InputStreamReader(input, chartset);
        BufferedReader reader = new BufferedReader(re);

        String line = "";
        while ((line = reader.readLine()) != null) {

            analyzeLyric(line);


        }

        reader.close();
        re.close();
        input.close();

        StringBuilder sb = new StringBuilder();


        if (musiInfo != null && musiInfo.song_line.size() != 0) {

            int size = musiInfo.song_line.size();

            for (int i = 0; i < size; i++) {

                sb.append(musiInfo.getSong_line().get(i).getSong_content());
                sb.append("\r\n");

            }

        }


        return sb.toString();
    }

    private void analyzeLyric(String line) {
        int index = line.lastIndexOf("]");

        if (line != null && line.startsWith("[ti:")) {

            musiInfo.setSong_title(line.substring(4, index));

        } else if (line != null && line.startsWith("[ar:")) {
            musiInfo.setSong_artist(line.substring(4, index));

        } else if (line != null && line.startsWith("[al:")) {
            musiInfo.setSong_album(line.substring(4, index));

        } else if (line != null && line.startsWith("[offset:")) {
            musiInfo.setSong_offset(Long.parseLong(line.substring(8, index)));
        } else if (line != null && line.startsWith("[by:")) {
            return;
        } else if (line != null && index == 9 && line.trim().length() > 10) {

            lineInfo = new LineInfo();
            lineInfo.setSong_content(line.substring(10, line.length()));
            lineInfo.setSong_time(measureStartTimeMillis(line.substring(0, 10)));
            musiInfo.song_line.add(lineInfo);
        }
    }

    /**
     * 从字符串中获得时间值
     */
    private long measureStartTimeMillis(String str) {
        long minute = Long.parseLong(str.substring(1, 3));
        long second = Long.parseLong(str.substring(4, 6));
        long millisecond = Long.parseLong(str.substring(7, 9));
        return millisecond + second * 1000 + minute * 60 * 1000;
    }
}
