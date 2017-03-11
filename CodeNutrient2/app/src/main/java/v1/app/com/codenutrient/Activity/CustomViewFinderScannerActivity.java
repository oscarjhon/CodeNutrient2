package v1.app.com.codenutrient.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.zxing.Result;
import com.squareup.picasso.Picasso;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import v1.app.com.codenutrient.R;

public class CustomViewFinderScannerActivity extends BaseScannerActivity implements ZXingScannerView.ResultHandler {
    private boolean mFlash;
    private ZXingScannerView mScannerView;

    public CustomViewFinderScannerActivity() {}

    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.code_reader);
        ViewGroup contentFrame = (ViewGroup) findViewById(R.id.content_frame);
        this.mScannerView = new ZXingScannerView(this);
        this.mScannerView.setFormats(ZXingScannerView.ALL_FORMATS);
        this.mFlash = false;
        contentFrame.addView(this.mScannerView);
    }

    public void handleResult(Result rawResult) {
        Intent resultData = new Intent();
        resultData.putExtra("code", rawResult.getText());
        resultData.putExtra("format", rawResult.getBarcodeFormat().toString());
        setResult(-1, resultData);
        finish();
    }



    public void onPause() {
        super.onPause();
        this.mScannerView.stopCamera();
        setResult(0);
        finish();
    }

    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    public void onStop() {
        super.onStop();
        this.mScannerView.stopCamera();
        setResult(0);
        finish();
    }

    public void toggleFlash(View v) {
        mFlash = !mFlash;
        if (mFlash) {
            Picasso.with(getApplicationContext()).load(R.drawable.ic_flash_off).into((ImageView) v);
        } else {
            Picasso.with(getApplicationContext()).load(R.drawable.ic_flash_on).into((ImageView) v);
        }
        this.mScannerView.setFlash(this.mFlash);
    }


}
