package com.example.localmovies

import android.content.Context
import android.inputmethodservice.InputMethodService
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.localmovies.adapters.CommentAdapter
import com.example.localmovies.databinding.ActivityCommentBinding
import com.example.localmovies.datasource.Comment

class CommentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommentBinding
    private lateinit var viewModel: MainViewModel
    private var editId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val movieId = intent.extras?.get(MOVIE_ID).toString().toInt()
        val movieName = intent.extras?.get(MOVIE_NAME).toString()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = movieName
        binding = DataBindingUtil.setContentView(this, R.layout.activity_comment)
        binding.newRating.also {
            it.minValue = 0
            it.maxValue = 5
        }
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.commentList.adapter = CommentAdapter({
            // on delete button
            viewModel.deleteComment(it._id) {
                getComments(movieId)
                Log.d("hello", "delete")
            }
        },
            {
                // on edit button
                binding.apply {
                    btnSend.text = "SAVE"

                    newUsername.setText(it.username)
                    newUsername.isEnabled = false

                    newRating.value = it.rating

                    newComment.setText(it.review)
                    newComment.requestFocus()

                    btnCancel.visibility = View.VISIBLE
                }
                editId = it._id
                showKeyboard()


            })
        getComments(movieId)

        binding.btnSend.setOnClickListener {
            if (binding.btnSend.text.toString() == "SEND") {
                // add comment button
                viewModel.addComment(
                    binding.newUsername.text.toString(),
                    binding.newComment.text.toString(),
                    binding.newRating.value,
                    movieId,
                    movieName
                ) {
                    // callback
                    getComments(movieId)
                    hideKeyboard()
                    binding.commentList.smoothScrollToPosition(0)
                    binding.apply {
                        newUsername.text.clear()
                        newRating.value = 0
                        newComment.text.clear()
                    }
                }
            } else if (binding.btnSend.text.toString() == "SAVE") {
                // edit comment button
                viewModel.updateComment(
                    editId,
                    binding.newComment.text.toString(),
                    binding.newRating.value
                ) {
                    editId = -1
                    binding.apply {
                        resetCommentBox()
                    }
                    getComments(movieId)
                    hideKeyboard()
                }
            }
        }

        binding.btnCancel.setOnClickListener {
            binding.apply {
                resetCommentBox()
            }
        }
    }

    private fun resetCommentBox() {
        binding.apply {
            btnSend.text = "SEND"
            newUsername.isEnabled = true
            newUsername.text.clear()
            newComment.text.clear()
            newRating.value = 0
            btnCancel.visibility = View.INVISIBLE
        }
    }

    private fun getComments(movieId: Int) {
        viewModel.getComments { comments ->
            val movieComments = mutableListOf<Comment>()
            comments.forEach { comment ->
                if (comment.movieId == movieId)
                    movieComments.add(comment)
            }
            movieComments.reverse()

            (binding.commentList.adapter as CommentAdapter).submitList(movieComments)
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    private fun showKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.newComment, InputMethodManager.SHOW_IMPLICIT)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            else -> return false
        }
        return true
    }
}