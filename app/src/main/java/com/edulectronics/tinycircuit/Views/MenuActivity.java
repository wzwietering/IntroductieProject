package com.edulectronics.tinycircuit.Views;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;

import com.edulectronics.tinycircuit.R;
import com.edulectronics.tinycircuit.Views.Adapters.MainMenuAdapter;

import java.util.Locale;

public class MenuActivity extends AppCompatActivity {
    String language ="nl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu);
        this.createMenu();

    }

    public void changelanguage(View view){
        ImageView flag = (ImageView) findViewById(R.id.flag);
        if (language == "nl") {
            language = "en";
            flag.setImageResource(R.drawable.englishflag);
        }
        else {
            language = "nl";
            flag.setImageResource(R.drawable.dutchflag);
        }
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        this.createMenu();
    }

    private void createMenu(){
        ListView listView = (ListView) findViewById(R.id.buttonArea);
        String[] items = {getResources().getString(R.string.exercise), getResources().getString(R.string.freeplay)};
        listView.setAdapter(new MainMenuAdapter(this, items));
    }
}
