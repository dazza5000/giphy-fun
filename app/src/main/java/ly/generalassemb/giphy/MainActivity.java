package ly.generalassemb.giphy;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.Random;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.squareup.haha.guava.base.Ascii.checkNotNull;

public class MainActivity extends AppCompatActivity {
    public static final String ENDPOINT =
            "http://api.giphy.com/v1/gifs/trending?api_key=dc6zaTOxFJmzC&rating=pg&limit=100";

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

            String gifUrl = giphyList.get(new Random().nextInt(giphyList.size())).getGifUrl();
            String gifUrl2 = giphyList.get(new Random().nextInt(giphyList.size())).getGifUrl();
            String gifUrl3 = giphyList.get(new Random().nextInt(giphyList.size())).getGifUrl();

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






//        Subscription subscription = getImageNetworkCall()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<String>() {
//                    @Override
//                    public void onCompleted() {
//                        // Update user interface if needed
//                        Log.d("completed","yes");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d("error",e.getMessage());
//
//                    }
//
//                    @Override
//                    public void onNext(String bitmap) {
//                        Log.d("bit",bitmap);
//
//                        // Handle result of network request
//                    }
//                });

    }

//    public Observable<String> getImageNetworkCall() {
//        // Insert network call here!
//        return Observable.just("we");
//    }




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

    private static class GifAdapter extends RecyclerView.Adapter<GifAdapter.ViewHolder> {

        private List<Giphy> mGifs;
        private GifItemListener mItemListener;
        private Context context;

        public GifAdapter(Context context, List<Giphy> giphies, GifItemListener itemListener) {
            this.context = context;
            setList(giphies);
            mItemListener = itemListener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View churchView = inflater.inflate(R.layout.gif_item, parent, false);

            return new ViewHolder(churchView, mItemListener);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            Giphy giphy = mGifs.get(position);

            Glide.with(context).load(giphy.getGifUrl())
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(
                                Exception e, String model, Target<GlideDrawable> target,
                                boolean isFirstResource) {

                            // do something
                            Log.d(LOG_TAG,"onException");
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(
                                GlideDrawable resource, String model, Target<GlideDrawable> target,
                                boolean isFromMemoryCache, boolean isFirstResource) {

                            // do something
                            Log.d(LOG_TAG,"onResourceReady");
                            return false;
                        }
                    })
                    .into(viewHolder.gifImageView);
        }

        public void replaceData(List<Giphy> giphies) {
            setList(giphies);
            notifyDataSetChanged();
        }

        private void setList(List<Giphy> churches) {
            mGifs = checkNotNull(churches);
        }

        @Override
        public int getItemCount() {
            return mGifs.size();
        }

        public Giphy getItem(int position) {
            return mGifs.get(position);
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private ImageView gifImageView;
            private GifItemListener mItemListener;

            public ViewHolder(View itemView, GifItemListener listener) {
                super(itemView);
                mItemListener = listener;
                gifImageView = (ImageView) itemView.findViewById(R.id.gif_image_view);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                Giphy giphy = getItem(position);
                mItemListener.onGifClick(giphy);

            }
        }
    }

    public interface GifItemListener {

        void onGifClick(Giphy clickedGif);
    }


}
