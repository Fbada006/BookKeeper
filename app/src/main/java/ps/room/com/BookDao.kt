package ps.room.com

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface BookDao {

    @Insert
    suspend fun insert(book: Book)

    @Query("SELECT * FROM books WHERE book LIKE :searchString OR author LIKE :searchString")
    fun getBooksByBookOrAuthor(searchString: String): LiveData<List<Book>>

    @get:Query("SELECT * FROM books")
    val allBooks: LiveData<List<Book>>

    @Update
    suspend fun update(book: Book)

    @Delete
    suspend fun delete(book: Book)
}