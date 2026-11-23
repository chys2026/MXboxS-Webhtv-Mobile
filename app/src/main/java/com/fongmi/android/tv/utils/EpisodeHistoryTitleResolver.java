package com.silent.android.mxboxs.utils;

import com.silent.android.mxboxs.bean.Episode;
import com.silent.android.mxboxs.bean.TmdbEpisode;

import java.util.Map;

public final class EpisodeHistoryTitleResolver {

    private EpisodeHistoryTitleResolver() {
    }

    public static String resolve(
            Episode episode,
            Map<Integer, String> scrapedTitles,
            boolean showScraped,
            boolean includeFileSize) {
        if (episode == null) return "";
        int number = episode.getNumber();
        String scrapedTitle = "";
        TmdbEpisode tmdbEpisode = episode.getTmdbEpisode();
        if (tmdbEpisode != null) {
            if (tmdbEpisode.getNumber() > 0) number = tmdbEpisode.getNumber();
            scrapedTitle = tmdbEpisode.getTitle();
        }
        if (isEmpty(scrapedTitle) && scrapedTitles != null) scrapedTitle = scrapedTitles.get(number);
        if (!showScraped || isEmpty(scrapedTitle)) return episode.getName();
        String title = EpisodeTitleFormatter.formatTmdbTitle(number, scrapedTitle);
        return EpisodeTitleFormatter.withSourceFileSize(episode.getName(), title, includeFileSize);
    }

    private static boolean isEmpty(String value) {
        return value == null || value.isEmpty();
    }
}
