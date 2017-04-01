package net.redfrench.scheduleit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

public class BookingActivity extends AppCompatActivity {

    CalendarView calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        final TextView greetingMsg = (TextView) findViewById(R.id.tvGreeting);
        final TextView welcomeMsg = (TextView) findViewById(R.id.tvWelcome);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        String greeting = "Hi, " + name + "!";
        String bookMsg = "\n Choose a date to see available times below.";
//        String editMsg = "\n You're next appointment is on the --- . Change or cancel below."

        greetingMsg.setText(greeting);
        // if () // if user already booked, show their appointment and ask if they want to change or cancel

        welcomeMsg.setText(bookMsg);

//            else if //
//        welcomeMsg.setText(greetingMsg + editMsg);

        calendar = (CalendarView) findViewById(R.id.calendar);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(getBaseContext(), "Selected Date: " + dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
            }
        });
    }
}

