package br.com.benquer.app.benquer;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class AccountTypesListActivity extends AppCompatActivity {

    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_types_list);

        sharedPref = getSharedPreferences("Auth", Context.MODE_PRIVATE);
        String access_token = sharedPref.getString("access_token", "");
        Log.d("AccountTypesListActivity", access_token);

    }
}
