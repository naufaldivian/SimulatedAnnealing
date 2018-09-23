package com.example.lenovo.simulatedannealing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText min, max, cooling, tempawal;
    private Button mulai;
    TextView display;
    double x1,x2,tawal, dE, cr, nx1, nx2, ncost, bcost, newState, initial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        min = findViewById(R.id.nilaimin);
        max = findViewById(R.id.nilaimax);
        cooling = findViewById(R.id.nilairate);
        tempawal = findViewById(R.id.nilaitempawal);
        display = findViewById(R.id.tampilan);
        mulai = findViewById(R.id.memulai);

        mulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = 1;
                int minimal = Integer.parseInt(min.getText().toString());
                int maksimal = Integer.parseInt(max.getText().toString());
                Random r = new Random();
                x1 = Math.random() * (maksimal-minimal) + (minimal);
                x2 = Math.random() * (maksimal-minimal) + (minimal);
                tawal = Double.parseDouble(tempawal.getText().toString());
                cr = Double.parseDouble(cooling.getText().toString());
                initial = F(x1, x2);
                ncost = initial;
                bcost = ncost;

                while (i <= 10000) {
                    nx1 = Math.random() * (maksimal-minimal) + (minimal);
                    nx2 = Math.random() * (maksimal-minimal) + (minimal);
                    newState = F(nx1,nx2);
                    dE = Deltae(newState,ncost);
                    if (dE < 0) {
                        ncost = newState;
                        bcost = newState;
                        i++;
                        tawal = tawal - cr;
                    }
                    else if (dE >= 0) {
                        double P = P(dE,tawal);
                        double ran = Math.random() * (1-0) + 0;
                        if (P >= ran) {
                            newState = ncost;
                            i++;
                            tawal = tawal - cr;
                        }
                        i++;
                    }
                }
                display.setText(String.valueOf(bcost));
            }
        });
    }
    private double Deltae(double newState,double n_cost) {
        double dE;
        dE = newState - n_cost;
        return dE;
    }
    private double P(double dE,double T) {
        double p = Math.exp(-dE/T);
        return p;
    }
    private double F(double x1,double x2) {
        double sin = Math.sin(x1);
        double cos = Math.cos(x2);
        double exp = Math.exp(Math.abs(1 - (Math.sqrt(Math.pow(x1, 2) + Math.pow(x2, 2))/Math.PI)));
        return -1 * Math.abs(sin*cos*exp);
    }
}
