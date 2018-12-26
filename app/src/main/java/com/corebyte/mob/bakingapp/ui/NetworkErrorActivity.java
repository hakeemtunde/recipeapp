package com.corebyte.mob.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.corebyte.mob.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NetworkErrorActivity extends AppCompatActivity {

    public static final String NETWORK_ERROR_EXTRA = "NETWORK_ERROR_MSG";

    @BindView(R.id.retry_btn)
    Button retry_btn;
    @BindView(R.id.network_error_tv)
    TextView errorMessage_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_error);

        ButterKnife.bind(this);

        displayNetworkError(getIntent());
    }

    @OnClick(R.id.retry_btn)
    public void onClick() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    private void displayNetworkError(Intent intent) {

        String networkError = intent
                .getStringExtra(NETWORK_ERROR_EXTRA);

        errorMessage_tv.setText(networkError);
    }
}
