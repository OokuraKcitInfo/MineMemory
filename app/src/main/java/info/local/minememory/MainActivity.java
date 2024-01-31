package info.local.minememory;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedInputStream;
import java.io.InputStream;

import info.local.minememory.util.Constant;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b1 = (Button) findViewById(R.id.MainPlaceListButton);
        Button b2 = (Button) findViewById(R.id.MainPlaceRegistrationButton);
        Button b3 = (Button) findViewById(R.id.button2);
        //b2が押されたときにボタンイベントに飛ぶ
        b1.setOnClickListener(new ButtonEvent());
        b2.setOnClickListener(new ButtonEvent2());
        b3.setOnClickListener(new ButtonEvent3());

        Button entityTestMoveButton = findViewById(R.id.mainEntityTestMoveButton);
        entityTestMoveButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, EntityTestActivity.class));
        });
    }

    class ButtonEvent implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            //画面移動用クラス
            Intent it = new Intent();

            //還元先の設定
            //new Intent(現在のクラス名 this,移動先.class)で指定
            it = new Intent(MainActivity.this, PlaceListActivity.class);

            //新規画面開始
            startActivity(it);
            finish();
        }
    }

    class ButtonEvent2 implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            //画面移動用クラス
            Intent it = new Intent();

            //還元先の設定
            //new Intent(現在のクラス名 this,移動先.class)で指定
            it = new Intent(MainActivity.this, PlaceRegistrationActivity.class);

            //新規画面開始
            startActivity(it);
            finish();
        }
    }


    class ButtonEvent3 implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            //画面移動用クラス
            Intent it = new Intent();

            //還元先の設定
            //new Intent(現在のクラス名 this,移動先.class)で指定
            it = new Intent(MainActivity.this, PictureZoomActivity.class);

            //新規画面開始
            startActivity(it);
            finish();
        }
    }

    private final ActivityResultLauncher<String[]> activityResultLauncher
            = registerForActivityResult(
            // １つのみ選択可能な状態でギャラリーを開く
            new ActivityResultContracts.OpenDocument(),
            // 背景画像を選択するギャラリーから戻ってきた際に呼ばれる処理
            // result に選択された画像のパス（URI）が格納されている
            result -> {
                // 画像が選択されなかったら何もしない
                if (result == null) return;
                try {
                    InputStream stream = MainActivity.this.getContentResolver().openInputStream(result);
                    Bitmap backgroundImage = BitmapFactory.decodeStream(new BufferedInputStream(stream));
                    stream.close();
                } catch (Exception e) {
                    Log.e(Constant.LOG_TAG, e.getMessage());
                }
            }
    );
}