package com.qianmo.common.gallery.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.example.appdemo.R;
import com.qianmo.android.library.base.BaseActivity;
import com.qianmo.common.ConfigCommon;
import com.qianmo.common.gallery.adapter.GalleryPreviewAdapter;
import com.qianmo.common.gallery.view.HackyViewPager;

import java.util.ArrayList;

public class GalleryPreviewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_layout_activity_gallery);

        Bundle data = this.getIntent().getExtras();
        if (data == null) {
            finish();
        } else {
            ArrayList<String> stringUrls = data.getStringArrayList(ConfigCommon.KEY_GALLERY_PREVIEW_IMAGE_URLS);
            int index = data.getInt(ConfigCommon.KEY_GALLERY_PREVIEW_IMAGE_INDEX, 0);
            if (stringUrls != null && !stringUrls.isEmpty()) {
                ViewPager viewPager = (HackyViewPager) findViewById(R.id.view_pager);
                viewPager.setAdapter(new GalleryPreviewAdapter(mContext, stringUrls));
                viewPager.setCurrentItem(index);
            } else {
                finish();
            }
        }
    }
}
