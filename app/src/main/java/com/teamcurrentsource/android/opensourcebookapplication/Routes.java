package com.teamcurrentsource.android.opensourcebookapplication;


public class Routes {
    public static final String BASEURL = "http://www.khanacademy.org/api/v1";
    public static final String CATEGORIES =  BASEURL + "/topictree?kind=Topic";
    public static final String CATEROGY = BASEURL + "/topic/";
    public static final String VIDEOS = BASEURL + "/topictree?kind=video";
    public static final String VIDEO = BASEURL + "/videos/";

    public static String getVideoImageUrl(String category) {
        return CATEROGY + category + "/videos";
    }
}
