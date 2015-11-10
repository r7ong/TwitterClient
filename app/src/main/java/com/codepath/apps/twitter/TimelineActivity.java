package com.codepath.apps.twitter;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.models.Tweet;
import com.codepath.apps.twitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class TimelineActivity extends AppCompatActivity {

    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter aTweets;
    private ListView lvTweets;
    SwipeRefreshLayout swipeContainer;
    private long lowId = 0;
    private long lastlowId = 0;
    final int REQUEST_CODE = 90;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        //find listview
        lvTweets = (ListView) findViewById(R.id.lvTweets);
        //create the arraylist(data source)
        tweets = new ArrayList<>();
        //construct the adapter from data source
        aTweets = new TweetsArrayAdapter(this, tweets);
        //connect adapter to list view
        lvTweets.setAdapter(aTweets);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                populateTimeline();
            }
        });

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                Log.d("Debug in-- ref", Integer.toString(page));
                customLoadMoreDataFromApi(page);
                // or customLoadMoreDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });
        
        //get the client
        client = TwitterApplication.getRestClient();
        populateTimeline();
    }

    private void customLoadMoreDataFromApi(int page) {
        Toast.makeText(this, Integer.toString(page), Toast.LENGTH_SHORT).show();
        client.getHomeTimeLine(new JsonHttpResponseHandler() {
            // success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                Log.d("in-- DEBUG", json.toString());
                // json here
                // deserialize json
                // create model and add them to the adapter
                // load the model data into listview
                aTweets.addAll(Tweet.fromJSONArray(json));
                lastlowId = aTweets.getItem(json.length() - 1).getUid();
                swipeContainer.setRefreshing(false);
            }

            // failure
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("in--f DEBUG", errorResponse.toString());
            }
        }, lowId);
        lowId = lastlowId;
        /*
        String searchUrl = buildSearchUrl();
        if(offset > 0){
            String start = Integer.toString(offset * 8);
            searchUrl = searchUrl + "&start=" + start;
        }

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(searchUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray imageResultsJson = null;
                try {
                    imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
                    aImageResults.addAll(ImageResult.fromJSONArray(imageResultsJson));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
         */
    }


    // send an api request to get the timeline json
    // fill the listview by creating the tweet objects from the json
    private void populateTimeline() {
        client.getHomeTimeLine(new JsonHttpResponseHandler(){
            // success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                Log.d("in-- DEBUG", json.toString());
                aTweets.clear();
                // json here
                // deserialize json
                // create model and add them to the adapter
                // load the model data into listview
                aTweets.addAll(Tweet.fromJSONArray(json));
                lastlowId = aTweets.getItem(json.length()-1).getUid();

                swipeContainer.setRefreshing(false);
            }

            // failure
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("in--f DEBUG", errorResponse.toString());
            }
        }, lowId);
        lowId = lastlowId;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast.makeText(TimelineActivity.this, "onActivityResult", Toast.LENGTH_SHORT).show();
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            String newTweet = data.getStringExtra("newTweet");
            Toast.makeText(TimelineActivity.this, newTweet, Toast.LENGTH_SHORT).show();

            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy");
            String formattedDate = df.format(c.getTime());

            String imgUrl = "https://pbs.twimg.com/profile_images/558541794829299712/WrO1V1wl.jpeg";
            Tweet tweet = new Tweet(newTweet, 143895494, new User("桔梗店老板", 143895494, "kikyo0o",imgUrl), formattedDate);
            tweets.add(0, tweet);
            aTweets.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        if(id == R.id.mnuCompose){
//            Toast.makeText(MainActivity.this, "onOptionsItemSelected", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(TimelineActivity.this, ComposeActivity.class); //to start new activity, only need this line and startActivity(intent);
            intent.putExtra("age", 26); // usually put keys in strings file

            //start request activity
//            startActivity(intent);
            startActivityForResult(intent, REQUEST_CODE);
        }

        return super.onOptionsItemSelected(item);
    }
}
