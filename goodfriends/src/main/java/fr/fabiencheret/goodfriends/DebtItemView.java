package fr.fabiencheret.goodfriends;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;
import fr.fabiencheret.goodfriends.model.Debt;

/**
 * User: fcheret
 * Copyright 2013 fcheret. All rights reserved.
 * Date: 20/05/13
 * Time: 00:30
 */
@EViewGroup(R.layout.debt_row)
public class DebtItemView extends RelativeLayout{

    @ViewById
    TextView debtName;

    @ViewById
    TextView debtAmount;

    public DebtItemView(Context context) {
        super(context);
    }

    public void bind(Debt debt) {
        debtName.setText(debt.user.name);
        debtAmount.setText(debt.what);
    }

}
