package com.example.kevin.androidserver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.kevin.androidserver.test1.ClientActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindView();
    }

    private void bindView() {
        findViewById(R.id.test1).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.test1:
                startActivity(new Intent(this, ClientActivity.class));
                break;
        }
    }
}
