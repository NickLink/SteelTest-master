package link.nick.com.steeltest.service;

import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import link.nick.com.steeltest.domain.GithubRepo;
import link.nick.com.steeltest.domain.GithubUser;

/**
 * Created by Nick on 07.02.2017.
 */

public class RealmServiceImpl implements RealmService {
    Realm realm = Realm.getDefaultInstance();

    @Override
    public GithubUser getGitUser() {
        if(realm.where(GithubUser.class).count() != 0)
            return realm.where(GithubUser.class).findFirst();
        else
            return null;
    }

    @Override
    public void saveGitUser(GithubUser user) {
        realm.beginTransaction();
        realm.delete(GithubUser.class);
        realm.copyToRealm(user);
        realm.commitTransaction();
    }

    @Override
    public List<GithubRepo> getReposList() {
        return  realm.where(GithubRepo.class).findAll();
    }

    @Override
    public List<GithubRepo> getFilteredReposList(String s) {
        return realm.where(GithubRepo.class).beginsWith("name", s, Case.INSENSITIVE).findAll();
    }

    @Override
    public void saveReposList(List<GithubRepo> list) {
        realm.beginTransaction();
        realm.delete(GithubRepo.class);
        for (GithubRepo r: list) {
            realm.copyToRealm(r);
        }
        realm.commitTransaction();
    }
}
