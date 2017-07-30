package net.redfrench.scheduleit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

public class MyAppointmentsActivity extends AppCompatActivity {

    String usersName;
    String chosenMonth;
    String nextMonth;
    ArrayAdapter<String> patientApmtsAdptr;
    ArrayAdapter<String> patientApmtsAdptr2;

    public ArrayList<String> patientApmts = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointments);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        usersName = getIntent().getStringExtra("name");
        chosenMonth = getIntent().getStringExtra("thisMonth");
        nextMonth = getIntent().getStringExtra("nextMonth");
        System.out.println("name in MyAppointments is " + usersName);
        System.out.println("chosenMonth in MyAppointments is " + chosenMonth);
        System.out.println("nextMonth in MyAppointments is " + nextMonth);
        final Calendar cal = Calendar.getInstance();
        System.out.println("day of month =  " + cal.get(Calendar.DAY_OF_MONTH));

        requestThisMonthPatientSchedule();
        requestNextMonthPatientSchedule();

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });




        // set click listener on patient appointments for cancellation functionality
//        patientApmtsAdptr = new ArrayAdapter<>(this, R.layout.item, R.id.apmtTimeSlotsView, patientApmts);  // patientApmts is ArrayList
//        ListView apmtTimesView = (ListView) findViewById(R.id.patientApmtView1);
//        apmtTimesView.setAdapter(patientApmtsAdptr);
//        patientApmtsAdptr.notifyDataSetChanged();
//
//        patientApmtsAdptr.setOnItemClickListener(
//                new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//
//                    }
//                });
    }

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
            intent.putExtra("name", usersName);
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

    public void requestThisMonthPatientSchedule() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {

            // below happens inside PatientAppointments.php when the response has been executed
            @Override
            public void onResponse(String response) {  // 'response' is the boolean response from GetSchedule.php (volley provides this response)

                try {
                    JSONArray results = new JSONArray(response);
                    System.out.println("results are " + results);
                    parseSchedule(results);
                    loadPatientAppointments("thisMonth");

                } catch (JSONException e) {
                    e.printStackTrace();
                    AlertDialog.Builder builder = new AlertDialog.Builder(MyAppointmentsActivity.this);
                    builder.setMessage("Schedule retrieval failed")
                            .setNegativeButton("Retry", null)
                            .create()
                            .show();

//                    bookingActivity.schedule.clear();
//                    bookingActivity.loadSchedule();  // load blank schedule
                }
            }
        };

        MyAppointmentsRequest myAppointmentsRequestCurrentMonth = new MyAppointmentsRequest(usersName, chosenMonth, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MyAppointmentsActivity.this);
        System.out.println("Back from My AppointmentsRequest" + myAppointmentsRequestCurrentMonth);
        Log.v("scheduleRequest", String.valueOf(myAppointmentsRequestCurrentMonth));
        queue.add(myAppointmentsRequestCurrentMonth);

    }

    public void requestNextMonthPatientSchedule() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {

            // below happens inside PatientAppointments.php when the response has been executed
            @Override
            public void onResponse(String response) {  // 'response' is the boolean response from GetSchedule.php (volley provides this response)

                try {
                    JSONArray results = new JSONArray(response);
                    System.out.println("results are " + results);
                    parseSchedule(results);
                    loadPatientAppointments("nextMonth");

                } catch (JSONException e) {
                    e.printStackTrace();
                    AlertDialog.Builder builder = new AlertDialog.Builder(MyAppointmentsActivity.this);
                    builder.setMessage("Schedule retrieval failed")
                            .setNegativeButton("Retry", null)
                            .create()
                            .show();

//                    bookingActivity.schedule.clear();
//                    bookingActivity.loadSchedule();  // load blank schedule
                }
            }
        };

        MyAppointmentsRequest myAppointmentsRequestNextMonth = new MyAppointmentsRequest(usersName, nextMonth, responseListener);
        RequestQueue queue2 = Volley.newRequestQueue(MyAppointmentsActivity.this);
        System.out.println("Back from My AppointmentsRequest" + myAppointmentsRequestNextMonth);
        Log.v("scheduleRequest", String.valueOf(myAppointmentsRequestNextMonth));
        queue2.add(myAppointmentsRequestNextMonth);

    }

    public void parseSchedule(JSONArray daysSchedule) {
        String thisTime = "";
        String dateDay;
        patientApmts.clear();
        System.out.println("daysSchedule = " + daysSchedule);

        for (int i = 0; i < daysSchedule.length(); i++) {
            try {
                JSONObject theSchdl = daysSchedule.getJSONObject(i);
                Iterator<String> keys = theSchdl.keys();  // keys are the column names

                while (keys.hasNext()) {
                    String key = keys.next();
                    String patientName = theSchdl.get(key).toString();
                    System.out.println("thisSchdlName = " + patientName);

                    if(key.equals("time")) {
                        thisTime = theSchdl.get(key) + ":  ";;
                    }

                    if(patientName.equals(usersName)) {


                        switch(key) {
                            case "one": dateDay = "1st";
                                break;
                            case "two": dateDay = "2nd";
                                break;
                            case "three": dateDay = "3rd";
                                break;
                            case "four": dateDay = "4th";
                                break;
                            case "five": dateDay = "5th";
                                break;
                            case "six": dateDay = "6th";
                                break;
                            case "seven": dateDay = "7th";
                                break;
                            case "eight": dateDay = "8th";
                                break;
                            case "nine": dateDay = "9th";
                                break;
                            case "ten": dateDay = "10th";
                                break;
                            case "eleven": dateDay = "11th";
                                break;
                            case "twelve": dateDay = "12th";
                                break;
                            case "thirteen": dateDay = "13th";
                                break;
                            case "fourteen": dateDay = "14th";
                                break;
                            case "fifteen": dateDay = "15th";
                                break;
                            case "sixteen": dateDay = "16th";
                                break;
                            case "seventeen": dateDay = "17th";
                                break;
                            case "eighteen": dateDay = "18th";
                                break;
                            case "nineteen": dateDay = "19th";
                                break;
                            case "twenty": dateDay = "20th";
                                break;
                            case "twentyone": dateDay = "21st";
                                break;
                            case "twentytwo": dateDay = "22nd";
                                break;
                            case "twentythree": dateDay = "23rd";
                                break;
                            case "twentyfour": dateDay = "24th";
                                break;
                            case "twentyfive": dateDay = "25th";
                                break;
                            case "twentysix": dateDay = "26th";
                                break;
                            case "twentyseven": dateDay = "27th";
                                break;
                            case "twentyeight": dateDay = "28th";
                                break;
                            case "twentynine": dateDay = "29th";
                                break;
                            case "thirty": dateDay = "30th";
                                break;
                            case "thirtyone": dateDay = "31st";
                                break;
                            default:
                                dateDay = "00";
                        };

                        System.out.println(key + " " + dateDay + " at " + thisTime +  theSchdl.get(key));
                        String thisAppointment = dateDay + " at " + thisTime +  theSchdl.get(key);

                        patientApmts.add(thisAppointment);
                    }
                }

//                loadPatientAppointments();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadPatientAppointments(String month) {
        chosenMonth = chosenMonth.toUpperCase();
        nextMonth = nextMonth.toUpperCase();

        TextView dualMonthHeading = (TextView) findViewById(R.id.tvDualMonthHeading);
        dualMonthHeading.setText(chosenMonth + "/" + nextMonth);

        TextView currentMonthHeading = (TextView) findViewById(R.id.tvCurrentMonthHeading);
        currentMonthHeading.setText(chosenMonth);

        TextView nextMonthHeading = (TextView) findViewById(R.id.tvNextMonthHeading);
        nextMonthHeading.setText(nextMonth);



        if(month.equals("thisMonth")) {
            patientApmtsAdptr = new ArrayAdapter<>(this, R.layout.item_apmt, R.id.tvPatientApmtsView, patientApmts);  // patientApmts is ArrayList

            ListView apmtTimesView = (ListView) findViewById(R.id.patientApmtView1);
            apmtTimesView.setAdapter(patientApmtsAdptr);
            patientApmtsAdptr.notifyDataSetChanged();

        } else {
            patientApmtsAdptr2 = new ArrayAdapter<>(this, R.layout.item_apmt, R.id.tvPatientApmtsView, patientApmts);  // patientApmts is ArrayList

            ListView apmtTimesView2 = (ListView) findViewById(R.id.patientApmtView2);
            apmtTimesView2.setAdapter(patientApmtsAdptr2);
            patientApmtsAdptr2.notifyDataSetChanged();
        }

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



}
