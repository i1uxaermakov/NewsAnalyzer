package hyo.betelgeuse.backend;

import javax.annotation.Resource;
import java.util.*;

public class WordOccurrenceCounter {

    @Resource(name = "stopWordsList")
    private Set<String> stopWordsList;



    public List<WordOccurrenceItem> getWordOccurrences(
            List<Article> articleList, int minNumOfOccurrences) {
        Map<String, Integer> wordMap = new HashMap<String, Integer>();

        for(Article article: articleList) {
            Scanner stringScanner = new Scanner(article.getTitle());

            while(stringScanner.hasNext()) {
                String tokenWord = stringScanner.next();

                if (!stopWordsList.contains(tokenWord)) {
                    tokenWord = tokenWord.
                            replaceAll("\\p{Punct}", "").
                            toLowerCase().
                            trim();

                    //todo cut to the punctuation (remove 's)

                    // cutting the words if they are longer than 6 characters
                    if(tokenWord.length() > 6) {
                        tokenWord = tokenWord.substring(0, 6);
                    }

                    if (tokenWord.length() != 0) {
                        int count = wordMap.containsKey(tokenWord) ?
                                wordMap.get(tokenWord) : 0;
                        wordMap.put(tokenWord, count + 1);
                    }
                }
            }
        }

        return convertToWordOccurrencesList(wordMap, minNumOfOccurrences);
    }

    private List<WordOccurrenceItem> convertToWordOccurrencesList(
            Map<String, Integer> wordMap, int minNumOfOccurrences) {
        List<WordOccurrenceItem> resultList = new ArrayList<WordOccurrenceItem>();

        Iterator it = wordMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();

            // to appear in the output, the word has to appear at least 8 times
            if((Integer)pair.getValue() >= minNumOfOccurrences) {
                resultList.add(new WordOccurrenceItem(
                        (String)pair.getKey(), (Integer) pair.getValue()));
            }
            it.remove();
        }

        return resultList;
    }
}



