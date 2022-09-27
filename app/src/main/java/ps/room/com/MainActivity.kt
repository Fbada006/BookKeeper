package ps.room.com

import android.app.Activity
import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.UUID

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), BookListAdapter.OnDeleteClickListener {

    private lateinit var bookViewModel: BookViewModel
    private lateinit var toolbar: Toolbar
    private lateinit var fab: FloatingActionButton
    private lateinit var recyclerview: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        fab = findViewById(R.id.add_book_fab)
        recyclerview = findViewById(R.id.recyclerview)

        setSupportActionBar(toolbar)

        bookViewModel = ViewModelProvider(this)[BookViewModel::class.java]

        val bookListAdapter = BookListAdapter(this, this)

        recyclerview.adapter = bookListAdapter
        recyclerview.layoutManager = LinearLayoutManager(this)

        fab.setOnClickListener {
            val intent = Intent(this, NewBookActivity::class.java)
            startActivityForResult(intent, NEW_NOTE_ACTIVITY_REQUEST_CODE)
        }

        bookViewModel.allBooks.observe(this) { books ->
            books?.let {
                bookListAdapter.setBooks(books)
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == NEW_NOTE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            val id = UUID.randomUUID().toString()
            val authorName = data?.getStringExtra(NewBookActivity.NEW_AUTHOR)
            val bookName = data?.getStringExtra(NewBookActivity.NEW_BOOK)
            val description = data?.getStringExtra(NewBookActivity.NEW_DESCRIPTION)

            val book = Book(id, authorName, bookName, description!!)

            bookViewModel.insert(book)

            Toast.makeText(applicationContext, R.string.saved, Toast.LENGTH_LONG).show()

        } else if (requestCode == UPDATE_BOOK_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            val id = data?.getStringExtra(EditBookActivity.ID)
            val authorName = data?.getStringExtra(EditBookActivity.UPDATED_AUTHOR)
            val bookName = data?.getStringExtra(EditBookActivity.UPDATED_BOOK)
            val description = data?.getStringExtra(EditBookActivity.UPDATED_DESCRIPTION)

            val book = Book(id!!, authorName, bookName, description!!)

            bookViewModel.update(book)

            Toast.makeText(applicationContext, R.string.updated, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(
                applicationContext, R.string.not_saved, Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)

        // Get the SearchView and set the searchable configuration
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        // Setting the SearchResultActivity to show the result
        val componentName = ComponentName(this, SearchResultActivity::class.java)
        val searchableInfo = searchManager.getSearchableInfo(componentName)
        searchView.setSearchableInfo(searchableInfo)

        return true
    }

    companion object {
        private const val NEW_NOTE_ACTIVITY_REQUEST_CODE = 1
        const val UPDATE_BOOK_ACTIVITY_REQUEST_CODE = 2
        const val EXTRA_BOOK_ID = "id"
        const val EXTRA_BOOK_NAME = "book"
        const val EXTRA_BOOK_AUTHOR = "author"
        const val EXTRA_BOOK_DESCRIPTION = "description"
    }

    override fun onDeleteClickListener(myBook: Book) {
        bookViewModel.delete(myBook)
        Toast.makeText(applicationContext, R.string.deleted, Toast.LENGTH_LONG).show()
    }
}