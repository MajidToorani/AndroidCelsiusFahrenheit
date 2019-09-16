package io.github.rhildred.temperatureapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity
    implements TextView.OnEditorActionListener, View.OnClickListener{

    // form attributes

    EditText editTextCelsius;
    EditText editTextFahrenheit;
    Button btnToCelsius;
    Button btnToFahrenheit;

    // place to stash variables

    private SharedPreferences sharedPlace;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize attibutes from the form
        this.editTextCelsius = findViewById(R.id.editTextCelsius);
        this.editTextFahrenheit = findViewById(R.id.editTextFahrenheit);
        this.btnToCelsius = findViewById(R.id.btnToCelsius);
        this.btnToFahrenheit = findViewById(R.id.btnToFahrenheit);

        // initialize SharedPreferences
        this.sharedPlace = getSharedPreferences("SharedPlace", MODE_PRIVATE);

        //wire widgets/views to events

        this.editTextCelsius.setOnEditorActionListener(this);
        this.editTextFahrenheit.setOnEditorActionListener(this);
        this.btnToCelsius.setOnClickListener(this);
        this.btnToFahrenheit.setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        SharedPreferences.Editor sharedEditor = this.sharedPlace.edit();
        sharedEditor.putString("TextCelsius", this.editTextCelsius.getText().toString());
        sharedEditor.putString("TextFahrenheit", this.editTextFahrenheit.getText().toString());
        sharedEditor.commit();
        super.onPause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        this.editTextCelsius.setText(this.sharedPlace.getString("TextCelsius", ""));
        this.editTextFahrenheit.setText(this.sharedPlace.getString("TextFahrenheit", ""));
    }

    @Override
    public boolean onEditorAction(TextView textViewV, int nId, KeyEvent keyEventEvt){
        if(textViewV.getId() == this.editTextCelsius.getId()){
            this.toFahrenheit();
        }else{
            this.toCelsius();
        }
        return false;
    }

    @Override
    public void onClick(View viewV){
        if(viewV.getId() == this.btnToCelsius.getId()){
            this.toCelsius();
        }else{
            this.toFahrenheit();
        }

    }

    private void toFahrenheit(){
        float nCelsius = 0;
        try{
            nCelsius = Float.parseFloat(this.editTextCelsius.getText().toString());
        }catch(Exception e) {
        }
        float nFahrenheit = nCelsius * 1.8f + 32;
        NumberFormat number = NumberFormat.getNumberInstance();
        this.editTextFahrenheit.setText(number.format(nFahrenheit));
    }

    private void toCelsius(){
        float nFahrenheit = 0;
        try{
            nFahrenheit = Float.parseFloat(this.editTextFahrenheit.getText().toString());
        }catch(Exception e) {
        }
        float nCelsius = (nFahrenheit - 32)/ 1.8f;
        NumberFormat number = NumberFormat.getNumberInstance();
        this.editTextCelsius.setText(number.format(nCelsius));
    }
}
