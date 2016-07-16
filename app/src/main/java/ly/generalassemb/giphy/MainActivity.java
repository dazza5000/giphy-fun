package ly.generalassemb.giphy;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

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
            "http://api.giphy.com/v1/gifs/trending?api_key=dc6zaTOxFJmzC";

    public final static String LOG_TAG = "MainActivity";

    private List<Giphy> giphyList;
    private ImageView giphyView;
    private ImageView giphyView2;
    private ImageView giphyView3;
    private GifDrawable gifDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        giphyView = (ImageView) findViewById(R.id.giphy_view);
        giphyView2 = (ImageView) findViewById(R.id.giphy_view2);
        giphyView3 = (ImageView) findViewById(R.id.giphy_view3);



        final GiphyListener gifListener = gifs -> {

            giphyList = gifs;

            Toast.makeText(MainActivity.this,
                    giphyList.get(0).getGifUrl(), Toast.LENGTH_SHORT).show();

            String gifUrl = giphyList.get(0).getGifUrl();
            String gifUrl2 = giphyList.get(1).getGifUrl();
            String gifUrl3 = giphyList.get(2).getGifUrl();

            Log.d("TAG", "the gifurl is: " +gifUrl);



//            GifRequestBuilder<Drawable> thumbnailRequest = Glide.with(MainActivity.this)
//                    .load(gifUrl)
//                    .apply(decodeTypeOf(Bitmap.class));

            Glide.with(MainActivity.this).load(gifUrl)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            // do something
                            Log.d(LOG_TAG,"onException");
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            // do something
                            Log.d(LOG_TAG,"onResourceReady");
                            return false;
                        }
                    })
                    .into(giphyView);

            Glide.with(MainActivity.this).load(gifUrl2)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            // do something
                            Log.d(LOG_TAG,"onException");
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            // do something
                            Log.d(LOG_TAG,"onResourceReady");
                            return false;
                        }
                    })
                    .into(giphyView2);

            Glide.with(MainActivity.this).load(gifUrl3)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            // do something
                            Log.d(LOG_TAG,"onException");
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            // do something
                            Log.d(LOG_TAG,"onResourceReady");
                            return false;
                        }
                    })
                    .into(giphyView3);


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
                    JSONObject images = quoteArray.getJSONObject(i).getJSONObject("images");
                    JSONObject original = images.getJSONObject("original");
                    String imageUrl = original.getString("url");
                    Giphy chuckJoke = new Giphy(imageUrl);
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
