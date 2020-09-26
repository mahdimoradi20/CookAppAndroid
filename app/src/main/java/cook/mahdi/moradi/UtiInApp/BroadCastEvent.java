package cook.mahdi.moradi.UtiInApp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cook.mahdi.moradi.ServicesInApp.HandleNewRecipe;
import cook.mahdi.moradi.ServicesInApp.SaveTokenService;

public class BroadCastEvent extends BroadcastReceiver {
    private static final String TAG = "Event" ;

    @Override
    public void onReceive(Context context, Intent intent) {
        int type = intent.getIntExtra("type" , 0);
        if(type == 1){
            Intent intent1 = new Intent(context , SaveTokenService.class);
            intent1.putExtra("appToken" , intent.getStringExtra("token"));
            context.startService(intent1);
        }
        else if(type == 2){
            Intent intent1 = new Intent(context , HandleNewRecipe.class); //TODO : add a service to controll the new Recipe
            intent1.putExtra("foodName" , intent.getStringExtra("content"));
            intent1.putExtra("foodCount" , intent.getStringExtra("count"));
            context.startService(intent1);
        }
    }
}
