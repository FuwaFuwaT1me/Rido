package com.example.source.mangalib.api

import com.example.core_models.manga.MangaListItem
import com.example.source.mangalib.MangaLibSource
import kotlinx.coroutines.flow.Flow
import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface MangaLibApi {

    @GET("manga-list?types[]=1")
    fun getMangaLibLatest(): Response

    @POST("filterlist?dir=desc&sort=views&page={page}&chapters[min]=1")
    fun getMangaLibMangaList(
        @Header("User-Agent") userAgent: String,
        @Header("Accept") accept: String,
        @Header("X-Requested-With") requestedWith: String,
        @Header("x-csrf-token") csrfToken: String,
        @Path("page") page: Int
    ): List<MangaListItem>
}
