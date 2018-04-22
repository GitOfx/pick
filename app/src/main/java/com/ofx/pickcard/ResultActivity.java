package com.ofx.pickcard;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

public class ResultActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_result);
        TextView result = findViewById(R.id.result);
        SpTool spTool = SpTool.getSp(this);
        String resultStr = "默认结局";
        if (spTool.getInt(SpTool.water)<=0 && spTool.getInt(SpTool.food)<=0){
            resultStr = "死亡结局";
        }else if (spTool.getInt(SpTool.time_day) > 100){
            resultStr = "等待救援结局";
        }else if (spTool.getInt(SpTool.science) == 50){
            resultStr = "自力更生结局";
        }else if (spTool.getInt(SpTool.dig) == 50){
            resultStr = "求生讯息结局";
        }

        result.setText(resultStr);
    }
}
