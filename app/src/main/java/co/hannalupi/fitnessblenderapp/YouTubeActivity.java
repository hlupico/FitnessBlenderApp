package co.hannalupi.fitnessblenderapp;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.ErrorReason;
import com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;
import com.google.android.youtube.player.YouTubePlayerView;

/**
 * Created by hannalupico on 5/2/15.
 */
public class YouTubeActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    public static final String GOOGLE_API_KEY = "AIzaSyBdJ1zGyVe6RmNLcsgO8lggZNDn-AA8M88";


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //Set ContentView to custom youtube.xml
        setContentView(R.layout.youtube);

        //set up YouTubePlayerView and initialize
        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player);
        youTubePlayerView.initialize(GOOGLE_API_KEY, this);

    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        //Create playerStateChangeListener & playbackEventListener callbacks below
        //The listeners can be empty/blank but they do need to be provided
        player.setPlayerStateChangeListener(playerStateChangeListener);
        player.setPlaybackEventListener(playbackEventListener);

        //play video
        if(!wasRestored){
            player.cueVideo(getIntent().getStringExtra("VIDEO_ID"));
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, "Cannot initilaize Youtube Player", Toast.LENGTH_LONG).show();
    }


    private PlaybackEventListener playbackEventListener = new PlaybackEventListener() {
        public void onBuffering(boolean arg0){

        }

        public void onPaused(){

        }

        public void onPlaying(){

        }

        public void onSeekTo(int arg0){

        }

        public void onStopped(){

        }
    };

    private PlayerStateChangeListener playerStateChangeListener = new PlayerStateChangeListener() {


        public void onAdStarted(){

        }

        public void onError(ErrorReason arg0){


        }
        public void onLoading(){

        }

        public void onLoaded(String arg0){

        }

        public void onVideoStarted(){

        }

        public void onVideoEnded(){

        }
    };

}