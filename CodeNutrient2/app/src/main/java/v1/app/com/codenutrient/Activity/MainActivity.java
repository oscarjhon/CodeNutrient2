package v1.app.com.codenutrient.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import java.io.IOException;
import java.sql.SQLException;

import v1.app.com.codenutrient.Helpers.DataBaseHelper;
import v1.app.com.codenutrient.POJO.AppUser;
import v1.app.com.codenutrient.R;

public class MainActivity extends AppCompatActivity {
    public static AppUser appUser;
    private int CAMERA_INTENT = 1;
    public ImageButton about;
    public ImageButton calendario;
    public ImageButton camara;
    public ImageButton cuenta;
    public ImageButton graficas;

    public ImageButton settings;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.camara = (ImageButton) findViewById(R.id.camara);
        this.cuenta = (ImageButton) findViewById(R.id.cuenta);
        this.graficas = (ImageButton) findViewById(R.id.graficas);
        this.calendario = (ImageButton) findViewById(R.id.calendario);
        this.settings = (ImageButton) findViewById(R.id.settings);
        this.about = (ImageButton) findViewById(R.id.about);
        this.camara.setOnClickListener(onClickListener);
        this.cuenta.setOnClickListener(onClickListener);
        this.graficas.setOnClickListener(onClickListener);
        this.calendario.setOnClickListener(onClickListener);
        this.settings.setOnClickListener(onClickListener);
        this.about.setOnClickListener(onClickListener);
        DataBaseHelper d = new DataBaseHelper(getApplicationContext());
        try {
            d.createDataBase();
            d.openDataBaseReadWrite();
            if (d.checkMeasure()) {
                d.executeMeasure();
            }
            d.close();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()) {
                case R.id.camara:
                    intent = getIntent().setClass(MainActivity.this, CustomViewFinderScannerActivity.class);
                    startActivityForResult(intent, CAMERA_INTENT);
                    break;
                case R.id.cuenta:
                    intent = getIntent().setClass(MainActivity.this, InfoAppUserActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_INTENT) {
            if (resultCode == RESULT_OK) {
                String codigo = data.getStringExtra("code");
                Intent intent = new Intent().setClass(this, ProductActivity.class);
                intent.putExtra("code", codigo);
                startActivity(intent);
            }else{
                Snackbar.make(findViewById(R.id.main_coordinator), "No se ha obtenido ningun código", Snackbar.LENGTH_SHORT).show();
            }
        }
    }
}
