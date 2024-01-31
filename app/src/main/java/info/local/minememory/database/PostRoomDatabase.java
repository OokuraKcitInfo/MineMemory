package info.local.minememory.database;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {PostEntity.class}, version = 1, exportSchema = false)
public abstract class PostRoomDatabase extends RoomDatabase {
    public abstract PostDao postDao();

    private static volatile PostRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static PostRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PostRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext()
                                    , PostRoomDatabase.class
                                    , "post_database"
                            )
                            .addCallback(ROOM_DATABASE_CALLBACK)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback ROOM_DATABASE_CALLBACK = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                // アプリのインストール時の処理（初期起動時の処理）
                // データベースに初期データを追加する場合などはここに処理を記述
//                // 以下テストコード
//                PostDao dao = INSTANCE.postDao();
//                // 全件削除
//                dao.deleteAllPost();
//                PostEntity entity = new PostEntity(
//                        35.46885213872538
//                        , 139.520270821662
//                        , "2024-01-31"
//                        , "産業技術短期大学校"
//                        , null
//                );
//                dao.insert(entity);
            });
        }
    };
}
