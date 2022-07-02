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

    private String format(List<String> myList) {
        String myString = "";
        for (int i = 0; i < myList.size(); i++) {
            myString = myString + myList.get(i);
            if (i >= 0 && i < (myList.size() - 1)) {
                myString += ", ";
            }
        }
        return myString;
    }


    @Override
    public HashMap<String, String> read(JsonReader reader) throws IOException {
        HashMap<String, String> myMap = new HashMap<String, String>();
        reader.beginObject();
        String key = null;
        String value = null;
        while (reader.hasNext()) {
            JsonToken nextToken = reader.peek();
            if (nextToken.equals(JsonToken.NAME)) {
                // get the current token
                key = reader.nextName();
            }

            // everything below this point is the case where the token is a value
            else if (nextToken.equals(JsonToken.STRING) || nextToken.equals(JsonToken.NUMBER)) {
                // we do the bare minimun no modification needed
                value = reader.nextString();
            }
            
            
            else if (nextToken.equals(JsonToken.BEGIN_ARRAY)) {
                List<String> myList = new ArrayList<String>();
                
                // go through the elements of the array and add them to a list
                reader.beginArray();
                while (!reader.peek().equals(JsonToken.END_ARRAY)){
                    myList.add(reader.nextString());
                }
                reader.endArray();

                // construct the correct string from the list of elements and set it value
                value = format(myList);
            } else if (nextToken.equals(JsonToken.BEGIN_OBJECT)) {
                List<String> myList = new ArrayList<String>();

                // we be reading a dictionary
                reader.beginObject();
                while (!reader.peek().equals(JsonToken.END_OBJECT)){
                    String valueKey = reader.nextName().toString();
                    String valueValue = reader.nextString();
                    myList.add(valueKey + ": " + valueValue);
                }
                reader.endObject();

                value = format(myList);
            }

            myMap.put(key, value);
        }
        reader.endObject();
        return myMap;
    }

    @Override
    public void write(JsonWriter writer, HashMap<String, String> myMap) throws IOException {

    }

}