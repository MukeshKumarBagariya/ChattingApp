package com.example.chatforall;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class ChatUserActivity extends AppCompatActivity {
    private ListView chatList;
    private ArrayList<String> chatMessage;
    private ArrayAdapter adapter;
    private String selectedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_user);
        selectedUser = getIntent().getStringExtra("selectedUser");
        chatList = findViewById(R.id.chatList);
        chatMessage = new ArrayList<>();
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,chatMessage);
        try {
            ParseQuery<ParseObject> firstUserChat = ParseQuery.getQuery("chat");
            ParseQuery<ParseObject> secondUserChat = ParseQuery.getQuery("chat");

            firstUserChat.whereEqualTo("senderUserName",ParseUser.getCurrentUser().getUsername());
            firstUserChat.whereEqualTo("receiverUserName",selectedUser);

            secondUserChat.whereEqualTo("receiverUserName",ParseUser.getCurrentUser().getUsername());
            secondUserChat.whereEqualTo("senderUserName",selectedUser);

            ArrayList<ParseQuery<ParseObject>> allQuaries = new ArrayList<>();
            allQuaries.add(firstUserChat);
            allQuaries.add(secondUserChat);

            ParseQuery<ParseObject> myQuery = ParseQuery.or(allQuaries);
            myQuery.orderByAscending("createdAt");
            myQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null && objects.size() > 0){
                        for (ParseObject chatObject : objects){
                            String sendMessage = chatObject.get("message") + "";
                            if (chatObject.get("senderUserName").equals(ParseUser.getCurrentUser().getUsername())){
                                sendMessage = ParseUser.getCurrentUser().getUsername() + " : "+ sendMessage;
                            }
                            if (chatObject.get("senderUserName").equals(selectedUser)){
                                sendMessage = selectedUser + " : " + sendMessage;
                            }
                            chatMessage.add(sendMessage);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            });

        } catch (Exception e){
            e.printStackTrace();
        }
        chatList.setAdapter(adapter);
        findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText messageText = findViewById(R.id.messageText);
                ParseObject parseObject = new ParseObject("chat");
                parseObject.put("senderUserName", ParseUser.getCurrentUser().getUsername());
                parseObject.put("receiverUserName",selectedUser);
                parseObject.put("message",messageText.getText().toString());
                parseObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null){
                            chatMessage.add(ParseUser.getCurrentUser().getUsername() + " : "+ messageText.getText().toString());
                            messageText.setText("");
                            messageText.setHint("Type a message");
                            adapter.notifyDataSetChanged();

                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ChatUserActivity.this);
                            builder.setMessage("Please check your internet connection");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //
                                }
                            });
                        }
                    }
                });
            }
        });

    }
}