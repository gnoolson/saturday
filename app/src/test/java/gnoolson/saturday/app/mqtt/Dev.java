package gnoolson.saturday.app.mqtt;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;

public class Dev {


//    @Test
    void random() {

        int randomNum = ThreadLocalRandom.current().nextInt(1, 10_001);
        for (int i = 0; i < 1_000; i++) {
            if (randomNum == ThreadLocalRandom.current().nextInt(1, 10_001)) {
                System.out.println(i + 1);
            }
        }

    }

}
