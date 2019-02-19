package com.github.vasiliz.vkclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //add comment
        init();
    }

    private void init() {

        mTextView = findViewById(R.id.all);

        //init all views here
    }
}
