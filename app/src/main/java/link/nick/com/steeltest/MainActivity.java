package link.nick.com.steeltest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import link.nick.com.steeltest.domain.GithubUser;
import link.nick.com.steeltest.domain.LocalUser;
import link.nick.com.steeltest.service.GithubService;
import link.nick.com.steeltest.service.PrefService;
import link.nick.com.steeltest.service.PrefServiceImpl;
import link.nick.com.steeltest.service.RealmService;
import link.nick.com.steeltest.service.RealmServiceImpl;
import link.nick.com.steeltest.service.ServiceFactory;
import link.nick.com.steeltest.ui.Dialogs;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    //private ProgressDialog progressDialog;
    private EditText userName;
    private EditText userPassword;
    private Button loginButton;
    private PrefService prefService;
    private RealmService realmService;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        prefService = new PrefServiceImpl();
        realmService = new RealmServiceImpl();

        userName = (EditText)findViewById(R.id.editUserName);
        userPassword = (EditText)findViewById(R.id.editUserPassword);
        loginButton = (Button) findViewById(R.id.buttonLogin);

        LocalUser localUser = prefService.getLocalUser();
        if(localUser != null){
            userName.setText(localUser.getUser());
            userPassword.setText(localUser.getPass());
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!userName.getText().toString().isEmpty() && !userPassword.getText().toString().isEmpty()){
                    DoLogin(userName.getText().toString(), userPassword.getText().toString());
                } else {
                    Dialogs.ShowEmptyInputDialog(MainActivity.this);
                }
            }
        });
    }

    private void DoLogin(final String username, final String password){
        Dialogs.ShowProgressDialog(this);
        GithubUser githubUser = realmService.getGitUser();
        if(prefService.getLocalUser() != null
                && username.equals(prefService.getLocalUser().getUser())
                && githubUser != null){
            //Data from database is ok
            Toast.makeText(getApplicationContext(), "Data from -> REALM", Toast.LENGTH_SHORT).show();
            loginSuccess();
        } else {
            //Get data from web and cache it
            Toast.makeText(getApplicationContext(), "Data from -> WEB ", Toast.LENGTH_SHORT).show();
            final GithubService loginService = ServiceFactory.createService(GithubService.class, GithubService.SERVICE_ENDPOINT, username, password);
            loginService.getUserData()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<GithubUser>() {
                        @Override
                        public void onCompleted() {
                        }
                        @Override
                        public void onError(Throwable e) {
                            Log.e("Github LOGIN ERROR ->", e.getMessage());
                            Dialogs.HideProgressDialog();
                            Dialogs.ShowErrorDialog(MainActivity.this, e.getMessage());
                        }
                        @Override
                        public void onNext(GithubUser githubUser) {
                            prefService.saveLocalUser(new LocalUser(username, password));
                            realmService.saveGitUser(githubUser);
                            loginSuccess();
                        }
                    });
        }
    }

    private void loginSuccess(){
        Dialogs.HideProgressDialog();
        Intent nextScreen = new Intent(MainActivity.this, ReposActivity.class);
        startActivity(nextScreen);
        overridePendingTransition(R.anim.flip_in, R.anim.flip_out);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.flip_in, R.anim.flip_out);
    }

}
