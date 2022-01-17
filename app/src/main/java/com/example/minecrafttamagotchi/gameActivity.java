package com.example.minecrafttamagotchi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.View;

public class gameActivity extends AppCompatActivity
{
    // Declarations for this class:
    ProgressBar happinessBar, saturationBar, comfortBar;
    int happiness, saturation, comfort;
    int maxStatValue = 100;
    int statDecayRate = 1;

    // Declarations of data to be received from overviewActivity:
    String petSelected = "Null";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Load data from file:
        // ----- Currently just setting them hardcoded for development reasons
        happiness = 10;
        saturation = 10;
        comfort = 10;

        // Load data from overviewActivity:
        String petSelected = getIntent().getStringExtra("stringPetSelected");

        // Set the petSelected string from overviewActivity as the title of the gameActivity
        TextView gameTitle = (TextView) findViewById(R.id.gameTitle);
        gameTitle.setText(petSelected);

        happinessBar = (ProgressBar) findViewById(R.id.happinessBar);
        happinessBar.setMax(maxStatValue);
        saturationBar = (ProgressBar) findViewById(R.id.saturationBar);
        saturationBar.setMax(maxStatValue);
        comfortBar = (ProgressBar) findViewById(R.id.comfortBar);
        comfortBar.setMax(maxStatValue);

        update();
    }

    private void tamagotchiLogic()
    {

    }

    private void update()
    {
        happinessBar.setProgress(happiness);
        saturationBar.setProgress(saturation);
        comfortBar.setProgress(comfort);
    }

    public void pressedLove (View view)
    {
        happiness =+ 10;
        update();
    }
    public void pressedFood (View view)
    {
        saturation =+ 10;
        update();
    }
    public void pressedComfort (View view)
    {
        comfort =+ 10;
        update();
    }

    public void pressedBack (View view)
    {
        // This will send the app back to overviewActivity
        finish();
    }
}