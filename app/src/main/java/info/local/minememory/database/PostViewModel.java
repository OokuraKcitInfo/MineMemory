package info.local.minememory.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class PostViewModel extends AndroidViewModel {
    private final PostRepository repository;
//    private final LiveData<List<PostEntity>> allPostsDesc;

    public PostViewModel(@NonNull Application application) {
        super(application);
        repository = new PostRepository(application);
    }

    public LiveData<List<PostEntity>> getAllPostsDesc() {
        return repository.getAllPostsDesc();
    }

    public void insert(PostEntity entity) { repository.insert(entity); }
    public void update(PostEntity entity) { repository.update(entity); }
    public void delete(PostEntity entity) { repository.delete(entity); }
}
