package cook.mahdi.moradi.ServicesInApp;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SendTokenToServer extends Service {


    private static final String TAG = "Send Token:" ;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String token = intent.getStringExtra("token");
        String baseUrl = "Your host";
        String apiKey = "Your api key";
        //TODO :: do basic server thing
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(baseUrl + "/saveUserToken/" + apiKey + "/" + token).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                SendTokenToServer.this.stopSelf();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                Log.e(TAG, "onResponse: " + res);
                if(res.equals("OK")) {
                    SharedPreferences shared = getSharedPreferences("userHandleFile", MODE_PRIVATE);
                    SharedPreferences.Editor editor = shared.edit();
                    editor.putString("isTokenSaved", "true");
                    editor.commit();
                    SendTokenToServer.this.stopSelf();
                }
                else{
                    SendTokenToServer.this.stopSelf();
                }
            }
        });


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
