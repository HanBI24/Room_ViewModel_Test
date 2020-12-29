package hello.world.roomtest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// RecyclerView Holder 클래스
public class WordViewHolder extends RecyclerView.ViewHolder {
    private final TextView wordItemView;

    // TextView 가져옴
    public WordViewHolder(@NonNull View itemView) {
        super(itemView);
        wordItemView = itemView.findViewById(R.id.textView);
    }

    // 데이터 바인딩
    public void bind(String text){
        wordItemView.setText(text);
    }

    // xml에 있는 레이아웃을 inflate해서 반환
    static WordViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new WordViewHolder(view);
    }
}
