package hyo.betelgeuse.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.util.*;

public class WordOccurrenceCounter {

    @Resource(name = "stopWordsList")
    private Set<String> stopWordsList;


    public void setStopWordsList(Set<String> stopWordsList) {
        this.stopWordsList = stopWordsList;
    }

    public List<WordOccurrenceItem> getWordOccurrences(List<Article> articleList) {
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

                    if(tokenWord.length() > 6) {
                        tokenWord = tokenWord.substring(0,6);
                    }
                    if (tokenWord.length() != 0) {
                        int count = wordMap.containsKey(tokenWord) ?
                                wordMap.get(tokenWord) : 0;
                        wordMap.put(tokenWord, count + 1);
                    }
                }
            }
        }

        return convertToWordOccurrencesList(wordMap);
    }

    private List<WordOccurrenceItem> convertToWordOccurrencesList(
            Map<String, Integer> wordMap) {
        List<WordOccurrenceItem> resultList = new ArrayList<WordOccurrenceItem>();

        Iterator it = wordMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if((Integer)pair.getValue() >= 8) {
                resultList.add(new WordOccurrenceItem(
                        (String)pair.getKey(), (Integer) pair.getValue()));
            }
            it.remove();
        }

        return resultList;
    }
}
