package volunteeride.com.volunteerideandroidapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import volunteeride.com.volunteerideandroidapp.R;
import volunteeride.com.volunteerideandroidapp.dto.Ride;

public class RideDetailsViewActivity extends AppCompatActivity {

    DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");

    private Ride ride;

    private TextView status;
    private TextView pickUpTime;
    private TextView pickUpLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_details_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();

        status = (TextView) findViewById(R.id.textStatus);
        pickUpTime = (TextView) findViewById(R.id.textPckUpTime);
        pickUpLocation = (TextView) findViewById(R.id.textPckUpLoc);

        ride = (Ride) bundle.getSerializable("clickedRide");
        status.setText(ride.getStatus());
        pickUpLocation.setText(ride.getPickupLoc().getLocationName());
        pickUpTime.setText(fmt.print(ride.getPickupTime()));

    }

}
