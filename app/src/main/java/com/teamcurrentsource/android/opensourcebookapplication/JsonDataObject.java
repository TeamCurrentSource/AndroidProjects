package com.teamcurrentsource.android.opensourcebookapplication;

import java.util.Arrays;

/**
 * Created by Antti on 17.3.2016.
 */

public class JsonDataObject {
    public Children children[];

    @Override
    public String toString() {
        return "JsonDataObject{" +
                "children=" + Arrays.toString(children) +
                '}';
    }

    public class Children {
        public String relative_url;
        public String description;
        public String title;
        public String translated_title;
        public String youtube_id;
        public SubChildren children[];

        public class SubChildren{
            public String title;
            public String youtube_id;
            public String description;
        }
        @Override
        public String toString() {
            return "Children{" +
                    "relative_url='" + relative_url + '\'' +
                    ", description='" + description + '\'' +
                    ", title='" + title + '\'' +
                    ", translated_title='" + translated_title + '\'' +
                    '}';
        }
    }
}