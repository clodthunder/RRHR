package com.lskj.gx.rrhr.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Home on 16/7/15.
 * usage:
 * new TimeUtil(data.getTime()).toString(new TimeUtil.RecentDateFormat())
 *
 */
public class TimeUtil {
    public static final int SECOND = 60;
    public static final int HOUR = 3600;
    public static final int DAY = 86400;
//    public static final int WEEK = 604800;

    Calendar currentTime;

    public TimeUtil(){
        currentTime=new GregorianCalendar();
        currentTime.setTime(new Date());
    }
    public TimeUtil(long timestamp){
        currentTime=new GregorianCalendar();
        currentTime.setTime(new Date(timestamp*1000));
    }
    public TimeUtil(int year, int month, int day){
        currentTime=new GregorianCalendar(year,month,day);
    }

    public int getDay(){
        return currentTime.get(Calendar.DATE);
    }
    public int getMonth(){
        return currentTime.get(Calendar.MONTH)+1;
    }
    public int getYear(){
        return currentTime.get(Calendar.YEAR);
    }
    public long getTimestamp(){
        return currentTime.getTime().getTime()/1000;
    }

    /**
     * 格式化输出日期
     * 年:y		月:M		日:d		时:h(12制)/H(24值)	分:m		秒:s		毫秒:S
     * @param formatString
     */
    public String toString(String formatString){
        SimpleDateFormat format = new SimpleDateFormat(formatString);
        String date = format.format(currentTime.getTime());
        return date;
    }


    /**
     * 格式化解析日期文本
     * 年:y		月:M		日:d		时:h(12制)/H(24值)	分:m		秒:s		毫秒:S
     * @param formatString
     */
    public TimeUtil parse(String formatString,String content){
        SimpleDateFormat format = new SimpleDateFormat(formatString);
        try {
            currentTime.setTime(format.parse(content));
            return this;
        } catch (ParseException e) {
            return null;
        }
    }

    public String toString(DateFormat format){
        long delta = (System.currentTimeMillis() - currentTime.getTime().getTime())/1000;
        return format.format(this,delta);
    }

    public interface DateFormat{
        String format(TimeUtil date, long delta);
    }

    public static class RecentDateFormat implements DateFormat{
        private String lastFormat;

        public RecentDateFormat() {
            this("MM-dd");
        }

        public RecentDateFormat(String lastFormat) {
            this.lastFormat = lastFormat;
        }

        @Override
        public String format(TimeUtil date, long delta) {
            if (delta>0){
                if (delta / TimeUtil.SECOND < 1){
                    return delta +"秒前";
                }else if (delta / TimeUtil.HOUR < 1){
                    return delta / TimeUtil.SECOND+"分钟前";
                }else if (delta / TimeUtil.DAY < 2 && new TimeUtil().getDay() == date.getDay()){
                    return delta / TimeUtil.HOUR+"小时前";
                }else if (delta / TimeUtil.DAY < 3 && new TimeUtil().getDay() == new TimeUtil(date.getTimestamp()+ TimeUtil.DAY).getDay()){
                    return "昨天"+date.toString("HH:mm");
                }else if (delta / TimeUtil.DAY < 4 && new TimeUtil().getDay() == new TimeUtil(date.getTimestamp()+ TimeUtil.DAY*2).getDay()){
                    return "前天"+date.toString("HH:mm");
                }else{
                    return date.toString(lastFormat);
                }
            }else{
                delta = -delta;
                if (delta / TimeUtil.SECOND < 1){
                    return delta +"秒后";
                }else if (delta / TimeUtil.HOUR < 1){
                    return delta / TimeUtil.SECOND+"分钟后";
                }else if (delta / TimeUtil.DAY > -2 && new TimeUtil().getDay() == date.getDay()){
                    return delta / TimeUtil.HOUR+"小时后";
                }else if (delta / TimeUtil.DAY > -3 && new TimeUtil().getDay() == new TimeUtil(date.getTimestamp()- TimeUtil.DAY).getDay()){
                    return "明天"+date.toString("HH:mm");
                }else if (delta / TimeUtil.DAY > -4 && new TimeUtil().getDay() == new TimeUtil(date.getTimestamp()- TimeUtil.DAY*2).getDay()){
                    return "后天"+date.toString("HH:mm");
                }else{
                    return date.toString(lastFormat);
                }
            }
        }
    }

}
