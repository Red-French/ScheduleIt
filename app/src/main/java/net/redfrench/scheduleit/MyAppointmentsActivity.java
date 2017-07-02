package net.redfrench.scheduleit;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Calendar;

public class MyAppointmentsActivity extends AppCompatActivity {
    String name;

    BookingActivity bookingActivity = new BookingActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointments);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        name = getIntent().getStringExtra("name");
        System.out.println("name in MyAppointments is " + name);
        final Calendar cal = Calendar.getInstance();
        System.out.println("day of month =  " + cal.get(Calendar.DAY_OF_MONTH));
        requestPatientSchedule();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void requestPatientSchedule() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {

            // below happens inside GetSchedule.php when the response has been executed
            @Override
            public void onResponse(String response) {  // 'response' is the boolean response from GetSchedule.php (volley provides this response)
                try {
//                    System.out.println(response);
                    JSONArray results = new JSONArray(response);// get 'response' string Volley has given back; 'response' was encoded into JSON string in Booking.php

                    bookingActivity.schedule.clear();
                    bookingActivity.parseSchedule(results);

                    // set chosen date in TextView
                    TextView userMsg = (TextView) findViewById(R.id.tvChosenDate);
                    userMsg.setText("Appointments for " + bookingActivity.appointmentDate);

                } catch (JSONException e) {
                    e.printStackTrace();
                    AlertDialog.Builder builder = new AlertDialog.Builder(BookingActivity.this);
                    builder.setMessage("Schedule retrieval failed")
                            .setNegativeButton("Retry", null)
                            .create()
                            .show();

                    bookingActivity.schedule.clear();
                    bookingActivity.loadSchedule();  // load blank schedule
                }
            }
        };

        ScheduleRequest scheduleRequest = new ScheduleRequest(name, responseListener);
        RequestQueue queue = Volley.newRequestQueue(BookingActivity.this);
        Log.v("scheduleRequest", String.valueOf(scheduleRequest));
        queue.add(scheduleRequest);
    }

}
