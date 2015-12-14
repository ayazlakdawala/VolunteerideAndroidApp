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

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import volunteeride.com.volunteerideandroidapp.dto.Center;

public class MainActivity extends AppCompatActivity {

    private Button bttnRetrieveCenters;
    private ListView lstCenters;
    List<Center> centersList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bttnRetrieveCenters = (Button)findViewById(R.id.bttnRtrvCenters);
        lstCenters = (ListView)findViewById(R.id.lstCenters);

        bttnRetrieveCenters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ArrayAdapter<Center> centerArrayAdapter =
                        new ArrayAdapter<Center>(MainActivity.this, R.layout.list_item, centersList);
                lstCenters.setAdapter(centerArrayAdapter);

            }
        });

        DownloadCentersData downloadCentersData = new DownloadCentersData();
        downloadCentersData.execute();

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

    private class DownloadCentersData extends AsyncTask<String, Void, List<Center>>{

        @Override
        protected List<Center> doInBackground(String... params) {

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            ResponseEntity<Center[]> result =  restTemplate.getForEntity("http://10.0.2.2:8080/volunteeride/centers", Center[].class);

            centersList = Arrays.asList(result.getBody());

            return centersList;
        }

        @Override
        protected void onPostExecute(List<Center> centers) {
            super.onPostExecute(centers);


        }
    }
}
