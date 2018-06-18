package com.pulpcode.twitter;

import twitter4j.*;

import java.io.IOException;

public class UserSearcher
{
    public static void main(String[] args) throws TwitterException, IOException
    {
        Twitter twitter = TwitterLoginUtil.logIntoTwitter();
        ResponseList<User>  responseList = twitter.searchUsers("jpmorgan",50);
        System.out.println("Size Returned = " + responseList.size());
        for (User user: responseList)
        {
            System.out.println(user.getId() + " " + user.getScreenName());
        }
    }
}
