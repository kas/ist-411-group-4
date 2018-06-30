package newpackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

@Path("events")
public class EventsResource {
    final String eventsFilename = "events.txt";

    @Context
    private UriInfo context;

    private void recreateEventsFile(String eventsString) throws FileNotFoundException, UnsupportedEncodingException {
        // https://www.mkyong.com/java/how-to-delete-file-in-java/
        File file = new File(eventsFilename);
        try {
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // https://www.mkyong.com/java/how-to-create-a-file-in-java/
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // https://stackoverflow.com/a/3598792
        if (eventsString != null && !eventsString.isEmpty()) {
            PrintWriter writer = new PrintWriter(eventsFilename, "UTF-8");
            writer.println(eventsString);
            writer.close();
        }
    }

    public EventsResource() throws FileNotFoundException, UnsupportedEncodingException {
        // delete existing file and create new file so we can have a fresh start
        recreateEventsFile(null);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getEvents() throws FileNotFoundException, IOException {
        String eventsString = null;

        // https://stackoverflow.com/a/4716623
        BufferedReader bufferedReader = new BufferedReader(new FileReader(eventsFilename));
        try {
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                stringBuilder.append(System.lineSeparator());
                line = bufferedReader.readLine();
            }

            eventsString = stringBuilder.toString();
        } finally {
            bufferedReader.close();
        }

        return eventsString;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String putEvent(String oldEvent, String newEvent) throws FileNotFoundException, IOException {
        // iterate over lines of existing file, adding those lines to a new string. when
        // we reach oldEvent, instead of adding oldEvent to the new string, add newEvent
        // to the new string. save the new string.

        String newEventsString = null;

        // https://stackoverflow.com/a/4716623
        BufferedReader bufferedReader = new BufferedReader(new FileReader(eventsFilename));
        try {
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                if (line != oldEvent) {
                    stringBuilder.append(line);
                } else {
                    stringBuilder.append(newEvent);
                }

                stringBuilder.append(System.lineSeparator());

                line = bufferedReader.readLine();
            }

            newEventsString = stringBuilder.toString();
        } finally {
            bufferedReader.close();
        }

        recreateEventsFile(newEventsString);

        return newEvent;
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteEvent(String event) throws FileNotFoundException, IOException {
        // iterate over lines of existing file, adding those lines to a new string. when
        // we reach oldEvent, do not add oldEvent to the new string. save the new
        // string.

        String newEventsString = null;

        // https://stackoverflow.com/a/4716623
        BufferedReader bufferedReader = new BufferedReader(new FileReader(eventsFilename));
        try {
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                if (line != event) {
                    stringBuilder.append(line);
                } else {
                    continue;
                }

                stringBuilder.append(System.lineSeparator());

                line = bufferedReader.readLine();
            }

            newEventsString = stringBuilder.toString();
        } finally {
            bufferedReader.close();
        }

        recreateEventsFile(newEventsString);

        return event;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String postEvent(String event) {
        // add a new event (on its own line) at the end of the events file
        return "postEvent";
    }
}
