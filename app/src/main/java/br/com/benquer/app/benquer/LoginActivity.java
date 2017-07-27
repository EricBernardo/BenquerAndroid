package br.com.benquer.app.benquer;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import br.com.benquer.app.benquer.interfaces.OAuth2;
import br.com.benquer.app.benquer.models.TokenRequest;
import br.com.benquer.app.benquer.models.TokenResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button button;
    ProgressBar progressBar;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        button = (Button) findViewById(R.id.btn_login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(getString(R.string.api_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                TokenRequest tokenRequest = new TokenRequest();

                tokenRequest.setUsername(email.getText().toString());
                tokenRequest.setPassword(password.getText().toString());
                tokenRequest.setGrant_type(getString(R.string.token_request_grant_type));
                tokenRequest.setClient_id(getString(R.string.token_request_client_id));
                tokenRequest.setClient_secret(getString(R.string.token_request_client_secret));

                OAuth2 service = retrofit.create(OAuth2.class);

                Call<TokenResponse> tokenResponseCall = service.getTokenAccess(tokenRequest);

                tokenResponseCall.enqueue(new Callback<TokenResponse>() {

                    @Override
                    public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                        int statusCode = response.code();

                        TokenResponse tokenResponse = response.body();

                        Log.d("LoginActivity", "onResponse: " + statusCode);

                        if(statusCode == 200) {

                            sharedPref = getSharedPreferences("Auth", Context.MODE_PRIVATE);
                            editor = sharedPref.edit();

                            editor.putString("access_token", tokenResponse.getAccess_token());
                            editor.putString("token_type", tokenResponse.getToken_type());
                            editor.putString("refresh_token", tokenResponse.getRefresh_token());
                            editor.putString("access_token", tokenResponse.getAccess_token());
                            editor.commit();

                            String access_token = sharedPref.getString("access_token", "");
                            Log.d("access_token", access_token);

                        }

                        progressBar.setVisibility(View.GONE);

                    }

                    @Override
                    public void onFailure(Call<TokenResponse> call, Throwable t) {

                        Log.d("LoginActivity", "onFailure: " + t.getMessage());
                        progressBar.setVisibility(View.GONE);

                    }

                });


            }
        });

    }
}
