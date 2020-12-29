package hello.world.roomtest;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.net.ContentHandler;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Room Database
// 추상 클래스로 작성
// 이 클래스가 Database가 되도록 Annotation 작성
@Database(entities = {Word.class}, version = 1, exportSchema = false)
public abstract class WordRoomDatabase extends RoomDatabase {
    public abstract WordDao wordDao();

    private static volatile WordRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    // DB는 비동기로 처리되어야 하기 때문에 Thread Pool 생성
    // Thread Pool: 스레들의 개수를 제한된 개수만큼 지정하고, 큐에 들어오는 작업들을 스레드가 하나 씩 맡아서 처리
    // (스레드의 증폭 방지)
    // newFixedThreadpool(int n):  n개 만큼의 고정된 Thread Pool 생성
    // newCachedThreadpool(): 필요할 때, 필요한 만큼 Thread Pool 생성. 초기엔 0개 (60초 동안 추가된 스레드가 작업이 없을 경우 스레드 종료 및 풀에서 제거
    // newSingleThreadExecutor(): 스렐드가 1개인 ExecutorService를 반환. Single Thread에서 동작해야 하는 작업을 처리할 때 사용
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static WordRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    WordRoomDatabase.class, "word_database")
                    .addCallback(sRoomDatabaseCallback)
                    .build();
        }

        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // 람다식은 db에서 모두 삭제한 다음 "Hello"와 "World"를 추가함
            databaseWriteExecutor.execute(() -> {
                WordDao dao = INSTANCE.wordDao();
                dao.deleteAll();

                Word word = new Word("Hello");
                dao.insert(word);
                word = new Word("World");
                dao.insert(word);
            });
        }
    };

    // Singleton 패턴으로 만들기 위해 INSTANCE 해제
    public static void destroyInstance() { INSTANCE = null; }
}
