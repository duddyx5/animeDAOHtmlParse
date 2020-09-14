/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package nz.co.duddyx5.animedao;

import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.leanback.widget.BaseCardView;
import androidx.leanback.widget.ImageCardView;
import androidx.leanback.widget.Presenter;

import com.bumptech.glide.Glide;

import nz.co.duddyx5.animedao.models.Anime;


/*
 * A CardPresenter is used to generate Views and bind Objects to them on demand.
 * It contains an Image CardView
 */
public class SearchResultsCardPresenter extends Presenter {
    private static final String TAG = "CardPresenter";
    private static final int CARD_WIDTH = 300;
    private static final int CARD_HEIGHT = 380;
    private static Drawable sSelectedBackgroundColor;
    private static Drawable sDefaultBackgroundColor;

    private static void updateCardBackgroundColor(ImageCardView view, Boolean selected) {
        Drawable color = selected ? sSelectedBackgroundColor : sDefaultBackgroundColor;
        view.setBackground(color);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        sDefaultBackgroundColor = parent.getResources().getDrawable(R.drawable.button_default_background);
        sSelectedBackgroundColor = parent.getResources().getDrawable(R.drawable.button_background);
        final ImageCardView cardView =
                new ImageCardView(parent.getContext()) {
                    @Override
                    public void setSelected(boolean selected) {
                        updateCardBackgroundColor(this, selected);
                        super.setSelected(selected);
                    }
                };
        cardView.setMainImageScaleType(ImageView.ScaleType.FIT_XY);
        cardView.setInfoVisibility(BaseCardView.CARD_REGION_VISIBLE_ALWAYS);
        cardView.setElevation(0);
        cardView.setFocusable(true);
        cardView.setCardType(BaseCardView.CARD_TYPE_INFO_UNDER);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        Anime animeEvent = (Anime) item;
        final ImageCardView cardView = (ImageCardView) viewHolder.view;

        if (animeEvent.animeImage != null) {
            cardView.setTitleText(animeEvent.animeTitle);
            cardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT);
            Glide.with(viewHolder.view.getContext()).load(animeEvent.animeImage).into(cardView.getMainImageView());
        }
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
        ImageCardView cardView = (ImageCardView) viewHolder.view;
        cardView.setBadgeImage(null);
        cardView.setMainImage(null);
    }
}
