package ibf2022.paf.assessment.server.utils;

import java.util.UUID;

public class utils {

    public static String generateUUID(int numOfChar) {
        return UUID.randomUUID().toString().substring(0, numOfChar);
    }

}
