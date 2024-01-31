package info.local.minememory.activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.InputStream;

import info.local.minememory.R;
import info.local.minememory.database.PostEntity;
import info.local.minememory.database.PostViewModel;
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
        PostViewModel viewModel = new ViewModelProvider(this).get(PostViewModel.class);
        viewModel.getAllPostsDesc().observe(this, postEntities -> {
            if (postEntities.size() == 0) return;
            entity = postEntities.get(0);
        });

        Button chooseButton = findViewById(R.id.etChooseButton);
        Button toggleImageButton = findViewById(R.id.etToggleImageButton);
        Button saveButton = findViewById(R.id.etSaveButton);
        Button loadButton = findViewById(R.id.etLoadButton);

        photoImageView.setOnTouchListener((v, event) -> {
            if (event.getAction() != MotionEvent.ACTION_DOWN) return false;
            activityResultLauncher.launch(new String[]{"image/*"});
            return false;
        });

        chooseButton.setOnClickListener(v -> activityResultLauncher.launch(new String[]{"image/*"}));

        toggleImageButton.setOnClickListener(v -> {
            if (entity.getBitmapPicture() == null) return;
            photoImageView.setImageBitmap(entity.getBitmapFromByteArray());
        });

        saveButton.setOnClickListener(v -> {
            entity.setPostId(0);
            entity.setBitmapPicture(((BitmapDrawable)photoImageView.getDrawable()).getBitmap());
            entity.setDate(dateEditText.getText().toString());
            entity.setPlaceName(placeEditText.getText().toString());
            entity.setComment(commentEditText.getText().toString());
            Log.d(Constant.LOG_TAG, entity.toString());
            viewModel.insert(entity);
        });

        loadButton.setOnClickListener(v -> {
            photoImageView.setImageBitmap(entity.getBitmapFromByteArray());
            dateEditText.setText(entity.getDate());
            placeEditText.setText(entity.getPlaceName());
            commentEditText.setText(entity.getComment());
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