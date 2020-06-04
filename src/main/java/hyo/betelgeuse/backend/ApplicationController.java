package hyo.betelgeuse.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class ApplicationController {

    // Data Access Object (DAO) - connection to database
    @Autowired
    private ArticleRepository articleRepository;



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

    @PostMapping(path = "/getWordsBetweenDates")
    public @ResponseBody List<WordOccurrenceItem> getArticlesBetweenDates(
            @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate) {

        List<Article> articleList = articleRepository.
                findByPublishDate(startDate, endDate);

        System.out.println(articleList);

        List<WordOccurrenceItem> wordOccurrences = WordOccurrenceCounter.
                getWordOccurrences(articleList);

        return wordOccurrences;
    }


    @GetMapping(path="/all")
    public @ResponseBody
    Iterable<Article> getAllArticles() {
        // This returns a JSON or XML with the users
        return articleRepository.findAll();
    }


    //todo upload a file and save everything to db
    public String addArticlesInBulkViaJSON() {
        return "";
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