//package nz.co.duddyx5.animedao;
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.leanback.app.BrowseFragment;
//import androidx.leanback.app.BrowseSupportFragment;
//import androidx.leanback.app.DetailsFragment;
//import androidx.leanback.app.DetailsFragmentBackgroundController;
//import androidx.leanback.widget.Action;
//import androidx.leanback.widget.ArrayObjectAdapter;
//import androidx.leanback.widget.ClassPresenterSelector;
//import androidx.leanback.widget.DetailsOverviewRow;
//import androidx.leanback.widget.FullWidthDetailsOverviewRowPresenter;
//import androidx.leanback.widget.FullWidthDetailsOverviewSharedElementHelper;
//import androidx.leanback.widget.HeaderItem;
//import androidx.leanback.widget.ImageCardView;
//import androidx.leanback.widget.ListRow;
//import androidx.leanback.widget.ListRowPresenter;
//import androidx.leanback.widget.OnActionClickedListener;
//import androidx.leanback.widget.OnItemViewClickedListener;
//import androidx.leanback.widget.Presenter;
//import androidx.leanback.widget.Row;
//import androidx.leanback.widget.RowPresenter;
//import androidx.core.app.ActivityOptionsCompat;
//import androidx.core.content.ContextCompat;
//
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.resource.drawable.GlideDrawable;
//import com.bumptech.glide.request.animation.GlideAnimation;
//import com.bumptech.glide.request.target.SimpleTarget;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.select.Elements;
//
//import java.io.IOException;
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//import nz.co.duddyx5.animedao.models.Anime;
//
///*
// * LeanbackDetailsFragment extends DetailsFragment, a Wrapper fragment for leanback details screens.
// * It shows a detailed view of video and its meta plus related videos.
// */
//public class VideoDetailsFragment extends BrowseFragment {
//
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        Log.d(TAG, "onCreate DetailsFragment");
//        super.onCreate(savedInstanceState);
//
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//    }
//
//
//
//    private int convertDpToPixel(Context context, int dp) {
//        float density = context.getResources().getDisplayMetrics().density;
//        return Math.round((float) dp * density);
//    }
//
//    private final class ItemViewClickedListener implements OnItemViewClickedListener {
//        @Override
//        public void onItemClicked(
//                Presenter.ViewHolder itemViewHolder,
//                Object item,
//                RowPresenter.ViewHolder rowViewHolder,
//                Row row) {
//
//            if (item instanceof Anime) {
//                Log.d(TAG, "Item: " + item.toString());
//                Intent intent = new Intent(getActivity(), DetailsActivity.class);
//                intent.putExtra(getResources().getString(R.string.movie), mSelectedMovie);
//
//                Bundle bundle =
//                        ActivityOptionsCompat.makeSceneTransitionAnimation(
//                                getActivity(),
//                                ((ImageCardView) itemViewHolder.view).getMainImageView(),
//                                DetailsActivity.SHARED_ELEMENT_NAME)
//                                .toBundle();
//                getActivity().startActivity(intent, bundle);
//            }
//        }
//    }
//}
