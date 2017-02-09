package link.nick.com.steeltest;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ListView;

import link.nick.com.steeltest.adapters.ReposListAdapter;
import link.nick.com.steeltest.service.RealmService;
import link.nick.com.steeltest.service.RealmServiceImpl;
import rx.Observable;
import rx.android.widget.OnTextChangeEvent;
import rx.android.widget.WidgetObservable;
import rx.functions.Action1;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Nick on 07.02.2017.
 */

public class SearchActivity  extends AppCompatActivity {

    private ListView searchListView;
    private RealmService realmService;
    private ReposListAdapter adapter;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_screen);

        realmService = new RealmServiceImpl();
        searchListView = (ListView)findViewById(R.id.searchListView);
        adapter = new ReposListAdapter(SearchActivity.this, realmService.getReposList());
        searchListView.setAdapter(adapter);

        Observable<OnTextChangeEvent> userNameText =
                WidgetObservable.text((EditText) findViewById(R.id.searchText));

        userNameText.subscribe(new Action1<OnTextChangeEvent>() {
            @Override
            public void call(OnTextChangeEvent onTextChangeEvent) {
                adapter.setReposList(realmService.getFilteredReposList(onTextChangeEvent.text().toString()));
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.flip_in, R.anim.flip_out);
    }


}
