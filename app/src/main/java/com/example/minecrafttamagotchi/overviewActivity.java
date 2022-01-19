package com.example.minecrafttamagotchi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    Intent switchActivityToGameIntent;
    Intent switchActivityToNewPetIntent;

    SharedPreferences petData;
    boolean p1_enabled, p2_enabled;

    // Declarations of data to be sent:
    int petSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        // Create intents
        switchActivityToGameIntent = new Intent(this, gameActivity.class);
        switchActivityToNewPetIntent = new Intent(this, newPetActivity.class);

        petData = getSharedPreferences("petDataPrefs", Context.MODE_PRIVATE);

        p1_enabled = petData.getBoolean("p1_enabled", true);
        p2_enabled = petData.getBoolean("p2_enabled", true);
    }

    // If Pet1 option is pressed
    public void pressedPet1(View view)
    {
        // Create data here:
        petSelected = 1;

        // If the pet is NOT enabled, go to newPetActivity
        if (!p1_enabled)
        {
            switchActivityToNewPet();
            return;
        }
        // Switches to gameActivity if the pet IS enabled
        switchActivityToGame();
    }

    // If Pet2 option is pressed
    public void pressedPet2(View view)
    {
        // Create data here:
        petSelected = 2;

        // If the pet is NOT enabled, go to newPetActivity
        if (!p2_enabled)
        {
            switchActivityToNewPet();
            return;
        }
        // Switches to gameActivity if the pet IS enabled
        switchActivityToGame();
    }

    private void switchActivityToGame()
    {
        // Send data here:
        switchActivityToGameIntent.putExtra("petSelected", petSelected);
        // Switches to gameActivity
        startActivity(switchActivityToGameIntent);
    }
    private void switchActivityToNewPet()
    {
        // Send data here:
        switchActivityToNewPetIntent.putExtra("petSelected", petSelected);
        // Switches to newPetActivity
        startActivity(switchActivityToNewPetIntent);
    }

    public void pressedBack (View view)
    {
        // This will send the app back to mainActivity
        finish();
    }
}