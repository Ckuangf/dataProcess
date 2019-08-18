package com.cloudwise.sdg.function;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by f1026 on 2019/6/26.
 */
public class CreateDate {
    public static String  randomDate(String beginDate, String endDate, String dateFormate) {
        try {
            SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
            Date start = format.parse(beginDate);// 构造开始日期
            Date end = format.parse(endDate);// 构造结束日期
            // getTime()表示返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。
            if (start.getTime() >= end.getTime()) {
                return null;
            }
            long date = random(start.getTime(), end.getTime());
            SimpleDateFormat dtFormat = new SimpleDateFormat(dateFormate);
            Date dt=new Date(date);
            String dateStr;
//            switch (dateType){
//    		case "1":
//    			//System.out.println("1");
//    			dtFormat = new SimpleDateFormat("yyyy-MM-dd");
//    			break;
//    		case "2":
//    			dtFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    			//System.out.println("2");
//    			break;
//    		default:
//    			dtFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    			}

            dateStr = dtFormat.format(dt);
            return dateStr;
            //return new Date(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    private static long random(long begin, long end) {
        long rtn = begin + (long) (Math.random() * (end - begin));
        // 如果返回的是开始时间和结束时间，则递归调用本函数查找随机值
        if (rtn == begin || rtn == end) {
            return random(begin, end);
        }
        return rtn;
    }
}
