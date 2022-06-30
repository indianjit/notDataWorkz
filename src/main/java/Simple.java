//import required classes and packages
import java.util.Map;
import com.google.gson.Gson;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Simple {
    public static void main(String args[]) {
        // loc is the absolute file path of the json file of interset
        String loc = "src\\main\\java\\sample.json";
        String result;
        try {
            // read byte data from the Sample.json file and convert it into String
            result = new String(Files.readAllBytes(Paths.get(loc)));
            // store string data into Map by using TypeToken class
            System.out.println(result);
            
            Gson gson = new Gson();
            Map jsonMap = gson.fromJson(result, Map.class);
            System.out.println(jsonMap);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}