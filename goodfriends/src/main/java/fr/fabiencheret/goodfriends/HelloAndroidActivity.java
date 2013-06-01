package fr.fabiencheret.goodfriends;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import com.actionbarsherlock.app.SherlockActivity;
import com.googlecode.androidannotations.annotations.*;
import fr.fabiencheret.goodfriends.model.Debt;

import static android.widget.Toast.*;

@EActivity(R.layout.main)
public class HelloAndroidActivity extends SherlockActivity {

    private static String TAG = "goodfriends";

    @ViewById
    ListView debtList;

    @Bean
    DebtListAdapter adapter;

    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after
     * previously being shut down then this Bundle contains the data it most
     * recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
    }

    @AfterViews
    void bindAdapter() {
        debtList.setAdapter(adapter);
    }

    @ItemClick
    void debtRowItemClicked(Debt debt) {
        makeText(this, debt.what, LENGTH_SHORT).show();
    }

}
