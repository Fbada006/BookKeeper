package ps.room.com

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    private val bookDao: BookDao

    init {
        val bookDB = BookRoomDatabase.getDatabase(application)
        bookDao = bookDB!!.bookDao()
    }

    fun getBooksByBookOrAuthor(searchQuery: String): LiveData<List<Book>>? {
        return bookDao.getBooksByBookOrAuthor(searchQuery)
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
