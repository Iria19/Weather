package org.i.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.i.weatherapplication.Retrofit.ApiClient;
import org.i.weatherapplication.Retrofit.ApiInterface;
import org.i.weatherapplication.Retrofit.Example;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ImageView search;
    TextView tempText,tempDescText, feelText, humidityText,temp_maxText,temp_minText;
    EditText textField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search=this.findViewById(R.id.search);
        tempText=this.findViewById(R.id.tempText);
        feelText=this.findViewById(R.id.feelText);
        humidityText=this.findViewById(R.id.humidityText);
        textField=this.findViewById(R.id.textField);
        temp_maxText=this.findViewById(R.id.temp_maxText);
        temp_minText=this.findViewById(R.id.temp_minText);
        tempDescText=this.findViewById(R.id.tempDescText);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Llamamos a API
                getWeatherDate(textField.getText().toString().trim());
            }
        });

    }
    private void getWeatherDate(String name){
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);

            Call<Example> call=apiInterface.getWeatherData(name);
            call.enqueue(new Callback<Example>() {
                //petición en cola porque puede haber más peticiones en espera de realizarse
                @Override
                public void onResponse(Call<Example> call, Response<Example> response) { //si se obtuvo una respuesta
                    //response.body es el cuerpo de la respuesta
                    tempText.setText("Temperatura:"+" "+response.body().getMain().getTemp()+" "+"Cº");
                    tempDescText.setText("Descripción:"+" "+response.body().getWeather().get(0).getDescription());
                    feelText.setText("Sensación térmica:"+" "+response.body().getMain().getFeels_like()+" "+"Cº");
                    humidityText.setText("Humedad:"+" "+response.body().getMain().getHumidity());
                    temp_minText.setText("Temperatura minima:"+" "+response.body().getMain().getTemp_min()+" "+"Cº");
                    temp_maxText.setText("Temperatura máxima:"+" "+response.body().getMain().getTemp_max()+" "+"Cº");

                }

                @Override
                public void onFailure(Call<Example> call, Throwable t) {

                }
            });
    }
}