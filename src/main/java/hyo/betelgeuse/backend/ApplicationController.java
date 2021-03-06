package hyo.betelgeuse.backend;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;


/*
Application Controller handles HTTP requests to the application, using commands such as GET and POST.
*/
@Controller
public class ApplicationController {

    //Logger to debug.
    Logger logger = Logger.getLogger(ApplicationController.class.getName());

    private final int standardMinNumOfOccurrences = 8;

    // Data Access Object (DAO) - connection to database
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private WordOccurrenceCounter wordOccurrenceCounter;


    //Adds one singular article to the database.
    @PostMapping(path="/addArticle") // Map ONLY POST Requests
    public @ResponseBody String addNewArticle (
            @RequestParam String title,
            @RequestParam String newsOutlet,
            @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date publishDate) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        Article article = new Article();
        article.setTitle(title);
        article.setNewsOutlet(newsOutlet);
        article.setPublishDate(publishDate);

        articleRepository.save(article);
        return "Saved";
    }


    //Gets the words as a WordOccurenceItem with a date between user's requested time frame.
    @GetMapping(path = "/getWordsBetweenDates")
    public @ResponseBody List<WordOccurrenceItem> getArticlesBetweenDates(
            @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate) {

        List<Article> articleList = articleRepository.
                findByPublishDate(startDate, endDate);


        List<WordOccurrenceItem> wordOccurrences = wordOccurrenceCounter.
                getWordOccurrences(articleList, standardMinNumOfOccurrences);

        return wordOccurrences;
    }


    @GetMapping(path = "/getWordsBetweenDatesWithNumOfOccurences")
    public @ResponseBody List<WordOccurrenceItem> getArticlesBetweenDatesWithNumOfOccurences(
            @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate,
            @RequestParam Integer minNumOfOccurrences) {

        List<Article> articleList = articleRepository.
                findByPublishDate(startDate, endDate);


        List<WordOccurrenceItem> wordOccurrences = wordOccurrenceCounter.
                getWordOccurrences(articleList, minNumOfOccurrences);
        return wordOccurrences;
    }


    //Gets all articles that is in the database.
    @GetMapping(path="/all")
    public @ResponseBody Iterable<Article> getAllArticles() {
        // This returns a JSON or XML with the users
        return articleRepository.findAll();
    }

    //Get all results from database, as pairs of word with count.
    @GetMapping(path="allPairs")
    public @ResponseBody
    List<WordOccurrenceItem> getAllArticlesOccur(){
        List<Article> articles = new ArrayList<>();
        articleRepository.findAll().forEach(articles::add);
        List<WordOccurrenceItem> wordOccurrences = wordOccurrenceCounter.
                getWordOccurrences(articles, 0);

        return wordOccurrences;
    }


    //Get the x most popular words.
    @GetMapping(path="xMostPopular")
    public @ResponseBody
    List<WordOccurrenceItem> getMostPopular(@RequestParam int x){
        List<Article> articles = new ArrayList<>();
        articleRepository.findAll().forEach(articles::add);
        List<WordOccurrenceItem> wordOccurrences = wordOccurrenceCounter.
                getWordOccurrences(articles, 0);
        List<WordOccurrenceItem> mostPop = wordOccurrenceCounter.getPopularArticles(wordOccurrences, x);

        return mostPop;
    }


    //Get the articles titles withing users time frame.
    @GetMapping(path = "/getTitlesBetweenDates")
    public @ResponseBody List<String> getTitlesBetweenDates(
            @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate) {

        List<Article> articleList = articleRepository.
                findByPublishDate(startDate, endDate);

        List<String> titles = wordOccurrenceCounter.titlesByDate(articleList);
        logger.info("Returning wordoccurrences.");
        return titles;
    }


    //Upload json files through local host.
    @PostMapping(path = "/addArticlesInBulk")
    public String addArticlesInBulkViaJSON(@RequestParam("file") MultipartFile file) {
        JSONParser parser = new JSONParser();

        try {
            InputStream is = file.getInputStream();
            InputStreamReader reader = new InputStreamReader(is);
            JSONArray result = (JSONArray) parser.parse(reader);

            Iterator<Object> iteratorOverDates = result.iterator();

            while(iteratorOverDates.hasNext()) {
                JSONObject currentEntry = (JSONObject) iteratorOverDates.next();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy:MM:dd");
                Date publishDate = simpleDateFormat.parse((String)currentEntry.get("date"));

                String newsOutlet = (String)currentEntry.get("site");

                JSONArray titles = (JSONArray) currentEntry.get("titles");
                Iterator<String> iteratorOverTitles = titles.iterator();

                while(iteratorOverTitles.hasNext()) {
                    Article article = new Article(publishDate, iteratorOverTitles.next(), newsOutlet);
                    articleRepository.save(article);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/all";
    }


    @GetMapping("/allByOutlet")
    public @ResponseBody List<Article> getAllArticlesFromTheOutlet(
            @RequestParam("newsOutlet") String newsOutlet) {
        List<Article> articles = articleRepository.findByNewsOutlet(newsOutlet);
        return articles;
    }



}


/*
Get all results from database (as pairs of title with occurrences)
Get words occurrences in pairs in specific time interval
Get x number of words or x most popular words (as pairs of title with occurrences)
Get all article titles in the time interval.
Get all titles from specific news outlet (or in interval)
If we have different news sections like politics or entertainment, then return these different category names.
Possibly filter out a number of occurrences
Possibly filter out a certain word/title
 */