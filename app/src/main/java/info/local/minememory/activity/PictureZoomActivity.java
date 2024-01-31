package info.local.minememory.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import info.local.minememory.R;

public class PictureZoomActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //画面設定　どのレイアウトファイルを使うか
        setContentView(R.layout.activity_picture_zoom);
        Button backButton =  findViewById(R.id.pzBackButton);
        Button deleteButton =  findViewById(R.id.pzdeleteButton);
        //b1が押されたときにボタンイベントに飛ぶ
        backButton.setOnClickListener(view -> {
            //画面移動用クラス
            Intent it = new Intent();

            //還元先の設定
            //new Intent(現在のクラス名 this,移動先.class)で指定
            it = new Intent(PictureZoomActivity.this, PlaceListActivity.class);

            //新規画面開始
            startActivity(it);
            finish();
        });
        deleteButton.setOnClickListener(view ->{
            //画面移動用クラス
            Intent it = new Intent();

            //還元先の設定
            //new Intent(現在のクラス名 this,移動先.class)で指定
            it = new Intent(PictureZoomActivity.this, PlaceListActivity.class);

            //新規画面開始
            startActivity(it);
            finish();
        });
    }
}