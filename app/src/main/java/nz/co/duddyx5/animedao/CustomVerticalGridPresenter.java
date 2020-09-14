package nz.co.duddyx5.animedao;

import android.view.ViewGroup;

import androidx.leanback.widget.VerticalGridPresenter;
import androidx.leanback.widget.VerticalGridView;

public class CustomVerticalGridPresenter extends VerticalGridPresenter {

    public CustomVerticalGridPresenter(int focusZoomFactor, boolean useFocusDimmer) {
        super(focusZoomFactor, useFocusDimmer);
    }

    @Override
    protected void initializeGridViewHolder(ViewHolder vh) {
        super.initializeGridViewHolder(vh);
        VerticalGridView gridView = vh.getGridView();
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        gridView.setLayoutParams(params);
    }
}
