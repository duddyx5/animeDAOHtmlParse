package nz.co.duddyx5.animedao;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.leanback.app.VideoSupportFragment;
import androidx.leanback.app.VideoSupportFragmentGlueHost;
import androidx.leanback.media.MediaPlayerAdapter;
import androidx.leanback.media.PlaybackTransportControlGlue;
import androidx.leanback.widget.PlaybackControlsRow;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import nz.co.duddyx5.animedao.models.Anime;

/**
 * Handles video playback with media controls.
 */
public class PlaybackVideoFragment extends VideoSupportFragment {

    private PlaybackTransportControlGlue<MediaPlayerAdapter> mTransportControlGlue;
    private MediaPlayerAdapter playerAdapter;
    private Anime movie;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        movie =
                (Anime) getActivity().getIntent().getSerializableExtra("movie");
        VideoSupportFragmentGlueHost glueHost =
                new VideoSupportFragmentGlueHost(PlaybackVideoFragment.this);
        playerAdapter = new MediaPlayerAdapter(getContext());
        playerAdapter.setRepeatAction(PlaybackControlsRow.RepeatAction.INDEX_NONE);
        mTransportControlGlue = new PlaybackTransportControlGlue<>(getContext(), playerAdapter);
        mTransportControlGlue.setHost(glueHost);
        mTransportControlGlue.setTitle(movie.animeTitle);
        mTransportControlGlue.setSubtitle(movie.animeEpiTitle);
        getAnimeInfo();


    }
    private void getAnimeInfo() {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    crawl("https://animedao.to" + movie.animeEpiURL);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();



    }

    private void crawl(String url) throws IOException {
        Connection.Response response = Jsoup.connect(url).followRedirects(false).execute();
        System.out.println(response.statusCode() + " : " + url);
        if (response.hasHeader("location")) {
            String redirectUrl = response.header("location");
            crawl(redirectUrl);
        }
        String subString;
        if (response.statusCode() == 200){
            Document document = response.parse();
            Elements scriptElements = document.getElementsByTag("script");
            for (Element element :scriptElements ){
                for (DataNode node : element.dataNodes()) {
                        if (node.toString().contains("gstoreplayer.source")) {
                            String[] split = node.toString().split("src");
                            String secondSubString = split[1];
                            String[] split2 = secondSubString.split("type");
                            split2[0] = split2[0].replace("'","");
                            subString = split2[0].replace(",","");
                            subString = split2[0].replace(" ","");
                            String urlString = "https://animedao25.stream";
                            subString = urlString + subString.toString();
                            subString.replace(":","");
                            play(subString);
                            Log.d("Play:", subString);
                        }
                }
            }
        }
    }

    private void play(String videoUrl){
        mTransportControlGlue.playWhenPrepared();
        playerAdapter.setDataSource(Uri.parse(videoUrl));
    }
    @Override
    public void onPause() {
        super.onPause();
        if (mTransportControlGlue != null) {
            mTransportControlGlue.pause();
        }
    }
}
