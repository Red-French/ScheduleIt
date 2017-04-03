package net.redfrench.scheduleit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class BookingActivity extends AppCompatActivity {

    CalendarView calendar;
    ArrayList<String> itemList;
    ArrayAdapter<String> timeslotAdptr;
    String dayOfAppointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        long time = cal.getTimeInMillis();
//        DatePicker datepicker = (DatePicker) findViewById(R.id.datePicker);
//        datepicker.setMinDate(time);


        String currentDate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        dayOfAppointment = currentDate;
        Log.v("LOG DATE", currentDate);

        //  ********** GREETING & MSG **********
//        final TextView greetingMsg = (TextView) findViewById(R.id.tvGreeting);
        final TextView welcomeMsg = (TextView) findViewById(R.id.tvWelcome);

        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");

        String greeting = "Hi, " + name + "!";
        String bookMsg = "\n Choose a date to see available times below.";
//        String editMsg = "\n You're next appointment is on the --- . Change or cancel below."

//        greetingMsg.setText(greeting);
        // if () // if user already booked, show their appointment and ask if they want to change or cancel

        welcomeMsg.setText(bookMsg);

//            else if //
//        welcomeMsg.setText(greetingMsg + editMsg);


        //  ********** CALENDAR **********
        calendar = (CalendarView) findViewById(R.id.calendar);
        calendar.setMinDate(time);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                month = month + 1;
                dayOfAppointment = month + "/" + dayOfMonth + "/" + year;
                Log.v("LOG", dayOfAppointment);
                Toast.makeText(getBaseContext(), "Selected Date: " + month + "/" + dayOfMonth + "/" + year, Toast.LENGTH_LONG).show();
            }
        });


        //  ********** TIMESLOTS **********
        String[] timeslots = {
                "8:00", "8:30",
                "9:00", "9:30",
                "10:00", "10:30",
                "11:00", "11:30",
                "12:00", "12:30",
                "1:00", "1:30",
                "2:00", "2:30",
                "3:00", "3:30",
                "4:00", "4:30",
                "5:00"
        };

        itemList = new ArrayList<String>(Arrays.asList(timeslots));
        timeslotAdptr = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, timeslots);  // by default, 1st parameter is 'this'
                                                                                                         // 2nd parameter is type of list
                                                                                                         // 3rd parameter is name of list
        ListView timeslotView = (ListView) findViewById(R.id.timeslots);
        timeslotView.setAdapter(timeslotAdptr);

        timeslotView.setOnItemClickListener(
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String timeslot = String.valueOf(parent.getItemIdAtPosition(position));
                    String apmtTime = String.valueOf(parent.getItemAtPosition(position));  // gets array position
//                    Toast.makeText(BookingActivity.this, timeslot, Toast.LENGTH_SHORT).show();
                    Toast.makeText(BookingActivity.this, name, Toast.LENGTH_SHORT).show();
                    Toast.makeText(BookingActivity.this, dayOfAppointment, Toast.LENGTH_SHORT).show();
                    Toast.makeText(BookingActivity.this, apmtTime, Toast.LENGTH_LONG).show();
                    itemList.add(apmtTime);
                    timeslotAdptr.notifyDataSetChanged();
                    Log.v("LOG", itemList.toString());
                    Log.v("LOG", dayOfAppointment);
                    Log.v("LOG", apmtTime);
                }
            }
        );


//        timeslotView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String newItem = String.valueOf().getText().toString();  // get user data
//                // add new item to arraylist
//                itemList.add(newItem);  // add user data to original item list
//                // notify listview of data changed
//                adapter.notifyDataSetChanged();  // notify adapter of data change
//            }
//        });

    }
}

