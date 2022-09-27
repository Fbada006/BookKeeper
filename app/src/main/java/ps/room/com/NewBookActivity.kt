package ps.room.com

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class NewBookActivity : AppCompatActivity() {

    private lateinit var bAdd: Button
    private lateinit var etAuthorName: EditText
    private lateinit var etBookName: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_book)

        bAdd = findViewById(R.id.bAdd)
        etAuthorName = findViewById(R.id.etAuthorName)
        etBookName = findViewById(R.id.etBookName)

        bAdd.setOnClickListener {

            val resultIntent = Intent()

            if (TextUtils.isEmpty(etAuthorName.text) || TextUtils.isEmpty(etBookName.text)) {
                setResult(Activity.RESULT_CANCELED, resultIntent)
            } else {
                val author = etAuthorName.text.toString()
                val book = etBookName.text.toString()

                resultIntent.putExtra(NEW_AUTHOR, author)
                resultIntent.putExtra(NEW_BOOK, book)
                setResult(Activity.RESULT_OK, resultIntent)
            }
            finish()
        }
    }

    companion object {
        const val NEW_AUTHOR = "new_author"
        const val NEW_BOOK = "new_book"
    }
}