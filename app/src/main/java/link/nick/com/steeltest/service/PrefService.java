package link.nick.com.steeltest.service;

import link.nick.com.steeltest.domain.LocalUser;

/**
 * Created by Nick on 09.02.2017.
 */

public interface PrefService {

    void saveLocalUser(LocalUser localUser);

    LocalUser getLocalUser();
}
