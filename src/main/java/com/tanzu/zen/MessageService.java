package com.tanzu.zen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class MessageService {

    private static final Logger LOG = LoggerFactory.getLogger(MessageService.class);

    private final SayingsRepository sayingsRepository;

    public MessageService(SayingsRepository sayingsRepository) {
        this.sayingsRepository = sayingsRepository;
    }

    public String getRandomSaying() {
        List<Saying> sayings = this.getSayings();

        int randomIndex = ThreadLocalRandom.current().nextInt(sayings.size());
        Saying saying = sayings.get(randomIndex);
        LOG.info("random saying nr {}: {}", randomIndex, saying.getText());

        return saying.getText();
    }

    public List<Saying> getSayings() {
        return this.sayingsRepository.findAll();
    }

    @Transactional
    public void save(String saying) {
        this.sayingsRepository.save(new Saying(null, saying));
    }
}
