package com.jd.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author: cuiguoqiang
 * @CreateTime: 2020-11-02 17:53
 * @Description: ${Description}
 */
public class DateUtils {
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String SIMPLE_FORMAT = "yyyy-MM-dd";

    /**
     * 获取当前系统时间yyyyMMddHHmmss
     *
     * @return
     */
    public static long getCurTime() {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat(YYYYMMDDHHMMSS);
        return Long.valueOf(dateFormat.format(date));
    }

    /**
     * 根据传入的时间戳获取日期
     */
    public static String getCurrentDate(Long date){
        SimpleDateFormat format= new SimpleDateFormat(SIMPLE_FORMAT);
        String currentDate = format.format(new Date(date));
        return currentDate;
    }

    /**
     * 获取当前日期
     * @return
     */
    public static String getCurrentDate(){
        SimpleDateFormat format = new SimpleDateFormat(SIMPLE_FORMAT);
        String currentDate = format.format(new Date());
        return currentDate;
    }

    /**
     * 获取前一天日期
     * @return
     */
    public static String getPreDate(){
        Calendar calendar = Calendar.getInstance();//得到一个Calendar的实例
        calendar.setTime(new Date()); //设置时间为当前时间
        calendar.add(Calendar.DATE, -1);
        DateFormat format = new SimpleDateFormat(SIMPLE_FORMAT);
        return format.format(calendar.getTime());
    }

    /**
     * 字符串转日期
     *
     * @param date
     * @param format
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String date, String format) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(date);
    }

    /**
     * 日期格式化
     *
     * @param date
     * @param format
     * @return
     */
    public static String formatDate(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * 获取昨天的0点
     *
     * @return
     */
    public static Date getYestodayZero() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取不包括时分秒的日期部分
     *
     * @return
     */
    public static Date getDateByDateTime(Date datetime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datetime);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 日期加减
     *
     * @return
     */
    public static Date dateAdd(Date date, int diff) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, diff);
        return calendar.getTime();
    }

    /**
     * 日期加、减
     * @param date
     * @param diff + 向后， - 向前
     * @return
     */
    public static String dateBeforeOrAfter(String date,int diff) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parseDate = dateFormat.parse(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parseDate);
        calendar.add(Calendar.DAY_OF_MONTH, diff);
        return dateFormat.format(calendar.getTime());
    }



    /**
     * 日期加减
     *
     * @param date yyyyMMdd
     * @return
     */
    public static Date dateAdd(Integer date, int diff) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(date / 10000, (date / 100) % 100 - 1, date % 100);
        Date d = dateAdd(calendar.getTime(), diff);
        return getDateByDateTime(d);
    }

    public static String dayMove(String date, int len) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(date));
            cal.add(Calendar.DATE, len);
            return sdf.format(cal.getTime());
        } catch (Exception e) {
            return date;
        }
    }

    public static String monthMove(String date, int len) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(date));
            cal.add(Calendar.MONTH, len);
            return sdf.format(cal.getTime());
        } catch (Exception e) {
            return date;
        }
    }



    /**
     * 日期加减
     *
     * @return
     */
    public static Date firstDateOfMon(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, 1);
        return getDateByDateTime(calendar.getTime());
    }

    /**
     * 年第一天
     *
     * @return
     */
    public static Date firstDateOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.MONTH, 0);
        return getDateByDateTime(calendar.getTime());
    }

    /**
     * 返回日期列表，如查询2020-11-10 10 到 2020-11-13 10 则返回["2020-11-10","2020-11-11","2020-11-12","2020-11-13"]
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<String> generalDateList(Date startTime, Date endTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        List<String> dateList = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        while (calendar.getTimeInMillis() < endTime.getTime()) {
            dateList.add(dateFormat.format(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return dateList;
    }

    /**
     * 把时间段按周划分成一段段
     * @throws ParseException
     */
    public static List<String> generalWeekList(Date startTime, Date endTime) throws ParseException {
        List<String> weekList = new ArrayList<>();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdw = new SimpleDateFormat("E");
        String begins = formatDate(startTime,"yyyy-MM-dd");
        String ends = formatDate(endTime,"yyyy-MM-dd");
        String begin_date =begins;
        String end_date =ends;
        String begin_date_fm =  begins;
        String end_date_fm = ends;
        Date b = null;
        Date e = null;
        try {
            b = sd.parse(begin_date_fm);
            e = sd.parse(end_date_fm);
        } catch (ParseException ee) {
            ee.printStackTrace();
        }
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(b);
        Date time = b;
        String year = begin_date_fm.split("-")[0];
        String mon = Integer.parseInt(begin_date_fm.split("-")[1])<10?begin_date_fm.split("-")[1]:begin_date_fm.split("-")[1];
        String day = Integer.parseInt(begin_date_fm.split("-")[2])<10?begin_date_fm.split("-")[2]:begin_date_fm.split("-")[2];
        String timeb = year+mon+day;
        String timee = null;
        if(begin_date==end_date){
            //System.out.println(begin_date+"~"+end_date);
            weekList.add(begin_date+"~"+end_date);
        }else{
            while(time.getTime()<=e.getTime()){
                rightNow.add(Calendar.DAY_OF_YEAR,1);
                time = sd.parse(sd.format(rightNow.getTime()));
                if(time.getTime()>e.getTime()){break;}
                String timew = sdw.format(time);
                if(("星期一").equals(timew)){
                    timeb = (sd.format(time)).replaceAll("-", "");
                }
                if(("星期日").equals(timew) || ("星期七").equals(timew) || time.getTime() == e.getTime()){
                    timee = (sd.format(time)).replaceAll("-", "");
                    String begindate=fomaToDatas(timeb);
                    String enddate=fomaToDatas(timee);
                    //System.out.println(begindate+"~"+enddate);
                    weekList.add(begindate+"~"+enddate);
                }
            }

        }
        return weekList;
    }

    public static String fomaToDatas(String data){
        DateFormat fmt=new SimpleDateFormat("yyyyMMdd");
        try {
            Date parse=fmt.parse(data);
            DateFormat fmt2=new SimpleDateFormat("yyyy-MM-dd");
            return fmt2.format(parse);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 返回环比日期
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static Date[] getLinkDate(Date startTime, Date endTime) {
        long diff = endTime.getTime() - startTime.getTime();
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startTime);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endTime);
        startCal.add(Calendar.DAY_OF_MONTH, -1);

        startCal.set(Calendar.HOUR_OF_DAY, endCal.get(Calendar.HOUR_OF_DAY));
        startCal.set(Calendar.MINUTE, endCal.get(Calendar.MINUTE));
        startCal.set(Calendar.SECOND, endCal.get(Calendar.SECOND));
        Date endDate = startCal.getTime();
        long startTimeStamp = endDate.getTime() - diff;
        Date startDate = new Date(startTimeStamp);
        return new Date[]{startDate, endDate};

    }

    /**
     * 判断是否是同一天
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean isSameDay(Date startTime, Date endTime) {
        if ((endTime.getTime() - startTime.getTime()) < 24 * 60 * 60 * 1000
                && startTime.getDate() == endTime.getDate()) {
            return true;
        }
        return false;
    }

    /**
     * 获取间隔天数
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static int dayRange(Date startTime, Date endTime) {
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startTime);
        startCal.set(Calendar.HOUR_OF_DAY, 0);
        startCal.set(Calendar.MINUTE, 0);
        startCal.set(Calendar.SECOND, 0);

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endTime);
        endCal.set(Calendar.HOUR_OF_DAY, 0);
        endCal.set(Calendar.MINUTE, 0);
        endCal.set(Calendar.SECOND, 0);
        int day = (int) ((endCal.getTimeInMillis() - startCal.getTimeInMillis()) / (24 * 60 * 60 * 1000));
        return endCal.getTimeInMillis() == endTime.getTime() ? day : (day + 1);
    }

    /**
     * 获得小时列表
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<String> generalHourList(Date startTime, Date endTime) {
        List<String> hourList = new ArrayList<>();
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startTime);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endTime);
        endCal.set(Calendar.MINUTE, 0);
        endCal.set(Calendar.SECOND, 0);
        boolean includeLastHour = endTime.getTime() != endCal.getTimeInMillis();
        int startHour = startCal.get(Calendar.HOUR_OF_DAY);
        int endHour = endCal.get(Calendar.HOUR_OF_DAY);
        if (includeLastHour) {
            endHour += 1;
        }
        for (int i = startHour; i < endHour; i++) {
            if (i < 10) {
                hourList.add("0" + i);
            } else {
                hourList.add(i + "");
            }
        }
        return hourList;
    }

    /**
     * 获取月份列表
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<String> generalMonthList(Date startTime, Date endTime) {
        List<String> monthList = new ArrayList<>();
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startTime);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endTime);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        while (startCal.getTimeInMillis() < endCal.getTimeInMillis()) {
            monthList.add(dateFormat.format(startCal.getTime()));
            startCal.add(Calendar.MONTH, 1);
        }
        if (startCal.get(Calendar.MONTH) == endCal.get(Calendar.MONTH) &&
                (!(endCal.get(Calendar.DAY_OF_MONTH) == 1 && endCal.get(Calendar.HOUR_OF_DAY) == 0
                        && endCal.get(Calendar.MINUTE) == 0
                        && endCal.get(Calendar.SECOND) == 0))) {
            monthList.add(dateFormat.format(startCal.getTime()));
        }
        return monthList;
    }


    /**
     * 获得今日0点
     *
     * @return
     */
    public static Date getTodayZero() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获得某日期的0点
     *
     * @param date
     * @return
     */
    public static Date getDayZero(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    private static final ZoneId ZONE_ID = ZoneId.systemDefault();

    /**
     * 将Date类型转为需要的字符串格式
     *
     * @param date              Date
     * @param dateTimeFormatter DateTimeFormatter
     * @return 字符串格式的日期
     */
    public static String convertDateToStr(Date date, DateTimeFormatter dateTimeFormatter) {
        Instant instant = date.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZONE_ID);
        return dateTimeFormatter.format(localDateTime);
    }

    /**
     * 将字符串转为LocalDateTime
     *
     * @param timeStr           字符串格式的日期
     * @param dateTimeFormatter DateTimeFormatter
     * @return LocalDateTime
     */
    public static LocalDateTime convertStrToLocalDateTime(String timeStr, DateTimeFormatter dateTimeFormatter) {
        return LocalDateTime.parse(timeStr, dateTimeFormatter);
    }


    /**
     * LocalDate
     *
     * @param timeStr           字符串格式的日期
     * @param dateTimeFormatter DateTimeFormatter
     * @return LocalDate
     */
    public static LocalDate convertStrToLocalDate(String timeStr, DateTimeFormatter dateTimeFormatter) {
        return LocalDate.parse(timeStr, dateTimeFormatter);
    }


    /**
     * 将Long类型转为Date类型
     *
     * @param timeStamp 时间戳
     * @return Date
     */
    public static Date convertTimeStampToDate(Long timeStamp) {
        if (timeStamp != null) {
            return Date.from(Instant.ofEpochMilli(timeStamp));
        }
        return null;
    }

    public static Integer getIntFormmatDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR) * 10000 + (cal.get(Calendar.MONTH) + 1) * 100 + cal.get(Calendar.DATE);
    }

    public static long remainedTime(boolean isMilliSecondUnit) {
        Date curr = new Date();
        long tommrow = getDateByDateTime(dateAdd(curr, 1)).getTime();
        long remained = tommrow - curr.getTime();
        if (isMilliSecondUnit) {
            return remained;
        }

        long m = remained % 60L;
//        return remained/60L+m;
        return 5 * 60L;
    }

    /**
     * 日期转星期
     *
     * @param datetime
     * @return
     */
    public static String dateToWeek(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 获取今天是本年中的第几周
     *
     * @param date
     * @return
     */
    public  static int  getWeek(String date) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            cal.setTime(format.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int week = cal.get(Calendar.WEEK_OF_YEAR)-1;
        return week;
    }

    /**
     * 获取每个月的最后一天
     * @param year
     * @param month
     * @return
     */
    public static String getLastDayOfMonth(int year,int month)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        DateFormat format = new SimpleDateFormat(SIMPLE_FORMAT);
        return format.format(calendar.getTime());
    }

    /**
     * 获取每个月的最后一天
     * @param date 格式为 yyyy-MM
     * @return
     */
    public static String getLastDayOfMonth(String date)
    {
        String[] splitDate = date.split("-");
        if (splitDate.length<2){
            return date;
        }
        int year = Integer.parseInt(splitDate[0]);
        String tempMonth = splitDate[1];
        if (tempMonth.startsWith("0")){
            tempMonth = tempMonth.substring(1, 2);
        }
        int month = Integer.parseInt(tempMonth);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        DateFormat format = new SimpleDateFormat(SIMPLE_FORMAT);
        return format.format(calendar.getTime());
    }

    /**
     * 将字符串日期只保留年与日
     */
    public static String dateSubString(String StringDate){
        SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(StringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return format.format(date);
    }
}
