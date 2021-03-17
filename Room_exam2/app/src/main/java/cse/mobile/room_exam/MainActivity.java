package cse.mobile.room_exam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.loader.content.AsyncTaskLoader;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText mTodoEditText;
    private TextView mResultTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTodoEditText= findViewById(R.id.todo_edit);
        mResultTextView = findViewById(R.id.result_text);




        //UI 갱신
        db.todoDao().getAll().observe(this, new Observer<List<Todo>>() {
            @Override
            public void onChanged(List<Todo> todos) {
                mResultTextView.setText(todos.toString());
            }
        });

        // 버튼 클릭시 DB에 Insert
        findViewById(R.id.todo_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new InsertAsyncTask(db.todoDao())
                        .execute(new Todo(mTodoEditText.getText().toString()));
            }
        });

    }

}