package link.nick.com.steeltest.service;

import java.util.List;

import link.nick.com.steeltest.domain.GithubRepo;
import link.nick.com.steeltest.domain.GithubUser;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by Nick on 05.02.2017.
 */

public interface GithubService {
    String SERVICE_ENDPOINT = "https://api.github.com";

    @GET("/user")
    Observable<GithubUser> getUserData();

    @GET("/users/{user}/repos")
    Observable<List<GithubRepo>> getReposData(@Path("user") String user);

}

