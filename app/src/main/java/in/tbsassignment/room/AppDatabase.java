package in.tbsassignment.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {MessageTable.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MessageDao messageDao();

}