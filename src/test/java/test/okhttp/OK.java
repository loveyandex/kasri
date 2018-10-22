package test.okhttp;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Test;

import java.io.IOException;

/**
 * is created by aMIN on 10/19/2018 at 6:22 PM
 */
public class OK {
    @Test
    public void test() throws IOException {
        OkHttpClient client = new OkHttpClient();
        class Http {
            String run(String url) throws IOException {
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                Response response = client.newCall(request).execute();
                return response.body().string();
            }
        }
        System.out.println(new Http().run("https://financial.org"));
    }

}
