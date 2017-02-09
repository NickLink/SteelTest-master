package link.nick.com.steeltest.service;

/**
 * Created by Nick on 05.02.2017.
 */

import android.util.Base64;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

public class ServiceFactory {

    public static final String API_BASE_URL = GithubService.SERVICE_ENDPOINT;

    public static <T> T createRetrofitService(final Class<T> clazz, final String endPoint) {
        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(endPoint)
                .build();
        T service = restAdapter.create(clazz);
        return service;
    }

    public static <T> T createService(Class<T> serviceClass, final String endPoint, String username, String password) {
        final RestAdapter.Builder builder = new RestAdapter.Builder();
        if (username != null && password != null) {
            // concatenate username and password with colon for authentication
            String credentials = username + ":" + password;
            // create Base64 encodet string
            final String loginEncoded = new String(Base64.encode((username + ":" + password).getBytes(), Base64.NO_WRAP));
            builder
                    .setEndpoint(endPoint)
                    .setRequestInterceptor(new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    request.addHeader("Authorization", "Basic " + loginEncoded);
                    request.addHeader("Content-Type", "application/json");
                }
            });
        }

        RestAdapter restAdapter = builder.build();
        T service = restAdapter.create(serviceClass);
        return service;
    }
}