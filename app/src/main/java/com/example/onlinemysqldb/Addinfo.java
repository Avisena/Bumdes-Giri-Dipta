package com.example.onlinemysqldb;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class Addinfo extends Activity {
EditText Kategori_kendaraan, Nomor_kartu;
String kategori_kendaraan, nomor_kartu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_info_layout);
        Kategori_kendaraan = (EditText) findViewById(R.id.et_kategori);
        Nomor_kartu = (EditText) findViewById(R.id.et_nomorkartu);
    }

    public void saveInfo(View view) {
nomor_kartu = Kategori_kendaraan.getText().toString();
kategori_kendaraan = Nomor_kartu.getText().toString();
BackgroundTask backgroundTask = new BackgroundTask();
backgroundTask.execute(kategori_kendaraan,nomor_kartu);
finish();
    }

    class BackgroundTask extends AsyncTask<String,Void,String>
    {
      String add_info_url;
        @Override
        protected void onPreExecute() {
            add_info_url = "https://ngrenehan.000webhostapp.com/add-info.php";
        }

        @Override
        protected String doInBackground(String... args) {
            String nomor_kartu, kategori_kendaraan;
            kategori_kendaraan=args[1];
            nomor_kartu=args[0];
            try {
                URL url = new URL(add_info_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection ) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data_string = URLEncoder.encode("kategori","UTF-8")+"="+ URLEncoder.encode(kategori_kendaraan, "UTF-8")+"&"+
                        URLEncoder.encode("nomor","UTF-8")+"="+ URLEncoder.encode(nomor_kartu, "UTF-8");
                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                inputStream.close();
                httpURLConnection.disconnect();
                return "Data Inserted";


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
        }



    }
}