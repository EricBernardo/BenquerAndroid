package br.com.benquer.app.benquer.interfaces;

import br.com.benquer.app.benquer.models.TokenRequest;
import br.com.benquer.app.benquer.models.TokenResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OAuth2 {

    @POST("auth")
    Call<TokenResponse> getTokenAccess(@Body TokenRequest tokenRequest);
}
