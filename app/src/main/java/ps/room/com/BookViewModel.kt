package ps.room.com

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class BookViewModel(application: Application) : AndroidViewModel(application) {

    val allBooks: LiveData<List<Book>>
    private val bookRepository = BookRepository(application)

    init {
        allBooks = bookRepository.allBooks
    }

    fun insert(book: Book) {
        viewModelScope.launch {
            bookRepository.insert(book)
        }
    }

    fun update(book: Book) {
        viewModelScope.launch {
            bookRepository.update(book)
        }
    }

    fun delete(book: Book) {
        viewModelScope.launch {
            bookRepository.delete(book)
        }
    }
}