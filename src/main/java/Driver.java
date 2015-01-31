import com.github.jreddit.utils.restclient.*;
import com.github.jreddit.entity.User;
import com.github.jreddit.message.*;
import java.util.*;
/**
 * Created by marc_leef on 1/31/15.
 */
public class Driver {
    public static void main(String[] args) {
        // Testing API
        // Initialize REST Client
        RestClient restClient = new HttpRestClient();
        restClient.setUserAgent("bot/1.0 by name");

        // Connect the user
        User user = new User(restClient, "horseradisher", "jordan");
        try {
            user.connect();
            List<Message> inbox = new Messages(restClient).getMessages(user, 10, MessageType.INBOX);
            for(Message M : inbox) {
                System.out.println(M.getBody());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
