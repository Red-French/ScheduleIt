package net.redfrench.scheduleit;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest{
    private static final String LOGIN_REQUEST_URL = "http://myunclefatty.000webhostapp.com/Login.php";
    private Map<String, String> params;

    // constructor (first method that runs when an instance of this class is created)
    public LoginRequest(String username, String password, Response.Listener<String> listener) {

        // pass data to Volley (below explains parameters of super())
        // 1. Method.POST sends data to REGISTER_REQUEST_URL
        // 2. REGISTER_REQUEST_URL returns data
        // 3. listener: when Volley is finished with the request, it will inform the listener
        // 4. null: error handling would be here
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();  // create HashMap
        params.put("username", username);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {  // when the request is executed, Volley will call getParams()
        return params;  // returns params declared above
    }
}
