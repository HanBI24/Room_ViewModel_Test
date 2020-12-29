package hello.world.roomtest;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

// DAO (Database Access Object)
// Dao는 interface 혹은 Abstract Class만 가능
@Dao
public interface WordDao {

    // 한 단어 삽입
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Word word);

    // 모든 단어 삭제
    @Query("DELETE FROM word_table")
    void deleteAll();

    // 모든 단어 오름차순 반환
    @Query("SELECT * FROM word_table ORDER BY word ASC")
    LiveData<List<Word>> getAlphabetizedWords();
}
