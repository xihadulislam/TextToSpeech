package com.example.texttospeech;

import android.os.Build;
import android.speech.tts.TextToSpeech;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    private EditText editText;
    private Button button;

    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.text);
        button = findViewById(R.id.speak);

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

                if (status==TextToSpeech.SUCCESS){

                    int res = textToSpeech.setLanguage(Locale.ENGLISH);
                    if (res == TextToSpeech.LANG_MISSING_DATA || res == TextToSpeech.LANG_NOT_SUPPORTED){

                        Toast.makeText( MainActivity.this, "Language is not Supported.", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        button.setEnabled(true);
                        textToSpeech.setSpeechRate(1.60f);
                        textToSpeech.setPitch(0.9f);

                        speak();
                    }
                }

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });


    }


    private  void  speak(){


        String text = editText.getText().toString().trim();

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){

            textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH,null,null);
        }
        else
        {
            textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH,null);
        }

    }


    @Override
    protected void onDestroy() {

        if (textToSpeech!=null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}
