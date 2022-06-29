import java.util.HashMap;
import com.google.gson.reflect.TypeToken;

public class test {
    public static void main(String[] args) {
        HashMap<String, String> mapClass = (HashMap<String, String>) new TypeToken<HashMap<String, String>>(){}.getType();

    }
    
}
