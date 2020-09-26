package cook.mahdi.moradi.ActivitiesInApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import cook.mahdi.moradi.R;

public class setting extends AppCompatActivity {
    private CheckBox chkGetNew;
    private CheckBox chkShowNotif;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        chkGetNew = findViewById(R.id.chkGetNewRec);
        chkShowNotif = findViewById(R.id.chkShowNotif);
        chkGetNew.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    UpdateSettingFile("chkGetNew" , "true");
                    chkShowNotif.setEnabled(true);
                    FirebaseMessaging.getInstance().subscribeToTopic("getUpdates")
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                private static final String TAG = "this class";

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                   /* String msg = "was successfull";
                                    if (!task.isSuccessful()) {
                                        msg = "was not succsessful";
                                    }
                                    Log.d(TAG, msg);
                                    Toast.makeText(setting.this, msg, Toast.LENGTH_SHORT).show();*/
                                }
                            });

                }
                else{
                    UpdateSettingFile("chkGetNew" , "false");
                    chkShowNotif.setEnabled(false);
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("getUpdates")
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                private static final String TAG = "this class";

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                   /* String msg = "was successfull un";
                                    if (!task.isSuccessful()) {
                                        msg = "was not succsessful un";
                                    }
                                    Log.d(TAG, msg);
                                    Toast.makeText(setting.this, msg, Toast.LENGTH_SHORT).show();*/
                                }
                            });
                }
            }
        });
        chkShowNotif.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    UpdateSettingFile("chkShowNotif" , "true");
                }else{
                    UpdateSettingFile("chkShowNotif" , "false");
                }
            }
        });
        setUpValues();

    }
    private void setUpValues(){
        SharedPreferences shared = getSharedPreferences("setting" , MODE_PRIVATE);
        String chkGetNewStatus = shared.getString("chkGetNew" , "defualt");
        String chkShowNotifStatus = shared.getString("chkShowNotif" , "defualt");
        if (!chkGetNewStatus.equals("defualt")){
            if(chkGetNewStatus.equals("true")) {
                chkGetNew.setChecked(true);
            }
            else{
                    chkGetNew.setChecked(false);
                    chkShowNotif.setEnabled(false);
                }
        }
        if(!chkGetNewStatus.equals("defualt")){
            if(chkShowNotifStatus.equals("true")){
                chkShowNotif.setChecked(true);
            }
            else{
                chkShowNotif.setChecked(false);
            }
        }
    }
    private void UpdateSettingFile(String key , String value){
        SharedPreferences shared = getSharedPreferences("setting" , MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString(key , value);
        editor.commit();
    }
}
