package com.codepath.apps.twitter;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twitter.models.Tweet;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * taking the tweet objects and tuning them into views deplayed in the list
 */
public class TweetsArrayAdapter extends ArrayAdapter<Tweet>{

    final String TWITTER="EEE MMM dd HH:mm:ss ZZZZZ yyyy";//"Mon Nov 09 07:30:05 +0000 2015"

    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, 0, tweets);
    }
    //override and setup custom template
    //ViewHolder pattern
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1 get the tweet
        Tweet tweet = getItem(position);
        // find or inflate the template
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }
        // find the sub views to fill with data in the template
        ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
        TextView tvScreenName = (TextView) convertView.findViewById(R.id.tvScreenName);
        TextView tvCreateTime = (TextView) convertView.findViewById(R.id.tvCreateTime);
        //populate data into subviews
        tvName.setText(tweet.getUser().getName());
        tvBody.setText(tweet.getBody());
        tvScreenName.setText("@" + tweet.getUser().getScreenNme());

        Date createdTime = null;

        SimpleDateFormat sf = new SimpleDateFormat(TWITTER);
        try {
            createdTime=sf.parse(tweet.getCreatedAt());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        CharSequence reletiveTime = DateUtils.getRelativeTimeSpanString(createdTime.getTime(), System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
        String[] parts = reletiveTime.toString().split(" ");
        String simpleRT = parts[0] + parts[1].subSequence(0, 1);
        tvCreateTime.setText(simpleRT);

        ivProfileImage.setImageResource(android.R.color.transparent);//clear out the old image for a recycled view
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);
        // return the view to be inserted into the list
        return convertView;

    }
}
