package co.hannalupi.fitnessblenderapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeStandalonePlayer;


/**
 * Created by hannalupico on 5/2/15.
 */
public class StandAloneActivity extends Activity implements View.OnClickListener {

    //implements View.OnClickListener --> generic onClickListener is being used

    public static final String GOOGLE_API_KEY = "AIzaSyBdJ1zGyVe6RmNLcsgO8lggZNDn-AA8M88";
    public static final String YOUTUBE_VIDEO_ID = "IR31lyaxJE4";
    public static final String YOUTUBE_PLAYLIST_ID = "PL5lPziO_t_Vgo38eyudKETCHOo29WCLqX";
    //public static final int REQUEST_CODE = 0;

    private Button btnPlay;
    private Button btnPlaylist;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set ContentView to custom youtube.xml
        setContentView(R.layout.standalone);

        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnPlaylist = (Button) findViewById(R.id.btnPlaylist);

        //set up onClickListeners
        btnPlay.setOnClickListener(this);
        btnPlaylist.setOnClickListener(this);

    }

    //Create onClickListener, pass generic view
    public void onClick(View v){
        Intent intent = null;
        if(v == btnPlay){
            //Play single video
            intent = YouTubeStandalonePlayer.createVideoIntent(this, GOOGLE_API_KEY, YOUTUBE_VIDEO_ID);
        }else if (v == btnPlaylist){
            //Play the playlist
            intent = YouTubeStandalonePlayer.createPlaylistIntent(this, GOOGLE_API_KEY, YOUTUBE_PLAYLIST_ID);
        }

        if(intent != null){
            //REQUEST_CODE = 1 will return activityResult
            //REQUEST_CODE = 0 will not return information from the intent call
            startActivity(intent);
        }

    }


}
