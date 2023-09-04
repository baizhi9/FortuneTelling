package com.baizhi.utils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class FateUtils {
    private static final String API_URL = "https://api.jisuapi.com/huangli/date";
    private static final String APP_KEY = "72881dfb69aa8b91";

    public static String getHuangLi(String year, String month, String day) {
        try {
            URL url = new URL(API_URL + "?appkey=" + APP_KEY + "&year=" + year + "&month=" + month + "&day=" + day);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

