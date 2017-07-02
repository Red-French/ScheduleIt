package net.redfrench.scheduleit;


import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class MyAppointmentsRequest extends StringRequest {
    private static final String SCHEDULE_REQUEST_URL = "http://myunclefatty.000webhostapp.com/PatientApmts.php";
    private Map<String, String> params;

    // constructor (first method that runs when an instance of this class is created)
    public MyAppointmentsRequest(String name, String chosenMonth, Response.Listener<String> listener) {

        // pass data to Volley
        super(Method.POST, SCHEDULE_REQUEST_URL, listener, null);  // post sends to URL which returns data which listener listens for

        // pass information to the request via params
        params = new HashMap<>();
        params.put("name", name);  // put data into HashMap
        params.put("chosenMonth", chosenMonth);
//        params.put("chosenDay", chosenDay);
    }

    @Override
    public Map<String, String> getParams() {  // when request is executed, Volley will call this getParams() method
        Log.v("from MyAppointmentsReq", String.valueOf(params));
        return params;  // returns params (put in HashMap, above)
    }

}
