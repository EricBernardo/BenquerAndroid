package br.com.benquer.app.benquer.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AccountTypeList {

    @SerializedName("data")
    @Expose
    private List<AccountType> data;

    public List<AccountType> getData() {
        return data;
    }

    public void setData(List<AccountType> data) {
        this.data = data;
    }
}
