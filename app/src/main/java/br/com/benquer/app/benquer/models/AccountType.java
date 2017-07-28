package br.com.benquer.app.benquer.models;

import java.util.ArrayList;
import java.util.List;

public class AccountType {

    private List<AccountTypeDetails> accountTypeDetails = new ArrayList<AccountTypeDetails>();

    public List<AccountTypeDetails> getAccountTypeDetails() {
        return accountTypeDetails;
    }

    public void setAccountTypeDetails(List<AccountTypeDetails> accountTypeDetails) {
        this.accountTypeDetails = accountTypeDetails;
    }

}
