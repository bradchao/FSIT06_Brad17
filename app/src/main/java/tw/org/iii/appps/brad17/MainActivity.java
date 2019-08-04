package tw.org.iii.appps.brad17;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void test1(View view) {
        new Thread(){
            @Override
            public void run() {
                try{
                    URL url = new URL("http://10.0.105.82:8080/MyJavaEE/Brad04");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");

                    ContentValues values = new ContentValues();
                    values.put("x", "10");
                    values.put("y", "3");
                    String urlstr = parseURLString(values);

                    Log.v("brad", urlstr);

                    OutputStream out = conn.getOutputStream();
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                    bw.write(urlstr);
                    bw.flush();
                    bw.close();

                    conn.connect();

                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line = br.readLine();
                    br.close();
                    Log.v("brad", line);



                }catch (Exception e){
                    Log.v("brad", e.toString());
                }
            }
        }.start();


    }

    private String parseURLString(ContentValues data){

        Set<String> keys = data.keySet();
        StringBuffer sb = new StringBuffer();
        try {
            for (String key : keys) {
                sb.append(URLEncoder.encode(key, "UTF-8"));
                sb.append("=");
                sb.append(URLEncoder.encode(data.getAsString(key), "UTF-8"));
                sb.append("&");
            }
            sb.deleteCharAt(sb.length()-1);
        }catch (Exception e){

        }
        return sb.toString();
    }


}
