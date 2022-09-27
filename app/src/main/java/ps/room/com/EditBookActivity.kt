package ps.room.com

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class EditBookActivity : AppCompatActivity() {

    var id: String? = null
    private lateinit var bSave: Button
    private lateinit var etAuthorName: EditText
    private lateinit var etBookName: EditText
    private lateinit var bCancel: Button
    private lateinit var etDescription: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_book)

        bSave = findViewById(R.id.bSave)
        etAuthorName = findViewById(R.id.etAuthorName)
        etBookName = findViewById(R.id.etBookName)
        bCancel = findViewById(R.id.bCancel)
        etDescription = findViewById(R.id.etDescription)

        val bundle: Bundle? = intent.extras

        bundle?.let {
            id = bundle.getString(MainActivity.EXTRA_BOOK_ID)
            val book = bundle.getString(MainActivity.EXTRA_BOOK_NAME)
            val author = bundle.getString(MainActivity.EXTRA_BOOK_AUTHOR)
            val description = bundle.getString(MainActivity.EXTRA_BOOK_DESCRIPTION)

            etAuthorName.setText(author)
            etBookName.setText(book)
            etDescription.setText(description)
        }

        bSave.setOnClickListener {
            val updatedAuthor = etAuthorName.text.toString()
            val updatedBook = etBookName.text.toString()
            val updatedDescription = etDescription.text.toString()

            val resultIntent = Intent()
            resultIntent.putExtra(ID, id)
            resultIntent.putExtra(UPDATED_AUTHOR, updatedAuthor)
            resultIntent.putExtra(UPDATED_BOOK, updatedBook)
            resultIntent.putExtra(UPDATED_DESCRIPTION, updatedDescription)
            setResult(Activity.RESULT_OK, resultIntent)

            finish()
        }

        bCancel.setOnClickListener {
            finish()
        }
    }

    companion object {
        const val ID = "book_id"
        const val UPDATED_AUTHOR = "author_name"
        const val UPDATED_BOOK = "book_name"
        const val UPDATED_DESCRIPTION = "description"
    }
}