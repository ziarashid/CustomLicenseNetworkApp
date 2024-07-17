package com.paksoftwares.trialy;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LicenseApiService {
    @GET("license.json?rlkey=0819cozcyx7rok6f9rnhmbld1&dl=1") // Specify the endpoint here
    Call<LicenseResponse> checkLicenseKey(@Query("licenseKey") String licenseKey);
}