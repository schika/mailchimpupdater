import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.ini4j.Ini;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: DarkShade
 * Date: 14.09.13
 * Time: 12:41
 * To change this template use File | Settings | File Templates.
 */
public class Updater implements Runnable {
    public void run() {
        try {
            Ini ini = new Ini();
            ini.load(new File("config.ini"));
            Ini.Section config = ini.get("config");
            Class.forName("com.mysql.jdbc.Driver");
            Connection db = DriverManager.getConnection("jdbc:mysql://"+config.get("host")+"/"+config.get("dbname"), config.get("user"), config.get("pass"));
            if (db == null) System.exit(1);
            Statement stmt = db.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM yuw0q_users");
            while (res.next()) {
                HttpClient client = HttpClients.createDefault();
                HttpPost post = new HttpPost();
                post.setURI(URI.create("http://godsofcontent.biz/cron/chimpupd_one.php"));
                post.addHeader("Referer", "");
                List<NameValuePair> formparams = new ArrayList<NameValuePair>();
                formparams.add(new BasicNameValuePair("id", res.getString("id")));
                System.out.println(res.getString("id"));
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
                post.setEntity(entity);
                HttpResponse response = client.execute(post);
            }
            stmt.close();
            db.close();
            System.exit(0);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
