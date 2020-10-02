package cook.mahdi.moradi.ActivitiesInApp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import co.intentservice.chatui.ChatView;
import co.intentservice.chatui.models.ChatMessage;
import cook.mahdi.moradi.DataBaseInApp.CookDB;
import cook.mahdi.moradi.R;
public class chatActivity extends AppCompatActivity {
    private ChatView chatView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        chatView = findViewById(R.id.chat_view);
        getAndShowMesages();
        chatView.setOnSentMessageListener(new ChatView.OnSentMessageListener(){
            @Override
            public boolean sendMessage(ChatMessage chatMessage){
                CookDB db = new CookDB(chatActivity.this);
                db.addMessage(chatMessage);
                db.close();
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
}
