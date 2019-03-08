package lab03.eim.systems.cs.pub.ro;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.SyncStateContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class PhoneDialerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_dialer);

        final EditText editText = findViewById(R.id.edit_text);

        final Button button1 = findViewById(R.id.button_1);
        final Button button2 = findViewById(R.id.button_2);
        final Button button3 = findViewById(R.id.button_3);
        final Button button4 = findViewById(R.id.button_4);
        final Button button5 = findViewById(R.id.button_5);
        final Button button6 = findViewById(R.id.button_6);
        final Button button7 = findViewById(R.id.button_7);
        final Button button8 = findViewById(R.id.button_8);
        final Button button9 = findViewById(R.id.button_9);
        final Button button0 = findViewById(R.id.button_0);

        button1.setOnClickListener(new ListenerDialer(editText, button1));
        button2.setOnClickListener(new ListenerDialer(editText, button2));
        button3.setOnClickListener(new ListenerDialer(editText, button3));
        button4.setOnClickListener(new ListenerDialer(editText, button4));
        button5.setOnClickListener(new ListenerDialer(editText, button5));
        button6.setOnClickListener(new ListenerDialer(editText, button6));
        button7.setOnClickListener(new ListenerDialer(editText, button7));
        button8.setOnClickListener(new ListenerDialer(editText, button8));
        button9.setOnClickListener(new ListenerDialer(editText, button9));
        button0.setOnClickListener(new ListenerDialer(editText, button0));


        ImageButton button_back = findViewById(R.id.back);

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().length() > 0)
                    editText.setText(editText.getText().toString().substring(0, editText.getText().length() - 1));
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
}
