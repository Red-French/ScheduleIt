package net.redfrench.scheduleit;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

// this class will allow a request to be made to the Register.php file on the server
// and get a response as a string, which is why StringRequest is being extended
public class RegisterRequest extends StringRequest{
    private static final String REGISTER_REQUEST_URL = "http://myunclefatty.000webhostapp.com/Register.php";
    private Map<String, String> params;

    // constructor (first method that runs when an instance of this class is created)
    public RegisterRequest(String name, String username, String password, Response.Listener<String> listener) {

        // pass data to Volley (below explains parameters of super())
        // 1. Method.POST sends data to REGISTER_REQUEST_URL
        // 2. REGISTER_REQUEST_URL returns data
        // 3. listener: when Volley is finished with the request, it will inform the listener
        // 4. null: error handling would be here
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("name", name);  // put data into HashMap
        params.put("username", username);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {  // when request is executed, Volley will call getParams()
        Log.v("line 35", String.valueOf(params));
        return params;  // returns params declared above
    }
}
