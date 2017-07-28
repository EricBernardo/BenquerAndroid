package br.com.benquer.app.benquer;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import br.com.benquer.app.benquer.interfaces.AccountTypeService;
import br.com.benquer.app.benquer.models.AccountTypeDec;
import br.com.benquer.app.benquer.models.AccountTypeList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AccountTypesListActivity extends AppCompatActivity {

    SharedPreferences sharedPref;

    private ListView listView;

    private ArrayAdapter<String> adpData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_types_list);

        Gson gson = new GsonBuilder().registerTypeAdapter(AccountTypeService.class, new AccountTypeDec()).create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.api_url))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        AccountTypeService service = retrofit.create(AccountTypeService.class);

        sharedPref = getSharedPreferences("Auth", Context.MODE_PRIVATE);
        String token_type = sharedPref.getString("token_type", "");
        String access_token = sharedPref.getString("access_token", "");

        token_type = "Bearer";
        access_token = "gyNULtpYWv12JyZS67m3cooQIHpqRYnuRNqf7P6m";

        String authToken = "Bearer " + access_token;

        Call<List<AccountTypeList>> accountTypes = service.getAccountTypes(authToken);

        accountTypes.enqueue(new Callback<List<AccountTypeList>>() {

            @Override
            public void onResponse(Call<List<AccountTypeList>> call, Response<List<AccountTypeList>> response) {

                int statusCode = response.code();

                if(statusCode == 200) {
                    //List<AccountTypeList> accountTypes = response.body();
                    //listAccountType(accountTypes);
                }

            }

            @Override
            public void onFailure(Call<List<AccountTypeList>> call, Throwable t) {
                Log.d("LoginActivity", "onFailure: " + t.getMessage());
            }

        });

    }

    private void listAccountType(List<AccountTypeList> accountTypeResponse)
    {

        ListView listView = (ListView) findViewById(R.id.listData);
        adpData = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        for (AccountTypeList a: accountTypeResponse){
            //AccountTypeDetails accountType = new AccountTypeDetails(a);
            //adpData.add(accountType);
        }

        listView.setAdapter(adpData);
    }
}
