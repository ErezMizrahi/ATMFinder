package com.erez8.atmfinder;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView bankImage;
    private ImageView handicapSymbol;
    private TextView name_of_bank;
    private TextView branch;
    private TextView bank_id;
    private TextView atm_num;
    private TextView full_address;
    private TextView fulll_address_ex;
    private TextView atm_type;
    private TextView commision;
    private TextView atm_location;
    private Button open_maps;
    private HashMap<String, Integer> bankImages = new HashMap<String, Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        setPointer();
        setView();
    }

    private void setPointer() {
        bankImage = findViewById(R.id.details_bankImage);
        name_of_bank = findViewById(R.id.nameofbank);
        bank_id = findViewById(R.id.bank_id);
        branch = findViewById(R.id.branch);
        atm_num=findViewById(R.id.atmnumber);
        atm_location=findViewById(R.id.atm_dlocation);
        full_address = findViewById(R.id.fullatm_add);
        fulll_address_ex = findViewById(R.id.fullatm_add_extra);
        atm_type=findViewById(R.id.atmtype_d);
        commision = findViewById(R.id.atm_co);
        handicapSymbol = findViewById(R.id.deatils_hanicapSymbol);
        open_maps = findViewById(R.id.button_openMaps);
        open_maps.setOnClickListener(this);
        setBankImages();
    }


    private void setView() {
        ATMDetials atm = XLSXWork.getInstance().getSelectedATM();
        Picasso.get().load(bankImages.get(atm.getBank_Name())).into(bankImage);
        name_of_bank.setText(atm.getBank_Name());
        bank_id.setText(atm.getBank_Code());
        branch.setText(atm.getBranch_Code());
        atm_num.setText(atm.getAtm_Num());
        atm_type.setText(atm.getATM_Type());
        atm_location.setText(atm.getATM_Location());
        fulll_address_ex.setText(atm.getATM_Address_Extra());
        full_address.setText(atm.getFull_Add());
        commision.setText(atm.getCommission());
        if (atm.getHandicap_Access().equalsIgnoreCase("כן")){
            Picasso.get().load(R.drawable.wheelchair).into(handicapSymbol);
        }

    }
    private void setBankImages() {
        bankImages.put("בנק הפועלים בע\"מ",R.drawable.pohalim);
        bankImages.put("בנק אגוד לישראל בע\"מ",R.drawable.unionbank);
        bankImages.put("בנק אוצר החייל בע\"מ",R.drawable.soldier);
        bankImages.put("בנק דיסקונט לישראל בע\"מ",R.drawable.diskont);
        bankImages.put("בנק יהב לעובדי המדינה בע\"מ",R.drawable.yahav);
        bankImages.put("בנק ירושלים בע\"מ",R.drawable.jeruselam);
        bankImages.put("בנק לאומי לישראל בע\"מ",R.drawable.leumi);
        bankImages.put("בנק מזרחי טפחות בע\"מ",R.drawable.mizrahi);
        bankImages.put("בנק מרכנתיל דיסקונט בע\"מ",R.drawable.markintal);
        bankImages.put("בנק פועלי אגודת ישראל בע\"מ",R.drawable.poalibankisreal);
        bankImages.put("בנק פועלי הבינלאומי הראשון ישראל בע\"מ",R.drawable.benlumiisreal);
        bankImages.put("יובנק בע\"מ",R.drawable.ubank);
    }

    @Override
    public void onClick(View v) {
        getLocationPermission();
    }

    private void geoLocate() {
        List<Address>addresses = new ArrayList<>();
        Geocoder geocoder = new Geocoder(this);
        try {
            addresses = geocoder.getFromLocationName(full_address.getText().toString(),1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String lon = String.valueOf(addresses.get(0).getLongitude());
        String lat = String.valueOf(addresses.get(0).getLatitude());

        openGoogleMapsUsingIntent(lat,lon);


    }

    private void openGoogleMapsUsingIntent(String lat, String lon) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Intent chooser = null;
        intent.setData(Uri.parse("geo:"+lat+","+lon+"?z=20"));
        chooser = Intent.createChooser(intent,"Maps");
        startActivity(chooser);  }


    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            geoLocate();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    geoLocate();
                }
            }
        }
    }
}
