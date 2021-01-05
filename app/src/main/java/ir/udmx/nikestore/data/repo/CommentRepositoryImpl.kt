package ir.udmx.nikestore.data.repo

import io.reactivex.Single
import ir.udmx.nikestore.data.Comment
import ir.udmx.nikestore.data.repo.source.CommentDataSource

class CommentRepositoryImpl(val commentDataSource: CommentDataSource) : CommentRepository {
    override fun getAll(productId:Int): Single<List<Comment>> = commentDataSource.getAll(productId)

    override fun insert(): Single<Comment> {
        TODO("Not yet implemented")
    }
}