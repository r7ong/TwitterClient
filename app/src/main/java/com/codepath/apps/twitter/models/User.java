package com.codepath.apps.twitter.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    //list attr
    private String name;
    private long uid;
    private String screenNme;
    private String profileImageUrl;

    public User(String name, long uid, String screenNme, String profileImageUrl) {
        this.name = name;
        this.uid = uid;
        this.screenNme = screenNme;
        this.profileImageUrl = profileImageUrl;
    }

    public User() {

    }

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
