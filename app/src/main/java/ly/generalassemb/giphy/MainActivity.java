package ly.generalassemb.giphy;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    public static final String ENDPOINT =
            "http://api.giphy.com/v1/gifs/search?q=funny+cat&api_key=dc6zaTOxFJmzC ";

    private List<Giphy> giphyList;
    private ImageView giphyView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        giphyView = (ImageView) findViewById(R.id.giphy_view);

        final GiphyListener gifListener = gifs -> {

            giphyList = gifs;

            Toast.makeText(MainActivity.this,
                    giphyList.get(0).getGifUrl(), Toast.LENGTH_SHORT).show();

            String gifUrl = giphyList.get(0).getGifUrl();

            Log.d("TAG", "the gifurl is: " +gifUrl);

            Ion.with(MainActivity.this)
                    .load("http://images4.fanpop.com/image/photos/16000000/Beautiful-Cat-cats-16096437-1280-800.jpg")
                    .withBitmap()
                    .placeholder(R.mipmap.ic_launcher)
                    .intoImageView(giphyView);


        };

        new GifAsync(gifListener).execute();





    }

    public interface GiphyListener {
        void onGifsLoaded(List<Giphy> giphyList);
    }



    public static class GifAsync extends AsyncTask<String, Void, List<Giphy>> {
        GiphyListener quotesListener;

        public GifAsync(GiphyListener quotesListener) {
            this.quotesListener = quotesListener;
        }


        @Override
        protected List<Giphy> doInBackground(String...params) {
            List<Giphy> quotesList = new ArrayList<>();
            OkHttpClient client = new OkHttpClient();


            // Create request for remote resource.
            Request request = new Request.Builder()
                    .url(ENDPOINT)
                    .build();

            // Execute the request and retrieve the response.
            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }


            try {
                JSONObject chuckObject = new JSONObject(response.body().string());
                JSONArray quoteArray = chuckObject.getJSONArray("data");

                for(int i = 0; i < quoteArray.length(); i++){
                    String joke = quoteArray.getJSONObject(i).getString("url");
                    Giphy chuckJoke = new Giphy(joke);
                    quotesList.add(chuckJoke);

                }
                return quotesList;

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(List<Giphy> chuckList) {
            quotesListener.onGifsLoaded(chuckList);

        }
    }
}
