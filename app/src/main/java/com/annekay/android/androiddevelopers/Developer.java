package com.annekay.android.androiddevelopers;

/**
 * Created by HP on 8/23/2017.
 */

public class Developer {
    // variables for the custom class
    private String mUserName, mThumbnailUrl, mGitHubUrl, mDevLocation;
    //private byte[] imageByte;

    //constructor for the developer.java class

    public Developer(String thumbnailUrl, String userName, String gitHubUrl){
        mUserName = userName;
        mThumbnailUrl = thumbnailUrl;
        mGitHubUrl = gitHubUrl;
    }
    public Developer(){}

    //methods for the Developer class

    public String getUserName(){
        return mUserName;
    }

    public String getThumbnailUrl(){
        return mThumbnailUrl;
    }

    public String getGitHubUrl(){
        return mGitHubUrl;
    }

    public void setmDevLocation(String mDevLocation) {
        this.mDevLocation = mDevLocation;
    }
}
