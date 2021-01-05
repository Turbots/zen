package com.tanzu.zen;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class MessageService {

    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    private final SayingsRepository sayingsRepository;

    public MessageService(SayingsRepository sayingsRepository) {
        this.sayingsRepository = sayingsRepository;
    }

    public String getRandomSaying() {
        List<Saying> sayings = this.getSayings();
        return sayings.get(RANDOM.nextInt(sayings.size())).getText();
    }

    public List<Saying> getSayings() {
        return this.sayingsRepository.findAll();
    }

    @Transactional
    public void save(String saying) {
        this.sayingsRepository.save(new Saying(null, saying));
    }
}
