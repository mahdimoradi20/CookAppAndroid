package cook.mahdi.moradi.ApiHandlers;

import android.util.Log;

import java.io.IOException;

import cook.mahdi.moradi.UtiInApp.GetConfig;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class SendStaticToServer {

    private static final String TAG = "SendStaticToServer";
    private static String baseUrl = GetConfig.getHOSTNAME();
    private static String apiKey = GetConfig.getApiKey();

    public static void sendStatic(String key , String value){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(baseUrl + "/sendStatic/" + apiKey + "/"  + key + "/" + value).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //TODO : I dont know what to do at this time maybe i add something later
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //TODO : getServerResponse
                String res = response.body().string();
                Log.i(TAG, "onResponse: " + res);

            }
        });
    }
}
