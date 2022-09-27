package ps.room.com

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ps.room.com.MainActivity.Companion.EXTRA_BOOK_AUTHOR
import ps.room.com.MainActivity.Companion.EXTRA_BOOK_DESCRIPTION
import ps.room.com.MainActivity.Companion.EXTRA_BOOK_ID
import ps.room.com.MainActivity.Companion.EXTRA_BOOK_NAME
import ps.room.com.MainActivity.Companion.EXTRA_LAST_UPDATED
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BookListAdapter(
    private val context: Context,
    private val onDeleteClickListener: OnDeleteClickListener
) : RecyclerView.Adapter<BookListAdapter.BookViewHolder>() {

    private var bookList: List<Book> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return BookViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = bookList[position]
        holder.setData(book.author!!, book.book!!, book.lastUpdated, position)
        holder.setListeners()
    }

    override fun getItemCount(): Int = bookList.size

    fun setBooks(books: List<Book>) {
        bookList = books
        notifyDataSetChanged()
    }

    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var pos: Int = 0
        private val tvAuthor: TextView = itemView.findViewById(R.id.tvAuthor)
        private val tvBook: TextView = itemView.findViewById(R.id.tvBook)
        private val ivRowDelete: ImageView = itemView.findViewById(R.id.ivRowDelete)
        private val tvLastUpdated: TextView = itemView.findViewById(R.id.tvLastUpdated)

        fun setData(author: String, book: String, lastUpdated: Date?, position: Int) {
            tvAuthor.text = author
            tvBook.text = book
            tvLastUpdated.text = getFormattedDate(lastUpdated)
            this.pos = position
        }

        fun setListeners() {
            itemView.setOnClickListener {
                val intent = Intent(context, EditBookActivity::class.java)
                intent.putExtra(EXTRA_BOOK_ID, bookList[pos].id)
                intent.putExtra(EXTRA_BOOK_AUTHOR, bookList[pos].author)
                intent.putExtra(EXTRA_BOOK_NAME, bookList[pos].book)
                intent.putExtra(EXTRA_BOOK_DESCRIPTION, bookList[pos].description)
                intent.putExtra(EXTRA_LAST_UPDATED, getFormattedDate(bookList[pos].lastUpdated))
                (context as Activity).startActivityForResult(intent, MainActivity.UPDATE_BOOK_ACTIVITY_REQUEST_CODE)
            }

            ivRowDelete.setOnClickListener {
                onDeleteClickListener.onDeleteClickListener(bookList[pos])
            }
        }

        private fun getFormattedDate(lastUpdated: Date?): String {
            var time = "Last Updated: "
            time += lastUpdated?.let {
                val sdf = SimpleDateFormat("HH:mm d MMM, yyyy", Locale.getDefault())
                sdf.format(lastUpdated)
            } ?: "Not Found"
            return time
        }
    }

    interface OnDeleteClickListener {
        fun onDeleteClickListener(myBook: Book)
    }
}
