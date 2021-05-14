package com.mparkersimms.taskmaster;

import com.amplifyframework.analytics.AnalyticsProperties;
import com.amplifyframework.analytics.UserProfile;
import com.amplifyframework.core.Amplify;

public class Analytics {
    private static Analytics analytics;

    public static Analytics getAnalytics() {
        if(analytics == null) {
            analytics = new Analytics();

            Amplify.Analytics.registerGlobalProperties(
                    AnalyticsProperties.builder()
                            .add("platform", "msimms task android app")
                            .add("priceOfAnOrange", 27)
                            .build()
            );

            // Track the user
            if(Amplify.Auth.getCurrentUser() != null){
                String id = Amplify.Auth.getCurrentUser().getUserId();
                UserProfile userProfile = UserProfile.builder()
                        .email(Amplify.Auth.getCurrentUser().getUsername())
                        .build();
                Amplify.Analytics.identifyUser(id, userProfile);
            }
        }
        return analytics;
    }
}
