package com.example.minecrafttamagotchi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

public class newPetActivity extends AppCompatActivity
{
    int statDefaultValue = 75;

    SharedPreferences petData;
    int petSelected;

    // Amount of taps until the spawn egg breaks
    int tapCountTillBreak = 5;

    ImageView displayImage;
    TextView titleText;
    Button confirmButton;

    Bitmap petPreview, egg;

    ProgressBar egg_healthbar;

    // typeID is made a random number
    int typeID;
    // typeID list
    // 0 : Snow Fox
    // 1 : Regular Fox
    // 2 : Wolf
    // 3 : Wolf Variation
    // 4 : Cat Black
    // 5 : Cat Calico

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Set the XML layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pet);

        // Load which pet is selected from overviewActivity:
        petSelected = getIntent().getIntExtra("petSelected", 0);

        generateNewPet();

        displayImage = (ImageView) findViewById(R.id.displayImage);
        titleText = (TextView) findViewById(R.id.titleText);
        confirmButton = (Button) findViewById(R.id.confirmButton);

        // Switch display data based on the random typeID number
        switch (typeID){
            case 0: // ID 0 : Snow Fox
                egg = BitmapFactory.decodeResource(this.getResources(), R.drawable.spawn_egg_snowfox);
                petPreview = BitmapFactory.decodeResource(this.getResources(), R.drawable.preview_snowfox);
                break;
            case 1: // ID 1 : Regular Fox
                egg = BitmapFactory.decodeResource(this.getResources(), R.drawable.spawn_egg_fox);
                petPreview = BitmapFactory.decodeResource(this.getResources(), R.drawable.preview_fox);
                break;
            case 2: // Wolf

                break;
            case 3: // Wolf Variation

                break;
            case 4: // Cat Black

                break;
            case 5: // Cat Calico

                break;
            default:    // If there was an error in getting a random number
                egg = BitmapFactory.decodeResource(this.getResources(), R.drawable.spawn_egg_default);
                petPreview = BitmapFactory.decodeResource(this.getResources(), R.drawable.preview_default);
                break;
        }

        confirmButton.setVisibility(View.INVISIBLE);

        egg_healthbar = (ProgressBar) findViewById(R.id.egg_healthbar);
        egg_healthbar.setMax(tapCountTillBreak);

        displayImage.setImageBitmap(egg);
    }

    // Check if the user has tapped 5 times
    int tapCount;
    public void screenTapped(View view)
    {
        tapCount++;
        if (tapCount >= tapCountTillBreak)
        {
            // Reset tapCount
            tapCount = 0;
            egg_healthbar.setProgress(tapCountTillBreak);
            egg_healthbar.setVisibility(View.INVISIBLE);
            titleText.setVisibility((View.INVISIBLE));
            displayImage.setImageBitmap(petPreview);
            confirmButton.setVisibility(View.VISIBLE);
            return;
        }
        egg_healthbar.setProgress(tapCount);
    }

    private void generateNewPet()
    {
        // Set typeID to be a random number between 0 and maximum.
        int maximum = 1;
        Random Random = new Random();
        typeID = Random.nextInt(maximum + 1);

        // For some reason this doesn't work........
        //String typeIDtoString = String.valueOf(typeID);
        //titleText.setText("ID: " + typeIDtoString);

        // Allows SharedPreference "petData" to be edited easily.
        petData = getSharedPreferences("petDataPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = petData.edit();
    }

    public void pressedBack (View view)
    {
        // This will send the app back to overviewActivity
        finish();
    }
    public void pressedConfirm (View view)
    {
        // Save data
        petData = getSharedPreferences("petDataPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = petData.edit();
        // Change typeID in sharedPreferences to the correctly selected pet
        if (petSelected == 1) {
            spEditor.putInt("p1_typeID", typeID);
            spEditor.putBoolean("p1_enabled", true);

            // Reset stats
            spEditor.putInt("p1_lastHappinessValue", statDefaultValue);
            spEditor.putInt("p1_lastSaturationValue", statDefaultValue);
            spEditor.putInt("p1_lastHygieneValue", statDefaultValue);

            spEditor.putInt("p1_xpValue", 0);
            spEditor.putBoolean("p1_isAdult", false);
        }
        if (petSelected == 2) {
            spEditor.putInt("p2_typeID", typeID);
            spEditor.putBoolean("p2_enabled", true);

            // Reset stats
            spEditor.putInt("p2_lastHappinessValue", statDefaultValue);
            spEditor.putInt("p2_lastSaturationValue", statDefaultValue);
            spEditor.putInt("p2_lastHygieneValue", statDefaultValue);

            spEditor.putInt("p2_xpValue", 0);
            spEditor.putBoolean("p2_isAdult", false);
        }
        spEditor.apply();

        // This will send the app back to overviewActivity
        finish();
    }
}