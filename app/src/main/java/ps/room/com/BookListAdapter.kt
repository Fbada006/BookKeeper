package ps.room.com

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BookListAdapter(private val context: Context) : RecyclerView.Adapter<BookListAdapter.BookViewHolder>() {

    private var bookList: List<Book> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return BookViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = bookList[position]
        holder.setData(book.author!!, book.book!!, position)
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

        fun setData(author: String, book: String, position: Int) {
            tvAuthor.text = author
            tvBook.text = book
            this.pos = position
        }
    }
}
