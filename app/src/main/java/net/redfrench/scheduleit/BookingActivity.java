package net.redfrench.scheduleit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class BookingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final TextView welcomeMsg = (TextView) findViewById(R.id.tvWelcome);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String username = intent.getStringExtra("username");
        int age = intent.getIntExtra("age", -1);  // getIntExtra requires initializing int value (-1 will indicate a value wasn't passed)

        String message = name + ", welcome to your user area.";
        welcomeMsg.setText(message);
        etUsername.setText(username);
    }
}
