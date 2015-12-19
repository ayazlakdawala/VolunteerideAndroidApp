package volunteeride.com.volunteerideandroidapp.activity;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import volunteeride.com.volunteerideandroidapp.R;
import volunteeride.com.volunteerideandroidapp.adapter.RideAdapter;
import volunteeride.com.volunteerideandroidapp.dto.PagedResponse;
import volunteeride.com.volunteerideandroidapp.dto.Ride;

public class UserRideListActivity extends ListActivity {

    private Button bttnRetrieveCenters;
    List<Ride> rides;
    public static final String VOLUNTEERIDE_PREFERENCES = "VolRidePrefs" ;
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.user_rides);

        //Add data in shared preferences.
        sharedpreferences = getSharedPreferences(VOLUNTEERIDE_PREFERENCES,
                Context.MODE_PRIVATE);

//        SharedPreferences.Editor editor = sharedpreferences.edit();
//        editor.putString("JSESSIONID", "BA1B0B180C0AAA3478F052B095B0B731");
//        editor.commit();

        bttnRetrieveCenters = (Button)findViewById(R.id.bttnRtrvRides);

        bttnRetrieveCenters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setListAdapter(new RideAdapter(UserRideListActivity.this, rides));
            }
        });

        DownloadRidesData downloadRidesData = new DownloadRidesData();
        downloadRidesData.execute();

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Ride clickedRide = (Ride) getListAdapter().getItem(position);
        Intent intent = new Intent(v.getContext(), RideDetailsViewActivity.class);
        intent.putExtra("clickedRide", clickedRide);
        startActivity(intent);
    }


    private class DownloadRidesData extends AsyncTask<String, Void, List<Ride>>{

        @Override
        protected List<Ride> doInBackground(String... params) {

            HttpHeaders requestHeaders = new HttpHeaders();

            //add session id to request
            String jsessionId = sharedpreferences.getString("JSESSIONID", null);
            //requestHeaders.add("Cookie", "JSESSIONID=" + jsessionId);

            //add basic authentication header
            HttpAuthentication authHeader = new HttpBasicAuthentication("rideusr", "password");
            requestHeaders.setAuthorization(authHeader);
            HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            String URL = "http://10.0.2.2:8080/volunteeride/centers/564798040364519acbc255b1/rides";

            ResponseEntity<PagedResponse> response = null;
            try {
                response = restTemplate.exchange(URL, HttpMethod.GET, requestEntity, PagedResponse.class);
            } catch (Exception e) {
                //TODO Handle UnAuthorized errors
                e.printStackTrace();
            }

            PagedResponse result = response.getBody();

            //retrieve session id from response
            String SetCookieHeaderValue = response.getHeaders().get("Set-Cookie").get(0);
            int index = SetCookieHeaderValue.indexOf("JSESSIONID=");
            int endIndex = SetCookieHeaderValue.indexOf(";", index);
            String sessionID = SetCookieHeaderValue.substring(
                    index + "JSESSIONID=".length(), endIndex);

            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("JSESSIONID", sessionID);
            editor.commit();

            rides = Arrays.asList((Ride[])result.getContent());

           return rides;
        }

    }
}
