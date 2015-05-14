package co.hannalupi.fitnessblenderapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

;

/**
 * Created by hannalupico on 5/3/15.
 */
public class SearchActivity extends Activity {

    //Declare views from search_activity.xml
    private EditText searchInput;
    private ListView videosFound;
    private List<VideoItem> searchResults;

    //Include Handler to make updates on the UI thread
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);

        //Create views declared above
        searchInput = (EditText)findViewById(R.id.search);
        videosFound = (ListView)findViewById(R.id.videos_found);

        handler = new Handler();

        //Use .setOnEditorActionListener to return changes from EditText field
        searchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    //Use searchOnYoutube to initailize YouTubeGetData & run search()
                    searchOnYoutube(v.getText().toString());
                    return false;
                }
                return true;
            }
        });

        //Call addClickListener to create onClickListener for items displayed in ListView
        addClickListener();
    }

    private void searchOnYoutube(final String keywords){
        //A new thread is needed  because network operations cannot be performed on the the main user interface thread
        new Thread(){
            public void run(){
                YouTubeGetData youTubeGetData = new YouTubeGetData(SearchActivity.this);
                searchResults = youTubeGetData.search(keywords);
                handler.post(new Runnable(){
                    public void run(){
                        updateVideosFound();
                    }
                });
            }
        }.start();
    }

    private void updateVideosFound(){
        //Create an ArrayAdapter to handle listview items
        ArrayAdapter<VideoItem> adapter = new ArrayAdapter<VideoItem>(getApplicationContext(), R.layout.video_item, searchResults){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                //Inflate video_item view, create new view if not recycled, display returned data
                if(convertView == null){
                    convertView = getLayoutInflater().inflate(R.layout.video_item, parent, false);
                }
                ImageView thumbnail = (ImageView)convertView.findViewById(R.id.video_thumbnail);
                TextView title = (TextView)convertView.findViewById(R.id.video_title);
                TextView description = (TextView)convertView.findViewById(R.id.video_description);

                VideoItem searchResult = searchResults.get(position);

                //Picasso's load() method will fetch thumbnail of the video
                //.into() passes the thumbnail to ImageView
                Picasso.with(getApplicationContext()).load(searchResult.getThumbnailURL()).into(thumbnail);
                title.setText(searchResult.getTitle());
                description.setText(searchResult.getDescription());
                return convertView;
            }
        };

        //Set adapter to ListView
        videosFound.setAdapter(adapter);
    }

    //Create onClickListener for items displayed in ListView
    private void addClickListener(){
        //Link addClickListener to the videosFound ListView
        videosFound.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //Create new intent for item clicked
            //Pass video_id of view clicked with intent
            //YouTubeActivity will use the video_id to play the selected video
            @Override
            public void onItemClick(AdapterView<?> av, View v, int pos, long id) {
                Intent intent = new Intent(SearchActivity.this, YouTubeActivity.class);
                intent.putExtra("VIDEO_ID", searchResults.get(pos).getId());
                startActivity(intent);
            }

        });
    }

}
