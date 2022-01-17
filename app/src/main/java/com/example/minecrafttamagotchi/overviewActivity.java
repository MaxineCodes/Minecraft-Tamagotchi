package com.example.minecrafttamagotchi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class overviewActivity extends AppCompatActivity
{
    // This activity is used to let the user select which pet to take care of.
    // Pressing a button sends the player to gameActivity and also loads the appropriate data with that pet.
    // If there is no pet, the user will be prompted to select a new spawn egg.

    // Future features:
    // Preview image, the button will display an image of the pet the user is taking care of.
    // Preview stats, the user can quickly view the current stats of the pet.

    // Declarations for this class:
    Intent switchActivityIntent;

    // Declarations of data to be sent:
    String petSelected = "Null";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        // Create an intent
        switchActivityIntent = new Intent(this, gameActivity.class);
    }

    // If Pet1 option is pressed
    public void pressedPet1(View view)
    {
        // Create data here:
        petSelected = "Pet 1";

        // Switches to gameActivity
        switchActivity();
    }

    // If Pet2 option is pressed
    public void pressedPet2(View view)
    {
        // Create data here:
        petSelected = "Pet 2";

        // Switches to gameActivity
        switchActivity();
    }

    private void switchActivity()
    {
        // Send data here:
        switchActivityIntent.putExtra("stringPetSelected", petSelected);

        // Switches to gameActivity
        startActivity(switchActivityIntent);
    }
}