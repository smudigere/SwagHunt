package tech.swaghunt.app;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.FrameLayout;

import net.sourceforge.zbar.Config;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;

/**
 * This class extends Activity to display the camera preview,
 * for the user to scan coupons and store them in the wallet.
 */
public class CameraScanActvity extends AppCompatActivity {

    @SuppressWarnings("deprecation")
    private Camera mCamera;
    private Handler autoFocusHandler;

    FrameLayout preview;

    ImageScanner scanner;
    private boolean previewing = true;

    @SuppressLint("ClickableViewAccessibility")
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_camera_scan);

        autoFocusHandler = new Handler();
        mCamera = getCameraInstance();
        // Instance barcode scanner
        scanner = new ImageScanner();
        scanner.setConfig(0, Config.X_DENSITY, 3);
        scanner.setConfig(0, Config.Y_DENSITY, 3);

        int[] symbols = getIntent().getIntArrayExtra(ZBarConstants.SCAN_MODES);
        if (symbols != null) {
            scanner.setConfig(Symbol.NONE, Config.ENABLE, 0);
            for (int symbol : symbols) {
                scanner.setConfig(symbol, Config.ENABLE, 1);
            }
        }

        CameraPreview mPreview = new CameraPreview(this, mCamera, previewCb, autoFocusCB);
        preview = findViewById(R.id.camera_preview);
        preview.addView(mPreview);

        super.onCreate(savedInstanceState);
    }


    /**
     * A safe way to get an instance of the Camera object.
     */
    @SuppressWarnings("deprecation")
    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open();
        } catch (Exception e) {
            //nada
        }
        return c;
    }

    /**
     * It is important that the camera preview is stopped,
     * in every occassion, the user exits or someother function is added to the main UI.
     */
    private void releaseCamera() {
        if (mCamera != null) {
            previewing = false;
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }


    /**
     * When the barcode is scanned, the data must be handled from here.
     */
    @SuppressWarnings("deprecation")
    Camera.PreviewCallback previewCb = new Camera.PreviewCallback() {
        @SuppressLint("StaticFieldLeak")
        public void onPreviewFrame(byte[] data, Camera camera) {
            Camera.Parameters parameters = camera.getParameters();
            Camera.Size size = parameters.getPreviewSize();

            Image barcode = new Image(size.width, size.height, "Y800");
            barcode.setData(data);

            final int result = scanner.scanImage(barcode);

            if (result != 0) {
                mCamera.cancelAutoFocus();
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();
                previewing = false;
                SymbolSet syms = scanner.getResults();  //the barcode is stored here in this string.
                for (Symbol sym : syms) {
                    String symData = sym.getData();
                    if (!TextUtils.isEmpty(symData)) {

                        Log.i("Scan Data", symData);

                        finish();
                    }
                }
            }
        }
    };

    // Mimic continuous auto-focusing
    @SuppressWarnings("deprecation")
    Camera.AutoFocusCallback autoFocusCB = new Camera.AutoFocusCallback() {
        public void onAutoFocus(boolean success, Camera camera) {
            autoFocusHandler.postDelayed(doAutoFocus, 3000);
        }
    };

    private Runnable doAutoFocus = new Runnable() {
        public void run() {
            if (previewing)
                mCamera.autoFocus(autoFocusCB);
        }
    };

    public void onPause() {
        super.onPause();
        releaseCamera();
    }

    @Override
    public void onBackPressed() {

            releaseCamera();
            Intent intent = new Intent();
            intent.putExtra("BARCODE", "NULL");
            setResult(RESULT_OK, intent);

        super.onBackPressed();
    }
}