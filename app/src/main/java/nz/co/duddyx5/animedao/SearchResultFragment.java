package nz.co.duddyx5.animedao;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityOptionsCompat;
import androidx.leanback.app.VerticalGridSupportFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.BrowseFrameLayout;
import androidx.leanback.widget.FocusHighlight;
import androidx.leanback.widget.HeaderItem;
import androidx.leanback.widget.ImageCardView;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.ListRowPresenter;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.OnItemViewSelectedListener;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import javax.net.ssl.HttpsURLConnection;
import nz.co.duddyx5.animedao.models.Anime;


public class SearchResultFragment extends VerticalGridSupportFragment {
    private static final int NUM_COLUMNS = 3;
    String searchQuery = "";
    SearchResultsCardPresenter cardPresenter = new SearchResultsCardPresenter();
    private ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);
    private String previousBrowsedBelt = "";
    private static String searchURL = "https://animedao.to/search/?key=";
    private String animeURLPrefex = "https://animedao.to";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomVerticalGridPresenter gridPresenter = new CustomVerticalGridPresenter(FocusHighlight.ZOOM_FACTOR_SMALL, false);
        gridPresenter.setKeepChildForeground(false);
        gridPresenter.setShadowEnabled(false);
        gridPresenter.setNumberOfColumns(NUM_COLUMNS);
        setGridPresenter(gridPresenter);
        //new SearchTask().execute();
        search(searchQuery);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        final EditText searchBar = getActivity().findViewById(R.id.search_bar);
        SearchKeyBoard keyboard = getActivity().findViewById(R.id.reg_frame);
        InputConnection ic = searchBar.onCreateInputConnection(new EditorInfo());
        keyboard.setInputConnection(ic);
        final LinearLayout verticalFrame = getActivity().findViewById(R.id.search_result_grid);
        searchBar.setActivated(true);
        searchBar.setPressed(true);
        verticalFrame.setVisibility(View.VISIBLE);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                verticalFrame.setVisibility(View.VISIBLE);
                searchQuery = s.toString();
              //  new SearchTask().execute();
                search(searchQuery);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equalsIgnoreCase("")) {
                    verticalFrame.setVisibility(View.GONE);
                }
            }
        });
        setupEventListeners();
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    private void search(final String searchedPhrase){
        final ArrayList<Anime> recentAnimeArrayList = new ArrayList<>();
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Document document = Jsoup.connect(searchURL+ searchedPhrase).get();
                    Elements elements = document.body().getElementsByClass("col-xs-12 col-sm-6 col-md-6 col-lg-4");
                    for (int i = 0; i < elements.size(); i++) {
                        Log.d("elements", elements.get(i).getElementsByClass("ongoingtitle").select("b").toString());
                        Anime anime = new Anime();
                        anime.animeTitle = elements.get(i).getElementsByClass("ongoingtitle").select("b").toString();
                        anime.animeURL = animeURLPrefex + elements.get(i).select("a[href]").attr("href");
                        anime.animeImage = animeURLPrefex + elements.get(i).select("img").attr("data-src");
                        recentAnimeArrayList.add(anime);
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listRowAdapter.clear();
                            for (int j = 0; j < recentAnimeArrayList.size(); j++) {
                                listRowAdapter.add(recentAnimeArrayList.get(j));
                            }
                            setAdapter(listRowAdapter);
                TextView numberOfShows = getActivity().findViewById(R.id.number_of_shows);
                numberOfShows.setVisibility(View.VISIBLE);
                numberOfShows.setText(String.valueOf(recentAnimeArrayList.size()) + " " + "Shows found.");
                workaroundFocus();

                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
    public void workaroundFocus() {
        final View viewToFocus = getActivity().findViewById(R.id.back_to_ondemand_search);
        if (getView() != null) {
            BrowseFrameLayout browseFrameLayout = getView().findViewById(androidx.leanback.R.id.grid_frame);
            browseFrameLayout.setOnFocusSearchListener(new BrowseFrameLayout.OnFocusSearchListener() {
                @Override
                public View onFocusSearch(View focused, int direction) {
                    if (direction == View.FOCUS_UP) {
                        return viewToFocus;
                    } else {
                        return null;
                    }
                }
            });
        }
    }

//    public class SearchTask extends AsyncTask<String, String, String> {
//        HttpsURLConnection conn;
//        URL url = null;
//        String currentTime;
//
//
//        @Override
//        protected void onPreExecute() {
//            Date date = new Date();
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//            currentTime = dateFormat.format(date);
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            String URL = getString(R.string.api_search)+ searchQuery + " &available_to=" + currentTime + "&expand_related=full&include_related=1&related_entity_types=images&limit=100&on_demand=only";
//            LogUtil.d("Search URL:",URL);
//            try {
//                url = new URL(URL);
//                conn = (HttpsURLConnection) url.openConnection();
//            } catch (IOException e) {
//                e.printStackTrace();
//                return e.toString();
//            }
//            try {
//                int response_code = conn.getResponseCode();
//                // Check if successful connection made
//                if (response_code == 200) {
//                    // Read data sent from server
//                    InputStream input = conn.getInputStream();
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
//                    StringBuilder result = new StringBuilder();
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        result.append(line);
//                    }
//                    return (result.toString());
//                } else if (response_code == 204) {
//                    return ("204");
//                } else {
//                    return (getString(R.string.unsuccessful));
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//                return e.toString();
//            } finally {
//                conn.disconnect();
//            }
//        }
//
//        @SuppressLint("SetTextI18n")
//        @Override
//        protected void onPostExecute(String result) {
//            ArrayList<OnDemandEvent> homepageList = new ArrayList<>();
//
//            try {
//                JSONObject jsonObject2 = new JSONObject(result);
//                JSONArray belts2 = jsonObject2.optJSONArray("data");
//                for (int g = 0; g < belts2.length(); g++) {
//                    JSONObject beltItems2 = belts2.optJSONObject(g);
//                    String showID = beltItems2.optString("id");
//                    String showTitle = beltItems2.optString("title");
//                    if (showTitle.contains("&#039;")){
//                        showTitle = showTitle.replace("&#039;","'");
//                    }
//                    JSONObject relatedObj = beltItems2.optJSONObject("related");
//                    JSONArray categories = beltItems2.optJSONArray("categories");
//                    String studio = "";
//                    if (categories != null) {
//                        for (int c = 0; c < categories.length(); c++) {
//                            String category;
//                            try {
//                                category = categories.getString(c);
//                                if (category != null && category.contains("content_type/movie")) {
//                                    studio = getString(R.string.content_movie);
//                                }
//                            } catch (JSONException e) {
//                                LogUtil.d("categories ", e.toString());
//                            }
//                        }
//                    }
//                    String urlImage = null;
//                    if (relatedObj != null) {
//                        JSONArray imagesArray = relatedObj.optJSONArray("images");
//                        for (int h = 0; h < imagesArray.length(); h++) {
//                            JSONObject imageObj = imagesArray.optJSONObject(h);
//                            JSONArray tagArr = imageObj.optJSONArray("tags");
//                            String tagLine = null;
//                            if (tagArr != null) {
//                                tagLine = tagArr.optString(0);
//                                if (tagLine.equalsIgnoreCase("show-image-titled")) {
//                                    urlImage = imageObj.optString("url");
//                                }
//                            } else if (tagArr == null && urlImage == null) {
//                                urlImage = imageObj.optString("url");
//                            }
//                        }
//                    }
//                    if (urlImage == null) {
//                        urlImage = getString(R.string.phimage);
//                    }
//
//                    OnDemandEvent onDemandEvent = new OnDemandEvent();
//                    onDemandEvent.setCardImageUrl(urlImage);
//                    onDemandEvent.setTitle(showTitle);
//                    onDemandEvent.setId(showID);
//                    homepageList.add(onDemandEvent);
//                    onDemandEvent.setStudio(studio);
//                }
//                listRowAdapter.clear();
//                for (int j = 0; j < homepageList.size(); j++) {
//                    listRowAdapter.add(homepageList.get(j));
//                }
//                setAdapter(listRowAdapter);
//                TextView numberOfShows = getActivity().findViewById(R.id.number_of_shows);
//                numberOfShows.setVisibility(View.VISIBLE);
//                numberOfShows.setText(String.valueOf(belts2.length()) + " " + getString(R.string.shows_found));
//                workaroundFocus();
//            } catch (JSONException e) {
//                String TAG = "ChannelListFragment";
//
//            }
//
//        }
//    }

    private void setupEventListeners() {
        setOnItemViewClickedListener(new ItemViewClickedListener());
        setOnItemViewSelectedListener(new ItemViewSelectedListener());
    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                  RowPresenter.ViewHolder rowViewHolder, Row row) {
            if (item instanceof Anime) {
                Anime movie = (Anime) item;
                Log.d("Search", "Item: " + item.toString());
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra(DetailsActivity.MOVIE, movie);
                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(),
                        ((ImageCardView) itemViewHolder.view).getMainImageView(),
                        DetailsActivity.SHARED_ELEMENT_NAME)
                        .toBundle();
                getActivity().startActivity(intent, bundle);
            } else if (item instanceof String) {
                if (((String) item).contains(getString(R.string.error_fragment))) {
                    Intent intent = new Intent(getActivity(), BrowseErrorActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), ((String) item), Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    private final class ItemViewSelectedListener implements OnItemViewSelectedListener {
        @Override
        public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item,
                                   RowPresenter.ViewHolder rowViewHolder, Row row) {
            if (rowViewHolder != null) {
                if (!previousBrowsedBelt.equals(rowViewHolder.getRow().getHeaderItem().getName())) {
                    previousBrowsedBelt = rowViewHolder.getRow().getHeaderItem().getName();
                }
            }
        }
    }
}