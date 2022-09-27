package ps.room.com

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class BookViewModel(application: Application) : AndroidViewModel(application) {

    private val bookDao: BookDao

    init {
        val bookDb = BookRoomDatabase.getDatabase(application)
        bookDao = bookDb!!.bookDao()
    }

    fun insert(book: Book) {
        viewModelScope.launch {
            bookDao.insert(book)
        }
    }
}