package link.nick.com.steeltest.service;

import java.util.List;

import link.nick.com.steeltest.domain.GithubRepo;
import link.nick.com.steeltest.domain.GithubUser;

/**
 * Created by Nick on 07.02.2017.
 */

public interface RealmService {
    //User service
    GithubUser getGitUser();
    void saveGitUser(GithubUser user);
    //Repos service
    List<GithubRepo> getReposList();
    List<GithubRepo> getFilteredReposList(String s);
    void saveReposList(List<GithubRepo> list);
}
