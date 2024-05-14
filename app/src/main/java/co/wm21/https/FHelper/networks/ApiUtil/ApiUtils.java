package co.wm21.https.FHelper.networks.ApiUtil;

import android.util.Log;

import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.FHelper.networks.Remote.RetroClient;


public class ApiUtils {
    public static APIService getApiService(String baseURL){
        Log.d("responsedata", baseURL);
        return RetroClient.getClient(baseURL).create(APIService.class);
    }
}
