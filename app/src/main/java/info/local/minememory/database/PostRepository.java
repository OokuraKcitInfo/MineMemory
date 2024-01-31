package info.local.minememory.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class PostRepository {
    private final PostDao postDao;

    public PostRepository(Application application) {
        PostRoomDatabase db = PostRoomDatabase.getDatabase(application);
        postDao = db.postDao();
    }

    public LiveData<List<PostEntity>> getAllPostsDesc() {
        return postDao.findAllPostsDesc();
    }

    public void insert(PostEntity entity) {
        PostRoomDatabase.databaseWriteExecutor.execute(() -> postDao.insert(entity));
    }

    public void update(PostEntity entity) {
        PostRoomDatabase.databaseWriteExecutor.execute(() -> postDao.update(entity));
    }

    public void delete(PostEntity entity) {
        PostRoomDatabase.databaseWriteExecutor.execute(() -> postDao.delete(entity));
    }
}
