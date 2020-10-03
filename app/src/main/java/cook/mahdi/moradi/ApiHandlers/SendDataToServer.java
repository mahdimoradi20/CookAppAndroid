package cook.mahdi.moradi.ApiHandlers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;
import java.net.Authenticator;

import co.intentservice.chatui.models.ChatMessage;
import cook.mahdi.moradi.UtiInApp.GetConfig;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class SendDataToServer {

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
    public static void sendMessageToserver(ChatMessage chatMessage , Context context){
        SharedPreferences shared = context.getSharedPreferences("userHandleFile" , context.MODE_PRIVATE);
        String isSaved = shared.getString("isTokenSaved" , "false");
        String token = shared.getString("tokenFMessaging" , "notDefined");
        OkHttpClient client = new OkHttpClient();
        if(isSaved != "false") {
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("token", token)
                    .addFormDataPart("text", chatMessage.getMessage())
                    .addFormDataPart("time", String.valueOf(chatMessage.getTimestamp()))
                    .build();
            Request request = new Request.Builder().url(baseUrl + "/sendMessages/" + apiKey)
                    .post(requestBody)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    //TODO :: onFailure task
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    //TODO :: onResponse task
                    String res = response.body().string();
                    Log.i(TAG, "onResponse: " + res);
                }
            });
        }
    }
}
