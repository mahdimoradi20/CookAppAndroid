package cook.mahdi.moradi.ActivitiesInApp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import co.intentservice.chatui.ChatView;
import co.intentservice.chatui.models.ChatMessage;
import cook.mahdi.moradi.R;
public class chatActivity extends AppCompatActivity {
    private ChatView chatView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        chatView = findViewById(R.id.chat_view);
        chatView.setOnSentMessageListener(new ChatView.OnSentMessageListener() {
            @Override
            public boolean sendMessage(ChatMessage chatMessage) {
                return true;
            }
        });
    }
}
