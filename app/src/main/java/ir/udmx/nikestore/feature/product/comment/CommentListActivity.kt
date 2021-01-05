package ir.udmx.nikestore.feature.product.comment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.udmx.nikestore.R
import ir.udmx.nikestore.common.EXTRA_KEY_ID
import ir.udmx.nikestore.common.NikeActivity
import ir.udmx.nikestore.data.Comment
import ir.udmx.nikestore.databinding.ActivityCommentListBinding
import ir.udmx.nikestore.databinding.ActivityProductDetailBinding
import ir.udmx.nikestore.feature.product.CommentAdapter
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CommentListActivity : NikeActivity() {
    private lateinit var binding: ActivityCommentListBinding
    val viewModel: CommentListViewModel by viewModel {
        parametersOf(
            intent.extras!!.getInt(
                EXTRA_KEY_ID
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel.progressBarLiveData.observe(this) {
            setProgressIndicator(it)
        }

        viewModel.commentsLiveData.observe(this) {
            val adapter = CommentAdapter(true)
            binding.commentsRv.layoutManager =
                LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            adapter.comments = it as ArrayList<Comment>
            binding.commentsRv.adapter = adapter
        }

        binding.commentListToolbar.onBackButtonClickListener = View.OnClickListener {
            finish()
        }
    }
}