package com.rayhahah.advancedanim;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void doClick(View view) {
        switch (view.getId()) {
            case R.id.btn_vector:
                startActivity(new Intent(this, VectorActivity.class));
                break;
        }

    }
}
