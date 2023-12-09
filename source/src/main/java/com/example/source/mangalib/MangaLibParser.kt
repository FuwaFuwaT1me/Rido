package com.example.source.mangalib

import com.example.core_models.manga.MangaListItem
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

class MangaLibParser {

    private val mangaLibSource = MangaLibSourceRequest()

    fun parseMangaList(): List<MangaListItem> {
        val url = "https://mangalib.me/manga-list?types[]=1"
        val doc: Document = Jsoup.connect(url).get()
        val itemSelector = ".media-card-wrap"

        return doc.select(itemSelector)
            .map { parseItem(it) }
    }

    private fun parseItem(element: Element): MangaListItem {
        val urlSelector = ".media-card"
        val titleSelector = ".media-card__title"

        val url = element.select(urlSelector).attr("href")
        val image = element.select(urlSelector).attr("data-src")
        val title = element.select(titleSelector).text()

        return MangaListItem(url, image, title)
    }
}
