package link.nick.com.steeltest.mvp;

/**
 * Created by Nick on 07.02.2017.
 */

public interface LoginPresenter {
    void validateCredentials(String username, String password);
    void onDestroy();
}
