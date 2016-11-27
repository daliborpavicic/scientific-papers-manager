package rs.ac.uns.ftn.informatika.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "scientific_paper")
public class ScientificPaper {

    Integer id;
    String title;
    String anAbstract;
    String keywords;
    ScientificPaperCategory category;
    Date publishDate;
    String filePath;
    String elasticiId;

    public ScientificPaper() {
    }

    @Id
    @Column(name = "paper_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "paper_title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "paper_abstract")
    public String getAnAbstract() {
        return anAbstract;
    }

    public void setAnAbstract(String anAbstract) {
        this.anAbstract = anAbstract;
    }

    @Column(name = "paper_keywords")
    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ref_category_id")
    public ScientificPaperCategory getCategory() {
        return category;
    }

    public void setCategory(ScientificPaperCategory category) {
        this.category = category;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "paper_publish_date")
    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    @Column(name = "paper_file_path")
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


    @Column(name = "paper_elastic_id")
    public String getElasticiId() {
        return elasticiId;
    }

    public void setElasticiId(String elasticiId) {
        this.elasticiId = elasticiId;
    }
}
