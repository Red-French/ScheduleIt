package net.redfrench.scheduleit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
    String name;
    String chosenMonth;
    ArrayAdapter<String> patientApmtsAdptr;

    public ArrayList<String> patientApmts = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointments);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        name = getIntent().getStringExtra("name");
        chosenMonth = getIntent().getStringExtra("chosenMonth");
        System.out.println("name in MyAppointments is " + name);
        System.out.println("chosenMonth in MyAppointments is " + chosenMonth);
        final Calendar cal = Calendar.getInstance();
        System.out.println("day of month =  " + cal.get(Calendar.DAY_OF_MONTH));

        requestPatientSchedule();

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
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

    public void requestPatientSchedule() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {

            // below happens inside PatientAppointments.php when the response has been executed
            @Override
            public void onResponse(String response) {  // 'response' is the boolean response from GetSchedule.php (volley provides this response)

                try {
                    JSONArray results = new JSONArray(response);
                    System.out.println("results are " + results);
                    parseSchedule(results);

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

        MyAppointmentsRequest myAppointmentsRequest = new MyAppointmentsRequest(name, chosenMonth, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MyAppointmentsActivity.this);
        System.out.println("Back from My AppointmentsRequest" + myAppointmentsRequest);
        Log.v("scheduleRequest", String.valueOf(myAppointmentsRequest));
        queue.add(myAppointmentsRequest);
    }

    public void parseSchedule(JSONArray daysSchedule) {
        System.out.println("daysSchedule = " + daysSchedule);

        for (int i = 0; i < daysSchedule.length(); i++) {
            try {
                JSONObject theSchdl = daysSchedule.getJSONObject(i);
                Iterator<String> keys = theSchdl.keys();

                while (keys.hasNext()) {
                    String key = keys.next();
                    String thisSchdlName = theSchdl.get(key).toString();

                    if(thisSchdlName.equals(name)) {
                        System.out.println("Time: " + key + " - " + theSchdl.get(key));
                        String thisAppointment = "Time: " + key + " - " + theSchdl.get(key);

                        patientApmts.add(thisAppointment);
                    }
                }

                loadPatientAppointments();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadPatientAppointments() {
        patientApmtsAdptr = new ArrayAdapter<>(this, R.layout.item, R.id.apmtTimeSlotsView, patientApmts);  // patientApmts is ArrayList
        final ListView apmtTimesView = (ListView) findViewById(R.id.patientApmtView);
        apmtTimesView.setAdapter(patientApmtsAdptr);
        patientApmtsAdptr.notifyDataSetChanged();
    }

}
