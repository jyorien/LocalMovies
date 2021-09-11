package com.example.localmovies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.localmovies.R
import com.example.localmovies.datasource.Comment

class CommentAdapter(val onDelete: (Comment) -> Unit, val onEdit: (Comment) -> Unit) :
    ListAdapter<Comment, CommentVH>(CommentComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentVH {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.comment_list_item, parent, false)
        return CommentVH(view)
    }

    override fun onBindViewHolder(holder: CommentVH, position: Int) {
        holder.bind(getItem(position), onDelete, onEdit)
    }

}

class CommentVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val username = itemView.findViewById<TextView>(R.id.comment_user)
    val rating = itemView.findViewById<TextView>(R.id.comment_rating)
    val body = itemView.findViewById<TextView>(R.id.comment_body)
    val date = itemView.findViewById<TextView>(R.id.comment_date)
    val btnDelete = itemView.findViewById<ImageButton>(R.id.btn_delete)
    val btnEdit = itemView.findViewById<ImageButton>(R.id.btn_edit)
    fun bind(comment: Comment, onDelete: (Comment) -> Unit, onEdit: (Comment) -> Unit) {
        val formattedRating = "Rating: ${comment.rating}"
        username.text = comment.username
        rating.text = formattedRating
        body.text = comment.review
        date.text = comment.datePosted
        btnDelete.setOnClickListener { onDelete(comment) }
        btnEdit.setOnClickListener { onEdit(comment) }
    }

}

class CommentComparator : DiffUtil.ItemCallback<Comment>() {
    override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
        return oldItem._id == newItem._id
    }

}