package com.rawrick.flicklist.data.api.login;

import android.content.Context;

public class LoginManager {

    private final String authenticationSessionNew = "https://www.themoviedb.org/authenticate/";

    private String token;
    private String sessionID;
    private String guestSessionID;
    private final Context context;
    private final LoginManager.LoginTokenListener tokenListener;
    private final LoginManager.LoginSessionListener sessionListener;
    private final LoginManager.LoginGuestSessionListener guestSessionListener;

    public LoginManager(Context context, LoginManager.LoginTokenListener tokenListener, LoginManager.LoginSessionListener sessionListener, LoginManager.LoginGuestSessionListener guestSessionListener) {
        this.context = context;
        this.tokenListener = tokenListener;
        this.sessionListener = sessionListener;
        this.guestSessionListener = guestSessionListener;
    }

    public void getTokenFromAPI() {
        TokenProvider provider = new TokenProvider(context);
        provider.getToken(new TokenProvider.DataListener() {
            @Override
            public void onTokenDataAvailable(String data) {
                token = data;
                tokenListener.onTokenCreated();
            }
        });
    }

    public String getAuthenticationURL(String token) {
        String url = authenticationSessionNew + token;
        return url;
    }

    public void getSessionIDFromAPI() {
        SessionProvider provider = new SessionProvider(context);
        provider.getSessionID(new SessionProvider.DataListener() {
            @Override
            public void onSessionDataAvailable(String data) {
                sessionID = data;
                sessionListener.onSessionCreated();
            }
        });
    }


    public void getGuestSessionFromAPI() {
        GuestSessionProvider provider = new GuestSessionProvider(context);
        provider.getGuestSessionID(new GuestSessionProvider.DataListener() {
            @Override
            public void onGuestSessionDataAvailable(String data) {
                guestSessionID = data;
                guestSessionListener.onGuestSessionCreated();
            }
        });
    }

    public String getToken() {
        return token;
    }

    public String getSessionID() {
        return sessionID;
    }

    public String getGuestSessionID() {
        return guestSessionID;
    }

    public interface LoginTokenListener {
        void onTokenCreated();
    }
    public interface LoginSessionListener {
        void onSessionCreated();
    }
    public interface LoginGuestSessionListener {
        void onGuestSessionCreated();
    }
}