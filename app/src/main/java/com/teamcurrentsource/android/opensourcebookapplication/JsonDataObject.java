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

        @Override
        public String toString() {
            return "Children{" +
                    "relative_url='" + relative_url + '\'' +
                    ", description='" + description + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }
    }
}