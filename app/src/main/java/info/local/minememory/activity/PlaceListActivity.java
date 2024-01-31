package info.local.minememory.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import info.local.minememory.R;

public class PlaceListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //画面設定　どのレイアウトファイルを使うか
        setContentView(R.layout.activity_place_list);
        Button backButton =  findViewById(R.id.plBackButton);
        //b1が押されたときにボタンイベントに飛ぶ
        backButton.setOnClickListener(new ButtonEvent());
    }

        class ButtonEvent implements View.OnClickListener {
            @Override
            public void onClick(View view) {
                //画面移動用クラス
                Intent it = new Intent();

                //還元先の設定
                //new Intent(現在のクラス名 this,移動先.class)で指定
                it = new Intent(PlaceListActivity.this, MainActivity.class);

                //新規画面開始
                startActivity(it);
                finish();

            }
}
}
