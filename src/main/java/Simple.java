//import required classes and packages
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Simple {
    public static void main(String args[]) {
        if (args.length < 1) {
            System.out.println("Enter the absolute path of the json file as argument when calling this file");
            return;
        }
        // loc is the absolute file path of the json file of interset
        String loc = args[0];
        String result;
        try {
            // read byte data from the Sample.json file and convert it into String
            result = new String(Files.readAllBytes(Paths.get(loc)));
            // store string data into Map by using TypeToken class
            Gson gson = new Gson();
            Type myMap = new TypeToken<HashMap<String, String>>(){}.getType();
            Map<String, String> jsonMap = gson.fromJson(result, myMap);
            Iterator<String> it = jsonMap.keySet().iterator();
            Map<String, String> innerMap = gson.fromJson(it.next(), myMap);
            Iterator<String> it2 = innerMap.keySet().iterator();
            System.out.println(innerMap.get(it2.next()));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}