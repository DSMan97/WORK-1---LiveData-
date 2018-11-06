package com.dsman.livedata;

/*
*
* Created By Guillermo Crespo Aguayo
*
* */
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Switch mSwithcT, mSwithcA;

    //LiveData para almacenar el cambio del Switch de Trabajo
    private MutableLiveData<Boolean> mutableLiveDataT = new MutableLiveData<>();
    //LiveData para almacenar el cambio del Switch de Aprobar
    private MutableLiveData<Boolean> mutableLiveDataA = new MutableLiveData<>();
    //LiveData para almacenar el cambio de introducción de texto de Edad
    private MutableLiveData<String> mutableLiveDataAge = new MutableLiveData<>();

    private EditText mAge;
    private TextView mTrabajo, mAprobar, tvtAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSwithcT = findViewById(R.id.switchT);
        mSwithcA = findViewById(R.id.switchA);
        mAge = findViewById(R.id.etAge);
        mTrabajo = findViewById(R.id.tvt_Trabajo);
        mAprobar = findViewById(R.id.tvt_Aprobar);
        tvtAge = findViewById(R.id.tvt_age);
        changeRegister();
        observers();

    }

    public void changeRegister() {
        //Uso el método "setOnCheckedListener para escuchar si está con "check" o no
        mSwithcT.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            //Este método es autogenerado y me gustaría saber que significa "CompoundButton"
            //y "buttonView" (se inglés XD), pero me gustaría saber como funcionan o que hacen

            //Uso el método onChekedChanged de tipo booleano para el "isCheked"
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mutableLiveDataT.postValue(true);
                } else {
                    mutableLiveDataT.postValue(false);
                }
            }
        });

        //Se repite lo anterior con cada elemento de la vista para pasar cada valor al LiveData

        mSwithcA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mutableLiveDataA.postValue(true);
                } else {
                    mutableLiveDataA.postValue(false);
                }
            }
        });

        mAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //
                mutableLiveDataAge.postValue(s.toString());

            }
        });

    }

    public void observers() {

        //Creo los observadores para cada LiveData y seteo en la vista
        mutableLiveDataT.observe(MainActivity.this, new Observer<Boolean>() {
            @Override
            public void onChanged(@NonNull Boolean mTstate) {
                if (mTstate) {
                    mTrabajo.setText("Si trabajo en Casa");
                } else {
                    mTrabajo.setText("No trabajo en casa");
                }

            }
        });

        mutableLiveDataA.observe(MainActivity.this, new Observer<Boolean>() {
            @Override
            public void onChanged(@NonNull Boolean mAstate) {
                if (mAstate) {
                    mAprobar.setText("Si Apruebo");
                } else {
                    mAprobar.setText("No Apruebo");
                }

            }
        });
        mutableLiveDataAge.observe(MainActivity.this, new Observer<String>() {
            @Override
            public void onChanged(@NonNull String mAge) {
                //si el EditText está lleno muestra el IF, si está vacio no muestra nada
                //Así se evita que te muestre "Tengo años"
                if (!mAge.equals("")) {
                    tvtAge.setText("Tengo "+mAge+" años");
                }else{
                    tvtAge.setText(" ");
                }

            }
        });
    }
}


