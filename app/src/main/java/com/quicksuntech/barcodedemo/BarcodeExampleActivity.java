package com.quicksuntech.barcodedemo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.EnumMap;
import java.util.Map;

public class BarcodeExampleActivity extends AppCompatActivity {

    String scanContent;
    String scanFormat;
    TextView tv, logout;
    Toolbar toolbar;
    ImageView img;
    Button scan;
    SharedPrefQRCode sharedPrefQRCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        sharedPrefQRCode = SharedPrefQRCode.getInstance();
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            toolbar.setTitle("");

            TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
            mTitle.setPadding(20, 0, 0, 0);
            mTitle.setText("QR Scanner");
            logout = (TextView) toolbar.findViewById(R.id.logout);
            logout.setVisibility(View.VISIBLE);
            setSupportActionBar(toolbar);

        } else {
            Toast.makeText(BarcodeExampleActivity.this, "toolbar is null", Toast.LENGTH_SHORT).show();
        }

        img = (ImageView) findViewById(R.id.img);
        scan = (Button) findViewById(R.id.scan);
        tv = (TextView) findViewById(R.id.tv);

        // barcode data
        String barcode_data = "123456789000";

        // barcode image
        Bitmap bitmap = null;

        try {

            bitmap = encodeAsBitmap(barcode_data, BarcodeFormat.QR_CODE, 800, 400);
            img.setImageBitmap(bitmap);
            img.setPadding(0, 20, 0, 0);

        } catch (WriterException e) {
            e.printStackTrace();
        }

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                IntentIntegrator scanIntegrator = new IntentIntegrator(BarcodeExampleActivity.this);
                scanIntegrator.setPrompt("Scan");
                scanIntegrator.setBeepEnabled(true);
                //The following line if you want QR code
                scanIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                scanIntegrator.setCaptureActivity(CaptureActivityAnyOrientation.class);
                scanIntegrator.setOrientationLocked(true);
                //scanIntegrator.setBarcodeImageEnabled(true);
                scanIntegrator.initiateScan();

                /*try{

                    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                    startActivityForResult(intent, 0);

                }catch (ActivityNotFoundException anfe) {l
                    Log.e("onCreate", "Scanner Not Found", anfe);
                }*/
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPrefQRCode.saveISLogged_IN(BarcodeExampleActivity.this, false);
                Intent intent = new Intent(BarcodeExampleActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }

    /**************************************************************
     * getting from com.google.zxing.client.android.encode.QRCodeEncoder
     *
     * See the sites below
     * http://code.google.com/p/zxing/
     * http://code.google.com/p/zxing/source/browse/trunk/android/src/com/google/zxing/client/android/encode/EncodeActivity.java
     * http://code.google.com/p/zxing/source/browse/trunk/android/src/com/google/zxing/client/android/encode/QRCodeEncoder.java
     */

    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;

    Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int img_width, int img_height) throws WriterException {
        String contentsToEncode = contents;
        if (contentsToEncode == null) {
            return null;
        }
        Map<EncodeHintType, Object> hints = null;
        String encoding = guessAppropriateEncoding(contentsToEncode);
        if (encoding != null) {
            hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result;
        try {
            result = writer.encode(contentsToEncode, format, img_width, img_height, hints);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanningResult != null) {
            if (scanningResult.getContents() != null) {
                scanContent = scanningResult.getContents().toString();
                scanFormat = scanningResult.getFormatName().toString();

                Toast.makeText(this, scanContent + "   type:" + scanFormat, Toast.LENGTH_SHORT).show();
                tv.setText("Barcode Data : "+scanContent);
            }

        } else {
            Toast.makeText(this, "Nothing scanned", Toast.LENGTH_SHORT).show();
        }
    }
}
