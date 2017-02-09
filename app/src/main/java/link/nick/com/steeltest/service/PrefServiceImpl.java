package link.nick.com.steeltest.service;

import android.content.SharedPreferences;

import link.nick.com.steeltest.application.AppController;
import link.nick.com.steeltest.domain.LocalUser;

/**
 * Created by Nick on 09.02.2017.
 */

public class PrefServiceImpl implements PrefService {

    private final String prefname = "username";
    private final String prefpass = "password";
    private SharedPreferences sharedPreferences = AppController.getInstance().getSharPref();

    @Override
    public void saveLocalUser(LocalUser localUser) {
        sharedPreferences.edit().putString(prefname, localUser.getUser()).apply();
        sharedPreferences.edit().putString(prefpass, localUser.getPass()).apply();
    }

    @Override
    public LocalUser getLocalUser() {
        LocalUser localUser = new LocalUser();
        localUser.setUser(sharedPreferences.getString(prefname, null));
        localUser.setPass(sharedPreferences.getString(prefpass, null));

        if(localUser.getUser() != null && localUser.getPass() != null){
            return localUser;
        } else
            return null;
    }
}
