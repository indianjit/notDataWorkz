//import required classes and packages
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Simple {
    public static void main(String args[]) {
        // create variable loc that store location of the myJSON.json file
        String loc = "C:\\Users\\19254\\Desktop\\notDataWorkz\\src\\main\\java\\sample.json";
        String result;
        try {
            // read byte data from the Sample.json file and convert it into String
            result = new String(Files.readAllBytes(Paths.get(loc)));
            // store string data into Map by using TypeToken class
            Gson gson = new Gson();
            Map<String, Object> JsonMap = gson.fromJson(result, new TypeToken<HashMap<String, Object>>(){}.getType());
            Map<String, Object> innerMap = (Map<String, Object>) JsonMap.get("good");
            System.out.println(innerMap.get("morning"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}