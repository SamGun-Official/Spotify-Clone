package com.spotify.ui.LoginRegister;

import com.spotify.app.Account;
import com.spotify.app.Admin;
import com.spotify.app.User;

import java.util.ArrayList;

public class CobaMain {
    public static void main(String[] args) {
        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(new Admin());
//        Flogin f = new Flogin(accounts);
//        Fregister f = new Fregister(accounts);
//        FQuestionVerify f = new FQuestionVerify(accounts);
        accounts.add(new User("username", "password", "firstname","","","",""));
        FresetPassword f = new FresetPassword(accounts, (User) accounts.get(1));
        f.setVisible(true);
    }
}
