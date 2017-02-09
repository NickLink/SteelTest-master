package link.nick.com.steeltest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import link.nick.com.steeltest.adapters.ReposListAdapter;
import link.nick.com.steeltest.domain.GithubRepo;
import link.nick.com.steeltest.domain.GithubUser;
import link.nick.com.steeltest.service.GithubService;
import link.nick.com.steeltest.service.RealmService;
import link.nick.com.steeltest.service.RealmServiceImpl;
import link.nick.com.steeltest.service.ServiceFactory;
import link.nick.com.steeltest.ui.Dialogs;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Nick on 06.02.2017.
 */

public class ReposActivity extends AppCompatActivity {
    private final String TAG = ReposActivity.class.getSimpleName();
    ReposListAdapter adapter;
    GithubUser githubUser;
    RealmService realmService;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repos_screen);

        realmService = new RealmServiceImpl();
        githubUser = realmService.getGitUser();

        ListView reposList;
        Button backToLogin, getReposList, goToSearch;

        View header = getLayoutInflater().inflate(R.layout.listview_header, null);
        TextView youLoggedAs = (TextView) header.findViewById(R.id.youLoggedAs);
        youLoggedAs.setText(getString(R.string.you_logged_as) + " " + githubUser.getLogin());
        TextView youIdIs = (TextView) header.findViewById(R.id.youIdIs);
        youIdIs.setText(getString(R.string.your_id_is) + " " + githubUser.getId());

        reposList = (ListView) findViewById(R.id.reposList);
        reposList.addHeaderView(header);
        adapter = new ReposListAdapter(ReposActivity.this, realmService.getReposList());
        reposList.setAdapter(adapter);

        backToLogin = (Button) findViewById(R.id.backToLogin);
        getReposList = (Button)findViewById(R.id.getReposList);
        goToSearch = (Button)findViewById(R.id.goToSearch);

        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.flip_in, R.anim.flip_out);
            }
        });

        getReposList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getReposList();
            }
        });

        goToSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextScreen = new Intent(ReposActivity.this, SearchActivity.class);
                startActivity(nextScreen);
                overridePendingTransition(R.anim.flip_in, R.anim.flip_out);
            }
        });
    }

    void getReposList(){
        Dialogs.ShowProgressDialog(this);
        GithubService service = ServiceFactory.createRetrofitService(GithubService.class, GithubService.SERVICE_ENDPOINT);
        service.getReposData(githubUser.getLogin())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<GithubRepo>>() {
                    @Override
                    public final void onCompleted() {
                    }
                    @Override
                    public final void onError(Throwable e) {
                        Log.e("GETREPOS ERROR ->", e.getMessage());
                        Dialogs.HideProgressDialog();
                        Dialogs.ShowErrorDialog(ReposActivity.this, e.getMessage());
                    }
                    @Override
                    public void onNext(List<GithubRepo> githubRepos) {
                        Dialogs.HideProgressDialog();
                        realmService.saveReposList(githubRepos);
                        adapter.setReposList(realmService.getReposList());
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.flip_in, R.anim.flip_out);
    }

}
