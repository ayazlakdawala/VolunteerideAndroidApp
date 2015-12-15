package volunteeride.com.volunteerideandroidapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
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

import volunteeride.com.volunteerideandroidapp.dto.PagedResponse;
import volunteeride.com.volunteerideandroidapp.dto.Ride;

public class MainActivity extends AppCompatActivity {

    private Button bttnRetrieveCenters;
    private ListView lstViewRides;
    List<Ride> rides;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bttnRetrieveCenters = (Button)findViewById(R.id.bttnRtrvRides);
        lstViewRides = (ListView)findViewById(R.id.lstViewRides);

        bttnRetrieveCenters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayAdapter<Ride> centerArrayAdapter =
                        new ArrayAdapter<>(MainActivity.this, R.layout.list_item, rides);
                lstViewRides.setAdapter(centerArrayAdapter);

            }
        });

        DownloadRidesData downloadRidesData = new DownloadRidesData();
        downloadRidesData.execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class DownloadRidesData extends AsyncTask<String, Void, List<Ride>>{

        @Override
        protected List<Ride> doInBackground(String... params) {

            HttpAuthentication authHeader = new HttpBasicAuthentication("rideusr", "password");
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setAuthorization(authHeader);
            HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            String URL = "http://10.0.2.2:8080/volunteeride/centers/564798040364519acbc255b1/rides";

           // ResponseEntity<Center[]> result =  restTemplate.getForEntity("http://10.0.2.2:8080/volunteeride/centers", Center[].class);


            ResponseEntity<PagedResponse> response = null;
            try {
                response = restTemplate.exchange(URL, HttpMethod.GET, requestEntity, PagedResponse.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

            PagedResponse result = response.getBody();

            rides = Arrays.asList((Ride[])result.getContent());

           return rides;
        }

    }
}
