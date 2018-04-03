package core;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;


/**
 * Created by D on 2/19/2018.
 */

public class DemoAD extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        try {
//            Log.e("hehe", run("https://docs.google.com/spreadsheets/d/e/2PACX-1vQDZcDo3vg4Z3wfbrixnRHYAgmEcfLhrPj4LtRbxzU5RB66vrUgDnQQU_MoCZ-de4lZd8IL3t-rXZjL/pub?output=csv"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

//    OkHttpClient client = new OkHttpClient();
//
//    String run(String url) throws IOException {
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//
//        Response response = client.newCall(request).execute();
//        return response.body().string();
//    }
}
