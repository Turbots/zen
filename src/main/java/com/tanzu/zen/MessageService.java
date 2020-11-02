package com.tanzu.zen;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class MessageService {

    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    public String getRandomSaying() {
        List<String> sayings = List.of(
                "The temptation to give up is strongest just before victory.",
                "The goal in life is to die young, but to do as late as possible.",
                "Don't speak if it doesn't improve on silence.",
                "A thousand-mile journey begins with just one step.",
                "A strong man overcomes an obstacle, a wise man goes the whole way.",
                "Don’t be afraid to go slowly. Be afraid of stopping.",
                "A hut full of laughter is richer than a palace full of sadness.",
                "A good teacher opens the door for you, but you must enter the room by yourself."
        );

        return sayings.get(RANDOM.nextInt(sayings.size()));
    }

}
