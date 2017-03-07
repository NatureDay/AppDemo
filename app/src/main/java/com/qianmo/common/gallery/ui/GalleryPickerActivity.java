package com.qianmo.common.gallery.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.appdemo.R;
import com.qianmo.android.library.base.BaseActivity;
import com.qianmo.android.library.utils.AlertUtil;
import com.qianmo.android.library.utils.Util;
import com.qianmo.common.ConfigCommon;
import com.qianmo.common.gallery.adapter.AlbumChooseAdapter;
import com.qianmo.common.gallery.adapter.GalleryPickerAdapter;
import com.qianmo.common.gallery.model.FileEntity;
import com.qianmo.common.gallery.model.FolderEntity;
import com.qianmo.common.gallery.tools.GalleryTool;

import java.util.ArrayList;

import static com.qianmo.common.ConfigCommon.KEY_GALLERY_PICKER_MULTI_MODE;

public class GalleryPickerActivity extends BaseActivity implements GalleryViewOperateListener, AlbumChooseAdapter.OnItemClickListener {

    public final static String TAG = "GalleryPickerActivity";
    /**
     * 默认最大选择数量
     */
    public final static int DEFAULT_MAX_NUM = 9;

    /**
     * 照片选择模式，默认是单选模式
     */
    private boolean mIsMultiMode = false;

    private RecyclerView mRecyclerView;
    private GalleryPickerAdapter mAdapter;
    private AppCompatTextView mAlbumChoose;
    private AppCompatTextView mNumber;
    private PopupWindow mPopupView;
    private RecyclerView mFolderView;
    private ArrayList<FolderEntity> mFolders;
    private ArrayList<FileEntity> mData;
    private ImageLoadTask mImageLoadTask;
    private AlbumChooseAdapter mFolderAdapter;
    private ArrayList<String> mSelectedPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity_gallery_picker);
        if (!Util.isExternalStorageAvailable()) {
            Toast.makeText(this, "No SD card!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            initIntentParams();
            initView();
            mImageLoadTask = new ImageLoadTask();
            mImageLoadTask.execute(mContext);
        }
    }

    /**
     * 初始化选项参数
     */
    private void initIntentParams() {
        mIsMultiMode = getIntent().getBooleanExtra(KEY_GALLERY_PICKER_MULTI_MODE, false);
    }

    private void initView() {
        initHead();
        mRecyclerView = (RecyclerView) findViewById(R.id.gallery_picker_list);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 3);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAlbumChoose = (AppCompatTextView) findViewById(R.id.gallery_picker_album_choose);
        mNumber = (AppCompatTextView) findViewById(R.id.gallery_picker_number);
        if (mIsMultiMode) {
            mNumber.setVisibility(View.VISIBLE);
        } else {
            mNumber.setVisibility(View.GONE);
        }
    }

    private void initHead() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mIsMultiMode) {
            getMenuInflater().inflate(R.menu.common_menu_picker_commit, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (mIsMultiMode) {
            if (mSelectedPhotos == null || mSelectedPhotos.isEmpty()) {
                menu.findItem(R.id.gallery_picker_commit).setEnabled(false);
            } else {
                menu.findItem(R.id.gallery_picker_commit).setEnabled(true);
            }
            return true;
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mIsMultiMode) {
            switch (item.getItemId()) {
                case R.id.gallery_picker_commit:
                    commit();
                    break;
                default:
                    break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void commit() {
        Intent intent = new Intent();
        intent.putStringArrayListExtra(ConfigCommon.KEY_GALLERY_PICKER_RESULT, mSelectedPhotos);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onItemClick(View view) {
        FolderEntity pf = (FolderEntity) view.getTag();
        if (pf == null) return;
        for (FolderEntity folderEntity : mFolders) {
            if (folderEntity.isSelected()) {
                folderEntity.setSelected(false);
            }
            if (folderEntity.getName().equals(pf.getName()) && folderEntity.getDirPath().equals(pf.getDirPath())) {
                folderEntity.setSelected(true);
                updateViews(folderEntity);
            }
        }
        mFolderAdapter.notifyDataSetChanged();
        if (mPopupView.isShowing()) {
            mPopupView.dismiss();
        }
    }

    private void updateViews(FolderEntity folderEntity) {
        mAlbumChoose.setText(folderEntity.getName());
        mData.clear();
        mData.addAll(folderEntity.getFileEntityList());
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 异步获取照片
     */
    private class ImageLoadTask extends AsyncTask<Context, Void, ArrayList<FolderEntity>> {
        @Override
        protected void onPreExecute() {
            showLoadingDialog();
        }

        @Override
        protected ArrayList<FolderEntity> doInBackground(Context... contexts) {
            Context context = contexts[0];
            return GalleryTool.getPhotos(context);
        }

        @Override
        protected void onPostExecute(ArrayList<FolderEntity> result) {
            getPhotosSuccess(result);
            closeLoadingDialog();
        }
    }

    private void getPhotosSuccess(ArrayList<FolderEntity> result) {
        mFolders = result;
        if (mFolders == null) {
            return;
        }
        mData = new ArrayList<FileEntity>();
        mSelectedPhotos = new ArrayList<String>();

        mAdapter = new GalleryPickerAdapter(mContext, mData);
        mAdapter.setMultiMode(mIsMultiMode);
        mAdapter.setGalleryViewOperateListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        for (FolderEntity pf : mFolders) {
            if (pf.isSelected()) {
                updateViews(pf);
            }
        }

        mAlbumChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initFolderChoose();
            }
        });
        if (mIsMultiMode) {
            mNumber.setText(String.format(mContext.getString(R.string.common_gallery_picker_number), mSelectedPhotos.size(), DEFAULT_MAX_NUM));
        }
    }

    private void initFolderChoose() {
        if (mPopupView == null) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.common_album_choose, null);
            mFolderView = (RecyclerView) view.findViewById(R.id.album_choose_list);
            mFolderView.setLayoutManager(new LinearLayoutManager(mContext));
            mFolderView.setItemAnimator(new DefaultItemAnimator());
            mFolderView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
            mFolderAdapter = new AlbumChooseAdapter(mContext, mFolders);
            mFolderAdapter.setOnItemClickListener(this);
            mFolderView.setAdapter(mFolderAdapter);
            mPopupView = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, calculateHeight(), true);
            mPopupView.setAnimationStyle(R.style.PopupStyle);
            mPopupView.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(mContext, android.R.color.white)));
            mPopupView.setTouchable(true);
            mPopupView.setTouchInterceptor(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return false;
                }
            });
            mPopupView.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    backgroundAlpha(1f);
                }
            });
        }
        mPopupView.showAtLocation(mRecyclerView, Gravity.BOTTOM, 0, 0);
        backgroundAlpha(0.7f);
    }

    private int calculateHeight() {
        int height = mContext.getResources().getDisplayMetrics().heightPixels * 2 / 3;
        int actuaLheight = Util.dip2px(mContext, mFolders.size() * (80 + 1));
        if (actuaLheight < height) {
            return actuaLheight;
        } else {
            return height;
        }
    }

    private void backgroundAlpha(float alpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = alpha;
        getWindow().setAttributes(lp);
    }

    @Override
    public void onNormalViewClick(View view) {
        FileEntity fileEntity = (FileEntity) view.getTag();
        if (fileEntity == null) return;
        if (mIsMultiMode) {
            if (mSelectedPhotos.size() >= DEFAULT_MAX_NUM) {
                AlertUtil.showToast(mContext, mContext.getString(R.string.common_gallery_picker_limit, DEFAULT_MAX_NUM));
                return;
            }
            fileEntity.setSelected(!fileEntity.isSelected());
            mAdapter.notifyItemChanged(mData.indexOf(fileEntity));
            if (fileEntity.isSelected()) {
                if (!mSelectedPhotos.contains(fileEntity.getPath())) {
                    mSelectedPhotos.add(fileEntity.getPath());
                    supportInvalidateOptionsMenu();
                    mNumber.setText(String.format(mContext.getString(R.string.common_gallery_picker_number), mSelectedPhotos.size(), DEFAULT_MAX_NUM));
                }
            } else {
                if (mSelectedPhotos.contains(fileEntity.getPath())) {
                    mSelectedPhotos.remove(fileEntity.getPath());
                    supportInvalidateOptionsMenu();
                    mNumber.setText(String.format(mContext.getString(R.string.common_gallery_picker_number), mSelectedPhotos.size(), DEFAULT_MAX_NUM));
                }
            }
        } else {
            mSelectedPhotos.add(fileEntity.getPath());
            commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (mPopupView != null && mPopupView.isShowing()) {
            mPopupView.dismiss();
        } else {
            super.onBackPressed();
        }
    }
}
