package com.example.doubt_hub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doubt_hub.databinding.ChatItemBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView welcomeTextView;
    EditText messageEdit;
    ImageButton sndBtn;
    List<Message> messageList;
    MessageAdapter messageAdapter;
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerView = findViewById(R.id.rv_bot);
        welcomeTextView = findViewById(R.id.welcomeText);
        messageEdit = findViewById(R.id.msgEditText);
        sndBtn = findViewById(R.id.sendBtn);
        messageList = new ArrayList<>();

        //recycler view setup
        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        recyclerView.setLayoutManager(llm);

        sndBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = messageEdit.getText().toString().trim();
                addToChat(question,Message.SENT_BY_ME);
                messageEdit.getText().clear();
                callAPI(question);
                welcomeTextView.setVisibility(View.GONE);
            }
        });

    }

    void addToChat(String message,String sentBy){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageList.add(new Message(message, sentBy));
                messageAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
            }
        });
    }

    void addResponse(String response){
        messageList.remove(messageList.size()-1);
        addToChat(response,Message.SENT_BY_BOT);
    }

    void callAPI(String question){
        //okhttp
        messageList.add(new Message("Typing....",Message.SENT_BY_BOT));
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("model","text-davinci-003");
            jsonBody.put("prompt",question);
            jsonBody.put("max_tokens",4000);
            jsonBody.put("temperature",0);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        RequestBody body = RequestBody.create(jsonBody.toString(),JSON);
        Request request =new Request.Builder()
                .url("https://api.openai.com/v1/completions")
                .header("Authorization","Bearer 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")
                .post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                addResponse("Some error plz try again"+e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("choices");
                        String result = jsonArray.getJSONObject(0).getString("text");
                        addResponse(result.trim());
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }else{
                    addResponse("Failed due to"+response.body().toString());
                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ChatActivity.this,MainActivity.class));
        finish();
    }
}