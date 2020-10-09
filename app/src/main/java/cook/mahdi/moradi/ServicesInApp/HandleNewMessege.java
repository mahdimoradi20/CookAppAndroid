package cook.mahdi.moradi.ServicesInApp;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import co.intentservice.chatui.models.ChatMessage;
import cook.mahdi.moradi.ActivitiesInApp.UpdateActivity;
import cook.mahdi.moradi.ActivitiesInApp.chatActivity;
import cook.mahdi.moradi.DataBaseInApp.CookDB;
import cook.mahdi.moradi.UtiInApp.GetConfig;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HandleNewMessege extends Service {
    private static final String TAG ="HandleNewMessage" ;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String msid = intent.getStringExtra("msid");
        SharedPreferences shared = getSharedPreferences("userHandleFile" , MODE_PRIVATE);
        String isSaved = shared.getString("isTokenSaved" , "false");
        String token = shared.getString("tokenFMessaging" , "notDefined");
        if (isSaved != "false"){
            String baseURL = GetConfig.getHOSTNAME();
            String apikey = GetConfig.getApiKey();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(baseURL + "/getMessage/" + apikey + "/" + msid + "/" + token).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    //TODO :: do something
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    String res = response.body().string();
                    String textBody = "";
                    String textTime = "";
                    Log.i(TAG, "onResponse: " + res);
                    try {
                        JSONObject json = new JSONObject(res);
                        JSONObject msg = json.getJSONObject("msg");
                        textBody = msg.getString("body");
                        textTime = msg.getString("time");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    CookDB db = new CookDB(HandleNewMessege.this);
                    ChatMessage chatMessage  = new ChatMessage(textBody , Long.parseLong(textTime) , ChatMessage.Type.RECEIVED);
                    db.addMessage(chatMessage);
                    db.close();
                    LocalBroadcastManager.getInstance(HandleNewMessege.this).sendBroadcast(new Intent("cook.mahdi.moradi.updatChat"));
                }
            });
        }
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
