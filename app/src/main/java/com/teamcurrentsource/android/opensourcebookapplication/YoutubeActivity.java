package com.teamcurrentsource.android.opensourcebookapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YoutubeActivity extends YouTubeBaseActivity {

    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer.OnInitializedListener onInitializedListener;
    public String video_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String videoTitle = extras.getString("Title");
        String videoDescription = extras.getString("Description");

        video_id = extras.getString("youtubeId");

        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtubeView);
        ((TextView)findViewById(R.id.descriptionTextView)).setText(videoDescription);
        ((TextView)findViewById(R.id.titleTextView)).setText(videoTitle);

        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {

                if (!b) {
                    youTubePlayer.loadVideo(video_id);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        youTubePlayerView.initialize("AIzaSyC76YD1XNk2hKedMOJtVPD7XHh1ucBTXvg", onInitializedListener);
    }
}