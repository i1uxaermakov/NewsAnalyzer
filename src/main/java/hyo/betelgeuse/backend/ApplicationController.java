package hyo.betelgeuse.backend;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.SimpleFormatter;

@Controller
public class ApplicationController {

    // Data Access Object (DAO) - connection to database
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private WordOccurrenceCounter wordOccurrenceCounter;


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

    @GetMapping(path = "/getWordsBetweenDates")
    public @ResponseBody List<WordOccurrenceItem> getArticlesBetweenDates(
            @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate) {

        List<Article> articleList = articleRepository.
                findByPublishDate(startDate, endDate);


        List<WordOccurrenceItem> wordOccurrences = wordOccurrenceCounter.
                getWordOccurrences(articleList);

        return wordOccurrences;
    }


    @GetMapping(path="/all")
    public @ResponseBody Iterable<Article> getAllArticles() {
        // This returns a JSON or XML with the users
        return articleRepository.findAll();
    }





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


}


/*
Saccha
+ Get all results from database (as pairs of word with occurrences)
+ Get x number of words or x most popular words (as pairs of title with occurrences)
    -   x specifiedd by the user
+ Get all article titles in the time interval

Ilya
+ Get all titles from specific news outlet (or in interval)
+ Possibly filter out a number of occurrences
 */