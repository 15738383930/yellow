package com.yellow.common.util;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Supplier;

/**
 * @author jianghy
 * @Description: 日期工具类
 * @date 2020/4/27 15:58
 */
public class DateUtils {

    public static final String DATE_PATTERN = "yyyy-MM-dd";

    public static final String YYYY = "yyyy";

    public static final String YYYY_MM = "yyyy-MM";

    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static String format(Date date) {
        return format(date, YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 时间转换
     *
     * @param date
     * @param pattern
     */
    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    /**
     * 时间戳转日期
     *
     * @param timeStamp 时间戳
     */
    public static Date timeStamp(long timeStamp) {
        return new Date(timeStamp);
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getNowDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static Date getNewDate() {
        return new Date();
    }

    public static String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    public static String dateTime(final Date date) {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static Date dateTime(final String str, final String format) {
        try {
            return new SimpleDateFormat(format).parse(str);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    /**
     * 获取两个日期间的天数
     *
     * @param before
     * @param after
     * @return int
     * @author Hao.
     * @date 2022/1/6 9:46
     */
    public static int getDistanceOfTwoDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        double cc = (afterTime - beforeTime) / (double) (1000 * 60 * 60 * 24);
        return (int) Math.ceil(cc);
    }

    /**
     * 比较两个日期之间的大小
     *
     * @param d1
     * @param d2
     * @return 前者大于后者返回true 反之false
     */
    public static boolean compareDate(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);

        int result = c1.compareTo(c2);
        return result >= 0;
    }

    /**
     * 日期增加
     *
     * @param date 原日期
     * @param type  添加的类型：Calendar.DATE、 Calendar.MINUTE等
     * @param num  添加的数量
     * @return Date
     * @author Hao.
     * @date 2022/1/6 10:06
     */
    public static Date addDate(Date date, int type, int num) {
        if (null == date) {
            return date;
        }
        Calendar c = Calendar.getInstance();
        // 设置当前日期
        c.setTime(date);
        // 日期加1天
        c.add(type, num);
//     c.add(Calendar.DATE, -1); //日期减1天
        date = c.getTime();
        return date;
    }

    /**
     * 日期增加加月
     *
     * @param date 原日期
     * @param month  月数
     * @return Date
     * @author Hao.
     * @date 2022/1/6 10:06
     */
    public static Date addDateByMonth(Date date, int month) {
        return addDate(date, Calendar.MONTH, month);
    }

    /**
     * 日期增加加天
     *
     * @param date 原日期
     * @param day  天数（前多少天取负数）
     * @return Date
     * @author Hao.
     * @date 2022/1/6 10:06
     */
    public static Date addDateByDay(Date date, int day) {
        return addDate(date, Calendar.DATE, day);
    }

    /**
     * 日期增加小时
     *
     * @param date 原日期
     * @param hour  小时数
     * @return Date
     * @author Hao.
     * @date 2022/1/6 10:06
     */
    public static Date addDateByHour(Date date, int hour) {
        return addDate(date, Calendar.HOUR, hour);
    }

    /**
     * 日期增加分钟
     *
     * @param date 原日期
     * @param minute  分钟数
     * @return Date
     * @author Hao.
     * @date 2022/1/6 10:06
     */
    public static Date addDateByMinute(Date date, int minute) {
        return addDate(date, Calendar.MINUTE, minute);
    }

    /**
     * 日期增加秒
     *
     * @param date 原日期
     * @param second  秒数
     * @return Date
     * @author Hao.
     * @date 2022/1/6 10:06
     */
    public static Date addDateBySecond(Date date, int second) {
        return addDate(date, Calendar.SECOND, second);
    }

    /**
     * 日期加一天
     *
     * @param date 原日期
     * @return Date
     * @author Hao.
     * @date 2022/1/6 10:06
     */
    public static Date addDateOneDay(Date date) {
        return addDateByDay(date, 1);
    }

    /**
     * 获取当月开始时间戳
     *
     * @param timeStamp 毫秒级时间戳
     * @return
     */
    public static long getMonthStartTime(Long timeStamp) {
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        calendar.setTimeZone(TimeZone.getDefault());
        calendar.setTimeInMillis(timeStamp);
        calendar.add(Calendar.YEAR, 0);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取当月的结束时间戳
     *
     * @param timeStamp 毫秒级时间戳
     * @return
     */
    public static Long getMonthEndTime(Long timeStamp) {
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        calendar.setTimeZone(TimeZone.getDefault());
        calendar.setTimeInMillis(timeStamp);
        calendar.add(Calendar.YEAR, 0);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));// 获取当前月最后一天
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }

    public static void main(String[] args) {
        long time = dateTime("2021-12-06", YYYY_MM_DD).getTime();
        Long monthEndTime = getMonthEndTime(time);
        Long s = getMonthStartTime(time);
        int distanceOfTwoDate = getDistanceOfTwoDate(timeStamp(s), timeStamp(monthEndTime));
        System.out.println(format(timeStamp(s)));
        System.out.println(format(timeStamp(monthEndTime)));
        System.out.println(isWorkingDay());
    }

    /**
     * 【start】-【end】时间段的数据统计填充【data】
     *
     * @param oldList   时间段内查询出的数据
     * @param start     起始时间
     * @param end       结束时间
     * @param dateField 数据统计的时间字段名
     * @param data      填充数据的方法
     * @return 填充的数据
     * @author Hao.
     * @date 2022/1/6 10:21
     */
    public static <T> List<T> addDayForData(List<T> oldList, Date start, Date end, String dateField, Supplier<? extends T> data) throws Exception {
        Objects.requireNonNull(dateField);
        Objects.requireNonNull(data);
        Objects.requireNonNull(data.get());
        if (Objects.isNull(start) || Objects.isNull(end)) {
            return oldList;
        }
        long n = DateUtils.getDistanceOfTwoDate(start, end);
        LinkedList<T> newList = new LinkedList<>();
        int num = oldList.size();
        int temp = 0;
        for (int i = 0; i < n; i++) {
            String dateFieldToUpperCase = dateField.substring(0, 1).toUpperCase() + dateField.substring(1);
            if (temp < num) {
                Date currentDate = (Date) oldList.get(temp).getClass().getMethod("fail" + dateFieldToUpperCase).invoke(oldList.get(temp));
                // 开始日期小于当前记录日期 增加自定义数据
                if (start.compareTo(currentDate) < 0) {
                    newList.add(data.get());
                    newList.getLast().getClass().getMethod("set" + dateFieldToUpperCase, Date.class).invoke(newList.getLast(), start);
                }
                // 原数据加入
                if (start.compareTo(currentDate) == 0) {
                    newList.add(oldList.get(temp));
                    temp++;
                }
            } else if (start.compareTo(end) <= 0) {
                // 增加自定义数据
                newList.add(data.get());
                newList.getLast().getClass().getMethod("set" + dateFieldToUpperCase, Date.class).invoke(newList.getLast(), start);
            }
            //开始时间向前加一天
            start = addDateOneDay(start);
        }
        return newList;
    }

    /**
     * 【start】-【end】时间段的数据统计填充【data】
     *
     * @param oldList   时间段内查询出的数据
     * @param start     起始时间
     * @param end       结束时间
     * @param dateField 数据统计的时间字段名
     * @param format    日期格式化
     * @param data      填充数据的方法
     * @return 填充的数据
     * @author Hao.
     * @date 2022/1/6 10:21
     */
    public static <T> List<T> addDayForData(List<T> oldList, Date start, Date end, String dateField, String format, Supplier<? extends T> data) throws Exception {
        Objects.requireNonNull(dateField);
        Objects.requireNonNull(data);
        Objects.requireNonNull(format);
        Objects.requireNonNull(data.get());
        if (Objects.isNull(start) || Objects.isNull(end)) {
            return oldList;
        }
        long n = DateUtils.getDistanceOfTwoDate(start, end);
        LinkedList<T> newList = new LinkedList<>();
        int num = oldList.size();
        int temp = 0;
        for (int i = 0; i < n; i++) {
            String dateFieldToUpperCase = dateField.substring(0, 1).toUpperCase() + dateField.substring(1);
            if (temp < num) {
                Date currentDate = DateUtils.dateTime((String) oldList.get(temp).getClass().getMethod("fail" + dateFieldToUpperCase).invoke(oldList.get(temp)), format);
                // 开始日期小于当前记录日期 增加自定义数据
                if (start.compareTo(currentDate) < 0) {
                    newList.add(data.get());
                    newList.getLast().getClass().getMethod("set" + dateFieldToUpperCase, String.class).invoke(newList.getLast(), DateUtils.format(start, format));
                }
                // 原数据加入
                if (start.compareTo(currentDate) == 0) {
                    newList.add(oldList.get(temp));
                    temp++;
                }
            } else if (start.compareTo(end) <= 0) {
                // 增加自定义数据
                newList.add(data.get());
                newList.getLast().getClass().getMethod("set" + dateFieldToUpperCase, String.class).invoke(newList.getLast(), DateUtils.format(start, format));
            }
            //开始时间向前加一天
            start = addDateOneDay(start);
        }
        return newList;
    }

    /**
     * 今天是否工作日
     *
     * @return true-是 false-否
     * @author Hao.
     * @date 2022/4/29 11:05
     */
    public static boolean isWorkingDay() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int num = calendar.get(Calendar.DAY_OF_WEEK);

        // 是否工作日工作日
        final Boolean isWorkingDay = arrangeWorkDays().get(format(date, YYYY_MM_DD));

        return Objects.isNull(isWorkingDay) ? (num != Calendar.SATURDAY && num != Calendar.SUNDAY) : isWorkingDay;
    }

    /**
     * 2022国务院节假日安排
     * @author Hao.
     * @date 2022/4/29 11:25
     * @param
     * @return java.util.Map<java.lang.String,java.lang.Boolean>
     */
    public static Map<String, Boolean> arrangeWorkDays() {
        Map<String, Boolean> res = new HashMap<>();

        //国务院小程序放假安排
        //false 不工作
        //true 工作
        res.put("2022-01-01", false);
        res.put("2022-01-02", false);
        res.put("2022-01-03", false);
        res.put("2022-01-29", true);
        res.put("2022-01-30", true);
        res.put("2022-01-31", false);

        res.put("2022-02-01", false);
        res.put("2022-02-02", false);
        res.put("2022-02-03", false);
        res.put("2022-02-04", false);
        res.put("2022-02-05", false);
        res.put("2022-02-06", false);

        res.put("2022-04-02", true);
        res.put("2022-04-03", false);
        res.put("2022-04-04", false);
        res.put("2022-04-05", false);
        res.put("2022-04-24", true);
        res.put("2022-04-30", false);

        res.put("2022-05-01", false);
        res.put("2022-05-02", false);
        res.put("2022-05-03", false);
        res.put("2022-05-04", false);
        res.put("2022-05-07", true);


        res.put("2022-06-03", false);
        res.put("2022-06-04", false);
        res.put("2022-06-05", false);

        res.put("2022-09-10", false);
        res.put("2022-09-11", false);
        res.put("2022-09-12", false);

        res.put("2022-10-01", false);
        res.put("2022-10-02", false);
        res.put("2022-10-03", false);
        res.put("2022-10-04", false);
        res.put("2022-10-05", false);
        res.put("2022-10-06", false);
        res.put("2022-10-07", false);
        res.put("2022-10-08", true);
        res.put("2022-10-09", true);

        return res;
    }

}
