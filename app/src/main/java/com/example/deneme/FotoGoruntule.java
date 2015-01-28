package com.example.deneme;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Random;

/**
 * Created by omer on 11.01.2015.
 */
public class FotoGoruntule extends Activity {
    public Button share;
    public Button SyhBeyz;
    public Button Kaydet;
    public Button Gri;
    public Button Sepya;
    public ImageView img;
    public Bitmap bmp;
    public Button Blur;
    public Button Zıtlık;
    public Button Parlaklık;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);

        share = (Button) findViewById(R.id.button2);
        SyhBeyz = (Button) findViewById(R.id.button3);
        Kaydet = (Button) findViewById(R.id.button);
        Sepya = (Button) findViewById(R.id.button5);
        Gri = (Button) findViewById(R.id.button4);
        Blur= (Button)findViewById(R.id.button10);
        img = (ImageView) findViewById(R.id.imageView);
        Zıtlık=(Button)findViewById(R.id.button7);
        Parlaklık=(Button)findViewById(R.id.button6);

        Bundle receiver = getIntent().getExtras();
        byte[] a = receiver.getByteArray("Verimiz");
        bmp = BitmapFactory.decodeByteArray(a, 0, a.length);
        img.setImageBitmap(bmp);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_STREAM, "");
                sendIntent.setType("image/*");
                startActivity(Intent.createChooser(sendIntent, "Bununla Paylaş:"));

            }
        });

        Zıtlık.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int width = bmp.getWidth();
                        int height = bmp.getHeight();
                        // create output bitmap
                        Bitmap bmOut = Bitmap.createBitmap(width, height, bmp.getConfig());
                        // color information
                        int A, R, G, B;
                        int pixel;
                // get contrast value
                double contrast = Math.pow((100 + 70) / 100, 2);

                // scan through all pixels
                for(int x = 0; x < width; ++x) {
                    for(int y = 0; y < height; ++y) {
                        // get pixel color
                        pixel = bmp.getPixel(x, y);
                        A = Color.alpha(pixel);
                        // apply filter contrast for every channel R, G, B
                        R = Color.red(pixel);
                        R = (int)(((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                        if(R < 0) { R = 0; }
                        else if(R > 255) { R = 255; }

                        G = Color.red(pixel);
                        G = (int)(((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                        if(G < 0) { G = 0; }
                        else if(G > 255) { G = 255; }

                        B = Color.red(pixel);
                        B = (int)(((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                        if(B < 0) { B = 0; }
                        else if(B > 255) { B = 255; }

                        // set new pixel color to output bitmap
                        bmOut.setPixel(x, y, Color.argb(A, R, G, B));
                    }
                }
                img.setImageBitmap(bmOut);
            }
        });
        Parlaklık.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int width = bmp.getWidth();
                int height = bmp.getHeight();
                // create output bitmap
                Bitmap bmOut = Bitmap.createBitmap(width, height, bmp.getConfig());
                // color information
                int A, R, G, B;
                int pixel;

                // scan through all pixels
                for(int x = 0; x < width; ++x) {
                    for(int y = 0; y < height; ++y) {
                        // get pixel color
                        pixel = bmp.getPixel(x, y);
                        A = Color.alpha(pixel);
                        R = Color.red(pixel);
                        G = Color.green(pixel);
                        B = Color.blue(pixel);

                        // increase/decrease each channel
                        R += 80;
                        if(R > 255) { R = 255; }
                        else if(R < 0) { R = 0; }

                        G += 80;
                        if(G > 255) { G = 255; }
                        else if(G < 0) { G = 0; }

                        B += 80;
                        if(B > 255) { B = 255; }
                        else if(B < 0) { B = 0; }

                        // apply new pixel color to output bitmap
                        bmOut.setPixel(x, y, Color.argb(A, R, G, B));

                    }

                }
                img.setImageBitmap(bmOut);
            }
        });

        SyhBeyz.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int width = bmp.getWidth();
                int height = bmp.getHeight();
                int[] pixels = new int[width * height];
                // get pixel array from source
                bmp.getPixels(pixels, 0, width, 0, 0, width, height);
                // random object
                Random random = new Random();

                int R, G, B, index = 0, thresHold = 0;
                // iteration through pixels
                for(int y = 0; y < height; ++y) {
                    for(int x = 0; x < width; ++x) {
                        // get current index in 2D-matrix
                        index = y * width + x;
                        // get color
                        R = Color.red(pixels[index]);
                        G = Color.green(pixels[index]);
                        B = Color.blue(pixels[index]);
                        // generate threshold
                        thresHold = random.nextInt(0xFF);
                        if(R < thresHold && G < thresHold && B < thresHold) {
                            pixels[index] = Color.rgb(0x00, 0x00, 0x00);
                        }
                    }
                }
                // output bitmap
                Bitmap bmOut = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                bmOut.setPixels(pixels, 0, width, 0, 0, width, height);
                img.setImageBitmap(bmOut);

            }

        });

        Kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                BitmapDrawable drawable = (BitmapDrawable) img.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                File sdCardDirectory = Environment.getExternalStorageDirectory();
                File image = new File(sdCardDirectory, "resim1.jpeg");
                boolean success = false;

                // Encode the file as a JPEG image.
                FileOutputStream outStream;
                try {
                    outStream = new FileOutputStream(image);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                    outStream.flush();
                    outStream.close();
                    success = true;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (success) {
                    Toast.makeText(getApplicationContext(), "Fotoğrafınız kaydedildi.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                           "Hata! ", Toast.LENGTH_LONG).show();
                }

            }
        });



        Gri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final double GS_RED = 0.299;
                final double GS_GREEN = 0.587;
                final double GS_BLUE = 0.114;

                // create output bitmap
                Bitmap bmOut = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());
                // pixel information
                int A, R, G, B;
                int pixel;

                // get image size
                int width = bmp.getWidth();
                int height = bmp.getHeight();

                // scan through every single pixel
                for (int x = 0; x < width; ++x) {
                    for (int y = 0; y < height; ++y) {
                        // get one pixel color
                        pixel = bmp.getPixel(x, y);
                        // retrieve color of all channels
                        A = Color.alpha(pixel);
                        R = Color.red(pixel);
                        G = Color.green(pixel);
                        B = Color.blue(pixel);
                        // take conversion up to one single value
                        R = G = B = (int) (GS_RED * R + GS_GREEN * G + GS_BLUE * B);
                        // set new pixel color to output bitmap
                        bmOut.setPixel(x, y, Color.argb(A, R, G, B));
                    }
                }
                img.setImageBitmap(bmOut);

            }
        });

        Blur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                final double GS_RED = 0.299;
                final double GS_GREEN = 0.587;
                  final double GS_BLUE = 0.114;

                // create output bitmap
                Bitmap bmOut = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());
                // pixel information
                int A, R, G, B;
                int pixel;

                // get image size
                int width = bmp.getWidth();
                int height = bmp.getHeight();

                // scan through every single pixel
                for (int x = 0; x < width; ++x) {
                    for (int y = 0; y < height; ++y) {
                        // get one pixel color
                        pixel = bmp.getPixel(x, y);
                        // retrieve color of all channels
                        A = Color.alpha(pixel);
                        R = Color.red(pixel);
                        G = Color.green(pixel);
                        B = Color.blue(pixel);
                        // take conversion up to one single value
                        R = G = B = (int) (GS_RED * R + GS_GREEN * G + GS_BLUE * B);
                        // set new pixel color to output bitmap
                        bmOut.setPixel(x, y, Color.argb(A, R, G, B));
                    }
                }
               img.setImageBitmap(bmOut);
            }
        });

        Sepya.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                int width = bmp.getWidth();
                int height = bmp.getHeight();
                // create output bitmap
                Bitmap bmOut = Bitmap.createBitmap(width, height, bmp.getConfig());
                // constant grayscale
                final double GS_RED = 0.3;
                final double GS_GREEN = 0.59;
                final double GS_BLUE = 0.11;
                // color information
                int A, R, G, B;
                int pixel;

                // scan through all pixels
                for (int x = 0; x < width; ++x) {
                    for (int y = 0; y < height; ++y) {
                        // get pixel color
                        pixel = bmp.getPixel(x, y);
                        // get color on each channel
                        A = Color.alpha(pixel);
                        R = Color.red(pixel);
                        G = Color.green(pixel);
                        B = Color.blue(pixel);
                        // apply grayscale sample
                        B = G = R = (int) (GS_RED * R + GS_GREEN * G + GS_BLUE * B);

                        // apply intensity level for sepid-toning on each channel
                        R += (10 * 1.5);
                        if (R > 255) {
                            R = 255;
                        }

                        G += (10 * 0.6);
                        if (G > 255) {
                            G = 255;
                        }

                        B += (10 * 0.12);
                        if (B > 255) {
                            B = 255;
                        }

                        // set new pixel color to output image
                        bmOut.setPixel(x, y, Color.argb(A, R, G, B));
                    }

                }
                img.setImageBitmap(bmOut);
            }
        });

    }
}