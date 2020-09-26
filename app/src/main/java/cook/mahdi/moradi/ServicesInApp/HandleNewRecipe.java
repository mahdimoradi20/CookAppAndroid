package cook.mahdi.moradi.ServicesInApp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import cook.mahdi.moradi.ActivitiesInApp.MainMenu;
import cook.mahdi.moradi.R;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cook.mahdi.moradi.DataBaseInApp.CookDB;
import cook.mahdi.moradi.DataModels.Recipe;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HandleNewRecipe extends Service {
    private static final String TAG = "HandleNewRecipe";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String food_name = intent.getStringExtra("foodName");
        String food_count = intent.getStringExtra("foodCount");
        SharedPreferences shared = getSharedPreferences("setting" , MODE_PRIVATE);
        String if_show_notif = shared.getString("chkShowNotif" , "false");
        if(if_show_notif == "true"){
            //TODO :: show a notif that contains food name and tell users that they got new recipe!
            Intent intent1 = new Intent(this, MainMenu.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent1,
                    PendingIntent.FLAG_ONE_SHOT);

            String channelId = "defchid";
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(this, channelId)
                            .setSmallIcon(R.drawable.ic_launcher_background)
                            .setContentTitle("دریافت دستور پخت جدید")
                            .setContentText("دیتور پخت های جدیدی به برنامه اضافه شدند")
                            .setAutoCancel(true)
                            .setSound(defaultSoundUri)
                            .setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            // Since android Oreo notification channel is needed.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(channelId,
                        "Channel human readable title",
                        NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
            }

            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        }
        String baseUrl = "Your host";
        String apiKey = "Your apikey";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(baseUrl + "/getNewFoods/" + apiKey).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: "  + e.toString());
                stopSelf();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String res = response.body().string();
                    Log.e(TAG, "onResponse: " + res);
                    JSONObject json = new JSONObject(res);
                    JSONArray recs = json.getJSONArray("recipes");
                    List<Recipe> recipes = new ArrayList<>();
                    for(int i=0 ; i < recs.length() ; i++){
                        Recipe recipe = new Recipe();
                        recipe.setId(recs.getJSONObject(i).getInt("id"));
                        recipe.setCatid(recs.getJSONObject(i).getInt("catid"));
                        recipe.setIngredient(recs.getJSONObject(i).getString("ing"));
                        recipe.setRec(recs.getJSONObject(i).getString("rec"));
                        recipe.setName(recs.getJSONObject(i).getString("name"));
                        recipe.setPic(recs.getJSONObject(i).getString("pic"));
                        recipe.setRes1(recs.getJSONObject(i).getString("res1"));
                        recipe.setRes2(recs.getJSONObject(i).getString("res2"));
                        recipes.add(recipe);
                    }
                    CookDB cookDB = new CookDB(HandleNewRecipe.this);
                    Log.i(TAG, "onResponse: i am here to");
                    cookDB.saveNewRecipes(recipes);
                    cookDB.close();
                } catch (JSONException e) {
                    Log.i(TAG, "onResponse: i am here tovvvv" + e.toString());
                    e.printStackTrace();
                }

                stopSelf();
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
