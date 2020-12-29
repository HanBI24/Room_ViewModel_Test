package hello.world.roomtest;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Table class
// 클래스와 다른 table 이름을 넣고 싶은 경우 @Entity(name = "name") 지정 가능
@Entity(tableName = "word_table")
public class Word {

    // 기본 키 생성
    // 자동으로 올라감
    @PrimaryKey (autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "word")
    // name이 word인 column 생성
    private String mWord;

    public Word(@NonNull String word) {
        this.mWord = word;
    }

    public String getWord(){
        return this.mWord;
    }

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }
}
