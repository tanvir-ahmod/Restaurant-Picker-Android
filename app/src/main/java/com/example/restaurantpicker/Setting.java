package com.example.restaurantpicker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.restaurantpicker.SharedPreferenceManager.SharedPrefManager;

public class Setting extends AppCompatActivity {
    Button saveButton;
    EditText serverURLEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        saveButton = findViewById(R.id.save_button);
        serverURLEditText = findViewById(R.id.server_url_edittext);
        setSavedServerURL();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveServerUrl();
                startActivity(new Intent(Setting.this, Login.class));
            }
        });
    }

    private void saveServerUrl() {
        String URL = serverURLEditText.getText().toString();
        if (!TextUtils.isEmpty(URL)) {
            SharedPrefManager.getInstance(this).saveSrverURL(URL);
            Constants.SERVER = URL + Constants.BASE_URL;
        }
    }

    public void setSavedServerURL() {
        String URL = SharedPrefManager.getInstance(this).getServerUrl();
        if (!TextUtils.isEmpty(URL)) {
            serverURLEditText.setText(URL);
        }
    }
}
