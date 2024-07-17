package com.paksoftwares.trialy;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
          String myLicenseKey = "ID_83jdjsfd9230dee_licenca_1";
         // call retrofit api to check license key
        checkLicenseWithRetrofit(this,myLicenseKey);
    }


    public void checkLicenseWithRetrofit(Context context,String licenseKey) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.dropbox.com/scl/fi/r5i4yaf4owse3mv2eykfm/") // Replace with your actual API URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LicenseApiService service = retrofit.create(LicenseApiService.class);
        Call<LicenseResponse> call = service.checkLicenseKey(licenseKey);
        call.enqueue(new Callback<LicenseResponse>() {
            @Override
            public void onResponse(Call<LicenseResponse> call, Response<LicenseResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LicenseResponse licenseResponse = response.body();
                    // Assuming the response directly contains the license information now
                    // No need to read from assets and parse JSON again
                    if (licenseResponse.getLicenses().isEmpty()) {
                        displayMessageAndExit(context, "License Not Found");
                    } else {
                        for (LicenseResponse.License license : licenseResponse.getLicenses()) {
                            if (license.getLicense_id().equals(licenseKey)) {
                                // Assuming validateLicense now directly uses License object
                                try {
                                    validateLicense(context, license);
                                } catch (Exception e) {
                                    // display toast message
                                    Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                    throw new RuntimeException(e);
                                }
                                return;
                            }
                        }
                        displayMessageAndExit(context, "License Not Found");
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Failed to verify license", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LicenseResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    private void validateLicense(Context context, LicenseResponse.License license) throws Exception {
        String expiredDate = license.getExpiry_date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date expiryDate = sdf.parse(expiredDate);
        Date currentDate = new Date();

        if (!license.isActive() || expiryDate.before(currentDate)) {
            displayMessageAndExit(context, "License is Inactive or Expired");
        } else {
            Toast.makeText(context, "License is Active and Valid", Toast.LENGTH_LONG).show();
        }
    }
    private void displayMessageAndExit(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        finishAffinity();
        System.exit(0);
    }


}