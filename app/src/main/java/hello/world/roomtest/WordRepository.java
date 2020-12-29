package hello.world.roomtest;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

// Repository Class
// 쿼리 관리 및 여러 백엔드 사용
// 리포지토리는 네트워크에서 데이터를 가져올 지 아니면 로컬 DB에서 캐시된 결과를 사용할지 구현
public class WordRepository {
    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;

    WordRepository(Application application){
        // Thread Pool로 비동기 데이터 삽입
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAlphabetizedWords();
    }

    LiveData<List<Word>> getAllWords(){
        return mAllWords;
    }

    void insert(Word word){
        WordRoomDatabase.databaseWriteExecutor.execute(() -> {
            mWordDao.insert(word);
        });
    }
}
