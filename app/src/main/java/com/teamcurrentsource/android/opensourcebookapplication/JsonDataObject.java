package com.teamcurrentsource.android.opensourcebookapplication;

import java.util.Arrays;

/**
 * Created by Antti on 17.3.2016.
 */

public class JsonDataObject {
    private String id;
    private String node_slug;
    public Children children[];

    @Override
    public String toString() {
        return "JsonDataObject{" +
                "id='" + id + '\'' +
                ", node_slug='" + node_slug + '\'' +
                ", children=" + Arrays.toString(children) +
                '}';
    }

    public class Children {
        String node_slug;
        String relative_url;
        public String title;

        @Override
        public String toString() {
            return "children{" +
                    "node_slug='" + node_slug + '\'' +
                    ", url='" + relative_url + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }
    }
}