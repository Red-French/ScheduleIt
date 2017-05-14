package net.redfrench.scheduleit;


import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CheckScheduleRequest extends StringRequest {
    private static final String SCHEDULE_REQUEST_URL = "http://myunclefatty.000webhostapp.com/CheckSchedule.php";
    private Map<String, String> params;

    // constructor (first method that runs when an instance of this class is created)
    public CheckScheduleRequest(String name, String chosenMonth, String chosenDay, String chosenTime, Response.Listener<String> listener) {

        // pass data to Volley
        super(Request.Method.POST, SCHEDULE_REQUEST_URL, listener, null);  // post sends to URL which returns data which listener listens for

        // pass information to the request via params
        params = new HashMap<>();
        params.put("name", name);  // put data into HashMap
        params.put("chosenMonth", chosenMonth);
        params.put("chosenDay", chosenDay);
        params.put("chosenTime", chosenTime);
    }

    @Override
    public Map<String, String> getParams() {  // when request is executed, Volley will call this getParams() method
        Log.v("from CheckSchedlReqst", String.valueOf(params));
        return params;  // returns params (put in HashMap, above)
    }
}
