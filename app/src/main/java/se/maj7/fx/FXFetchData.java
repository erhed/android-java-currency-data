package se.maj7.fx;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

// Makes HTTP-requests to server api

public class FXFetchData {

    public interface VolleyCallback {
        void onSuccessResponse(String result);
    }

    private static Context mContext;
    private static RequestQueue mRequestQueue;

    private static final int CURRENT_DAY = 0;
    private static final int PREVIOUS_DAY = 1;
    private static final int LAST_WEEK = 2;
    //private static final int LAST_MONTH = 3;
    //private static final int MONTH_2 = 4;
    //private static final int MONTH_11 = 13;
    private static final int LAST_YEAR = 14;

    public static void getPrice(String currency1, final String currency2, Context context, int time, final VolleyCallback callback) {

        mContext = context;

        // Set date for data request

        String date = getDate(time);

        // Set GET url

        String url = "https://api.exchangeratesapi.io/history?start_at=" + date + "&end_at=" + date + "&base=" + currency1 + "&symbols=" + currency2;

        // Use date for parsing json

        final String jsonDate = date;

        // Response

        // Returns the current price asked for as a string
        Response.Listener<String> response_listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response);
                try {
                    JSONObject data = new JSONObject(response);
                    JSONObject jsonObject = data.getJSONObject("rates").getJSONObject(jsonDate);
                    String price = jsonObject.getString(currency2);
                    Log.e("PRICE", price);
                    callback.onSuccessResponse(price);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener response_error_listener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Log.e("ERROR", "Timeout error");
                } else if (error instanceof AuthFailureError) {
                    Log.e("ERROR", "AuthFail error");
                } else if (error instanceof ServerError) {
                    Log.e("ERROR", "Server error");
                } else if (error instanceof NetworkError) {
                    Log.e("ERROR", "Network error");
                } else if (error instanceof ParseError) {
                    Log.e("ERROR", "Parse error");
                }
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response_listener, response_error_listener);
        getRequestQueue().add(stringRequest);
    }

    public static RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext);
        }
        return mRequestQueue;
    }

    // Get dates for GET-request, weekends gives no data

    public static String getDate(int time) {

        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DAY_OF_MONTH, -1);

        // Weekends gives no data
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            calendar.add(Calendar.DAY_OF_MONTH, -2);
        }

        if (time == CURRENT_DAY) {

        } else if (time == PREVIOUS_DAY) {
            // If monday get friday
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                calendar.add(Calendar.DAY_OF_MONTH, -3);
            } else {
                calendar.add(Calendar.DAY_OF_MONTH, -1);
            }
        } else if (time == LAST_WEEK) {
            calendar.add(Calendar.DAY_OF_MONTH, -7);
        } else if (time >= 3 && time <= 13) {
            int month = time - 3;
            calendar.add(Calendar.MONTH, -1 - month);
            // Weekends gives no data
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                calendar.add(Calendar.DAY_OF_MONTH, -1);
            }
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                calendar.add(Calendar.DAY_OF_MONTH, -2);
            }
        } else if (time == LAST_YEAR) {
            calendar.add(Calendar.YEAR, -1);
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                calendar.add(Calendar.DAY_OF_MONTH, -1);
            }
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                calendar.add(Calendar.DAY_OF_MONTH, -2);
            }
        }

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // January is 0
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String date = year + "-" + insertZero(month) + "-" + insertZero(day);

        return date;
    }

    // Insert zero if month or day is below 10
    public static String insertZero(int number) {
        String result;
        if (number < 10) {
            result = "0" + number;
        } else {
            result = String.valueOf(number);
        }
        return result;
    }
}
