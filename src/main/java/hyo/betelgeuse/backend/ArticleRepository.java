package hyo.betelgeuse.backend;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;


/*
Interface Repository that holds records the user makes.
 */
public interface ArticleRepository extends CrudRepository<Article, Integer> {

    @Query("SELECT a from Article a where a.publishDate " +
            "between :startDate and :endDate")
    List<Article> findByPublishDate(
            @Temporal(TemporalType.DATE) @Param("startDate") Date startDate,
            @Temporal(TemporalType.DATE) @Param("endDate") Date endDate);

    @Query("SELECT a from Article a where a.newsOutlet = :outlet")
    List<Article> findByNewsOutlet(@Param("outlet") String outlet);


}
