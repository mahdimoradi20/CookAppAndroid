package cook.mahdi.moradi.ActivitiesInApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.intentservice.chatui.ChatView;
import co.intentservice.chatui.models.ChatMessage;
import cook.mahdi.moradi.ApiHandlers.SendDataToServer;
import cook.mahdi.moradi.DataBaseInApp.CookDB;
import cook.mahdi.moradi.R;
import cook.mahdi.moradi.UtiInApp.BroadCastEvent;

public class chatActivity extends AppCompatActivity {
    private static final String TAG = "ChatActivity" ;
    private ChatView chatView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        LocalBroadcastManager.getInstance(this).registerReceiver(UpdateChatReciver , new IntentFilter("cook.mahdi.moradi.updatChat"));
        chatView = findViewById(R.id.chat_view);
        getAndShowMesages();
        chatView.setOnSentMessageListener(new ChatView.OnSentMessageListener(){
            @Override
            public boolean sendMessage(ChatMessage chatMessage){
                CookDB db = new CookDB(chatActivity.this);
                db.addMessage(chatMessage);
                db.close();
                SendDataToServer.sendMessageToserver(chatMessage , chatActivity.this);
                return true;
            }
        });
    }
    private void getAndShowMesages(){
        CookDB cookDB = new CookDB(this);
        List<ChatMessage> messages = cookDB.getAllMessages();
        cookDB.close();
        chatView.addMessages((ArrayList<ChatMessage>) messages);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(UpdateChatReciver);
    }
    private BroadcastReceiver UpdateChatReciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            chatView.clearMessages();
            getAndShowMesages();
        }
    };
}

