package com.teamcurrentsource.android.opensourcebookapplication;


public class JsonVideoObject {
    public Urls download_urls;
    public String youtube_id;

    @Override
    public String toString() {
        return "JsonVideoObject{" +
                "download_urls=" + download_urls +
                '}';
    }

    public class Urls {
        public String png;

        @Override
        public String toString() {
            return "Urls{" +
                    "png='" + png + '\'' +
                    '}';
        }
    }

}
