package net.redfrench.scheduleit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Config;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.function.ToDoubleBiFunction;

import static net.redfrench.scheduleit.Services.testo;

public class BookingActivity extends AppCompatActivity {

    CalendarView calendar;
    ArrayList<String> apmtTimeSlots;
    ArrayAdapter<String> apmtTimesAdptr;
    ArrayAdapter<String> schdlTimesAdptr;
//    String dayOfAppointment;
    String appointmentDate;
    String chosenMonth;
    String chosenDay;
    int chosenDayforUserMsg;
    JSONObject json = null;

//    private JSONArray result;
    public ArrayList<String> schedule = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        //  ********** INITIALIZE CALENDAR **********
        final Calendar cal = Calendar.getInstance();
//        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        final long time = cal.getTimeInMillis();

//        String currentDate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
//        dayOfAppointment = currentDate;

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

//            TODO: put returned data in 'DOM'
//            TODO: gray-out booked appontments other than this user

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, final int dayOfMonth) {
                chosenDayforUserMsg = dayOfMonth;
                switch (month) {
                    case 0:  chosenMonth = "january";
                        break;
                    case 1:  chosenMonth = "february";
                        break;
                    case 2:  chosenMonth = "march";
                        break;
                    case 3:  chosenMonth = "april";
                        break;
                    case 4:  chosenMonth = "may";
                        break;
                    case 5:  chosenMonth = "june";
                        break;
                    case 6:  chosenMonth = "july";
                        break;
                    case 7:  chosenMonth = "august";
                        break;
                    case 8:  chosenMonth = "septemter";
                        break;
                    case 9: chosenMonth = "october";
                        break;
                    case 10: chosenMonth = "november";
                        break;
                    case 11: chosenMonth = "december";
                        break;
                    default: chosenMonth = "Invalid month";
                        break;
                };

                switch (dayOfMonth) {
                    case 1:  chosenDay = "one";
                        break;
                    case 2:  chosenDay = "two";
                        break;
                    case 3:  chosenDay = "three";
                        break;
                    case 4:  chosenDay = "four";
                        break;
                    case 5:  chosenDay = "five";
                        break;
                    case 6:  chosenDay = "six";
                        break;
                    case 7:  chosenDay = "seven";
                        break;
                    case 8:  chosenDay = "eight";
                        break;
                    case 9:  chosenDay = "nine";
                        break;
                    case 10: chosenDay = "ten";
                        break;
                    case 11: chosenDay = "eleven";
                        break;
                    case 12: chosenDay = "tweleve";
                        break;
                    case 13:  chosenDay = "thirteen";
                        break;
                    case 14:  chosenDay = "fourteen";
                        break;
                    case 15:  chosenDay = "fifteen";
                        break;
                    case 16:  chosenDay = "sixteen";
                        break;
                    case 17:  chosenDay = "seventeen";
                        break;
                    case 18:  chosenDay = "eighteen";
                        break;
                    case 19:  chosenDay = "nineteen";
                        break;
                    case 20:  chosenDay = "twenty";
                        break;
                    case 21:  chosenDay = "twnetyone";
                        break;
                    case 22: chosenDay = "twentytwo";
                        break;
                    case 23: chosenDay = "twentythree";
                        break;
                    case 24: chosenDay = "twentyfour";
                        break;
                    case 25:  chosenDay = "twentyfive";
                        break;
                    case 26:  chosenDay = "twentysix";
                        break;
                    case 27:  chosenDay = "twentyseven";
                        break;
                    case 28:  chosenDay = "twentyeight";
                        break;
                    case 29: chosenDay = "twentynine";
                        break;
                    case 30: chosenDay = "thirty";
                        break;
                    case 31: chosenDay = "thirtyone";
                        break;
                    default: chosenDay = "Invalid day";
                        break;
                }
                month = month + 1;
                appointmentDate = month + "/" + dayOfMonth;  // for user message


                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    // below happens inside GetSchedule.php when the response has been executed
                    @Override
                    public void onResponse(String response) {  // 'response' is the boolean response from GetSchedule.php (volley provides this response)
                        try {
//                            JSONObject successMsg = new JSONObject(response);
//                            boolean success = successMsg.getBoolean("success");  // TODO: get success msg from GetSchedule.php

                            JSONArray results = new JSONArray(response);// get 'response' string Volley has given back; 'response' was encoded into JSON string in Booking.php

//                            System.out.println(results.length());
//                            for (int i=0; i<results.length(); i++) {
//                                JSONObject obj = results.getJSONObject(i);
//                                System.out.println(obj);
                                getSchedule(results);
//                            }

                            if(true) {  // TODO: get success msg from GetSchedule.php and check here
                                AlertDialog.Builder builder = new AlertDialog.Builder(BookingActivity.this);
                                builder.setMessage("Schedule retrieval success")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(BookingActivity.this);
                                builder.setMessage("Schedule retrieval failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                ScheduleRequest scheduleRequest = new ScheduleRequest(name, chosenMonth, chosenDay, responseListener);
                RequestQueue queue = Volley.newRequestQueue(BookingActivity.this);
                Log.v("scheduleRequest", String.valueOf(scheduleRequest));
                queue.add(scheduleRequest);
            }

            public void getSchedule(JSONArray daysSchedule) {
//                Log.v("LOG_SCDL", daysSchedule.toString());
                for (int i = 0; i < daysSchedule.length(); i++) {
                    try {
                        JSONObject theSchdl = daysSchedule.getJSONObject(i);
                        Iterator<String> keys = theSchdl.keys();

                        while (keys.hasNext()) {
                            String key = keys.next();
                            System.out.println("Time: " + key + " - " + theSchdl.get(key));
                            String thisTimeSlot = key + " - " + theSchdl.get(key);
                            schedule.add(thisTimeSlot);
                            loadSchedule();
//                            System.out.println("schedule =" + schedule);
//                            apmtTimeSlots.add(key);
//                            apmtTimeSlots.add((String) theSchdl.get(key));
//                            timeSlots[i] = key;
                        }
//                        System.out.println("timeslots array =" + apmtTimeSlots);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
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
//        apmtTimeSlots = new ArrayList<String>(Arrays.asList(timeSlots));
        apmtTimesAdptr = new ArrayAdapter<String>(this, R.layout.item, R.id.apmtTimeSlotsView, timeSlots);
        final ListView apmtTimesView = (ListView) findViewById(R.id.timeSlotsView);
        apmtTimesView.setAdapter(apmtTimesAdptr);


        //  ********** LOAD USER NAME INTO CHOSEN APPOINTMENT-TIME SLOT **********
        apmtTimesView.setOnItemClickListener(
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String timeIndexClicked = String.valueOf(parent.getItemIdAtPosition(position));  // get reference to clicked item
                    String chosenTime = String.valueOf(parent.getItemAtPosition(position));  // gets sent to database; also used in user message
//                    Toast.makeText(BookingActivity.this, timeIndexClicked, Toast.LENGTH_SHORT).show();
                    Toast.makeText(BookingActivity.this, "You're appointment is set for " + appointmentDate + " at " + chosenTime, Toast.LENGTH_LONG).show();

//                    Log.v("LOG", apmtTimeSlots.toString());
//                    Log.v("dayOfAppointment", dayOfAppointment);
//                    Log.v("timeIndexClicked", timeIndexClicked);
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
                    testo();  // test service

                    // responseListener is a PARAMETER of BookingRequest()
                    Response.Listener<String> responseListener = new Response.Listener<String>() {

                        // below happens inside Booking.php when the response has been executed
                        @Override
                        public void onResponse(String response) {  // 'response' is the boolean response from Booking.php (volley provides this response)
                            try {
                                JSONObject jsonResponse = new JSONObject(response);  // gets 'response' string Volley has given back; 'response' was encoded into JSON string in Booking.php
                                boolean success = jsonResponse.getBoolean("success");  // 'success' given a boolean value in Booking.php
                                if(success) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(BookingActivity.this);
                                    builder.setMessage("Booking success")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();

                                    String apmtMonth = chosenMonth.toUpperCase();
                                    TextView apmtBook = (TextView) findViewById(R.id.tvGreeting);
                                    apmtBook.setText("We'll see you on " +  apmtMonth + " " + chosenDayforUserMsg + "!");

                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(BookingActivity.this);
                                    builder.setMessage("Booking failed")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();

                                    TextView apmtBook = (TextView) findViewById(R.id.tvGreeting);
                                    apmtBook.setText("Oops. Something went wrong. Please call 790-0000 to book your appointment.");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    BookingRequest bookingRequest = new BookingRequest(name, chosenMonth, chosenDay, chosenTime, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(BookingActivity.this);
                    Log.v("bookingRequest", String.valueOf(bookingRequest));
                    queue.add(bookingRequest);
                }
            }
        );
    }


    public void loadSchedule() {
        schdlTimesAdptr = new ArrayAdapter<String>(this, R.layout.item, R.id.apmtTimeSlotsView, schedule);
        final ListView apmtTimesView = (ListView) findViewById(R.id.timeSlotsView);
        apmtTimesView.setAdapter(schdlTimesAdptr);
    }

}
