package com.wlu.aidan.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.content.Context;

import android.util.Log;
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
    public String ACTIVITY_NAME = this.getClass().getSimpleName();
    private ArrayList<String> messageList;
    private ChatAdapter messageAdapter;
    private static ChatDatabaseHelper databaseHelper;
    private static SQLiteDatabase database;

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

        databaseHelper = new ChatDatabaseHelper(this);
        database = databaseHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery(databaseHelper.GET_ALL_MESSAGES_COMMAND, null);
        cursor.moveToFirst();
        int index;

        while(!cursor.isAfterLast()) {
            index = cursor.getColumnIndex(databaseHelper.KEY_MESSAGE);
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString(index));
            messageList.add(cursor.getString(index));
            cursor.moveToNext();
        }

        Log.i(ACTIVITY_NAME, "Cursor’s  column count =" + cursor.getColumnCount());

        for(int i = 0; i < cursor.getColumnCount(); i++) {
            Log.i(ACTIVITY_NAME, "Column Name: "+ cursor.getColumnName(i));
        }

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messageList.add(editMessage.getText().toString());

                ContentValues values = new ContentValues();
                values.put(databaseHelper.KEY_MESSAGE, editMessage.getText().toString());
                database.insert(databaseHelper.TABLE_NAME, null, values);

                editMessage.setText("");
                messageAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        database.close();
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