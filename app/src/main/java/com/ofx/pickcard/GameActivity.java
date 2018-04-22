package com.ofx.pickcard;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends Activity implements View.OnClickListener{
    TextView tv_game_card;
    TextView tv_life_desc;
    TextView tv_card_desc;

    TextView tv_carda;
    TextView tv_cardb;
    TextView tv_cardc;
    TextView tv_cardd;
    TextView tv_day_num;
    TextView tv_pick_num;

    View ll_pick_card;
    View ll_nextday;

    public static String card_name_a = "A卡";
    public static String card_name_b = "B卡";
    public static String card_name_c = "C卡";
    public static String card_name_d = "D卡";

    Card carda = new Card(card_name_a,1,2,3,4);
    Card cardb = new Card(card_name_b,2,3,4,5);
    Card cardc = new Card(card_name_c,3,5,6,8);
    Card cardd = new Card(card_name_d,4,6,7,9);
    Card cardx = new Card("X",0,0,0,0);

    SpTool spTool ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spTool = SpTool.getSp(this);

        tv_game_card = findViewById(R.id.tv_game_card);
        tv_life_desc = findViewById(R.id.tv_life_desc);
        tv_carda = findViewById(R.id.tv_carda);
        tv_cardb = findViewById(R.id.tv_cardb);
        tv_cardc = findViewById(R.id.tv_cardc);
        tv_cardd = findViewById(R.id.tv_cardd);
        ll_nextday = findViewById(R.id.ll_nextday);
        ll_pick_card = findViewById(R.id.ll_pick_card);
        tv_pick_num = findViewById(R.id.tv_pick_num);
        tv_day_num = findViewById(R.id.tv_day_num);

        tv_carda.setOnClickListener(this);
        tv_cardb.setOnClickListener(this);
        tv_cardc.setOnClickListener(this);
        tv_cardd.setOnClickListener(this);
        ll_nextday.setOnClickListener(this);
        ll_pick_card.setOnClickListener(this);
//        tv_card_desc = findViewById(R.id.tv_card_desc);
        init();
    }

    private void init(){
        refresh();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_carda){
            userCard(carda);
        }else  if (v.getId() == R.id.tv_cardb){
            userCard(cardb);
        }else  if (v.getId() == R.id.tv_cardc){
            userCard(cardc);
        }else  if (v.getId() == R.id.tv_cardd){
            userCard(cardd);
        }else  if (v.getId() == R.id.ll_nextday){
            spTool.addInt(SpTool.pick_card_num,5);
            refresh();
            nextDay();

        }else  if (v.getId() == R.id.ll_pick_card){
            //随机抽张卡
           int result = (int) (Math.random()*10)%4;
            log("result "+result);
           switch (result){
               case 0:
                   pickCard(carda);
               break;
               case 1:
                   pickCard(cardb);
               break;
               case 2:
                   pickCard(cardc);
               break;
               case 3:
                   pickCard(cardd);
               break;
           }
        }
    }

    private void nextDay(){
        spTool.putInt(SpTool.time_day,spTool.getInt(SpTool.time_day)+1);
        Intent intent = new Intent(this,ResultActivity.class);
        startActivity(intent);
        finish();
    }

    class Card{
        public Card(String name, int dig, int science, int food, int water) {
            this.name = name;
            this.dig = dig;
            this.science = science;
            this.food = food;
            this.water = water;
        }

        public String name;
        public int dig;
        public int science;
        public int food;
        public int water;
        public int num;
    }

    private void userCard(Card card){
        if (spTool.getCardNum(card.name) < 1) {
            Toast(this,card.name+"不够了");
            return;
        }
        spTool.putString(SpTool.current_card,card.name);
        spTool.minusInt(SpTool.dig,card.dig);
        spTool.minusInt(SpTool.science,card.science);
        spTool.minusInt(SpTool.water,card.water);
        spTool.minusInt(SpTool.food,card.food);
        spTool.minusCardNum(card.name);
        spTool.minusInt(SpTool.pick_card_num,1);
        refresh();
        showDlg(card);

    }

    private void showDlg(Card card){
        View view = LayoutInflater.from(this).inflate(R.layout.dlg_view,null);
        TextView tv_game_card_dlg = view.findViewById(R.id.tv_game_card_dlg);
        tv_game_card_dlg.setText(card.name);
        TextView tv_life_desc_dlg = view.findViewById(R.id.tv_life_desc_dlg);
        tv_life_desc_dlg.setText("获得 "+card.name);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();

        tv_life_desc_dlg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void pickCard(Card card){
        if (spTool.getInt(SpTool.pick_card_num) < 1) {
            Toast(this,"剩余抽卡数不够了");
            return;
        }
//        spTool.putString(SpTool.current_card,card.name);
        spTool.addInt(SpTool.dig,card.dig);
        spTool.addInt(SpTool.science,card.science);
        spTool.addInt(SpTool.water,card.water);
        spTool.addInt(SpTool.food,card.food);
        spTool.addCardNum(card.name);
        spTool.minusInt(SpTool.pick_card_num,1);
        refresh();
    }

    private void refresh(){
        tv_game_card.setText(spTool.getString(SpTool.current_card));
        String value = String.format("体力:%d 挖掘:%d 科研:%d 喝水:%d",spTool.getInt(SpTool.food),spTool.getInt(SpTool.dig),spTool.getInt(SpTool.science),spTool.getInt(SpTool.water));
        tv_life_desc.setText(value);

        tv_carda.setText(carda.name+"x"+spTool.getCardNum(carda.name));
        tv_cardb.setText(cardb.name+"x"+spTool.getCardNum(cardb.name));
        tv_cardc.setText(cardc.name+"x"+spTool.getCardNum(cardc.name));
        tv_cardd.setText(cardd.name+"x"+spTool.getCardNum(cardd.name));

        tv_pick_num.setText("抽卡次数"+spTool.getInt(SpTool.pick_card_num));
        tv_day_num.setText("当前天数"+spTool.getInt(SpTool.time_day));
    }

    public static void log(String msg){
        Log.e("card",msg);
    }

    public static void Toast(Context context,String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();;

    }
}
