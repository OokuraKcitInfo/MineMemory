package info.local.minememory;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.InputStream;

import info.local.minememory.database.PostEntity;
import info.local.minememory.util.Constant;

public class EntityTestActivity extends AppCompatActivity {
    private ImageView photoImageView;
    private EditText dateEditText;
    private EditText placeEditText;
    private EditText commentEditText;

    private PostEntity entity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entity_test);

        initializedComponent();
        entity = new PostEntity();

        Button chooseButton = findViewById(R.id.etChooseButton);
        Button toggleImageButton = findViewById(R.id.etToggleImageButton);
        Button saveButton = findViewById(R.id.etSaveButton);

        chooseButton.setOnClickListener(v -> activityResultLauncher.launch(new String[]{"image/*"}));
        toggleImageButton.setOnClickListener(v -> {
            if (entity.getBitmapPicture() == null) return;
            photoImageView.setImageBitmap(entity.getBitmapFromByteArray());
        });
        saveButton.setOnClickListener(v -> {
            entity.setBitmapPicture(((BitmapDrawable)photoImageView.getDrawable()).getBitmap());
            entity.setDate(dateEditText.getText().toString());
            entity.setPlaceName(placeEditText.getText().toString());
            entity.setComment(commentEditText.getText().toString());
            Log.d(Constant.LOG_TAG, entity.toString());
        });
    }

    private void initializedComponent() {
        photoImageView = findViewById(R.id.etPhotoImageView);
        dateEditText = findViewById(R.id.etDateEditText);
        placeEditText = findViewById(R.id.etPlaceEditText);
        commentEditText = findViewById(R.id.etCommentEditText);
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
                    InputStream stream = getContentResolver().openInputStream(result);
                    Bitmap backgroundImage = BitmapFactory.decodeStream(new BufferedInputStream(stream));
                    photoImageView.setImageBitmap(backgroundImage);
                    stream.close();
                } catch (Exception e) {
                    Log.e(Constant.LOG_TAG, e.getMessage());
                }
            }
    );
}