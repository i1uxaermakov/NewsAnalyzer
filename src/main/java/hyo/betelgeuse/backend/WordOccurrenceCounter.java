package hyo.betelgeuse.backend;

import javax.annotation.Resource;
import java.util.*;
import java.util.logging.Logger;


public class WordOccurrenceCounter {

    Logger logger = Logger.getLogger(WordOccurrenceCounter.class.getName());

    @Resource(name = "stopWordsList")
    private Set<String> stopWordsList;


    public void setStopWordsList(Set<String> stopWordsList) {
        this.stopWordsList = stopWordsList;
    }

    /*
    Counts each word and sends it to another method to get the total list of words with it's occurrences.
     */
    public List<WordOccurrenceItem> getWordOccurrences(
            List<Article> articleList, int minNumOfOccurrences) {
        Map<String, Integer> wordMap = new HashMap<String, Integer>();

        for(Article article: articleList) {
            Scanner stringScanner = new Scanner(article.getTitle());

            while(stringScanner.hasNext()) {
                //Look through the title at each word.
                String tokenWord = stringScanner.next();

                if (!stopWordsList.contains(tokenWord)) {
                    //Removed punctuation and lowercase.
                    tokenWord = tokenWord.
                            replaceAll("\\p{Punct}", "").
                            toLowerCase().
                            trim();

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

    /*
    Returns a list of words with its occurrences. Limited by a minimum number of occurr. it must have.
     */
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


    /*
    Returns x most popular words found in the word occurrence list.
     */
    List<WordOccurrenceItem> getPopularArticles(List<WordOccurrenceItem> wordList, int x){
        List<WordOccurrenceItem> popList = wordList;
        popList.sort(Comparator.comparingInt(WordOccurrenceItem::getCount));

        //return x most popular
        //3 cases, x less than length, x greater than length, x is 0 or negative
        if(x <= 0 || x > wordList.size()){
            logger.info("returned original");
            return wordList;
        }
        else if(x < wordList.size()){
            logger.info("returns sublist");
            popList = popList.subList(wordList.size()-x, wordList.size());
            logger.info(popList.toString());
            return popList;
        }

        return popList;
    }


    /*
    Returns the list of only article titles that is within a time frame.
     */
    List<String> titlesByDate(List<Article> articles){
        List<String> titles = new ArrayList<>();
        articles.forEach(a -> titles.add(a.getTitle()));
        logger.info("Titles list: "+ titles.toString());
        return titles;
    }
}
