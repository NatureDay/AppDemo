package com.qianmo.android.library.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import java.util.Stack;

/**
 * 退出 注销 返回 处理类
 *
 * @author summer
 */
public class ExitUtil {

    public Stack<Activity> activityList;
    private static ExitUtil instance;
    private boolean flag;

    private ExitUtil() {
    }

    public synchronized static ExitUtil getInstance() {
        if (instance == null) {
            instance = new ExitUtil();
        }
        return instance;
    }

    public void addInstance(Activity activity) {
        if (activityList == null) {
            activityList = new Stack<Activity>();
        }
        activityList.add(activity);
    }

    /**
     * 获取当前的activity
     *
     * @return
     */
    public Activity currentActivity() {
        Activity activity = activityList.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityList.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityList.remove(activity);
            AlertUtil.clear();
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityList) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void ExitApp() {
        if (activityList == null)
            return;
        int listSize = activityList.size();
        for (int i = 0; i < listSize; i++) {
            if (null != activityList.get(i)) {
                activityList.get(i).finish();
            }
        }
        activityList.clear();
    }

    /**
     * 退出整个应用,带提示功能
     *
     * @param context
     * @return
     */
    public boolean exitShowDialog(final Context context, int exit_msg_res) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(exit_msg_res));
        builder.setPositiveButton("确定",
                new OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        flag = true;
                        ExitApp();
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(0);
                    }
                });
        builder.setNeutralButton("取消",
                new OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        flag = false;
                    }
                });
        builder.show();
        return flag;
    }

    /**
     * 退出整个应用Toast显示
     *
     * @param context
     * @return
     */
    public boolean exitShowToast(Context context) {

        if (!ToolsUtil.isFastDoubleClick(2000)) {
            AlertUtil.showToast(context, "再按一次退出程序");
        } else {
            ExitApp();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
        return true;

    }

    /**
     * 清除图片缓存暂时不弄进度
     *
     * @param context
     */
//    public void clearCache(final Context context) {
//        AlertUtil.showAlert(context,
//                R.string.clear_cache_title,
//                R.string.clear_cache_msg, R.string.ok,
//                new OnClickListener() {
//
//                    public void onClick(DialogInterface dialog,
//                                        int which) {
//                        final Handler handler = new Handler() {
//                            public void handleMessage(Message msg) {
//                                if (msg.what == 1) {
//                                    AlertUtil.showToast(context, "缓存清除成功");
//                                } else {
//                                    AlertUtil.showToast(context, "缓存清除失败");
//                                }
//                            }
//                        };
//                        AlertUtil.showToast(context, "清除中...");
//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Message msg = new Message();
//                                try {
//                                    clearAppCache(context);
//                                    msg.what = 1;
//                                } catch (Exception e) {
//                                    msg.what = -1;
//                                    e.printStackTrace();
//                                }
//                                handler.sendMessage(msg);
//                            }
//                        }).start();
//
//                    }
//                }, R.string.cancle, null);
//
//    }


    /**
     * 清除app缓存
     */
//    public void clearAppCache(Context context) {
//        FileUtil.deleteFile(context.getCacheDir());//data/data/cache目录
////		FileUtil.deleteFile(context.getFilesDir());//data/data/flies目录
//        //2.2版本才有将应用缓存转移到sd卡的功能
//        if (VERSION.SDK_INT >= VERSION_CODES.FROYO) {
//            if (context.getExternalCacheDir() != null)
//                FileUtil.deleteFile(context.getExternalCacheDir());
//        }
//        File temp = new File(FileUtil.getSDRootPath(), BaseConfig.CACHE_ROOT);
//        File dataTemp = FileUtil.getCachePath(context, CacheUtil.CACHEDATAPATH);
//        if (temp != null)
//            FileUtil.deleteFile(temp); // 删除sdcard里图片缓存
//        if (dataTemp != null)
//            FileUtil.deleteFile(dataTemp); //删除数据缓存
////		FastBitmap.create(context).clearCache();//异步任务
//    }
}
