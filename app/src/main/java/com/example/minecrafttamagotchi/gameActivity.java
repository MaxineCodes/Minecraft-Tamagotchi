package com.example.minecrafttamagotchi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.View;
import android.os.SystemClock;

public class gameActivity extends AppCompatActivity
{
    // Declarations for this class:

    ProgressBar happinessBar, saturationBar, hygieneBar, xpBar;
    ImageView displayImage;
    Bitmap petImage;

    int typeID;
    int experience; boolean isAdult; int experienceTillAdult = 100;

    int happiness, saturation, hygiene;
    int maxStatValue = 100;             // Maximum value. Increase for more gradual decay
    int happinessStatDecayRate = 3;     // DecayRate in points per hour
    int saturationStatDecayRate = 1;    // DecayRate in points per hour
    int hygienStatDecayRate = 5;        // DecayRate in points per hour
    int statDefaultValue = 1;           // Default stat for reset

    SharedPreferences petData;

    // Declarations of data to be received from overviewActivity:
    int petSelected;

    //region onCreate()
    @Override
    // onCreate() called upon initialization
    protected void onCreate(Bundle savedInstanceState)
    {
        // Set the XML layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //resetData();

        // Allows SharedPreference "petData" to be edited easily.
        petData = getSharedPreferences("petDataPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = petData.edit();

        // Load which pet is selected from overviewActivity:
        petSelected = getIntent().getIntExtra("petSelected", 0);

        // Load data from file:
        loadData();

        // Set the petSelected int from overviewActivity as the title of the gameActivity as string
        TextView gameTitle = (TextView) findViewById(R.id.gameTitle);
        String petSelectedToString = String.valueOf(petSelected);
        gameTitle.setText("Pet " + petSelectedToString + " selected");

        // Find the progress bars based on ID and set their max value
        happinessBar = (ProgressBar) findViewById(R.id.happinessBar);
        happinessBar.setMax(maxStatValue);
        saturationBar = (ProgressBar) findViewById(R.id.saturationBar);
        saturationBar.setMax(maxStatValue);
        hygieneBar = (ProgressBar) findViewById(R.id.comfortBar);
        hygieneBar.setMax(maxStatValue);
        xpBar = (ProgressBar) findViewById(R.id.xpBar);
        xpBar.setMax(experienceTillAdult);

        displayImage = (ImageView) findViewById(R.id.gamePetImage);

        // Temporary function to simulate decay:
        happiness = happiness - happinessStatDecayRate * 10;
        saturation = saturation - saturationStatDecayRate * 10;
        hygiene = hygiene - hygienStatDecayRate * 10;

        // Switch display data based on the typeID
        switch (typeID){
            case 0: // ID 0 : Snow Fox
                petImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.preview_snowfox);
                break;
            case 1: // ID 1 : Regular Fox
                petImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.preview_fox);
                break;
            case 2: // Wolf

                break;
            case 3: // Wolf Variation

                break;
            case 4: // Cat Black

                break;
            case 5: // Cat Calico

                break;
            default:    // If there was an error in getting the typeID
                petImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.preview_default);
                break;
        }

        displayImage.setImageBitmap(petImage);

        update();
    }
    //endregion

    //region Update()
    // Update() is called on initialization of this activity or
    // when a user input is detected or
    // when a minute has passed ---------- still working on this
    private void update()
    {
        // Allows SharedPreference "petData" to be edited easily.
        petData = getSharedPreferences("petDataPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = petData.edit();

        // Updating the progress bars to the current stat values
        happinessBar.setProgress(happiness);
        saturationBar.setProgress(saturation);
        hygieneBar.setProgress(hygiene);
        xpBar.setProgress(experience);

        // Clamping stat values
        if (happiness >= maxStatValue) {
            happiness = maxStatValue;
        }
        if (saturation >= maxStatValue) {
            saturation = maxStatValue;
        }
        if (hygiene >= maxStatValue) {
            hygiene = maxStatValue;
        }

        // Checking if pet is growing up
        if (experience >= experienceTillAdult && !isAdult) {
            isAdult = true;
            experience = 0;
            // Call a function() that starts the growing up process.
        }
        // Checking if pet is evolving
        if (experience >= experienceTillAdult && isAdult) {
            // Call a function() that evolves pet (choosing a new pet)
        }

        // Checking if pet is neglected
        if (happiness <= 0) {
            happiness = 0;
            // Pet is very sad
            // Pet leaves owner. A sign appears instead of the pet. "Found love someplace else.."
        }
        if (saturation <= 0) {
            saturation = 0;
            // kills pet
            if (petSelected == 1) {
                spEditor.putBoolean("p1_enabled", false);
            }
            if (petSelected == 2) {
                spEditor.putBoolean("p2_enabled", false);
            }
            spEditor.commit();
        }
        if (hygiene <= 0) {
            hygiene = 0;
            // Being unclean makes the pet sad.
            happiness = happiness - 5;
        }

        // Calling function that stores data to file.
        // ----- In update might be a bit excessive, but very reliable. Change if there are lag spikes.
        storeData();
    }
    //endregion

    //region Data Management
    // Loading data from file
    private void loadData()
    {
        // Data that needs to be loaded and stored to petData:
        // - Happiness, Saturation and Hygiene stat values from last visit.
        // - Pet Experience
        // - The time and date of last visit.

        // Allows SharedPreference "petData" to be edited easily.
        petData = getSharedPreferences("petDataPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = petData.edit();

        // Loads the current stat values from petData based on which pet was selected in overviewActivity
        if (petSelected == 1)
        {
            // Reads what type of pet it is
            typeID = petData.getInt("p1_typeID", 0);
            // typeID list
            // 0 : Snow Fox
            // 1 : Regular Fox
            // 2 : Wolf
            // 3 : Wolf Variation
            // 4 : Cat Black
            // 5 : Cat Calico

            // Loads the current stat values from petData
            happiness = petData.getInt("p1_lastHappinessValue", 0);
            saturation = petData.getInt("p1_lastSaturationValue", 0);
            hygiene = petData.getInt("p1_lastHygieneValue", 0);

            // Loads the current XP and whether the pet is a child from petData
            experience = petData.getInt("p1_xpValue", 0);
            isAdult = petData.getBoolean("p1_isAdult", false);
        }
        if (petSelected == 2)
        {
            // Reads what type of pet it is
            typeID = petData.getInt("p2_typeID", 0);
            // typeID list
            // 0 : Snow Fox
            // 1 : Regular Fox
            // 2 : Wolf
            // 3 : Wolf Variation
            // 4 : Cat Black
            // 5 : Cat Calico

            // Loads the current stat values from petData
            happiness = petData.getInt("p2_lastHappinessValue", 0);
            saturation = petData.getInt("p2_lastSaturationValue", 0);
            hygiene = petData.getInt("p2_lastHygieneValue", 0);

            // Loads the current XP and whether the pet is a child from petData
            experience = petData.getInt("p2_xpValue", 0);
            isAdult = petData.getBoolean("p2_isAdult", false);
        }
    }

    // Writing data to file
    private void storeData()
    {
        // Allows SharedPreference "petData" to be edited easily.
        petData = getSharedPreferences("petDataPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = petData.edit();

        // Stores the current stat values to petData based on which pet was selected in overviewActivity
        if (petSelected == 1)
        {
            // Stores the current stat values to petData
            spEditor.putInt("p1_lastHappinessValue", happiness);
            spEditor.putInt("p1_lastSaturationValue", saturation);
            spEditor.putInt("p1_lastHygieneValue", hygiene);

            // Stores the current XP and whether the pet is a child to petData
            spEditor.putInt("p1_xpValue", experience);
            spEditor.putBoolean("p1_isAdult", isAdult);
        }
        if (petSelected == 2)
        {
            // Stores the current stat values to petData
            spEditor.putInt("p2_lastHappinessValue", happiness);
            spEditor.putInt("p2_lastSaturationValue", saturation);
            spEditor.putInt("p2_lastHygieneValue", hygiene);

            // Stores the current XP and whether the pet is a child to petData
            spEditor.putInt("p2_xpValue", experience);
            spEditor.putBoolean("p2_isAdult", isAdult);
        }

        // Commits the updates to petData
        spEditor.apply();
    }

    private void resetData()
    {
        // Allows SharedPreference "petData" to be edited easily.
        petData = getSharedPreferences("petDataPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = petData.edit();

        // Resetting Pet 1 Data
        spEditor.putInt("p1_lastHappinessValue", statDefaultValue);
        spEditor.putInt("p1_lastSaturationValue", statDefaultValue);
        spEditor.putInt("p1_lastHygieneValue", statDefaultValue);

        spEditor.putInt("p1_xpValue", 0);
        spEditor.putBoolean("p1_isAdult", false);

        // Resetting Pet 2 Data
        spEditor.putInt("p2_lastHappinessValue", statDefaultValue);
        spEditor.putInt("p2_lastSaturationValue", statDefaultValue);
        spEditor.putInt("p2_lastHygieneValue", statDefaultValue);

        spEditor.putInt("p2_xpValue", 0);
        spEditor.putBoolean("p2_isAdult", false);

        // Commits the reset to petData
        spEditor.apply();
    }
    //endregion

    //region Inputs
    // --------- Button logic
    public void pressedLove (View view)
    {
        // In the future: press this button and then swipe over the pet to pet the pet.
        // Confusing sentence I know..
        happiness = happiness + 10;
        experience = experience + 5; // -------------------------------------------- temporary
        update();
    }
    public void pressedFood (View view)
    {
        // In the future: press this button and drag a food item to the pet.
        // Item type matters. A cat would love chicken and fish, beef and pork a little less, and would not like plant food at all.
        // The effectiveness of the item will be stored in the class for that pet.
        saturation = saturation + 10;
        experience = experience + 5; // -------------------------------------------- temporary
        update();
    }
    public void pressedHygiene(View view)
    {
        // Change to hygiene perhaps?
        // Using hygiene shows a showerhead. This showerhead would clean the pet.
        // Some pets might not need to be cleaned. A cat becomes unhappy from being showered.
        hygiene = hygiene + 10;
        experience = experience + 5; // -------------------------------------------- temporary
        update();
    }
    public void pressedBack (View view)
    {
        // This will send the app back to overviewActivity
        finish();
    }
    //endregion
}