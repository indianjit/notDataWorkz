import java.util.*;
import java.io.IOException;  

import com.google.gson.Gson; 
import com.google.gson.reflect.*;
import java.lang.reflect.Type;
import com.google.gson.GsonBuilder; 
import com.google.gson.TypeAdapter; 
import com.google.gson.stream.JsonReader; 
import com.google.gson.stream.JsonToken; 
import com.google.gson.stream.JsonWriter;  
import java.nio.file.Files;
import java.nio.file.Paths;


public class miniProj2 {
    public static void main(String[] args) {
        GsonBuilder builder = new GsonBuilder();
        Type mapType = new TypeToken<HashMap<String, Object>>(){}.getType(); 
        builder.registerTypeAdapter(mapType, new myAdapter()); 
        Gson gson = builder.create();


        String loc = "src\\main\\java\\sample.json";
        String result;

        try {
            // read byte data from the Sample.json file and convert it into String
            result = new String(Files.readAllBytes(Paths.get(loc)));
            
            Map<String, String> jsonMap = gson.fromJson(result, mapType);
            System.out.println(jsonMap);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
} 

class myAdapter extends TypeAdapter<HashMap<String, String>> {
    @Override
    public HashMap<String, String> read(JsonReader reader) throws IOException {
        HashMap<String, String> myMap = new HashMap<String, String>();
        reader.beginObject();
        String key = null;
        String value = null;
        while (reader.hasNext()) {
            JsonToken token = reader.peek();
            if (token.equals(JsonToken.NAME)) {
                // get the current token
                key = reader.nextName();
            }
            // everything below this point is the case where the token is a value
            else if (token.equals(JsonToken.STRING) || token.equals(JsonToken.NUMBER)) {
                // we do the bare minimun no modification needed
                value = reader.nextString();
            }
            else if (token.equals(JsonToken.BEGIN_ARRAY)) {
                // somehow get that array
                // go through the elements of the array
                // construct the correct string
                // add that string the right value of the map and sip martinis
                if (token.equals(JsonToken.BEGIN_ARRAY)) {
                    reader.beginArray();
                }
                List<String> myList = new ArrayList<String>();
                while (!reader.peek().equals(JsonToken.END_ARRAY)){
                    myList.add(reader.nextString());
                }
                String myString = "";
                for (int i = 0; i < myList.size(); i++) {
                    myString = myString + myList.get(i);
                    if (i >= 0 && i < (myList.size() - 1)) {
                        myString += ", ";
                    }
                }
                if (reader.peek().equals(JsonToken.END_ARRAY)) {
                    reader.endArray();
                }
                value = myString;
            } else if (token.equals(JsonToken.BEGIN_OBJECT)) {
                // we be reading a dictionary
                reader.beginObject();
                List<String> myList = new ArrayList<String>();
                while (!reader.peek().equals(JsonToken.END_OBJECT)){
                    String valueKey = reader.nextName().toString();
                    String valueValue = reader.nextString();
                    myList.add(valueKey + ": " + valueValue);
                }
                String myString = "";
                for (int i = 0; i < myList.size(); i++) {
                    myString = myString + myList.get(i);
                    if (i >= 0 && i < (myList.size() - 1)) {
                        myString += ", ";
                    }
                }
                if (reader.peek().equals(JsonToken.END_OBJECT)) {
                    reader.endObject();
                }
                value = myString;
            }

            myMap.put(key, value);
        }
        if (reader.peek().equals(JsonToken.END_OBJECT)) {
            reader.endObject();}
        return myMap;
    }

    @Override
    public void write(JsonWriter writer, HashMap<String, String> myMap) throws IOException {

    }

}