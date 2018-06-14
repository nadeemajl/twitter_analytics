package com.pulpcode.twitter;

import twitter4j.*;
import twitter4j.auth.AccessToken;

public class FollowersDataFetcher {

    public static void main(String args[]) throws Exception{
        Twitter twitter = TwitterLoginUtil.logIntoTwitter();
        //persist to the accessToken for future reference.
        //storeAccessToken(twitter.verifyCredentials().getId() , accessToken);

        long cursor = -1;
        int count = 200;
        PagableResponseList<User> followersList = twitter.getFollowersList("jpmorgan_UK",cursor,count);
        while((cursor = followersList.getNextCursor()) !=0)
        {
            followersList = twitter.getFollowersList("jpmorgan_UK",cursor,count);
            for(User user : followersList)
            {
                System.out.println(user.getId() + " " + user.getScreenName());
                long nestedCursor = -1;
                int nestedCount = 200;
                PagableResponseList<User> followersOfFollowers = twitter.getFollowersList(user.getScreenName(),nestedCursor,nestedCount);
                while((nestedCursor = followersOfFollowers.getNextCursor()) != 0 )
                {
                    followersOfFollowers = twitter.getFollowersList(user.getScreenName(),nestedCursor,nestedCount);
                    for(User followerOfFollwer : followersOfFollowers)
                    {
                        System.out.println("\t\t\t\t"+followerOfFollwer.getId() + " " + followerOfFollwer.getScreenName());
                    }
                    Thread.sleep(1000*2);
                }
            }
            Thread.sleep(1000*5);
        }

    }

    private static void storeAccessToken(int useId, AccessToken accessToken)
    {
        //store accessToken.getToken()
        //store accessToken.getTokenSecret()
    }

}
