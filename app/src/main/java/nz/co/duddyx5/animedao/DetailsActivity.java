package nz.co.duddyx5.animedao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.HeaderItem;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.ListRowPresenter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import nz.co.duddyx5.animedao.models.Anime;

/*
 * Details activity class that loads LeanbackDetailsFragment class
 */
public class DetailsActivity extends Activity {
    public static final String SHARED_ELEMENT_NAME = "hero";
    public static final String MOVIE = "Movie";
    private static final String TAG = "VideoDetailsFragment";
    private Anime mSelectedMovie;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mSelectedMovie =
                (Anime) getIntent().getSerializableExtra(DetailsActivity.MOVIE);
        if (mSelectedMovie != null){
            TextView titleView = findViewById(R.id.title);
            titleView.setText(mSelectedMovie.animeTitle);
            ImageView imageView = findViewById(R.id.image);
            Glide.with(this)
                    .load(mSelectedMovie.animeImage)
                    .asBitmap()
                    .centerCrop()
                    .error(R.drawable.default_background)
                    .into(imageView);

            getAnimeInfo();
        }else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void getAnimeInfo() {
        final ArrayList<Anime> animeEpsiodes = new ArrayList<>();
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Document document = Jsoup.connect(mSelectedMovie.animeURL).get();
                    Elements elements = document.body().getElementsByClass("episode_well_link");
                    for (int i = 0; i < elements.size(); i++) {
                        Log.d("elements", elements.get(i).select("img").attr("data-src"));
                        Anime anime = new Anime();
                        anime.animeEpiTitle = elements.get(i).getElementsByClass("latestanime-subtitle").select("span[title]").attr("title");
                        anime.animeTitle = elements.get(i).select("a[href][title]").attr("title");
                        anime.animeEpiURL = elements.get(i).select("a[href][title]").attr("href");
                        anime.animeImage = elements.get(i).select("img").attr("data-src");
                        anime.animeURL = mSelectedMovie.animeURL;
                        animeEpsiodes.add(anime);
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

                            // use this setting to improve performance if you know that changes
                            // in content do not change the layout size of the RecyclerView
                            recyclerView.setHasFixedSize(true);

                            // use a linear layout manager
                            layoutManager = new LinearLayoutManager(getApplicationContext());
                            recyclerView.setLayoutManager(layoutManager);

                            // specify an adapter (see also next example)
                            mAdapter = new EpisodeAdapter(animeEpsiodes);
                            recyclerView.setAdapter(mAdapter);
//                            ArrayObjectAdapter rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
//                            CardPresenter cardPresenter = new CardPresenter();
//                            ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);
//                            for (int j = 0; j < recentAnimeArrayList.size(); j++) {
//                                listRowAdapter.add(recentAnimeArrayList.get(j));
//                            }
//                            HeaderItem header = new HeaderItem(0, "Episodes: ");
//                            rowsAdapter.add(new ListRow(header, listRowAdapter));
//                            setAdapter(rowsAdapter);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();



    }

}
