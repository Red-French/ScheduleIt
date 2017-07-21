package net.redfrench.scheduleit;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;


public class BookingActivity extends AppCompatActivity {

    CalendarView calendar;
    ArrayList<String> apmtTimeSlots;
    ArrayAdapter<String> apmtTimesAdptr;
    ArrayAdapter<String> schdlTimesAdptr;

    String name;
    public String appointmentDate;
    String chosenMonth;
    String chosenDay;
    String chosenTime;

    public ArrayList<String> schedule = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        //  ********** INITIALIZE CALENDAR **********
        final Calendar cal = Calendar.getInstance();

        final long currentTime = cal.getTimeInMillis();  // for setMinDate() on calendar
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        final long nextMonth = cal.getTimeInMillis();  // for setMaxDate() on calendar


        //  ********** GREET USER **********
        final TextView userMsgArea = (TextView) findViewById(R.id.tvChosenDate);
        name = getIntent().getStringExtra("name");
        String greeting = "Hello, " + name + "!";
        userMsgArea.setText(greeting);

        // if () // if user already booked, show their appointment and ask if they want to change or cancel
//        welcomeMsg.setText(bookMsg);
//            else if //
//        userMsgArea.setText(greeting + editMsg);


        //  ********** CALENDAR **********
        calendar = (CalendarView) findViewById(R.id.calendar);
        calendar.setMinDate(currentTime);
        calendar.setMaxDate(nextMonth);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, final int dayOfMonth) {
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
                    case 8:  chosenMonth = "september";
                        break;
                    case 9: chosenMonth = "october";
                        break;
                    case 10: chosenMonth = "november";
                        break;
                    case 11: chosenMonth = "december";
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
                    case 12: chosenDay = "twelve";
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
                    case 21:  chosenDay = "twentyone";
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
                }
                month = month + 1;
                appointmentDate = month + "/" + dayOfMonth;  // for user message
                Toast.makeText(BookingActivity.this, "Just a moment.", Toast.LENGTH_SHORT).show();
                requestSchedule();
            }

        });


        //  ********** TIMESLOTS **********
//        final String[] timeSlots = {
//                "8:00", "8:30",
//                "9:00", "9:30",
//                "10:00", "10:30",
//                "11:00", "11:30",
//                "12:00", "12:30",
//                "1:00", "1:30",
//                "2:00", "2:30",
//                "3:00", "3:30",
//                "4:00", "4:30",
//                "5:00"
//        };

        //  ********** LOAD ALL APPOINTMENT TIMES INTO VIEW **********
//        apmtTimeSlots = new ArrayList<String>(Arrays.asList(timeSlots));
        apmtTimesAdptr = new ArrayAdapter<String>(this, R.layout.item, R.id.apmtTimeSlotsView, schedule);
        final ListView apmtTimesView = (ListView) findViewById(R.id.timeSlotsView);
        apmtTimesView.setAdapter(apmtTimesAdptr);


        //  ********** BOOK APPOINTMENT **********
        apmtTimesView.setOnItemClickListener(
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(BookingActivity.this, "Please wait for confirmation.", Toast.LENGTH_LONG).show();
                    int timeIndexClicked = (int)parent.getItemIdAtPosition(position);  // get reference to clicked item

                    chosenTime = "";

                    switch(timeIndexClicked) {
                        case 0:
                            chosenTime = "8:00";
                            break;
                        case 1:
                            chosenTime = "8:30";
                            break;
                        case 2:
                            chosenTime = "9:00";
                            break;
                        case 3:
                            chosenTime = "9:30";
                            break;
                        case 4:
                            chosenTime = "10:00";
                            break;
                        case 5:
                            chosenTime = "10:30";
                            break;
                        case 6:
                            chosenTime = "11:00";
                            break;
                        case 7:
                            chosenTime = "11:30";
                            break;
                        case 8:
                            chosenTime = "12:00";
                            break;
                        case 9:
                            chosenTime = "12:30";
                            break;
                        case 10:
                            chosenTime = "1:00";
                            break;
                        case 11:
                            chosenTime = "1:30";
                            break;
                        case 12:
                            chosenTime = "2:00";
                            break;
                        case 13:
                            chosenTime = "2:30";
                            break;
                        case 14:
                            chosenTime = "3:00";
                            break;
                        case 15:
                            chosenTime = "3:30";
                            break;
                        case 16:
                            chosenTime = "4:00";
                            break;
                        case 17:
                            chosenTime = "4:30";
                            break;
                        case 18:
                            chosenTime = "5:00";
                            break;
                    };


                    Response.Listener<String> responseListener = new Response.Listener<String>() {  // responseListener is a PARAMETER of BookingRequest()

                        // below happens when response has been executed
                        @Override
                        public void onResponse(String response) {  // 'response' is the boolean response from CheckSchedule.php (volley provides this response)
                            try {
                                JSONObject jsonResponse = new JSONObject(response);  // gets 'response' string Volley has given back; 'response' was encoded into JSON string in CheckSchedule.php
                                boolean available = jsonResponse.getBoolean("available");  // 'success' given a boolean value in CheckSchedule.php

//                                Iterator x = jsonResponse.keys();
//                                JSONArray jsonArray = new JSONArray();
//                                while (x.hasNext()){
//                                    String key = (String) x.next();
//                                    jsonArray.put(jsonResponse.get(key));
//                                }

                                if(available) {

                                    bookIt();

                                } else {
                                    cancel(jsonResponse);
//                                    AlertDialog.Builder builder = new AlertDialog.Builder(BookingActivity.this);
//                                    builder.setMessage("Sorry, but that time is taken.")
//                                            .setNegativeButton("Please retry", null)
//                                            .create()
//                                            .show();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    CheckScheduleRequest checkedRequest = new CheckScheduleRequest(name, chosenMonth, chosenDay, chosenTime, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(BookingActivity.this);
                    Log.v("check schedule reqst", String.valueOf(checkedRequest));
                    queue.add(checkedRequest);

                }  // end onItemClick()
            }  // end OnItemClickListener()
        );  // end setOnItemClickListener()

    }  // end onCreate()

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.myAppointments) {
            Intent intent = new Intent(this, MyAppointmentsActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("chosenMonth", chosenMonth);

            startActivity(intent);
        }
//        else if (id == R.id.balance) {
//            Intent intent = new Intent(this, );
//            startActivity(intent);
//        }
//
        return super.onOptionsItemSelected(item);
    }


    public void cancel(JSONObject daysSchedule) {
        System.out.println("INSIDE CANCEL!!!!" + daysSchedule);
//        for (int i = 0; i < daysSchedule.length(); i++) {
//            try {
//                JSONObject theSchdl = daysSchedule.getJSONObject(i);
//                Iterator<String> keys = theSchdl.keys();
//
//                while (keys.hasNext()) {
//                    String key = keys.next();
//                    System.out.println("Time: " + key + " - " + theSchdl.get(key));
////                    String thisTimeSlot = key + "       " + theSchdl.get(key);
////                    schedule.add(thisTimeSlot);
//                }
//
////                loadSchedule();
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
    }


    public void bookIt() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {  // responseListener is a PARAMETER of BookingRequest()

            // below happens when response has been executed
            @Override
            public void onResponse(String response) {  // 'response' is the boolean response from Booking.php (volley provides this response)
                try {
                    JSONObject jsonResponse = new JSONObject(response);  // gets 'response' string Volley has given back; 'response' was encoded into JSON string in Booking.php
                    boolean success = jsonResponse.getBoolean("success");  // 'success' given a boolean value in Booking.php
                    if(success) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(BookingActivity.this);
                        builder.setMessage("You're appointment is set for " + appointmentDate + " at " + chosenTime)
                                .setNegativeButton("OK", null)
                                .create()
                                .show();

                        requestSchedule();

                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(BookingActivity.this);
                        builder.setMessage("Booking failed")
                                .setNegativeButton("Please retry", null)
                                .create()
                                .show();

                        TextView userMsgText = (TextView) findViewById(R.id.tvChosenDate);
                        userMsgText.setText("Oops. That didn't work. Try again or call 790-0000.");
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

    public void requestSchedule() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {

            // below happens inside GetSchedule.php when the response has been executed
            @Override
            public void onResponse(String response) {  // 'response' is the boolean response from GetSchedule.php (volley provides this response)
                try {
//                    System.out.println(response);
                    JSONArray results = new JSONArray(response);// get 'response' string Volley has given back; 'response' was encoded into JSON string in GetSchedule.php
                    schedule.clear();
                    parseSchedule(results);

                    // set chosen date in TextView
                    TextView userMsg = (TextView) findViewById(R.id.tvChosenDate);
                    userMsg.setText("Appointments for " + appointmentDate);

                } catch (JSONException e) {
                    e.printStackTrace();
                    AlertDialog.Builder builder = new AlertDialog.Builder(BookingActivity.this);
                    builder.setMessage("Schedule retrieval failed")
                           .setNegativeButton("Retry", null)
                           .create()
                           .show();

                    schedule.clear();
                    loadSchedule();  // load blank schedule
                }
            }
        };

        ScheduleRequest scheduleRequest = new ScheduleRequest(name, chosenMonth, chosenDay, responseListener);
        RequestQueue queue = Volley.newRequestQueue(BookingActivity.this);
        Log.v("scheduleRequest", String.valueOf(scheduleRequest));
        queue.add(scheduleRequest);
    }

    public void parseSchedule(JSONArray daysSchedule) {
        for (int i = 0; i < daysSchedule.length(); i++) {
            try {
                JSONObject theSchdl = daysSchedule.getJSONObject(i);
                Iterator<String> keys = theSchdl.keys();

                while (keys.hasNext()) {
                    String key = keys.next();
//                    System.out.println("Time: " + key + " - " + theSchdl.get(key));
                    String thisTimeSlot = key + "       " + theSchdl.get(key);
                    schedule.add(thisTimeSlot);
                }

                loadSchedule();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadSchedule() {
        schdlTimesAdptr = new ArrayAdapter<String>(this, R.layout.item, R.id.apmtTimeSlotsView, schedule);
        final ListView apmtTimesView = (ListView) findViewById(R.id.timeSlotsView);
        apmtTimesView.setAdapter(schdlTimesAdptr);
        schdlTimesAdptr.notifyDataSetChanged();
    }

//    public void forceCrash(View view) {
//        throw new RuntimeException("This is a crash");
//    }

}