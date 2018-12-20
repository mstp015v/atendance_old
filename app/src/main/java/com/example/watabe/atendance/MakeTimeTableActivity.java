package com.example.watabe.atendance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import mysqlite.Database;
import mysqlite.SQLiteAdapter;
import mysqlite.TableData;
import mysqlite.TableRowData;

import java.util.Calendar;
import java.time.DayOfWeek;

import static com.example.watabe.atendance.R.array.k_name;

public class MakeTimeTableActivity extends AppCompatActivity {
    //フィールド
    Map<String,TextView> txtMap = new HashMap<>();
    SQLiteAdapter sqlAdapter;
    PopupWindow pw;
    private PopupWindow popWin;
    //メソッド
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_time_table);

        //インテント
        Intent intent = getIntent();
        String strRoom = intent.getStringExtra("txtRoom");
        //クラス名の挿入
        ((TextView)findViewById(R.id.txtRoom)).setText( strRoom );
        //MapにtxtXXXYを登録する
        txtMap.put("Mon1",(TextView)findViewById(R.id.txtMon1));
        txtMap.put("Mon2",(TextView)findViewById(R.id.txtMon2));
        txtMap.put("Mon3",(TextView)findViewById(R.id.txtMon3));
        txtMap.put("Mon4",(TextView)findViewById(R.id.txtMon4));

        txtMap.put("Tue1",(TextView)findViewById(R.id.txtTue1));
        txtMap.put("Tue2",(TextView)findViewById(R.id.txtTue2));
        txtMap.put("Tue3",(TextView)findViewById(R.id.txtTue3));
        txtMap.put("Tue4",(TextView)findViewById(R.id.txtTue4));

        txtMap.put("Wed1",(TextView)findViewById(R.id.txtWed1));
        txtMap.put("Wed2",(TextView)findViewById(R.id.txtWed2));
        txtMap.put("Wed3",(TextView)findViewById(R.id.txtWed3));
        txtMap.put("Wed4",(TextView)findViewById(R.id.txtWed4));

        txtMap.put("Thu1",(TextView)findViewById(R.id.txtThu1));
        txtMap.put("Thu2",(TextView)findViewById(R.id.txtThu2));
        txtMap.put("Thu3",(TextView)findViewById(R.id.txtThu3));
        txtMap.put("Thu4",(TextView)findViewById(R.id.txtThu4));

        txtMap.put("Fri1",(TextView)findViewById(R.id.txtFri1));
        txtMap.put("Fri2",(TextView)findViewById(R.id.txtFri2));
        txtMap.put("Fri3",(TextView)findViewById(R.id.txtFri3));
        txtMap.put("Fri4",(TextView)findViewById(R.id.txtFri4));
        //イベント登録
        for( String key : txtMap.keySet()){
            TextView tempTextView = txtMap.get(key);
            tempTextView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    txtXXXOnClick( view );
                }
            });
        }


        //Sqliteから時間割を取得する
        sqlAdapter = new SQLiteAdapter(getApplicationContext(), new Database("mydb.db"));
        TableData table = sqlAdapter.getTableData("select * from subject where r_name='@r_name'".replace("@r_name",strRoom));
        for(TableRowData row : table ){
            String key = null;
            String weekday = row.getValue(1);

            switch( weekday ){
                case "MONDAY":
                    key = "Mon";
                    break;
                case "TUESDAY":
                    key = "Tue";
                    break;
                case "WEDNESDAY":
                    key = "Wed";
                    break;
                case "THURSDAY":
                    key = "Thu";
                    break;
                case "FRIDAY":
                    key = "Fri";
                    break;
            }
            key = key + row.getValue(2);
            TextView tv = txtMap.get(key);
            tv.setText( row.getValue(3));

            //Log.d("timetable:row",key );
        }

        //リソースから作る場合、ファクトリーメソッドになる
        ArrayAdapter<CharSequence> aryAd = ArrayAdapter.createFromResource(
                this,
                R.array.k_name,
                R.layout.spinner_item_text8sp
        );

        aryAd.setDropDownViewResource(R.layout.spinner_dropdown_item);

    }

    //ポップアップを表示する
    public void txtXXXOnClick( View v){
        Log.d("event","ocClick");
        popWin = new PopupWindow( MakeTimeTableActivity.this);
        View popupView = getLayoutInflater().inflate(R.layout.listview_popupwindow,null);
        popupView.findViewById(R.id.close_button).setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick( View v ){
               if( popWin.isShowing() ){
                   popWin.dismiss();
               }
           }
        });
        popWin.showAtLocation(findViewById(R.id.txtMon1),Gravity.CENTER,0,0);
    }

}
