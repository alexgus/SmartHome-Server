/**
 * 
 */
package fr.utbm.to52.smarthome.model.calendar;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import net.fortuna.ical4j.filter.Filter;
import net.fortuna.ical4j.model.ComponentList;

/**
 * @author Alexandre Guyon
 *
 */
public class GoogleCalendar implements ICalendar {

	/** Application name. */
    private static final String APPLICATION_NAME = "smart";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
        System.getProperty("user.home"), ".smart");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
        JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes required by this quickstart. */
    private static final List<String> SCOPES =
        Arrays.asList(CalendarScopes.CALENDAR_READONLY);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    @SuppressWarnings({ "resource", "javadoc" })
	public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in =
            GoogleCalendar.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets =
            GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(
            flow, new LocalServerReceiver()).authorize("user");
        System.out.println(
                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    /**
     * Build and return an authorized Calendar client service.
     * @return an authorized Calendar client service
     * @throws IOException
     */
    @SuppressWarnings("javadoc")
	public static com.google.api.services.calendar.Calendar
        getCalendarService() throws IOException {
        Credential credential = authorize();
        return new com.google.api.services.calendar.Calendar.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    /**
     * Test logging on google's server for this API
     * @param args not used
     * @throws IOException
     */
    @SuppressWarnings({ "javadoc", "boxing" })
	public static void main(String[] args) throws IOException {
        // Build a new authorized API client service.
        // Note: Do not confuse this class with the
        //   com.google.api.services.calendar.model.Calendar class.
        com.google.api.services.calendar.Calendar service =
            getCalendarService();

        // List the next 10 events from the primary calendar.
        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = service.events().list("primary")
            .setMaxResults(10)
            .setTimeMin(now)
            .setOrderBy("startTime")
            .setSingleEvents(true)
            .execute();
        List<Event> items = events.getItems();
        if (items.size() == 0) {
            System.out.println("No upcoming events found.");
        } else {
            System.out.println("Upcoming events");
            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    start = event.getStart().getDate();
                }
                System.out.printf("%s (%s)\n", event.getSummary(), start);
            }
        }
    }

	
	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.calendar.ICalendar#load()
	 */
	@Override
	public void load() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.calendar.ICalendar#Add(java.util.Date, java.util.Date, java.lang.String)
	 */
	@Override
	public UUID add(Date begin, Date end, String name) {
		return null;
	}

	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.calendar.ICalendar#Add(java.util.Date, java.util.Date, java.lang.String, java.lang.String)
	 */
	@Override
	public UUID add(Date begin, Date end, String name, String Description) {
		return null;
	}

	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.calendar.ICalendar#Add(java.util.Date, java.util.Date, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public UUID add(Date begin, Date end, String name, String Description, String Location) {
		return null;
	}

	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.calendar.ICalendar#remove(java.util.UUID)
	 */
	@Override
	public boolean remove(UUID uuid) {
		return false;
	}

	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.calendar.ICalendar#get(net.fortuna.ical4j.filter.Filter)
	 */
	@Override
	public ComponentList get(Filter r) {
		return null;
	}

	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.calendar.ICalendar#get(java.util.Date, java.util.Date)
	 */
	@Override
	public ComponentList get(Date begin, Date end) {
		return null;
	}

	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.calendar.ICalendar#get(java.lang.String)
	 */
	@Override
	public ComponentList get(String name) {
		return null;
	}

	/* (non-Javadoc)
	 * @see fr.utbm.to52.smarthome.calendar.ICalendar#toICal()
	 */
	@Override
	public String toICal() {
		return null;
	}

}
