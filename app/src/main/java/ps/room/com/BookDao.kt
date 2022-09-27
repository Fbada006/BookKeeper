package ps.room.com

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface BookDao {

    @Insert
    suspend fun insert(book: Book)
}