package com.rawrick.flicklist.data.credits;

public class Cast {
    private final boolean adult;
    private final int gender;
    private final int id;
    private final String knownForDepartment;
    private final String name;
    private final String originalName;
    private final double popularity;
    private final String profilePath;
    private final int castID;
    private final String character;
    private final String creditID;
    private final int order;


    public Cast(boolean adult,
                int gender,
                int id,
                String knownForDepartment,
                String name,
                String originalName,
                double popularity,
                String profilePath,
                int castID,
                String character,
                String creditID,
                int order) {
        this.adult = adult;
        this.gender = gender;
        this.id = id;
        this.knownForDepartment = knownForDepartment;
        this.name = name;
        this.originalName = originalName;
        this.popularity = popularity;
        this.profilePath = profilePath;
        this.castID = castID;
        this.character = character;
        this.creditID = creditID;
        this.order = order;
    }

    public boolean isAdult() {
        return adult;
    }

    public int getGender() {
        return gender;
    }

    public int getId() {
        return id;
    }

    public String getKnownForDepartment() {
        return knownForDepartment;
    }

    public String getName() {
        return name;
    }

    public String getOriginalName() {
        return originalName;
    }

    public double getPopularity() {
        return popularity;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public int getCastID() {
        return castID;
    }

    public String getCharacter() {
        return character;
    }

    public String getCreditID() {
        return creditID;
    }

    public int getOrder() {
        return order;
    }
}
