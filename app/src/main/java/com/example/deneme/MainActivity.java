package com.example.deneme;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;



import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.SurfaceView;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity implements SurfaceHolder.Callback {

    public Camera mCamera;
    private Button fCekBtn;
    private SurfaceView srfView;
    public PackageManager pm;
    private Boolean hasCamera = false;

    @SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fCekBtn = (Button) findViewById(R.id.button1);
        srfView = (SurfaceView) findViewById(R.id.surfaceView1);
        pm = this.getPackageManager();

        if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            hasCamera = true;
            SurfaceHolder holder = srfView.getHolder();
            holder.addCallback(this);
            holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
            holder.setFixedSize(600, 400);

        } else {
            Log.v("uyarı", " *** kamera mevcut degil ! ");
        }

        fCekBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               takePicture();
            }

        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
         mCamera= Camera.open();
         mCamera.setDisplayOrientation(90);

        try {
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();
         } catch (IOException e) {
                e.printStackTrace();
         }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
         mCamera.stopPreview();
         mCamera.release();
    }
    private void takePicture(){
        mCamera.takePicture(_shutterCallBack, _rawCallBack, _jpgCallBack);
    }


    Camera.ShutterCallback _shutterCallBack;

    {
        _shutterCallBack = new Camera.ShutterCallback() {
            @Override
            public void onShutter() {
                //deklansör kapandıgında yapılacak islem
            }
        };
    }


    Camera.PictureCallback _rawCallBack=new Camera.PictureCallback(){
        @Override
        public void onPictureTaken(byte[] arg0, Camera arg1) {
            //g�r�nt�n�n raw verisiyle yap�lacak i�lem



        }
    };
    Camera.PictureCallback _jpgCallBack = new Camera.PictureCallback(){

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            Intent intent = new Intent(MainActivity.this,FotoGoruntule.class);
            intent.putExtra("Verimiz", data);
            startActivity(intent);
        }
    };


     
     

}