/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hossien
 */
public class ConstantValues {

    public final static String USER_SUPER_ADMIN = "shenakht";
    public final static String PASS_SUPER_ADMIN = "mmhshenakht";

    public static class ConstantFunction {

        public static Date getCurrentDate() {
            try {
                Date date = new Date(System.currentTimeMillis());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
                dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Tehran"));
                String s = dateFormat.format(date);
                date = dateFormat.parse(s);

                return date;
            } catch (ParseException ex) {
                Logger.getLogger(ConstantValues.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }

        public static String getPath() {
            String s = "../docroot/yourfile";
            return s;
        }
    }

    public static class EmailValues {

        public final static int TLS_port = 587;
        public final static int SSL_port = 465;
        public final static String host = "smtp.gmail.com";
        public final static String from = "hossien.gh.sh@gmail.com";
        public final static String username = "hossien.gh.sh@gmail.com";
        public final static String password = "8380163fc";
        public final static boolean authentication = true;

    }

    /**
     * address upload file and address server parse shenakht
     */
    public static class AddressServers {

        public static final String SERVER_UPLOAD_LOCATION_FOLDER = "/opt/glassfish4/glassfish/domains/domain1/docroot/";
//    public static final String SERVER_UPLOAD_LOCATION_FOLDER = "D:\\glassfish-4.1\\glassfish\\domains\\domain1\\docroot\\";
        public static final String LINK_SERVER = "http://185.211.56.72:8080/";
//    public static final String LINK_SERVER = "http://localhost:8080/";

        public static class Parse {

            public static final String APPLICATION_ID = "IcDv7bAUzTQ1HrJQEjmP4lL9S3uGGCcW7LcgFGnn";
            public static final String REST_API_KEY = "oy6wZ35un2iibGl33AuZzxnkXuyPRXDWoeAvGE9j";
            public static final String PUSH_URL = "http://parse.apora.ir:8080/";
            public static final String MASTER_KEY = "oy6wZ35un2iibGl33Ad76y4gteyQ1HrJQEjmP4l5w7enybv5e5rzsWoeAvGE9j";
        }
    }

    public static class SmsInfo {

        public static final String ADDRESS_SMS_PANEL = "http://ip.sms.ir/SendMessage.ashx";
        public static final String USER = "09187156889";
        public static final String PASSWORD = "8380163fc";
        public static final String LINE_NO = "30004554554908";
        public static final String NUMBER_PHONE_ADMIN = "09187156889";
        public static final String ADDRESS_GET_TOKEN = "http://RestfulSms.com/api/Token";
        public static final String ADDRESS_VERIFICATION_CUSTOM = "http://RestfulSms.com/api/UltraFastSend";
        public static final String ADDRESS_VERFICATION_DEFAULT = "http://RestfulSms.com/api/VerificationCode";
        public static final String USER_API_KEY = "3484ad9dc0f2f402100fcb86";
        public static final String SECRET_KEY = "mmhshenakht09183306035!#(%";

    }
}
