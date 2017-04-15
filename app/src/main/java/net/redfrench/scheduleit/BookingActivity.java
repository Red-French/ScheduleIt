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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class BookingActivity extends AppCompatActivity {

    CalendarView calendar;
    ArrayList<String> apmtTimeSlots;
    ArrayAdapter<String> apmtTimesAdptr;
    String dayOfAppointment;
    int chosenMonth;
    int chosenDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        //  ********** INITIALIZE CALENDAR **********
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        final Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        final long time = cal.getTimeInMillis();

        String currentDate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        dayOfAppointment = currentDate;
        Log.v("LOG DATE", currentDate);

        //  ********** GREETING & MSG **********
        final TextView greetingMsg = (TextView) findViewById(R.id.tvGreeting);
        final TextView welcomeMsg = (TextView) findViewById(R.id.tvWelcome);

        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");

        String greeting = "Hello, " + name + "!";
        String bookMsg = "\n Choose a date to see available times below.";
//        String editMsg = "\n You're next appointment is on the --- . Change or cancel below."

        greetingMsg.setText(greeting);
        // if () // if user already booked, show their appointment and ask if they want to change or cancel
        welcomeMsg.setText(bookMsg);
//            else if //
//        greetingMsg.setText(greeting + editMsg);



        //  ********** CALENDAR **********
        calendar = (CalendarView) findViewById(R.id.calendar);
        calendar.setMinDate(time);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

//              TODO:
//              DATABASE CALL FOR CHOSEN DATE'S APPOINTMENTS
//              SET timeSlots ARRAY TO TIMES AND BOOKED APPOINTMENTS
//              DISABLE CLICK FOR POSITIONS OF BOOKED APPOINTMENTS

                month = month + 1;
                dayOfAppointment = null;
                dayOfAppointment = month + "/" + dayOfMonth;
                Log.v("DATE", dayOfAppointment);
//                Toast.makeText(getBaseContext(), "Selected Date: " + month + "/" + dayOfMonth + "/" + year, Toast.LENGTH_LONG).show();
            }
        });


        //  ********** TIMESLOTS **********
        final String[] timeSlots = {
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



        //  ********** LOAD ALL APPOINTMENT TIMES INTO VIEW **********
        apmtTimeSlots = new ArrayList<String>(Arrays.asList(timeSlots));
        apmtTimesAdptr = new ArrayAdapter<String>(this, R.layout.item, R.id.apmtTimeSlotsView, timeSlots);
        final ListView apmtTimesView = (ListView) findViewById(R.id.timeSlotsView);
        apmtTimesView.setAdapter(apmtTimesAdptr);


        //  ********** LOAD USER NAME INTO CHOSEN APPOINTMENT-TIME SLOT **********
        apmtTimesView.setOnItemClickListener(
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String timeIndexClicked = String.valueOf(parent.getItemIdAtPosition(position));
                    String chosenTime = String.valueOf(parent.getItemAtPosition(position));
//                    Toast.makeText(BookingActivity.this, timeIndexClicked, Toast.LENGTH_SHORT).show();
                    Toast.makeText(BookingActivity.this, "You're appointment is set for " + dayOfAppointment + " at " + chosenTime, Toast.LENGTH_LONG).show();

//                    Log.v("LOG", apmtTimeSlots.toString());
                    Log.v("LOG", dayOfAppointment);
                    Log.v("LOG", chosenTime);
//                    TextView newApmt = (TextView) findViewById(R.id.patientNameView);
//                    newApmt.setText("       " + name);

                    switch(position) {
                        case 0:
                            timeSlots[position]="8:00" + "       " + name;
                            break;
                        case 1:
                            timeSlots[position]="8:30" + "       " + name;
                            break;
                        case 2:
                            timeSlots[position]="9:00" + "       " + name;
                            break;
                        case 3:
                            timeSlots[position]="9:30" + "       " + name;
                            break;
                        case 4:
                            timeSlots[position]="10:00" + "       " + name;
                            break;
                        case 5:
                            timeSlots[position]="10:30" + "       " + name;
                            break;
                        case 6:
                            timeSlots[position]="11:00" + "       " + name;
                            break;
                        case 7:
                            timeSlots[position]="11:30" + "       " + name;
                            break;
                        case 8:
                            timeSlots[position]="12:00" + "       " + name;
                            break;
                        case 9:
                            timeSlots[position]="12:30" + "       " + name;
                            break;
                        case 10:
                            timeSlots[position]="1:00" + "       " + name;
                            break;
                        case 11:
                            timeSlots[position]="1:30" + "       " + name;
                            break;
                        case 12:
                            timeSlots[position]="2:00" + "       " + name;
                            break;
                        case 13:
                            timeSlots[position]="2:30" + "       " + name;
                            break;
                        case 14:
                            timeSlots[position]="3:00" + "       " + name;
                            break;
                        case 15:
                            timeSlots[position]="3:30" + "       " + name;
                            break;
                        case 16:
                            timeSlots[position]="4:00" + "       " + name;
                            break;
                        case 17:
                            timeSlots[position]="4:30" + "       " + name;
                            break;
                        case 18:
                            timeSlots[position]="5:00" + "       " + name;
                            break;
                    };
                    apmtTimesAdptr.notifyDataSetChanged();
                }
            }
        );
    }
}

