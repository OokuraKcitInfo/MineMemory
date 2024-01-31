package info.local.minememory.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PostDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(PostEntity entity);
    @Update
    int update(PostEntity entity);
    @Delete
    int delete(PostEntity entity);

    @Query("DELETE FROM t_post")
    int deleteAllPost();

    @Query("SELECT * FROM t_post ORDER BY date DESC")
    LiveData<List<PostEntity>> findAllPostsDesc();

//    @Query("SELECT * FROM t_post WHERE date = :date")
//    LiveData<List<PostEntity>> findPostWithDate(String date);
}
