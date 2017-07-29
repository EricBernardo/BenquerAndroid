package br.com.benquer.app.benquer;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import br.com.benquer.app.benquer.interfaces.AccountTypeService;
import br.com.benquer.app.benquer.models.AccountType;
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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.api_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AccountTypeService service = retrofit.create(AccountTypeService.class);

        sharedPref = getSharedPreferences("Auth", Context.MODE_PRIVATE);
        String token_type = sharedPref.getString("token_type", "");
        String access_token = sharedPref.getString("access_token", "");

        String authToken = token_type + " " + access_token;

        Call accountTypes = service.getAccountTypes(authToken);

        accountTypes.enqueue(new Callback<AccountTypeList>() {

            @Override
            public void onResponse(Call<AccountTypeList> call, Response<AccountTypeList> response) {

                AccountTypeList accountTypeList = response.body();
                listAccountType(accountTypeList);

            }

            @Override
            public void onFailure(Call<AccountTypeList> call, Throwable t) {
                Log.d("LoginActivity", "onFailure: " + t.getMessage());
            }

        });

    }

    private void listAccountType(AccountTypeList accountTypeResponse)
    {

        ListView listView = (ListView) findViewById(R.id.listData);
        adpData = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        for (AccountType a : accountTypeResponse.getData()){
            adpData.add(a.getName());
        }

        listView.setAdapter(adpData);

    }
}
