package com.example.wuyf.unitywxsharesdk;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;

public class MyActivity extends UnityPlayerActivity implements View.OnClickListener{

    private File[] files;
    String path=Environment.getExternalStorageDirectory().getAbsolutePath();
    ImageView image,image2;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

           image =(ImageView)findViewById(R.id.image);
           image2=(ImageView)findViewById(R.id.image2);

    }

    @Override
    public void onClick(View v) {
//        int i = v.getId();
//        if (i == R.id.share_btn1) {
//            startGave();
//
//        }
    }

    /**
     * 权限管理
     * **/
    public void startGave(){

        UnityPlayer.UnitySendMessage("Main Camera","SetDebugStr","===startGave=====");
        if(Build.VERSION.SDK_INT <23){
            UnityPlayer.UnitySendMessage("Main Camera","SetDebugStr","===10=====");
        }else{
            UnityPlayer.UnitySendMessage("Main Camera","SetDebugStr","===20=====");
            List<String> list = new ArrayList<>();
            String [] permission;
            if(checkSelfPermission( Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
                list.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }

            if(checkSelfPermission( Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                list.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }

            if(list!=null&&list.size()>0){
                permission= new String[list.size()];
                for(int i =0;i<list.size();i++){
                    permission[i] = list.get(i);
                }
                this.requestPermissions(permission,0);
                list.clear();
                return;
            }
        }
        UnityPlayer.UnitySendMessage("Main Camera","SetDebugStr","===30=====");
        // 遍历 SD 卡下 .png 文件通过微信分享，保证SD卡根目录下有.png的文件
//        File root = Environment.getExternalStorageDirectory();
        File root = new File(path+"/sdcard/");
//        File destDir = new File(FILENAME); if (!destDir.exists()) { destDir.mkdirs();  }
        UnityPlayer.UnitySendMessage("Main Camera","SetDebugStr","===31=====");

        Bitmap abitmap = new BitmapFactory().decodeResource(getResources(), R.drawable.a);
        UnityPlayer.UnitySendMessage("Main Camera","SetDebugStr","===31.5=====");
        Bitmap bbitmap = new BitmapFactory().decodeResource(getResources(), R.drawable.b);
        UnityPlayer.UnitySendMessage("Main Camera","SetDebugStr","===32=====");
       // image.setImageBitmap(abitmap);
        File b = new File(path+"/sdcard/");
        UnityPlayer.UnitySendMessage("Main Camera","SetDebugStr","===33=====");
        if(!b.exists()){
            b.mkdirs();
            UnityPlayer.UnitySendMessage("Main Camera","SetDebugStr","===34=====");
        }

        try {
            UnityPlayer.UnitySendMessage("Main Camera","SetDebugStr","===40=====");
            File a = new File(path+"/sdcard/", "a.jpg");
            File bmap = new File(path+"/sdcard/", "b.jpg");
            if (!a.exists()) {
                a.createNewFile();
            }
            if(!b.exists()){
                bmap.createNewFile();
            }
            FileOutputStream out = new FileOutputStream(a);
            FileOutputStream bout = new FileOutputStream(bmap);
            abitmap.compress(CompressFormat.PNG, 90, out);
            bbitmap.compress(CompressFormat.PNG, 90, bout);
            out.flush();
            out.close();
            bout.flush();
            bout.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            UnityPlayer.UnitySendMessage("Main Camera","SetDebugStr","===50=====");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            UnityPlayer.UnitySendMessage("Main Camera","SetDebugStr","===60=====");
        }

        files = root.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (pathname.getName().endsWith(".jpg"))
                    return true;
                return false;
            }
        });
        UnityPlayer.UnitySendMessage("Main Camera","SetDebugStr","===70=====");
        ShareUtils.share9PicsToWXCircle(this, "你好，成功的分享了多张照片到微信",files,image2);

    }
}