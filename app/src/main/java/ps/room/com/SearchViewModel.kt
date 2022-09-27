package ps.room.com

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    private val bookRepository = BookRepository(application)

    fun getBooksByBookOrAuthor(searchQuery: String): LiveData<List<Book>>? {
        return bookRepository.getBooksByBookOrAuthor(searchQuery)
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
