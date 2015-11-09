package com.codepath.apps.twitter.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    //list attr
    private String name;

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenNme() {
        return screenNme;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    private long uid;
    private String screenNme;
    private String profileImageUrl;

    //deserialize the user json => User
    public static User fromJSON(JSONObject json){
        User u = new User();
        //extract and fill the values
        try {
            u.name = json.getString("name");
            u.uid = json.getLong("id");
            u.screenNme = json.getString("screen_name");
            u.profileImageUrl = json.getString("profile_image_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // return a user
        return u;

    }
}
