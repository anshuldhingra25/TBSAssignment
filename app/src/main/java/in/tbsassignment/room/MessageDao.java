package in.tbsassignment.room;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface MessageDao {

    @Query("SELECT * FROM MessageTable")
    List<MessageTable> getAll();

    @Insert
    void insert(MessageTable messageTable);

 }