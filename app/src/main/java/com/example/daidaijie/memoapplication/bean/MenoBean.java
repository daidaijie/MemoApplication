package com.example.daidaijie.memoapplication.bean;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by daidaijie on 2016/8/4.
 */
public class MenoBean implements Serializable {

    private String title;

    private String content;

    private long createTime;

    private long changeTime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long time) {
        this.createTime = time;
    }

    public void setChangeTime(long changeTime) {
        this.changeTime = changeTime;
    }

    public long getChangeTime() {
        return changeTime;
    }

    public String getCreateTimeString() {
        return time2String(createTime);
    }

    public String getChangeTimeString() {

        return time2String(changeTime);
    }

    private String time2String(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return String.format(
                "%4d-%02d-%02d %02d:%02d:%02d",
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH) + 1,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND)
        );
    }
}
