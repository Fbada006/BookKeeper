package ps.room.com

import android.app.Activity
import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

@Suppress("DEPRECATION")
class SearchResultActivity : AppCompatActivity(), BookListAdapter.OnDeleteClickListener {

    private lateinit var searchViewModel: SearchViewModel
    private var bookListAdapter: BookListAdapter? = null
    private lateinit var fab: FloatingActionButton
    private lateinit var recyclerview: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        fab = findViewById(R.id.add_book_fab)
        recyclerview = findViewById(R.id.recyclerview)

        setSupportActionBar(toolbar)

        fab.visibility = View.INVISIBLE

        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        bookListAdapter = BookListAdapter(this, this)
        recyclerview.adapter = bookListAdapter
        recyclerview.layoutManager = LinearLayoutManager(this)

        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {

            val searchQuery = intent.getStringExtra(SearchManager.QUERY)

            Log.i(TAG, "Search Query is $searchQuery")

            searchViewModel.getBooksByBookOrAuthor("%$searchQuery%")?.observe(this) { books ->
                books?.let { bookListAdapter!!.setBooks(books) }
            }
        }
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == UPDATE_BOOK_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Code to edit book
            val bookId = data!!.getStringExtra(EditBookActivity.ID)
            val authorName = data.getStringExtra(EditBookActivity.UPDATED_AUTHOR)
            val bookName = data.getStringExtra(EditBookActivity.UPDATED_BOOK)

            val book = Book(bookId!!, authorName, bookName)
            searchViewModel.update(book)

            Toast.makeText(applicationContext, R.string.updated, Toast.LENGTH_LONG).show()

        } else {
            Toast.makeText(applicationContext, R.string.not_saved, Toast.LENGTH_LONG).show()
        }
    }

    override fun onDeleteClickListener(myBook: Book) {
        searchViewModel.delete(myBook)
        Toast.makeText(applicationContext, R.string.deleted, Toast.LENGTH_LONG).show()
    }

    companion object {
        const val UPDATE_BOOK_ACTIVITY_REQUEST_CODE = 2
        private val TAG = this.javaClass.simpleName
    }
}