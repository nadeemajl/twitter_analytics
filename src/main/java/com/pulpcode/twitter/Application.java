package com.pulpcode.twitter;

import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Application {

    public static void main(String args[]) throws Exception{
        // The factory instance is re-useable and thread safe.
        Twitter twitter = TwitterFactory.getSingleton();
        twitter.setOAuthConsumer("nvZHPFmgwDbIahcOe9WU0w3PC", "5bGanQH68FBdLyQEVTI8yEC6neAgzMGeoRh8ovB4cqOMLTQypS");
        RequestToken requestToken = twitter.getOAuthRequestToken();
        AccessToken accessToken = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (null == accessToken)
        {
            System.out.println("Open the following URL and grant access to your account:");
            System.out.println(requestToken.getAuthorizationURL());
            System.out.print("Enter the PIN(if aviailable) or just hit enter.[PIN]:");
            String pin = br.readLine();
            try{
                if(pin.length() > 0){
                    accessToken = twitter.getOAuthAccessToken(requestToken, pin);
                }else{
                    accessToken = twitter.getOAuthAccessToken();
                }
            } catch (TwitterException te) {
                if(401 == te.getStatusCode()){
                    System.out.println("Unable to get the access token.");
                }else{
                    te.printStackTrace();
                }
            }
        }
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
                for(User followerOfFollwer : followersOfFollowers)
                {
                    System.out.println("\t\t\t\t"+followerOfFollwer.getId() + " " + followerOfFollwer.getScreenName());
                }
                Thread.sleep(1000*15);
            }
            Thread.sleep(1000*15);
        }

    }

    private static void storeAccessToken(int useId, AccessToken accessToken){
        //store accessToken.getToken()
        //store accessToken.getTokenSecret()
    }

}
