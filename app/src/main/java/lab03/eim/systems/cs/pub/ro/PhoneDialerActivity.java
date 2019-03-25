package lab03.eim.systems.cs.pub.ro;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class PhoneDialerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_dialer);



        // Uncommend this to lock the orientation to Portrait
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        final EditText editText = findViewById(R.id.edit_text);

        // Update to save state of the edit textbox
        if (savedInstanceState != null) {
            if(savedInstanceState.getString(Constants.PHONE_NUMBER_EDIT_TEXTBOX) != null) {
                editText.setText(savedInstanceState.getString(Constants.PHONE_NUMBER_EDIT_TEXTBOX));
            }
        }

        int buttons[] = {R.id.button_0,
                R.id.button_1,
                R.id.button_2,
                R.id.button_3,
                R.id.button_4,
                R.id.button_5,
                R.id.button_6,
                R.id.button_7,
                R.id.button_8,
                R.id.button_9,
                R.id.button_star,
                R.id.button_hash};


        for (int buttonIndex : buttons) {
            final Button button = findViewById(buttonIndex);
            button.setOnClickListener(new ListenerDialer(editText, button));
        }


        ImageButton button_back = findViewById(R.id.back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int textLen = editText.getText().length();
                if (textLen > 0)
                    editText.getText().delete(textLen - 1, textLen);
            }
        });

        ImageButton button_call = findViewById(R.id.call);
        button_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(PhoneDialerActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            PhoneDialerActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            Constants.PERMISSION_REQUEST_CALL_PHONE);
                } else {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + editText.getText().toString()));
                    startActivity(intent);
                }
            }
        });

        ImageButton button_end_call = findViewById(R.id.end_call);
        button_end_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Update for lab4:
        ImageButton contactsBtn = findViewById(R.id.contacts_btn);
        contactsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = editText.getText().toString();
                if  (phoneNumber.length() > 0) {
                    Intent intent = new Intent("lab04.eim.systems.cs.pub.ro.intent.action.ContactsManagerActivity");
                    intent.putExtra("lab04.eim.systems.cs.pub.ro.intent.action.ContactsManagerActivity.PHONE_NUMBER_KEY", phoneNumber);
                    startActivityForResult(intent, Constants.CONTACTS_MANAGER_REQUEST_CODE);
                } else {
                    Toast.makeText(getApplicationContext(), "No phone number", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    class ListenerDialer implements View.OnClickListener {
        EditText editText;
        Button button;

        public ListenerDialer(EditText editText, Button button) {
            this.editText = editText;
            this.button = button;
        }

        @Override
        public void onClick(View v) {
            editText.append((button.getText()));
        }
    }

    // Updates for forced save state
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        EditText phoneNrEditText = findViewById(R.id.edit_text);
        if(savedInstanceState.getString(Constants.PHONE_NUMBER_EDIT_TEXTBOX) != null) {
            phoneNrEditText.setText(savedInstanceState.getString(Constants.PHONE_NUMBER_EDIT_TEXTBOX));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        EditText phoneNrEditText = findViewById(R.id.edit_text);
        outState.putString(Constants.PHONE_NUMBER_EDIT_TEXTBOX, phoneNrEditText.getText().toString());
    }

}
