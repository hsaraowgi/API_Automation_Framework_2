/**
 * Created by hsaraowgi on 7/27/17.
 */

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;


public class delete
{
    public static void main(String[] args)
            throws Exception
    {
        if(args[0].toLowerCase().equals("reg"))
            DeleteAllReg(args[1],args[2],args[3]);
        else if (args[0].toLowerCase().equals("session"))
            DeleteAllSessions(args[1],args[2],args[3]);
        else if (args[0].toLowerCase().equals("exhibitor"))
            DeleteAllExhibitors(args[1],args[2],args[3]);
        else if (args[0].toLowerCase().equals("speaker"))
            DeleteAllSpeaker(args[1],args[2],args[3]);
        else if (args[0].toLowerCase().equals("all")) {
            DeleteAllSpeaker(args[1], args[2], args[3]);
            DeleteAllExhibitors(args[1], args[2], args[3]);
            DeleteAllSessions(args[1], args[2], args[3]);
            DeleteAllReg(args[1], args[2], args[3]);
        }
        else if (args[0].toLowerCase().equals("emt"))
            DeleteAllEmt(args[1],args[2],args[3]);
        System.out.println("All Records Deleted");
    }

    private static void DeleteAllReg(String Event,String env,String key)
            throws Exception
    {
        System.setProperty("AccessKey", key);
        System.setProperty("Event", Event);
        if(env.toLowerCase().equals("staging"))
            System.setProperty("TEST_URL", "api.staging.crowdcompass.com");
        else if(env.toLowerCase().equals("qa"))
            System.setProperty("TEST_URL", "api.qa.crowdcompass.com");

        int ticketscount = 0;
        do
        {
            int loop = 0;
            try
            {
                URL url = new URL("https://" + System.getProperty("TEST_URL") + "/v3/events/" + System.getProperty("Event") + "/invitations/");
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("Access-Token", System.getProperty("AccessKey"));
                conn.setDoOutput(true);
                int resp = conn.getResponseCode();
                if (resp != 200) {
                    throw new RuntimeException(" HTTP error code : " +
                            resp);
                }
                Scanner scan = new Scanner(conn.getInputStream());
                String entireResponse = new String();
                while (scan.hasNext()) {
                    entireResponse = entireResponse + scan.nextLine();
                }
                scan.close();
                JSONObject json = new JSONObject(entireResponse);
                ticketscount = json.getJSONArray("invitations").length();
                for (loop = 0; loop < ticketscount; loop++) {
                    try
                    {
                        URL url1 = new URL("https://" + System.getProperty("TEST_URL") + "/v3/events/" + System.getProperty("Event") + "/invitations/" + json.getJSONArray("invitations").getJSONObject(loop).getString("ref"));

                        HttpURLConnection conn1 = (HttpURLConnection)url1.openConnection();
                        conn1.setRequestMethod("DELETE");
                        conn1.setRequestProperty("Accept", "application/json");
                        conn1.setRequestProperty("Access-Token", System.getProperty("AccessKey"));
                        conn1.setDoOutput(true);
                        if (conn1.getResponseCode() != 204) {
                            System.out.println("There was probably a confirmed record so there would be an error, but dont worry, life is still good");
                        }
                        conn1.disconnect();
                    }
                    catch (MalformedURLException e)
                    {
                        e.printStackTrace();
                    }
                }
                conn.disconnect();
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
        } while (ticketscount == 100);
    }

    private static void DeleteAllEmt(String Event,String env,String key)
            throws Exception
    {
        System.setProperty("AccessKey", key);
        System.setProperty("Event", Event);
        System.setProperty("TEST_URL", "http://"+System.getProperty("Event")+"/restapi/registration");

        int ticketscount = 0;
        do
        {
            int loop = 0;
            try
            {
                URL url = new URL(System.getProperty("TEST_URL"));
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("Authorization", "Basic "+System.getProperty("AccessKey"));
                conn.setDoOutput(true);
                int resp = conn.getResponseCode();
                if (resp != 200) {
                    throw new RuntimeException(" HTTP error code : " +
                            resp);
                }
                Scanner scan = new Scanner(conn.getInputStream());
                String entireResponse = new String();
                while (scan.hasNext()) {
                    entireResponse = entireResponse + scan.nextLine();
                }
                scan.close();
                JSONObject json = new JSONObject(entireResponse);
                ticketscount = json.getJSONArray("registrantList").length();
                for (loop = 0; loop < ticketscount; loop++) {
                    try
                    {
                        URL url1 = new URL(System.getProperty("TEST_URL")+"/"+json.getJSONArray("registrantList").getJSONObject(loop).getString("id"));

                        HttpURLConnection conn1 = (HttpURLConnection)url1.openConnection();
                        conn1.setRequestMethod("DELETE");
                        conn1.setRequestProperty("Accept", "application/json");
                        conn1.setRequestProperty("Authorization", "Basic "+System.getProperty("AccessKey"));
                        conn1.setDoOutput(true);
                        if (conn1.getResponseCode() != 200) {
                            System.out.println("There was probably a confirmed record so there would be an error, but dont worry, life is still good");
                        }
                        conn1.disconnect();
                    }
                    catch (MalformedURLException e)
                    {
                        e.printStackTrace();
                    }
                }
                conn.disconnect();
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
        } while (ticketscount == 100);
    }

    private static void DeleteAllSessions(String Event,String env,String key)
            throws Exception
    {
        if(env.toLowerCase().equals("staging"))
            System.setProperty("TEST_URL", "api.staging.crowdcompass.com");
        else if(env.toLowerCase().equals("qa"))
            System.setProperty("TEST_URL", "api.qa.crowdcompass.com");
        System.setProperty("AccessKey", key);
        System.setProperty("Event", Event);
        int ticketscount = 0;
        do
        {
            int loop = 0;
            try
            {
                URL url = new URL("https://" + System.getProperty("TEST_URL") + "/v3/events/" + System.getProperty("Event") + "/activities/");

                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("Access-Token", System.getProperty("AccessKey"));
                conn.setDoOutput(true);
                if (conn.getResponseCode() != 200) {
                    throw new RuntimeException(" HTTP error code : " +
                            conn.getResponseCode());
                }
                Scanner scan = new Scanner(conn.getInputStream());
                String entireResponse = new String();
                while (scan.hasNext()) {
                    entireResponse = entireResponse + scan.nextLine();
                }
                scan.close();
                JSONObject json = new JSONObject(entireResponse);
                ticketscount = json.getJSONArray("activities").length();
                for (loop = 0; loop < ticketscount; loop++) {
                    try
                    {
                        URL url1 = new URL("https://" + System.getProperty("TEST_URL") + "/v3/events/" + System.getProperty("Event") + "/activities/" + json.getJSONArray("activities").getJSONObject(loop).getString("ref"));

                        HttpURLConnection conn1 = (HttpURLConnection)url1.openConnection();
                        conn1.setRequestMethod("DELETE");
                        conn1.setRequestProperty("Accept", "application/json");
                        conn1.setRequestProperty("Access-Token", System.getProperty("AccessKey"));
                        conn1.setDoOutput(true);
                        if (conn1.getResponseCode() != 204) {
                            throw new RuntimeException(" HTTP error code : " +
                                    conn.getResponseCode());
                        }
                        conn1.disconnect();
                    }
                    catch (MalformedURLException e)
                    {
                        e.printStackTrace();
                    }
                }
                conn.disconnect();
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
        } while (ticketscount == 100);
    }

    private static void DeleteAllExhibitors(String Event,String env,String key)
            throws Exception
    {
        if(env.toLowerCase().equals("staging"))
            System.setProperty("TEST_URL", "api.staging.crowdcompass.com");
        else if(env.toLowerCase().equals("qa"))
            System.setProperty("TEST_URL", "api.qa.crowdcompass.com");
        System.setProperty("AccessKey", key);
        System.setProperty("Event", Event);
        int ticketscount = 0;
        do
        {
            int loop = 0;
            try
            {
                URL url = new URL("https://" + System.getProperty("TEST_URL") + "/v3/events/" + System.getProperty("Event") + "/organizations/");

                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("Access-Token", System.getProperty("AccessKey"));
                conn.setDoOutput(true);
                if (conn.getResponseCode() != 200) {
                    throw new RuntimeException(" HTTP error code : " +
                            conn.getResponseCode());
                }
                Scanner scan = new Scanner(conn.getInputStream());
                String entireResponse = new String();
                while (scan.hasNext()) {
                    entireResponse = entireResponse + scan.nextLine();
                }
                scan.close();
                JSONObject json = new JSONObject(entireResponse);
                ticketscount = json.getJSONArray("organizations").length();
                for (loop = 0; loop < ticketscount; loop++) {
                    try
                    {
                        URL url1 = new URL("https://" + System.getProperty("TEST_URL") + "/v3/events/" + System.getProperty("Event") + "/organizations/" + json.getJSONArray("organizations").getJSONObject(loop).getString("ref"));

                        HttpURLConnection conn1 = (HttpURLConnection)url1.openConnection();
                        conn1.setRequestMethod("DELETE");
                        conn1.setRequestProperty("Accept", "application/json");
                        conn1.setRequestProperty("Access-Token", System.getProperty("AccessKey"));
                        conn1.setDoOutput(true);
                        if (conn1.getResponseCode() != 204) {
                            throw new RuntimeException(" HTTP error code : " +
                                    conn.getResponseCode());
                        }
                        conn1.disconnect();
                    }
                    catch (MalformedURLException e)
                    {
                        e.printStackTrace();
                    }
                }
                conn.disconnect();
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
        } while (ticketscount == 100);
    }

    private static void DeleteAllSpeaker(String Event,String env,String key)
            throws Exception
    {
        if(env.toLowerCase().equals("staging"))
            System.setProperty("TEST_URL", "api.staging.crowdcompass.com");
        else if(env.toLowerCase().equals("qa"))
            System.setProperty("TEST_URL", "api.qa.crowdcompass.com");
        System.setProperty("AccessKey", key);
        System.setProperty("Event", Event);
        int ticketscount = 0;
        do
        {
            int loop = 0;
            try
            {
                URL url = new URL("https://" + System.getProperty("TEST_URL") + "/v3/events/" + System.getProperty("Event") + "/people/");

                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("Access-Token", System.getProperty("AccessKey"));
                conn.setDoOutput(true);
                if (conn.getResponseCode() != 200) {
                    throw new RuntimeException(" HTTP error code : " +
                            conn.getResponseCode());
                }
                Scanner scan = new Scanner(conn.getInputStream());
                String entireResponse = new String();
                while (scan.hasNext()) {
                    entireResponse = entireResponse + scan.nextLine();
                }
                scan.close();
                JSONObject json = new JSONObject(entireResponse);
                ticketscount = json.getJSONArray("people").length();
                for (loop = 0; loop < ticketscount; loop++) {
                    try
                    {
                        URL url1 = new URL("https://" + System.getProperty("TEST_URL") + "/v3/events/" + System.getProperty("Event") + "/people/" + json.getJSONArray("people").getJSONObject(loop).getString("ref"));

                        HttpURLConnection conn1 = (HttpURLConnection)url1.openConnection();
                        conn1.setRequestMethod("DELETE");
                        conn1.setRequestProperty("Accept", "application/json");
                        conn1.setRequestProperty("Access-Token", System.getProperty("AccessKey"));
                        conn1.setDoOutput(true);
                        if (conn1.getResponseCode() != 204) {
                            throw new RuntimeException(" HTTP error code : " +
                                    conn.getResponseCode());
                        }
                        conn1.disconnect();
                    }
                    catch (MalformedURLException e)
                    {
                        e.printStackTrace();
                    }
                }
                conn.disconnect();
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
        } while (ticketscount == 100);
    }
}
