package com.codepath.apps.twitter;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.codepath.apps.twitter.models.Tweet;

import java.util.List;

/**
 * taking the tweet objects and tuning them into views deplayed in the list
 */
public class TweetsArrayAdapter extends ArrayAdapter<Tweet>{

    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, android.R.layout.simple_list_item_1, tweets);
    }
    //override and setup custom template
}
