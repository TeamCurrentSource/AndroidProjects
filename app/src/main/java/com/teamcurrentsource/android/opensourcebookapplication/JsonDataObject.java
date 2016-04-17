package com.teamcurrentsource.android.opensourcebookapplication;

import java.util.Arrays;


public class JsonDataObject {
    public Children children[];
    public UrlObject download_urls;
    public String domain_slug;
    public String node_slug;
    public String relative_url;
    public String kind;

    @Override
    public String toString() {
        return "JsonDataObject{" +
                "children=" + Arrays.toString(children) +
                ", download_urls=" + download_urls +
                ", domain_slug='" + domain_slug + '\'' +
                ", node_slug='" + node_slug + '\'' +
                ", relative_url='" + relative_url + '\'' +
                ", kind='" + kind + '\'' +
                '}';
    }
    public class UrlObject {
        public String png;
    }
    public class Children {
        public String domain_slug;
        public String relative_url;
        public String description;
        public String title;
        public String translated_title;
       // public String youtube_id;
        public String node_slug;
        public String kind;

        @Override
        public String toString() {
            return "Children{" +
                    "domain_slug='" + domain_slug + '\'' +
                    ", relative_url='" + relative_url + '\'' +
                    ", description='" + description + '\'' +
                    ", title='" + title + '\'' +
                    ", translated_title='" + translated_title + '\'' +
                    ", node_slug='" + node_slug + '\'' +
                    ", kind='" + kind + '\'' +
                    '}';
        }

    }
}