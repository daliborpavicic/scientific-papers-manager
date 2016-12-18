package rs.ac.uns.ftn.informatika.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "scientific_paper")
public class ScientificPaper {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    String title;

    String anAbstract;

    String keywords;

    @ManyToOne(fetch = FetchType.LAZY)
    ScientificPaperCategory category;

    @Temporal(TemporalType.DATE)
    Date publishDate;

    String filePath;

    String elasticiId;

    public ScientificPaper() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnAbstract() {
        return anAbstract;
    }

    public void setAnAbstract(String anAbstract) {
        this.anAbstract = anAbstract;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public ScientificPaperCategory getCategory() {
        return category;
    }

    public void setCategory(ScientificPaperCategory category) {
        this.category = category;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


    public String getElasticiId() {
        return elasticiId;
    }

    public void setElasticiId(String elasticiId) {
        this.elasticiId = elasticiId;
    }
}
