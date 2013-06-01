package fr.fabiencheret.goodfriends.database;

import android.app.Activity;
import fr.fabiencheret.goodfriends.HelloAndroidActivity;
import fr.fabiencheret.goodfriends.HelloAndroidActivity_;
import fr.fabiencheret.goodfriends.model.Debt;
import fr.fabiencheret.goodfriends.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

/**
 * User: fcheret
 * Copyright 2013 fcheret. All rights reserved.
 * Date: 19/05/13
 * Time: 21:14
 */
@Config(reportSdk = 17)
@RunWith(RobolectricTestRunner.class)
public class MyDatabaseTest {

    Activity act = null;
    MyDatabase myDatabase = null;

    @Before
    public void setUp() throws Exception {
        act = Robolectric.buildActivity(HelloAndroidActivity_.class).create().get();
        myDatabase = new MyDatabase(act);
        myDatabase.truncateDatabase();
    }

    @Test
    public void testUserTransaction() throws Exception {
        myDatabase.store(new User(0, "fabien"));
        assertEquals(myDatabase.getAllUsers().size(), 1);
        assertEquals(myDatabase.getAllUsers().get(0).name, "fabien");
        myDatabase.store(new User(1, "fabien2"));
        assertEquals(myDatabase.getAllUsers().size(), 2);
        assertEquals(myDatabase.getAllUsers().get(1).name, "fabien2");

        assertNotNull(myDatabase.getUserByName("fabien2"));
        assertNotNull(myDatabase.getUserByName("fabien"));
    }

    @Test
    public void testDebtTransaction() throws Exception {
        Debt debt1 = new Debt(0,new User(0, "fabien"),true,"a Harry Potter book");
        Debt debt2 = new Debt(1,new User(0, "fabien"),true,"an owl");
        Debt debt3 = new Debt(2,new User(0, "fabien2"),true,"a cup of tea");
        myDatabase.store(debt1);
        assertEquals(myDatabase.getAllDebts().size(), 1);
        myDatabase.store(debt2);
        assertEquals(myDatabase.getAllDebts().size(),2);
        myDatabase.store(debt3);
        assertEquals(myDatabase.getAllDebts().size(), 3);

        assertEquals(myDatabase.getAllUsers().size(),2);
        assertNotSame(myDatabase.getAllUsers().get(0).ID,myDatabase.getAllUsers().get(1).ID);
    }

    @Test
    public void testTruncate() throws Exception {
        myDatabase.store(new Debt(0,new User(0, "fabien"),true,"a Harry Potter book"));
        myDatabase.store(new User(0, "fabien2"));
        assertNotSame(myDatabase.getAllUsers().size(), 0);
        assertNotSame(myDatabase.getAllDebts().size(), 0);
        myDatabase.truncateDatabase();
        assertEquals(myDatabase.getAllDebts().size(),0);
        assertEquals(myDatabase.getAllUsers().size(),0);
    }

    @Test
    public void testUserRedundancy() throws Exception {
        myDatabase.truncateDatabase();
        User user1 = new User(0, "fabien");
        User user2 = new User(0, "fabien");
        User user3 = new User(0, "fabien");
        myDatabase.store(user1);
        myDatabase.store(user2);
        myDatabase.store(user3);
        assertEquals(myDatabase.getAllUsers().size(), 1);
        myDatabase.store(new User(0, "fabien2"));
        assertEquals(myDatabase.getAllUsers().size(), 2);
    }

    @Test
    public void testGetUserById() throws Exception {
        myDatabase.truncateDatabase();
        User user1 = new User(0, "fabien");
        User user2 = new User(0, "fabien");
        User user3 = new User(0, "fabien");
        myDatabase.store(user1);
        myDatabase.store(user2);
        myDatabase.store(user3);
        assertEquals(myDatabase.getUserById(user1.ID).name, user1.name);
        assertEquals(myDatabase.getUserById(user2.ID).name, user2.name);
        assertEquals(myDatabase.getUserById(user3.ID).name,user3.name);
        assertNull(myDatabase.getUserById(Long.MAX_VALUE));
    }
}
