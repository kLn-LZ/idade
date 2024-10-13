package br.edu.fateczl.idade;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    /*
     *@author: Kelvin Santos Guimarães
     */

    private EditText etAno;
    private EditText etMes;
    private EditText etDia;
    private TextView tvRes;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etAno = findViewById(R.id.etAno);
        etMes = findViewById(R.id.etMes);
        etDia = findViewById(R.id.etDia);
        tvRes = findViewById(R.id.tvRes);

        Button btnCalc = findViewById(R.id.btnCalc);

        btnCalc.setOnClickListener(op -> calcNasc());
    }

    private void calcNasc() {
        int ano = Integer.parseInt(etAno.getText().toString());
        int mes = Integer.parseInt(etMes.getText().toString());
        int dia = Integer.parseInt(etDia.getText().toString());

        if ((dia <= 0 && dia > 31) || (mes <= 0 && mes > 12) || ano <= 0) {
            tvRes.setText("Entradas inválidas. Verifique se os valores estão corretos.");
            return;
        }

        Date dataAtual = new Date();
        int anoAtual = Integer.parseInt(new SimpleDateFormat("yyyy").format(dataAtual));
        int mesAtual = Integer.parseInt(new SimpleDateFormat("MM").format(dataAtual));
        int diaAtual = Integer.parseInt(new SimpleDateFormat("dd").format(dataAtual));

        int[] diasMes = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        if (ehAnoBissexto(ano)) {
            diasMes[1] = 29;
        }

        int anos = anoAtual - ano;
        int meses = mesAtual - mes;
        int dias = diaAtual - dia;

        if (dias < 0) {
            meses--;
            dias += diasMes[(mesAtual - 2 + 12) % 12];
        }

        if (meses < 0) {
            anos--;
            meses += 12;
        }

        tvRes.setText("Sua idade é: " + anos + " anos, " + meses + " meses e " + dias + " dias.");
    }

    private static boolean ehAnoBissexto(int ano) {
        if (ano % 4 != 0) {
            return false;
        } else if (ano % 100 != 0) {
            return true;
        } else if (ano % 400 != 0) {
            return false;
        } else {
            return true;
        }
    }
}