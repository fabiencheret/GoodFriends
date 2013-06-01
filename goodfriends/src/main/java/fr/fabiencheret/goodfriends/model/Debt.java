package fr.fabiencheret.goodfriends.model;

/**
 * User: fcheret
 * Copyright 2013 fcheret. All rights reserved.
 * Date: 19/05/13
 * Time: 20:31
 */
public class Debt {

    public long ID;
    public User user;
    public boolean positive;
    public String what;

    public Debt(long ID, User user, boolean positive, String what) {
        this.ID = ID;
        this.user = user;
        this.positive = positive;
        this.what = what;
    }
}
