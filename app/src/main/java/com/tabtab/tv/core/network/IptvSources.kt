package com.tabtab.tv.core.network

/**
 * Per-country iptv-org M3U playlists (free-to-air only) + the global index as a bulk
 * fallback. Each per-country source is tagged with its real country name here, since
 * the `tvg-country` M3U attribute is frequently missing from individual entries — the
 * source playlist a channel came from is a far more reliable signal. Same list used by
 * the TabTab TV web project, validated there against ~6,500 real aggregated channels.
 */
val IPTV_PLAYLIST_SOURCES: List<Pair<String, String>> = listOf(
    // Maghreb & Monde arabe
    "https://iptv-org.github.io/iptv/countries/tn.m3u" to "Tunisie",
    "https://iptv-org.github.io/iptv/countries/dz.m3u" to "Algérie",
    "https://iptv-org.github.io/iptv/countries/ma.m3u" to "Maroc",
    "https://iptv-org.github.io/iptv/countries/ly.m3u" to "Libye",
    "https://iptv-org.github.io/iptv/countries/eg.m3u" to "Égypte",
    "https://iptv-org.github.io/iptv/countries/qa.m3u" to "Qatar",
    "https://iptv-org.github.io/iptv/countries/sa.m3u" to "Arabie Saoudite",
    "https://iptv-org.github.io/iptv/countries/ae.m3u" to "Émirats Arabes Unis",
    "https://iptv-org.github.io/iptv/countries/kw.m3u" to "Koweït",
    "https://iptv-org.github.io/iptv/countries/bh.m3u" to "Bahreïn",
    "https://iptv-org.github.io/iptv/countries/om.m3u" to "Oman",
    "https://iptv-org.github.io/iptv/countries/jo.m3u" to "Jordanie",
    "https://iptv-org.github.io/iptv/countries/lb.m3u" to "Liban",
    "https://iptv-org.github.io/iptv/countries/iq.m3u" to "Irak",
    "https://iptv-org.github.io/iptv/countries/sy.m3u" to "Syrie",
    "https://iptv-org.github.io/iptv/countries/ye.m3u" to "Yémen",
    "https://iptv-org.github.io/iptv/countries/sd.m3u" to "Soudan",
    "https://iptv-org.github.io/iptv/countries/ps.m3u" to "Palestine",
    // Turquie
    "https://iptv-org.github.io/iptv/countries/tr.m3u" to "Turquie",
    // Europe
    "https://iptv-org.github.io/iptv/countries/fr.m3u" to "France",
    "https://iptv-org.github.io/iptv/countries/be.m3u" to "Belgique",
    "https://iptv-org.github.io/iptv/countries/ch.m3u" to "Suisse",
    "https://iptv-org.github.io/iptv/countries/de.m3u" to "Allemagne",
    "https://iptv-org.github.io/iptv/countries/gb.m3u" to "Royaume-Uni",
    "https://iptv-org.github.io/iptv/countries/es.m3u" to "Espagne",
    "https://iptv-org.github.io/iptv/countries/it.m3u" to "Italie",
    "https://iptv-org.github.io/iptv/countries/pt.m3u" to "Portugal",
    "https://iptv-org.github.io/iptv/countries/nl.m3u" to "Pays-Bas",
    "https://iptv-org.github.io/iptv/countries/gr.m3u" to "Grèce",
    "https://iptv-org.github.io/iptv/countries/ru.m3u" to "Russie",
    "https://iptv-org.github.io/iptv/countries/pl.m3u" to "Pologne",
    "https://iptv-org.github.io/iptv/countries/ro.m3u" to "Roumanie",
    "https://iptv-org.github.io/iptv/countries/se.m3u" to "Suède",
    "https://iptv-org.github.io/iptv/countries/at.m3u" to "Autriche",
    "https://iptv-org.github.io/iptv/countries/ua.m3u" to "Ukraine",
    // Amériques
    "https://iptv-org.github.io/iptv/countries/us.m3u" to "États-Unis",
    "https://iptv-org.github.io/iptv/countries/ca.m3u" to "Canada",
    "https://iptv-org.github.io/iptv/countries/br.m3u" to "Brésil",
    "https://iptv-org.github.io/iptv/countries/mx.m3u" to "Mexique",
    "https://iptv-org.github.io/iptv/countries/ar.m3u" to "Argentine",
    "https://iptv-org.github.io/iptv/countries/co.m3u" to "Colombie",
    "https://iptv-org.github.io/iptv/countries/cl.m3u" to "Chili",
    // Afrique subsaharienne
    "https://iptv-org.github.io/iptv/countries/sn.m3u" to "Sénégal",
    "https://iptv-org.github.io/iptv/countries/ci.m3u" to "Côte d'Ivoire",
    "https://iptv-org.github.io/iptv/countries/ng.m3u" to "Nigeria",
    "https://iptv-org.github.io/iptv/countries/cm.m3u" to "Cameroun",
    "https://iptv-org.github.io/iptv/countries/za.m3u" to "Afrique du Sud",
    // Asie
    "https://iptv-org.github.io/iptv/countries/in.m3u" to "Inde",
    "https://iptv-org.github.io/iptv/countries/pk.m3u" to "Pakistan",
    "https://iptv-org.github.io/iptv/countries/id.m3u" to "Indonésie",
    "https://iptv-org.github.io/iptv/countries/my.m3u" to "Malaisie",
    "https://iptv-org.github.io/iptv/countries/ph.m3u" to "Philippines",
    "https://iptv-org.github.io/iptv/countries/jp.m3u" to "Japon",
    "https://iptv-org.github.io/iptv/countries/kr.m3u" to "Corée du Sud",
    // Index thématique + index global (bulk, pas de pays fixe — "" = pas de surcharge)
    "https://iptv-org.github.io/iptv/index.category.m3u" to "",
    "https://iptv-org.github.io/iptv/index.m3u" to ""
)

/** Raw channel/station as parsed from a source, before dedup/verification/persistence. */
data class RawChannel(
    val name: String,
    val logoUrl: String?,
    val rawCategory: String,
    val country: String,
    val streamUrl: String
)

object M3uParser {
    private val nameRegex = Regex(",([^,]+)$")
    private val logoRegex = Regex("tvg-logo=\"([^\"]+)\"")
    private val groupRegex = Regex("group-title=\"([^\"]+)\"")
    private val countryRegex = Regex("tvg-country=\"([^\"]+)\"")

    /** Mirrors the parsing logic validated in the TabTab TV web project's server.ts. */
    fun parse(playlistText: String, sourceCountry: String): List<RawChannel> {
        if (!playlistText.contains("#EXTINF:")) return emptyList()
        val result = mutableListOf<RawChannel>()
        var pendingName: String? = null
        var pendingLogo: String? = null
        var pendingCategory = "General"
        var pendingCountry = "International"

        for (rawLine in playlistText.lineSequence()) {
            val line = rawLine.trim()
            if (line.startsWith("#EXTINF:")) {
                pendingName = nameRegex.find(line)?.groupValues?.get(1)?.trim()
                pendingLogo = logoRegex.find(line)?.groupValues?.get(1)?.trim()
                pendingCategory = groupRegex.find(line)?.groupValues?.get(1)?.trim() ?: "General"
                val tagCountry = countryRegex.find(line)?.groupValues?.get(1)?.trim()
                pendingCountry = sourceCountry.ifEmpty { tagCountry ?: "International" }
            } else if ((line.startsWith("http://") || line.startsWith("https://")) && !line.contains(".mpd")) {
                val name = pendingName
                if (!name.isNullOrBlank()) {
                    result.add(RawChannel(name, pendingLogo, pendingCategory, pendingCountry, line))
                    pendingName = null
                }
            }
        }
        return result
    }
}

/** De-duplicates by stream URL and by normalized channel name (strips quality/status tags). */
object ChannelDeduplicator {
    private val tagStripRegex = Regex("""\((?:[^)]*)\)|\[(?:[^]]*)]""")
    private val qualityWordsRegex = Regex(
        """\b(hd|fhd|uhd|4k|sd|hevc|h265|backup|geo-?blocked|not 24/7)\b""",
        RegexOption.IGNORE_CASE
    )
    private val nonAlphaNumericRegex = Regex("""[^\p{L}\p{N}]+""")

    fun normalizeName(name: String): String {
        var cleaned = tagStripRegex.replace(name, "")
        cleaned = qualityWordsRegex.replace(cleaned, "")
        cleaned = nonAlphaNumericRegex.replace(cleaned, "")
        return cleaned.lowercase().trim()
    }

    fun <T> dedup(items: List<T>, streamUrlOf: (T) -> String, nameOf: (T) -> String): List<T> {
        val seenUrls = HashSet<String>()
        val seenNames = HashSet<String>()
        val result = ArrayList<T>(items.size)
        for (item in items) {
            val url = streamUrlOf(item)
            val nameKey = normalizeName(nameOf(item))
            if (url in seenUrls || (nameKey.isNotEmpty() && nameKey in seenNames)) continue
            seenUrls.add(url)
            if (nameKey.isNotEmpty()) seenNames.add(nameKey)
            result.add(item)
        }
        return result
    }
}

/**
 * Maps the ~140 raw/messy iptv-org category tags (e.g. "Kids;Religious", "Legislative")
 * into a small set of clean, professional genre buckets — one per channel, by priority.
 * Same taxonomy validated in the TabTab TV web project.
 */
object GenreTaxonomy {
    data class Bucket(val label: String, val rawTags: Set<String>)

    val buckets = listOf(
        Bucket("Actualités & Info", setOf("news", "business")),
        Bucket("Sport", setOf("sports")),
        Bucket("Cinéma & Films", setOf("movies", "classic")),
        Bucket("Séries & Feuilletons", setOf("series")),
        Bucket("Jeunesse", setOf("kids", "animation")),
        Bucket("Documentaires & Science", setOf("documentary", "science", "education")),
        Bucket("Musique", setOf("music")),
        Bucket("Religieux & Spiritualité", setOf("religious")),
        Bucket(
            "Divertissement & Lifestyle",
            setOf("entertainment", "comedy", "family", "lifestyle", "culture", "cooking", "auto", "travel", "outdoor", "relax")
        ),
        Bucket("Généraliste", setOf("general", "undefined", "public", "legislative", "weather", "shop", "interactive"))
    )

    fun normalize(rawCategory: String): String {
        val tags = rawCategory.lowercase().split(";").map { it.trim() }.toSet()
        return buckets.firstOrNull { it.rawTags.any { tag -> tag in tags } }?.label ?: "Généraliste"
    }
}
