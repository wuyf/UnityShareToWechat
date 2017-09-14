package com.example.wuyf.unitywxsharesdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import android.util.Log;

/**
 * @title:
 * @description:
 * @company: 美丽说（北京）网络科技有限公司
 * Created by Glan.Wang on 15/6/16.
 */
public class ShareUtils {
    /**
     * 不实用微信的SDK分享图片到好友
     * @param context
     * @param path
     */
    public static void sharePicToFriendNoSDK(Context context, String path) {
        if(!isInstallWeChart(context)){
            Toast.makeText(context,"您没有安装微信",Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
        intent.setComponent(comp);
        intent.setAction("android.intent.action.SEND");
        intent.setType("image/*");
        // intent.setFlags(0x3000001);
        File f = new File(path);
        if(f.exists()){
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(path)));
        } else {
            Toast.makeText(context,"文件不存在",Toast.LENGTH_SHORT).show();
            return;
        }
        context.startActivity(intent);
    }


    /**
     * 分享9图到朋友圈
     *
     * @param context
     * @param Kdescription 9图上边输入框中的文案
     * @param
     */
    public static void share9PicsToWXCircle(Context context, String Kdescription, File[] files ,ImageView imageView) {
        if (!isInstallWeChart(context)) {
            Toast.makeText(context,"您没有安装微信",Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();

        intent.setAction("android.intent.action.SEND_MULTIPLE");
        intent.setComponent(new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI"));
        ArrayList<Uri> imageList = new ArrayList<Uri>();

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.N){
            Uri imUrl = null;
            for (File file : files) {
                if (file.exists()) {
                    Uri uriForFile = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);
                    imUrl = uriForFile;
                    imageList.add(uriForFile);
                    intent.setDataAndType(uriForFile,context.getContentResolver().getType(uriForFile));
                }
            }

            imageView.setImageURI(imUrl);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_STREAM, imageList); //图片数据（支持本地图片的Uri形式）
            intent.putExtra("Kdescription", Kdescription); //微信分享页面，图片上边的描述
            context.startActivity(intent);
        }else{
            for (File file : files) {
                if (file.exists()) {
                    imageList.add(Uri.fromFile(file));
                    Toast.makeText(context,Uri.fromFile(file)+"",Toast.LENGTH_LONG).show();
                }
            }
            if(imageList.size() == 0){
                Toast.makeText(context,"图片不存在",Toast.LENGTH_SHORT).show();
                return;
            }
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_STREAM, imageList); //图片数据（支持本地图片的Uri形式）
            intent.putExtra("Kdescription", Kdescription); //微信分享页面，图片上边的描述
            context.startActivity(intent);

        }
    }

    /**不实用微信sdk检查是否安装微信
     * @param context
     * @return
     */
    public static boolean isInstallWeChart(Context context){
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo("com.tencent.mm", 0);
        } catch (Exception e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }


}
