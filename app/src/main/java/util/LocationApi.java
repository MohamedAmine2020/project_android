package util;

import android.app.Application;

public class LocationApi extends Application {
    private String username;
    private String userId;

    private static LocationApi instance;

    public static LocationApi getInstance(){
        if (instance == null) {
            instance = new LocationApi();
        }
        return instance;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


}
