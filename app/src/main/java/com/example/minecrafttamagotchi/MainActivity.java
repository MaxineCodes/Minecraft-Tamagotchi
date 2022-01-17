package com.example.minecrafttamagotchi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;

public class MainActivity extends AppCompatActivity
{
    // This activity is to display the Splash, and let the user click on a Play button.
    // The Play button will send the user to the OverviewActivity.
    // This activity is also potentially used to load in assets.

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void screenTapped(View view)
    {
        // Check if screen is tapped to skip splash screen perhaps?
        // This is for future use.
    }

    public void pressedStart(View view)
    {
        // Switches to OverviewActivity
        switchToOverviewActivity();
    }

    private void switchToOverviewActivity()
    {
        // Create Intent to switch to overviewActivity.
        startActivity(new Intent(this, overviewActivity.class));
    }
}