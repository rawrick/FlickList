package com.rawrick.flicklist.data.account;

public class Account {

    private final String id,
            name,
            username,
            avatar,
            hash;

    private final boolean adult;

    public Account(String id,
                   String name,
                   String username,
                   String avatar,
                   String hash,
                   boolean adult) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.avatar = avatar;
        this.hash = hash;
        this.adult = adult;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getHash() {
        return hash;
    }

    public boolean getAdult() {
        return adult;
    }
}
