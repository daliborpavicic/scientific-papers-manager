package rs.ac.uns.ftn.informatika.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.dto.NewScientificPaper;
import rs.ac.uns.ftn.informatika.repository.ScientificPaperRepository;

@Service
public class ScientificPaperService {

    private ScientificPaperRepository scientificPaperRepository;

    @Autowired
    public ScientificPaperService(ScientificPaperRepository scientificPaperRepository) {
        this.scientificPaperRepository = scientificPaperRepository;
    }

    public void save(NewScientificPaper newScientificPaper) {
        // todo: add dto to an Elastic search index, map it to domain and save data to DB
    }
}
