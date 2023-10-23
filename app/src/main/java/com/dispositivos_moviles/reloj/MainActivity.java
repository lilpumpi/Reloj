package com.dispositivos_moviles.reloj;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView textViewTime;
    private Timer timer;
    String alarmTime = null;
    String currentTime = null;
    boolean alarmShown = false; //Inidica si la alarma ya ha sido mostrada

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewTime = findViewById(R.id.textViewTime);

        //Iniciamos el Timer para actualizar la hora cada 1000ms (1seg)
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateCurrentTime();

                if(!alarmShown && currentTime.equals(alarmTime)){ //Si la hora actual coincide con la hora de alarma abrimos el dialogo
                    showAlarmDialog();
                }
            }
        }, 0, 1000);
    }

    //Proporciona la hora actual
    private void updateCurrentTime(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                currentTime = sdf.format(new Date()); //Guardamos la hora actual

                textViewTime.setText("Time: " + currentTime);
            }
        });
    }

    //Selecciona la hora de la alarma al pulsar el boton
    public void showTimePickerDialog(View view){
        TimePickerDialog tpd = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                alarmTime = hourOfDay + ":" + minute; //Guardamos la hora de alarma como un String
            }
        }, 0, 0, true); //Valores iniciales del dialogo

        tpd.show(); //Mostramos el dialogo
    }

    //Mostramos la Alarma
    private void showAlarmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ALARMA");
        builder.setMessage("Ha llegado la hora");

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Cerramos alarma
            }
        });

        alarmShown = true;
        AlertDialog dialog = builder.create();
        dialog.show();;
    }
}