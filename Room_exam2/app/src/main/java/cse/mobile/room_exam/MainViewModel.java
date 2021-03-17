package cse.mobile.room_exam;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import java.util.List;

public class MainViewModel extends AndroidViewModel{
    private AppDatabase db;
    public MainViewModel(@NonNull Application application) {
        super(application);

        db = Room.databaseBuilder(application,AppDatabase.class,"todo-db")
                .build();
    }

    public LiveData<List<Todo>> getAll(){
        return db.todoDao().getAll();
    }

    public void insert(Todo todo){
        new InsertAsyncTask(db.todoDao())
                .execute(todo);
    }


    // 백그라운드에서 작업
    private static class InsertAsyncTask extends AsyncTask<Todo,Void,Void> {
        private TodoDao mTodoDao;

        public InsertAsyncTask(TodoDao mTodoDao) {
            this.mTodoDao = mTodoDao;
        }

        @Override
        protected Void doInBackground(Todo... todos) {
            mTodoDao.insert(todos[0]);
            return null;
        }
    }


}
