package com.asu.cloudclan.util;

/**
 * Created by rubinder on 10/31/16.
 */
public class SizeUtil {

    public static String calculateSize(Integer size) {
        size = size==null ? 0 : size;
        size = size/1024;
        if(size>=1024) {
            size = size/1024;
            if(size>=1024) {
                size = size/1024;
                return size + " GB";
            } else {
                return size + " MB";
            }
        } else {
            return size + " KB";
        }
    }

    public static String calculateTimeSize(Double time) {
        time = time==null ? 0 : time;
        time = time/1024;

        if(time>=1000) {
            time = time/1000;
            if(time>=60) {
                time = time/60;
                if(time>=60) {
                    time = time/60;
                    if(time>=24) {
                        time = time/24;
                        return time + " KB Days";
                    } else {
                        return time + " KB Hours";
                    }
                } else {
                    return time + " KB Mintues";
                }
            } else {
                return time + " KB Seconds";
            }
        } else {
            return time + " KB Milliseconds";
        }
    }
}
