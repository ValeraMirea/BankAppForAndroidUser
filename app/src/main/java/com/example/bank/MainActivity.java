package com.example.bank;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    // переменные
    private double balance;
    boolean flag;
    private double withdraw;
    private double deposit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        balance = 0; // инициализируем счет
        withdraw = 100; //переменная отвечающая за снятие денег со счета
        deposit = 50; //переменная отвечающая за пополнение  счета


        final Button depositButton = findViewById(R.id.depositButton);
        final Button withdrawButton = findViewById(R.id.withdrawButton);
        final TextView balanceTextView = findViewById(R.id.balanceTextView);


        depositButton.setOnClickListener(new View.OnClickListener() { // пополнение на счет
            @Override
            public void onClick(View v) {
                new DepositAsyncTask().execute();
            }
        });


        withdrawButton.setOnClickListener(new View.OnClickListener() { // снятие денег со счета
            @Override
            public void onClick(View v) {
                new WithdrawAsyncTask().execute();
            }
        });


        balanceTextView.setText(Double.toString(balance));
    }


    private class WithdrawAsyncTask extends AsyncTask<Void, Void, Double> {

        @Override
        protected Double doInBackground(Void... voids) {
            balance -= withdraw;
            if (balance <= 0){
                flag = true;
            }
            return balance;
        }

        @Override
        protected void onPostExecute(Double result) {
            super.onPostExecute(result);


            TextView balanceTextView = findViewById(R.id.balanceTextView);
            balanceTextView.setText(Double.toString(result));
            if (flag == true){
                balanceTextView.setText("Недостаточно средств на вашем счете, снять средства не удалось" + "\n"+
                        "Ваш баланс:" + balance);

            }
        }
    }

    // асинхронный поток для пополнения средств
    private class DepositAsyncTask extends AsyncTask<Void, Void, Double> {

        @Override
        protected Double doInBackground(Void... voids) {
            balance += deposit;
            return balance;
        }

        @Override
        protected void onPostExecute(Double result) {
            super.onPostExecute(result);

// после окончания пополнения, обновляем баланс
            TextView balanceTextView = findViewById(R.id.balanceTextView);
            balanceTextView.setText(Double.toString(result));
        }
    }
}

