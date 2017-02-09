package link.nick.com.steeltest.mvp;

/**
 * Created by Nick on 07.02.2017.
 */

public interface LoginInteractor {
    interface OnLoginFinishedListener {
        void onUsernameError();

        void onPasswordError();

        void onSuccess();
    }

    void login(String username, String password, OnLoginFinishedListener listener);
    void showLoader();
    void hideLoader();
}
