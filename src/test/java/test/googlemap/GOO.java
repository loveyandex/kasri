package test.googlemap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.PendingResult;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

import java.io.IOException;

/**
 * is created by aMIN on 8/11/2018 at 4:44 AM
 */
public class GOO {
    public static void main(String[] args) {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyAolOBgT7MeYriXi6S08tlXuOySGOUKODY")
                .build();


        GeocodingApiRequest req = GeocodingApi.newRequest(context).address("Sydney");
//
//// Synchronous
//        try {
//            req.await();
//            // Handle successful request.
//        } catch (Exception e) {
//            // Handle error
//        }
//
////        req.awaitIgnoreError(); // No checked exception.
//
// Async
        req.setCallback(new PendingResult.Callback<GeocodingResult[]>() {
            @Override
            public void onResult(GeocodingResult[] result) {
                // Handle successful request.
                System.out.println(result);
                System.out.println("ki");
            }

            @Override
            public void onFailure(Throwable e) {
                // Handle error.
                System.out.println(e.toString());
                System.out.println('s');
            }
        });




    }
}
