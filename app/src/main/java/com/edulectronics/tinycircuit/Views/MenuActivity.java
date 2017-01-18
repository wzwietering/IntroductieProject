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
    String language = "nl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu);
        //The split is used because language tags like 'en-US' and 'en-GB' should all use English
        language = Locale.getDefault().toLanguageTag().substring(0, 2);
        setFlag();
        this.createMenu();
    }

    public void changelanguage(View view){
        switchLanguage();
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

    private void switchLanguage(){
        if (language == "nl"){
            language = "en";
        } else {
            language = "nl";
        }
        setFlag();
    }

    private void setFlag(){
        ImageView flag = (ImageView) findViewById(R.id.flag);
        if (language.equals("nl")) {
            flag.setImageResource(R.drawable.dutchflag);
        }
        else {
            flag.setImageResource(R.drawable.englishflag);
        }
    }
}
