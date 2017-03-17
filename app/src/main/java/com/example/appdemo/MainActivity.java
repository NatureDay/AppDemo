package com.example.appdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.qianmo.common.ConfigCommon;
import com.qianmo.common.gallery.tools.ImageProcessTask;
import com.qianmo.common.loginAndRegister.ui.LRActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ImageProcessTask.ImageProcssListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * 启动图库选择
         */
//        Intent intent = new Intent(ConfigCommon.ACTION_COMMON_GALLERY_PICKER);
//        intent.putExtra(ConfigCommon.KEY_GALLERY_PICKER_MULTI_MODE, true);
//        intent.setPackage(getPackageName());
//        startActivityForResult(intent, 1001);

        /**
         * 启动图库预览
         */
//        Intent intent1 = new Intent(ConfigCommon.ACTION_COMMON_GALLERY_PREVIEW);
//
//        ArrayList<String> urls = new ArrayList<String>();
//        urls.add("/storage/emulated/0/DCIM/Camera/20161217_165440.jpg");
//        intent1.putStringArrayListExtra(ConfigCommon.KEY_GALLERY_PREVIEW_IMAGE_URLS, urls);
//        startActivity(intent1);

//        Intent intent = new Intent(this, LRActivity.class);
//        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == RESULT_OK) {
            ArrayList<String> list = data.getStringArrayListExtra(ConfigCommon.KEY_GALLERY_PICKER_RESULT);
            for (String path : list) {
            }
            ImageProcessTask task = new ImageProcessTask(MainActivity.this);
            task.setImageProcssListener(this);
            task.execute(list);
        }
    }

    @Override
    public void onProcessBegin() {
        Log.e("fff", "-------onProcessBegin========");
    }

    @Override
    public void onProcessFinish(ArrayList<String> result) {
        for (String path : result) {
            Log.e("fff", "-------result========" + path);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.login:
                login();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void login() {
        Intent intent = new Intent(this, LRActivity.class);
        startActivity(intent);
    }
}
