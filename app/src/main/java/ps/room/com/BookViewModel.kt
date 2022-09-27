package ps.room.com

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class BookViewModel(application: Application) : AndroidViewModel(application) {

    private val bookDao: BookDao
    val allBooks: LiveData<List<Book>>

    init {
        val bookDb = BookRoomDatabase.getDatabase(application)
        bookDao = bookDb!!.bookDao()
        allBooks = bookDao.allBooks
    }

    fun insert(book: Book) {
        viewModelScope.launch {
            bookDao.insert(book)
        }
    }

    fun update(book: Book) {
        viewModelScope.launch {
            bookDao.update(book)
        }
    }

    fun delete(book: Book) {
        viewModelScope.launch {
            bookDao.delete(book)
        }
    }
}