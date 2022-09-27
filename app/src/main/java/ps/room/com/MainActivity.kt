package ps.room.com

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.UUID

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var bookViewModel: BookViewModel
    private lateinit var toolbar: Toolbar
    private lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        fab = findViewById(R.id.add_book_fab)

        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            val intent = Intent(this, NewBookActivity::class.java)
            startActivityForResult(intent, NEW_NOTE_ACTIVITY_REQUEST_CODE)
        }

        bookViewModel = ViewModelProvider(this)[BookViewModel::class.java]
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == NEW_NOTE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            val id = UUID.randomUUID().toString()
            val authorName = data?.getStringExtra(NewBookActivity.NEW_AUTHOR)
            val bookName = data?.getStringExtra(NewBookActivity.NEW_BOOK)

            val book = Book(id, authorName, bookName)

            bookViewModel.insert(book)

            Toast.makeText(applicationContext, R.string.saved, Toast.LENGTH_LONG).show()

        } else {
            Toast.makeText(
                applicationContext, R.string.not_saved, Toast.LENGTH_LONG
            ).show()
        }
    }

    companion object {
        private const val NEW_NOTE_ACTIVITY_REQUEST_CODE = 1
    }
}