package com.teamcurrentsource.android.opensourcebookapplication;

import java.util.Arrays;

/**
 * Created by Antti on 17.3.2016.
 */

public class JsonDataObject {
    public Children children[];
    public UrlObject download_urls;
    public String domain_slug;


    public class UrlObject {
        public String png;
    }
    public class Children {
        public String relative_url;
        public String description;
        public String title;
        public String translated_title;
        public String youtube_id;
        public String node_slug;
        public String kind;
        public SubChildren children[];

        @Override
        public String toString() {
            return "Children{" +
                    "relative_url='" + relative_url + '\'' +
                    ", description='" + description + '\'' +
                    ", title='" + title + '\'' +
                    ", translated_title='" + translated_title + '\'' +
                    ", youtube_id='" + youtube_id + '\'' +
                    ", node_slug='" + node_slug + '\'' +
                    ", kind='" + kind + '\'' +
                    ", children=" + Arrays.toString(children) +
                    '}';
        }

        public class SubChildren{
            public String title;
            public String youtube_id;
            public String description;
            public String node_slug;

            @Override
            public String toString() {
                return "SubChildren{" +
                        "title='" + title + '\'' +
                        ", youtube_id='" + youtube_id + '\'' +
                        ", description='" + description + '\'' +
                        ", node_slug='" + node_slug + '\'' +
                        '}';
            }
        }
    }
}