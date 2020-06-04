package hyo.betelgeuse.backend;

import java.util.*;

public class WordOccurrenceCounter {
    public static List<WordOccurrenceItem> getWordOccurrences(List<Article> articleList) {
        Map<String, Integer> wordMap = new HashMap<String, Integer>();

        for(Article article: articleList) {
            Scanner stringScanner = new Scanner(article.getTitle());

            while(stringScanner.hasNext()) {
                String tokenWord = stringScanner.next();
                tokenWord.replaceAll("[^a-zA-Z ]", "").
                        toLowerCase().trim();

                if (tokenWord.length() != 0) {
                    int count = wordMap.containsKey(tokenWord) ?
                            wordMap.get(tokenWord) : 0;
                    wordMap.put(tokenWord, count + 1);
                }
            }
        }

        return convertToWordOccurrencesList(wordMap);
    }

    private static List<WordOccurrenceItem> convertToWordOccurrencesList(
            Map<String, Integer> wordMap) {
        List<WordOccurrenceItem> resultList = new ArrayList<WordOccurrenceItem>();

        Iterator it = wordMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            resultList.add(new WordOccurrenceItem(
                    (String)pair.getKey(), (Integer) pair.getValue()));
            it.remove();
        }

        return resultList;
    }
}
