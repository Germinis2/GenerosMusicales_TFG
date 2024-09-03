package java.TFG.generosmusicales;

import android.content.Context;
import android.content.SharedPreferences;

public class AccessTokenManager {

    private static final String ACCESS_TOKEN_PREF = "access_token_pref";
    private static final String KEY_ACCESS_TOKEN = "access_token";

    private static AccessTokenManager instance;
    private final SharedPreferences sharedPreferences;

    private AccessTokenManager(Context context) {
        sharedPreferences = context.getSharedPreferences(ACCESS_TOKEN_PREF, Context.MODE_PRIVATE);
    }

    public static synchronized AccessTokenManager getInstance(Context context) {
        if (instance == null) {
            instance = new AccessTokenManager(context.getApplicationContext());
        }
        return instance;
    }

    public void saveAccessToken(String accessToken) {
        sharedPreferences.edit().putString(KEY_ACCESS_TOKEN, accessToken).apply();
    }

    public String getAccessToken() {
        return sharedPreferences.getString(KEY_ACCESS_TOKEN, null);
    }
}