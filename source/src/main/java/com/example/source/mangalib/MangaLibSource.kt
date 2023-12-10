package com.example.source.mangalib

import com.example.core_models.manga.MangaListItem
import com.example.core_models.source.manga.MangaSource
import com.example.source.mangalib.api.MangaLibApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class MangaLibSource(
    private val mangaLibApi: MangaLibApi
) : MangaSource {

    private val ioDispatcher = Dispatchers.IO

    override suspend fun getMangaList(page: Int): List<MangaListItem> {
        val csrfToken = getLatestMangaPage()

        return mangaLibApi.getMangaLibMangaList(
            userAgent = "Mozilla/5.0 (Android 7.0; Mobile; rv:109.0) Gecko/118.0 Firefox/118.0",
            accept = "application/json, text/plain, */*",
            requestedWith = "XMLHttpRequest",
            csrfToken = csrfToken,
            page = page
        )
    }

    private suspend fun getLatestMangaPage(): String {
        return withContext(ioDispatcher) {
            val token = async {
                val response = mangaLibApi.getMangaLibLatest()
                val responseBody = response.body().toString()
                "_token\" content=\"(.*)\"".toRegex().find(responseBody)!!.groups[1]!!.value
            }

            token.await()
        }
    }

    companion object {
        internal val baseUrl = "https://mangalib.me/"
    }
}
