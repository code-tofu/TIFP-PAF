public static synchronized String generateId(int numOfChar){
        SecureRandom sr = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        while(sb.length() < numOfChar) {
            sb.append(Integer.toHexString(sr.nextInt()));
        }
        
        return sb.toString().substring(0, numOfChar);
    }


public static String generateUUID(int numOfChar){ 
    return UUID.randomUUID().toString().substring(0,numOfChar);
}


public class IOUtil {
    private static final Logger logger = LoggerFactory.getLogger(IOUtil.class);

    //  Helper method to create directory on the local machine
    public static void createDir(String path){
        File dir = new File(path);
        boolean isDirExist = dir.mkdir();
        logger.info("Is Dir exist ?" + isDirExist);
        if(isDirExist) {å
            String osName = System.getProperty("os.name");
            if(!osName.contains("Windows")){
                //to fix issues with mac directory permissions
                String permission = "rwxrwx---";
                Set<PosixFilePermission>  permissions = 
                                PosixFilePermissions.fromString(permission);
                try {
                    Files.setPosixFilePermissions(dir.toPath(), permissions);
                } catch (IOException e) {
                    logger.error("Error", e);
                }
            }
        }
    }
}

    public static JsonObject getJSONObj(String jsonString){
        StringReader sr = new StringReader(jsonString.toString());
        JsonReader jsr = Json.createReader(sr);
        return (JsonObject)jsr.read();
    }


    public static JsonObject getJSONObj(String jsonString){
        StringReader sr = new StringReader(jsonString.toString());
        JsonReader jsr = Json.createReader(sr);
        return (JsonObject)jsr.read();
    }

    // Age calculator, returns 0 if error.
    public int calculateAge(LocalDate dateOfBirth) {
        int age = 0;
        if(dateOfBirth != null) age = Period.between(dateOfBirth, LocalDate.now()).getYears();
        return age;
    }