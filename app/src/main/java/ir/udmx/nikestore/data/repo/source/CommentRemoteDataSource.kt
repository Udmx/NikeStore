package ir.udmx.nikestore.data.repo.source

import io.reactivex.Single
import ir.udmx.nikestore.data.Comment
import ir.udmx.nikestore.services.http.ApiService

class CommentRemoteDataSource(val apiService: ApiService) : CommentDataSource {
    override fun getAll(productId:Int): Single<List<Comment>> = apiService.getComments(productId)

    override fun insert(): Single<Comment> {
        TODO("Not yet implemented")
    }
}