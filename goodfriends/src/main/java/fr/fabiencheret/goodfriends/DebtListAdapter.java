package fr.fabiencheret.goodfriends;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import fr.fabiencheret.goodfriends.database.MyDatabase;
import fr.fabiencheret.goodfriends.model.Debt;
import fr.fabiencheret.goodfriends.model.User;

import java.util.List;

/**
 * User: fcheret
 * Copyright 2013 fcheret. All rights reserved.
 * Date: 20/05/13
 * Time: 00:26
 */
@EBean
public class DebtListAdapter extends BaseAdapter {

    @Bean
    MyDatabase myDatabase;

    @RootContext
    Context context;

    List<Debt> debts;


    public DebtListAdapter() {
    }

    @AfterInject
    void initAdapter() {
        debts = myDatabase.getAllDebts();
    }


    @Override
    public int getCount() {
        return debts.size();
    }

    @Override
    public Debt getItem(int i) {
        return debts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        DebtItemView view;
        if(convertView == null){
            view = DebtItemView_.build(context);
        } else {
            view = (DebtItemView) convertView;
        }
        view.bind(getItem(i));
        return view;
    }
}
