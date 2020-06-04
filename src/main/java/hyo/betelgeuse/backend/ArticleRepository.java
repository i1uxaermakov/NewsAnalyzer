package hyo.betelgeuse.backend;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

public interface ArticleRepository extends CrudRepository<Article, Integer> {

    @Query("SELECT a from Article a where a.publishDate " +
            "between :startDate and :endDate")
    List<Article> findByPublishDate(
            @Temporal(TemporalType.DATE) @Param("startDate") Date startDate,
            @Temporal(TemporalType.DATE) @Param("endDate") Date endDate);
}