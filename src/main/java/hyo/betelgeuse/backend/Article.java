package hyo.betelgeuse.backend;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Date publishDate;
    private String title;
    private String newsOutlet;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getNewsOutlet() {
        return newsOutlet;
    }

    public void setNewsOutlet(String newsOutlet) {
        this.newsOutlet = newsOutlet;
    }

    protected Article() {
    }

    public Article(Date publishDate, String title, String newsOutlet) {
        this.publishDate = publishDate;
        this.title = title;
        this.newsOutlet = newsOutlet;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", publishDate=" + publishDate +
                ", title='" + title + '\'' +
                ", newsOutlet='" + newsOutlet + '\'' +
                '}';
    }
}
