package br.com.benquer.app.benquer.interfaces;

import java.util.List;

import br.com.benquer.app.benquer.models.AccountTypeList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface AccountTypeService {

    @GET("account-type")
    Call<AccountTypeList> getAccountTypes(@Header("Authorization") String authorization);

}
