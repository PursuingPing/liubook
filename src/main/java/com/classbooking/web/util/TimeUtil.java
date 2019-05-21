package com.classbooking.web.util;

import org.apache.log4j.Logger;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;

public class TimeUtil {

    private static Logger  log = Logger.getLogger(TimeUtil.class);

    public static List<String[]> getTime(String[] ranges,String[] units,String[] times){

        //先计算多少天
        //拿到每天的datatime
        //遍历周,拿到符合的datatime
        //拼接具体开始时间、结束时间
        List<String[]> list = new LinkedList<>();
        if(ranges.length==2 && times.length==2){
            LocalDate startDate  = LocalDate.parse(ranges[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate endDate = LocalDate.parse(ranges[1],DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDateTime ss = startDate.atTime(0,0,0);
            LocalDateTime ee = endDate.atTime(0,0,0);
            Duration duration = Duration.between(ss,ee);
            long days = duration.toDays();
            for(int i=0;i<days;i++){
                LocalDate tempDate = startDate.plusDays(i);
                for(int j=0; j<units.length;j++){
                    if(units[j].equals(String.valueOf(tempDate.getDayOfWeek()))){
                        String[] result = new String[times.length];
                        for(int k=0;k<times.length;k++){
                            LocalTime localDateTimeS = LocalTime.parse(times[k],DateTimeFormatter.ofPattern("HH:mm:ss"));
                            LocalDateTime s = tempDate.atTime(localDateTimeS);
                            String time = s.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                            log.info("周期时间-----：" + time);
                            result[k] = time;
                        }
                        list.add(result);
                    }

                }
            }
        }
        return list;
    }

    public static int getHour(String time){
        LocalDateTime localDateTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalTime localTime = localDateTime.toLocalTime();
        return localTime.getHour();
    }

    public static int getHourByString(String time){
        LocalTime localTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
        return localTime.getHour();
    }

    public static long getMillonSecond(String time){
        LocalDateTime localDateTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        long timestamp = localDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        return timestamp;
    }

}
