package co.wm21.https.FHelper.networks.ApiUtil;

import android.util.Log;

import com.wm21ltd.wm21.networks.Remote.APIService;
import com.wm21ltd.wm21.networks.Remote.RetroClient;

public class ApiUtils {
    public static APIService getApiService(String baseURL){
        Log.d("responsedata", baseURL);
        return RetroClient.getClient(baseURL).create(APIService.class);
    }
}
