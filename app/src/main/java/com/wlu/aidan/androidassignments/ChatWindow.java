package com.wlu.aidan.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {

    ListView chatView;
    EditText editMessage;
    Button sendButton;

    private ArrayList<String> messageList;
    private ChatAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        chatView = findViewById(R.id.chatView);
        editMessage = findViewById(R.id.editMessage);
        sendButton = findViewById(R.id.sendButton);

        messageList = new ArrayList<>();

        messageAdapter = new ChatAdapter(this);
        chatView.setAdapter(messageAdapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messageList.add(editMessage.getText().toString());
                editMessage.setText("");
                messageAdapter.notifyDataSetChanged();
            }
        });
    }

    private class ChatAdapter extends ArrayAdapter<String> {
        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount() {
            return messageList.size();
        }

        public String getItem(int position) {
            return messageList.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null;

            if(position % 2 == 0) {
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            }

            else {
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }

            TextView message = result.findViewById(R.id.messageText);
            message.setText(getItem(position));

            return result;
        }
    }
}