import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    static User user;
    static Message message;
    static ArrayList<Message> messages = new ArrayList();

    public static void main(String[] args) {
        Spark.staticFileLocation("/public");
        Spark.init();
        Spark.get("/",
                ((request, response) -> {
                    HashMap users = new HashMap();
                    if (user == null) {
                        return new ModelAndView(users, "/index.html");
                    } else {
                        users.put("name", user.getName());
                        users.put("messages", messages);
                        return new ModelAndView(users, "messages.html");
                    }

                }),
                new MustacheTemplateEngine()
        );
        Spark.post(
                "/create-user",
                ((request, response) -> {
                    String name = request.queryParams("name");
                    user = new User(name);
                    response.redirect("/");

                    return "";
                })
        );

        Spark.post(
                "/create-messages",
                ((request, response) -> {
                    String userMessage = request.queryParams("message");
                    message = new Message(userMessage);
                    messages.add(message);

                    response.redirect("/");

                    return "";
                })
        );

    }
}
