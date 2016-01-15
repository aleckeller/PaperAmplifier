import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.simple.*;

public class SendRequest {
    final String endpoint = "http://thesaurus.altervista.org/thesaurus/v1";
    protected static ArrayList<String> thesaurusList = new ArrayList<String>();
    private static String thesaurus;

    public SendRequest(String word, String language, String key, String output) {
        try {
            URL serverAddress = new URL(endpoint + "?word="
                    + URLEncoder.encode(word, "UTF-8") + "&language="
                    + language + "&key=" + key + "&output=" + output);
            HttpURLConnection connection = (HttpURLConnection) serverAddress
                    .openConnection();
            connection.connect();
            int rc = connection.getResponseCode();
            if (rc == 200) {
                String line = null;
                BufferedReader br = new BufferedReader(
                        new java.io.InputStreamReader(
                                connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    sb.append(line + '\n');
                    JSONObject obj = (JSONObject) JSONValue.parse(sb
                            .toString());
                    JSONArray array = (JSONArray) obj.get("response");
                    for (int i = 0; i < array.size(); i++) {
                        JSONObject list = (JSONObject) ((JSONObject) array
                                .get(i)).get("list");
                        thesaurus = (list.get("category") + ":" + list
                                .get("synonyms"));
                    }
                }
                thesaurusList.add(thesaurus);
            }
            else {
                thesaurus = ("No suggestions");
                thesaurusList.add(thesaurus);
            }
            connection.disconnect();
        }
        catch (java.net.MalformedURLException e) {
            e.printStackTrace();
        }
        catch (java.net.ProtocolException e) {
            e.printStackTrace();
        }
        catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getList() {
        return thesaurusList;
    }

    public static void reset() {
        thesaurusList.clear();
        thesaurus = "";
    }

}
