package link.nick.com.steeltest.mvp;

/**
 * Created by Nick on 07.02.2017.
 */

public class LoginInteractorImpl implements LoginInteractor {
    @Override
    public void login(String username, String password, OnLoginFinishedListener listener) {
        if(InputDataOk(username, password)){
            //Go to login Service

        } else {
            return;
        }

    }

    @Override
    public void showLoader() {

    }

    @Override
    public void hideLoader() {

    }


    private boolean InputDataOk(String userName, String userPassword){
        if(!userName.isEmpty() && !userPassword.isEmpty()){
            return true;
        } else {
            return false;
        }
    }

}
