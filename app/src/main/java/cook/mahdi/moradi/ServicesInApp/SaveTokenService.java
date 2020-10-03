package cook.mahdi.moradi.ServicesInApp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class SaveTokenService extends Service {
    private static final String TAG = "Handle Service" ;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        SharedPreferences shared = getSharedPreferences("userHandleFile" , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString("tokenFMessaging" , intent.getStringExtra("appToken"));
        editor.putString("isTokenSaved" , "false");
        editor.commit();
        Intent intent1 = new Intent(this , SendTokenToServer.class);
        intent1.putExtra("token" , intent.getStringExtra("appToken"));
        startService(intent1);
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
