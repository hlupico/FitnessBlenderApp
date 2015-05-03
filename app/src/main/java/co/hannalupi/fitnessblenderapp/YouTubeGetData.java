package co.hannalupi.fitnessblenderapp;

import android.content.Context;
import android.util.Log;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hannalupico on 5/3/15.
 */
public class YouTubeGetData {

    //An instance of YouTube to communicate with YouTube API
    private YouTube youtube;
    //An instance of YouTube.Search.List to represent a search query
    private YouTube.Search.List query;
    //Include developer API Key
    public static final String GOOGLE_API_KEY = "AIzaSyBdJ1zGyVe6RmNLcsgO8lggZNDn-AA8M88";
    public static final String LOG_TAG = "YouTubeGetData";

    //Initailize variables in constructor

    public YouTubeGetData(Context context) {

        //YouTube.Builder is used to initailize the instance of YouTube
        //Network connection & JSON processing classes are passed to YouTube.Builder
        youtube = new YouTube.Builder(new NetHttpTransport(),
                new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest hr) throws IOException {}
        }).setApplicationName(context.getString(R.string.app_name)).build();

        //Use search() to create search request
        //Use list() to list details wanted, these details will be returned for each search result
        try{
            //id & snippet are part parameters of the video resource
            //the part parameter requests help to return exact data requested; avoids passing excess data
            query = youtube.search().list("id,snippet");
            //Developer key must be passed for each search request
            query.setKey(GOOGLE_API_KEY);
            //.setType will limit content returned to videos
            query.setType("video");
            //Fields filters API response
            query.setFields("items(id/videoId,snippet/title,snippet/description,snippet/thumbnails/default/url)");
        }catch(IOException e){
            Log.d(LOG_TAG, "Could not initialize: "+e);
        }
    }

    public List<VideoItem> search(String keywords){
        //Set keywords passed to search()
        query.setQ(keywords);
        try{
            //Response to search query is returned as a SearchListResponse
            SearchListResponse response = query.execute();
            List<SearchResult> results = response.getItems();

            //Iterate through the items to create a new List of VideoItems from query results
            List<VideoItem> items = new ArrayList<VideoItem>();
            for(SearchResult result:results){
                VideoItem item = new VideoItem();
                item.setTitle(result.getSnippet().getTitle());
                item.setDescription(result.getSnippet().getDescription());
                item.setThumbnailURL(result.getSnippet().getThumbnails().getDefault().getUrl());
                item.setId(result.getId().getVideoId());
                items.add(item);
            }
            //Return the items found
            return items;
        }catch(IOException e){
            Log.d(LOG_TAG, "Could not search: "+e);
            return null;
        }
    }


}
