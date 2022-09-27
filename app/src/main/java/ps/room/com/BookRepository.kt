package ps.room.com

import android.app.Application
import androidx.lifecycle.LiveData

class BookRepository(application: Application) {

    val allBooks: LiveData<List<Book>>
    private val bookDao: BookDao

    init {
        val bookDb = BookRoomDatabase.getDatabase(application)
        bookDao = bookDb!!.bookDao()
        allBooks = bookDao.allBooks
    }

    fun getBooksByBookOrAuthor(searchQuery: String): LiveData<List<Book>> {
        return bookDao.getBooksByBookOrAuthor(searchQuery)
    }

    suspend fun insert(book: Book) {
        bookDao.insert(book)
    }

    suspend fun update(book: Book) {
        bookDao.update(book)
    }

    suspend fun delete(book: Book) {
        bookDao.delete(book)
    }
}