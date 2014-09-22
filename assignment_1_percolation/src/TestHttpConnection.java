import java.io.*;
import java.net.*;

public class TestHttpConnection {

    public void printHTML(String urlToRead) {

        try {

            URL url = new URL(urlToRead);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line;
            while ((line = rd.readLine()) != null) {
                System.out.println(line);
            }
            rd.close();

            System.out.println("sucess");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        TestHttpConnection test = new TestHttpConnection();
        test.printHTML(args[0]);
    }
}