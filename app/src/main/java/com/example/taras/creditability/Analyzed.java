package com.example.taras.creditability;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.icu.text.DecimalFormat;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class Analyzed extends AppCompatActivity {
    double dTotalIncome = 0;
    double dTotalCosts = 0;
    double percentage = 0;
    double neededTerm = 0;
    double dneededSum = 0;
    String sCurrency = "";
    String sName = "";
    String sSurname = "";
    String sFathername = "";
    String sCreditType = "";
    double dPureIncome = 0;
    double dMonthPay = 0;
    double pureVSpay = 0;
    String currencies = "USD_UAH";
    ArrayList<Float> oneweekData = new ArrayList<Float>();
    double dcurrency = 0;
    TextView fullNameView ;
    TextView totalIncomeView ;
    TextView totalCostsView ;
    TextView pureIncomeView;
    TextView creditInfo;
    TextView resultView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyzed);

        fullNameView = (TextView)findViewById(R.id.fullName);
        totalIncomeView = (TextView)findViewById(R.id.totalIncome);
        totalCostsView = (TextView)findViewById(R.id.totalCosts);
        pureIncomeView = (TextView)findViewById(R.id.pureIncome);
        creditInfo = (TextView)findViewById(R.id.creditInfo);
        resultView = (TextView)findViewById(R.id.result);

        Bundle extras = getIntent().getExtras();

        dTotalIncome = extras.getDouble("totalIncome");
        dTotalCosts = extras.getDouble("totalCosts");
        percentage = extras.getDouble("percentage");
        neededTerm = extras.getDouble("term");
        dneededSum = extras.getDouble("neededSum");
        sCurrency = extras.getString("currency");
        sName = extras.getString("name");;
        sSurname = extras.getString("surname");;
        sFathername = extras.getString("fathername");;
        sCreditType = extras.getString("creditType");

        dPureIncome = dTotalIncome - dTotalCosts;

        Toast.makeText(this,extras.getString("surname") + extras.getString("name"),Toast.LENGTH_SHORT).show();

        if(sCurrency.equals("грн.")){
            dMonthPay = dneededSum/neededTerm;
            String formattedMonthPay = String.format("%.2f", dMonthPay);
            pureVSpay = dMonthPay/dPureIncome;
            String formattedpureVSpay = String.format("%.2f", pureVSpay);
            fullNameView.setText(sSurname + " " + sName + " " + sFathername);
            totalCostsView.setText("Загальні витрати: " + dTotalCosts + "");
            totalIncomeView.setText("Загальний прибуток: " + dTotalIncome + "");
            creditInfo.setText("Тип кредиту: " + sCreditType + "\n" +
                                "Сума: " + dneededSum + " " + sCurrency +
                                "\nТермін(місяців): " + neededTerm + "; Річна ставка: " + percentage + "%\n" +
                                "Обовязкова щомісячна виплата: " + formattedMonthPay + "грн.");
            pureIncomeView.setText("Чистий прибуток за місяць: " + dPureIncome);
            resultView.setText("Щомісячна виплата становить " + formattedpureVSpay + " від чистого прибутку");

        }else{
            if(sCurrency.equals("дол.")){
                currencies = "USD_UAH";
            }

            if(sCurrency.equals("євр.")){
                currencies = "EUR_UAH";
            }

            getarrayweekTask myTask = new getarrayweekTask();

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
            Date mydate = calendar.getTime();
            calendar.setTime(mydate);
            calendar.add(Calendar.DAY_OF_YEAR, 0);
            String startDate = "" + mdformat.format(calendar.getTime());
            calendar.add(Calendar.DAY_OF_YEAR, -6);
            String endDate = "" + mdformat.format(calendar.getTime());
            Log.i("mytask", "startdate - " + startDate);
            Log.i("mytask", "enddate - " + endDate);
            myTask.execute("https://free.currencyconverterapi.com/api/v6/convert?q=" + currencies +"&compact=ultra&date=" + endDate + "&endDate=" + startDate +"&apiKey=667a8fab011ea833b877");

        }

    }

    class getarrayweekTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {

                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection)url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int data = inputStreamReader.read();

                while(data != -1){
                    char current = (char) data;
                    result  += current;
                    data = inputStreamReader.read();

                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }
        //https://free.currencyconverterapi.com/api/v6/convert?q=USD_GBP&compact=ultra&date=2019-2-28&endDate=2019-3-4&apiKey=667a8fab011ea833b877
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            JSONObject jsonObject = null;
            JSONArray jsonArray = null;
            try {
                oneweekData.clear();

                jsonObject = new JSONObject(result);
                result = jsonObject.getString(currencies);
                jsonObject = new JSONObject(result);

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
                Date mydate = calendar.getTime();
                calendar.setTime(mydate);

                calendar.add(Calendar.DAY_OF_YEAR, 0);
                for(int i = 0; i < 7; i++) {
                    String strDate = "" + mdformat.format(calendar.getTime());

                    result = jsonObject.getString(strDate);

                    float dresult = Float.parseFloat(result);
                    int iresult = (int) (dresult * 1000);
                    dresult = iresult;
                    dresult /= 1000;
                    if(dresult > dcurrency){
                        dcurrency = dresult;
                    }
                    oneweekData.add(dresult);

                    calendar.add(Calendar.DAY_OF_YEAR, -1);
                }

                dMonthPay = dneededSum * dcurrency / neededTerm;
                String formattedMonthPay = String.format("%.2f", dMonthPay);
                pureVSpay = dMonthPay/dPureIncome;
                String formattedpureVSpay = String.format("%.2f", pureVSpay);
                fullNameView.setText(sSurname + " " + sName + " " + sFathername);
                totalCostsView.setText("Загальні витрати: " + dTotalCosts + "");
                totalIncomeView.setText("Загальний прибуток: " + dTotalIncome + "");
                creditInfo.setText("Тип кредиту: " + sCreditType + "\n" +
                        "Сума: " + dneededSum + " " + sCurrency +
                        "\nТермін(місяців): " + neededTerm + "; Річна ставка: " + percentage + "%\n" +
                        "Обовязкова щомісячна виплата: " + formattedMonthPay);
                pureIncomeView.setText("Чистий прибуток за місяць: " + dPureIncome);
                resultView.setText("Щомісячна виплата становить " + formattedpureVSpay + " від чистого прибутку");

                draw(oneweekData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void draw(ArrayList<Float> oneweekData){
        float width = 0;
        float height = 0;
        float step = 0;
        float cof = 0;
        float windowheight = 0;
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.whereToDraw);
        //LinearLayout windowLayout = (LinearLayout)findViewById(R.id.window);
        Collections.reverse(oneweekData);
        float max = oneweekData.get(0);
        float min = oneweekData.get(0);

        for(int i = 1; i < oneweekData.size(); i++){
            if(max < oneweekData.get(i)){
                max = oneweekData.get(i);
            }

            if(min > oneweekData.get(i)){
                min = oneweekData.get(i);
            }
        }

        width = linearLayout.getWidth();
        height = linearLayout.getHeight();
        step = (width - 35 ) / (oneweekData.size() - 1);
        cof =(height - 20)/(max - min);
        // when we draw we mul cof with every element of array
        // also x  - x + step


        Paint paint = new Paint();
        Bitmap bg = Bitmap.createBitmap((int)width, (int)height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bg);

        paint.setColor(getApplicationContext().getResources().getColor(R.color.colorAccent));

        float swd = paint.getStrokeWidth();
        paint.setStrokeWidth(4);
        for(int i = 0; i < oneweekData.size() - 1 ; i++) {
            canvas.drawLine(35 + i  * step, height - (oneweekData.get(i) - min) * cof , 35 + (i + 1) * step, height - (oneweekData.get(i + 1) - min) * cof, paint);
        }

        paint.setStrokeWidth(swd);
        width = linearLayout.getWidth();
        height = linearLayout.getHeight();
        cof = (height - 20)/(max - min);

        paint.setTextSize(20);
        paint.setColor(getApplicationContext().getResources().getColor(R.color.colorGrey));
        for(int i = 0; i < oneweekData.size()  ; i++) {
            canvas.drawText("" + oneweekData.get(i),0, height - (oneweekData.get(i) - min) * cof - 1, paint);
            canvas.drawLine(0, height - (oneweekData.get(i) - min) * cof - 1 , width, height - (oneweekData.get(i) - min) * cof,paint);
        }

        linearLayout.setBackgroundDrawable(new BitmapDrawable(bg));
    }
}
