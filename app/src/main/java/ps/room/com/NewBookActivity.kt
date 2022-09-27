package ps.room.com

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class NewBookActivity : AppCompatActivity() {

    private lateinit var bSave: Button
    private lateinit var etAuthorName: EditText
    private lateinit var etBookName: EditText
    private lateinit var etBookDescription: EditText
    private lateinit var bCancel: Button
    private lateinit var txvLastUpdated: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_book)

        bSave = findViewById(R.id.bSave)
        etAuthorName = findViewById(R.id.etAuthorName)
        etBookName = findViewById(R.id.etBookName)
        bCancel = findViewById(R.id.bCancel)
        etBookDescription = findViewById(R.id.etDescription)
        txvLastUpdated = findViewById(R.id.txvLastUpdated)

        txvLastUpdated.visibility = View.INVISIBLE

        bSave.setOnClickListener {

            val resultIntent = Intent()

            if (TextUtils.isEmpty(etAuthorName.text) || TextUtils.isEmpty(etBookName.text)) {
                setResult(Activity.RESULT_CANCELED, resultIntent)
            } else {
                val author = etAuthorName.text.toString()
                val book = etBookName.text.toString()
                val description = etBookDescription.text.toString()

                resultIntent.putExtra(NEW_AUTHOR, author)
                resultIntent.putExtra(NEW_BOOK, book)
                resultIntent.putExtra(NEW_DESCRIPTION, description)
                setResult(Activity.RESULT_OK, resultIntent)
            }
            finish()
        }

        bCancel.setOnClickListener {
            finish()
        }
    }

    companion object {
        const val NEW_AUTHOR = "new_author"
        const val NEW_BOOK = "new_book"
        const val NEW_DESCRIPTION = "new_description"
    }
}