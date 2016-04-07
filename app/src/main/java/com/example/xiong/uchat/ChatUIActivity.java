package com.example.xiong.uchat;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiong.uchat.Util.LogUtil;
import com.example.xiong.uchat.base.BaseActionBarActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by xiong on 2016/4/2.
 */
public class ChatUIActivity extends BaseActionBarActivity {

    private TextView textViewContent;
    private EditText editTextContent;
    private Button buttonSend;

    private Socket socket;
    private BufferedWriter bw = null;
    private BufferedReader br = null;

    private String content = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_ui);
        setTitle("we are here");
        textViewContent = (TextView) findViewById(R.id.txt_chat_content);
        editTextContent = (EditText) findViewById(R.id.edit_content);
        buttonSend = (Button) findViewById(R.id.btn_chat_send);

        AsyncTask<Void, String, Void> asyncTask = new AsyncTask<Void, String, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Looper.prepare();

                try {
                    LogUtil.m("asdasfasfasdfd");
                    socket = new Socket("192.168.1.104", 12345);
                    bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    Toast.makeText(getApplicationContext(), "连接成功", Toast.LENGTH_SHORT).show();
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        publishProgress(line);
                        LogUtil.m(line+"ijijiji======");
                    }

                } catch (UnknownHostException e) {
                    Toast.makeText(getApplicationContext(), "无法连接", Toast.LENGTH_SHORT).show();
                    LogUtil.m("·1·1·1·1·1·1·1·1");
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "无法连接", Toast.LENGTH_SHORT).show();
                    LogUtil.m("·············");
                    e.printStackTrace();
                }

                Looper.loop();
                return null;
            }

            @Override
            protected void onProgressUpdate(String... values) {
                textViewContent.append("某个傻逼说:"+values[0] + "\n");
                super.onProgressUpdate(values);
            }
        };
        asyncTask.execute();


        findViewById(R.id.btn_chat_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send();
            }
        });
    }

    private void send() {
        try {
            textViewContent.append("你说:"+editTextContent.getText().toString() + "\n");
            bw.write(editTextContent.getText().toString() + "\n");
            bw.flush();
            editTextContent.setText("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
