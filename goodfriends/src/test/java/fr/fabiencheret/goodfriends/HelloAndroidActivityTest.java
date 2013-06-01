package fr.fabiencheret.goodfriends;

import android.widget.ListView;
import fr.fabiencheret.goodfriends.database.MyDatabase;
import fr.fabiencheret.goodfriends.model.Debt;
import fr.fabiencheret.goodfriends.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * User: fcheret
 * Copyright 2013 fcheret. All rights reserved.
 * Date: 19/05/13
 * Time: 14:52
 */
@Config(reportSdk = 17)
@RunWith(RobolectricTestRunner.class)
public class HelloAndroidActivityTest {

    HelloAndroidActivity act = null;
    MyDatabase myDatabase = null;

    @Before
    public void setUp() throws Exception {
        act = Robolectric.buildActivity(HelloAndroidActivity_.class).create().get();
        myDatabase = new MyDatabase(act);
        Debt debt1 = new Debt(0,new User(0, "fabien"),true,"a Harry Potter book");
        Debt debt2 = new Debt(1,new User(0, "fabien"),true,"an owl");
        Debt debt3 = new Debt(2,new User(0, "fabien2"),true,"a cup of tea");
        myDatabase.store(debt1);
        myDatabase.store(debt2);
        myDatabase.store(debt3);
    }

    @Test
    public void shouldHaveHappySmiles() throws Exception {
        String appName = new HelloAndroidActivity().getResources().getString(R.string.app_name);
        assertThat(appName, equalTo("GoodFriends"));
    }

    @Test
    public void testListView() throws Exception {
        ListView list = (ListView) act.findViewById(R.id.debtList);
        assertEquals(list.getAdapter().getCount(),3);

    }

}
