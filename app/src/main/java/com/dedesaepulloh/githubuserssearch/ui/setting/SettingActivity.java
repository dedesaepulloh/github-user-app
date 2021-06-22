package com.dedesaepulloh.githubuserssearch.ui.setting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.dedesaepulloh.githubuserssearch.R;
import com.dedesaepulloh.githubuserssearch.utils.receiver.ReminderReceiver;

import java.util.Objects;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String PREF_REMINDER = "pref_reminder";

    private ReminderReceiver mReminderReceiver;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ImageButton btnLanguage = findViewById(R.id.btn_language);
        Switch switchReminder = findViewById(R.id.switch_reminder);
        mReminderReceiver = new ReminderReceiver();

        btnLanguage.setOnClickListener(this);
        preferences = getSharedPreferences(PREF_REMINDER, Context.MODE_PRIVATE);
        if (preferences != null) {
            boolean reminderCheck = preferences.getBoolean(PREF_REMINDER, false);

            if (reminderCheck) {
                switchReminder.setChecked(true);
            } else {
                switchReminder.setChecked(false);
            }
        }

        switchReminder.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                String repeatTime = "09:00";
                String repeatMessage = getResources().getString(R.string.reminder_message);
                Context context = getApplicationContext();
                mReminderReceiver.setRepeatingAlarm(context, ReminderReceiver.TITLE, repeatTime, repeatMessage);
                saveSetting(true);
            } else {
                mReminderReceiver.cancelAlarm(this);
                saveSetting(false);
            }
        });

        String titleDetail = getString(R.string.change_setting);
        Objects.requireNonNull(getSupportActionBar()).setTitle(titleDetail);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_language) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
    }

    private void saveSetting(Boolean reminder_setting) {
        preferences = getSharedPreferences(PREF_REMINDER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(PREF_REMINDER, reminder_setting);
        editor.apply();
    }
}
