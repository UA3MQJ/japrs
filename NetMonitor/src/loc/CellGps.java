package loc;

import java.util.*;
import java.io.*;
import javax.microedition.io.*;
import org.kxml2.io.KXmlParser;

public class CellGps extends Parser {
    /*
     *
     */

    public static double ltd = 0d, lng = 0d;
    public static String signal = "0";
    public static Hashtable table = new Hashtable();
    public static int mcc2, mnc2, lac2, cid2;

    static class MyThread1 extends Thread {

        public void run() {
            table.put("ya/" + mcc2 + "/" + mnc2 + "/" + lac2 + "/" + cid2, YandexLatLon(mcc2, mnc2, lac2, cid2));
        }
    }

    static class MyThread2 extends Thread {

        public void run() {
            table.put("google/" + mcc2 + "/" + mnc2 + "/" + lac2 + "/" + cid2, GoogleLatLon(mcc2, mnc2, lac2, cid2));
        }
    }

    static class MyThread3 extends Thread {

        public void run() {
            table.put("open/" + mcc2 + "/" + mnc2 + "/" + lac2 + "/" + cid2, OpenCellLatLon(mcc2, mnc2, lac2, cid2));

        }
    }

    static class MyThread4 extends Thread {

        public void run() {
            table.put("loc/" + mcc2 + "/" + mnc2 + "/" + lac2 + "/" + cid2, locationLatLon(mcc2, mnc2, lac2, cid2));

        }
    }

    public static void starter(Thread t) {
        t.start();
    }

    public static double[] LatLon(int mcc, int mnc, int lac, int cid) {
        mcc2 = mcc;
        mnc2 = mnc;
        lac2 = lac;
        cid2 = cid;
        Thread ya, google, open, location;

        starter(ya = new MyThread1());
        starter(google = new MyThread2());
        starter(open = new MyThread3());
//        starter(location = new MyThread4());

        boolean flag = true;
        while (flag) {
            if (ya.isAlive() != true && google.isAlive() != true && open.isAlive() != true /*
                     * && location.isAlive() != true
                     */) {
                flag = false;
            }
        }
        double[] doubles_1, doubles_2, doubles_3, doubles_4;
        doubles_1 = doubles_2 = doubles_3 = doubles_4 = new double[]{0, 0};



        doubles_1 = (double[]) table.get("ya/" + mcc2 + "/" + mnc2 + "/" + lac2 + "/" + cid2);
        doubles_2 = (double[]) table.get("google/" + mcc2 + "/" + mnc2 + "/" + lac2 + "/" + cid2);
        doubles_3 = (double[]) table.get("open/" + mcc2 + "/" + mnc2 + "/" + lac2 + "/" + cid2);
//doubles_4=(double[]) table.get("loc/"+mcc2 +"/"+ mnc2 +"/"+ lac2 +"/"+ cid2);

        double countlat = 0;
        double countlon = 0;
        double lt = 0;
        double ln = 0;

        if (doubles_1[0] != 0) {
            lt = lt + doubles_1[0];
            countlat++;
        }

        if (doubles_2[0] != 0) {
            lt = lt + doubles_2[0];
            countlat++;
        }

        if (doubles_3[0] != 0) {
            lt = lt + doubles_3[0];
            countlat++;
        }
        if (doubles_4[0] != 0) {
            lt = lt + doubles_4[0];
            countlat++;
        }

        lt = lt / countlat;


        if (doubles_1[1] != 0) {
            ln = ln + doubles_1[1];
            countlon++;
        }

        if (doubles_2[1] != 0) {
            ln = ln + doubles_2[1];
            countlon++;
        }

        if (doubles_3[1] != 0) {
            ln = ln + doubles_3[1];
            countlon++;
        }
        if (doubles_4[1] != 0) {
            ln = ln + doubles_4[1];
            countlon++;
        }

        ln = ln / countlon;

        String lat = "" + lt;
        String lon = "" + ln;
        lat = lat.substring(0, lat.indexOf('.') + 6);
        lon = lon.substring(0, lon.indexOf('.') + 6);

        return new double[]{Double.parseDouble(lat), Double.parseDouble(lon)};

    }

    public static double[] LatLon(int mcc, int mnc, int lac, int cid, String sig) {
        try {
            mcc2 = mcc;
            mnc2 = mnc;
            lac2 = lac;
            cid2 = cid;
            signal = sig;
            Thread ya, google, open, thread, location;
            double[] doubles_1, doubles_2, doubles_3, doubles_4;

            starter(thread = new Thread() {

                public void run() {

                    sendData();

                }
            });


            starter(ya = new MyThread1());
            starter(google = new MyThread2());
            starter(open = new MyThread3());
//        starter(location = new MyThread4());

            boolean flag = true;
            while (flag) {
                if (ya.isAlive() != true && google.isAlive() != true && open.isAlive() != true /*
                         * && location.isAlive() != true
                         */) {
                    flag = false;
                }
            }
//     double[] doubles_1, doubles_2, doubles_3,doubles_4;
            doubles_1 = doubles_2 = doubles_3 = doubles_4 = new double[]{0, 0};



            doubles_1 = (double[]) table.get("ya/" + mcc2 + "/" + mnc2 + "/" + lac2 + "/" + cid2);
            doubles_2 = (double[]) table.get("google/" + mcc2 + "/" + mnc2 + "/" + lac2 + "/" + cid2);
            doubles_3 = (double[]) table.get("open/" + mcc2 + "/" + mnc2 + "/" + lac2 + "/" + cid2);
//doubles_4=(double[]) table.get("loc/"+mcc2 +"/"+ mnc2 +"/"+ lac2 +"/"+ cid2);


            double countlat = 0;
            double countlon = 0;
            double lt = 0;
            double ln = 0;
            double deltalat = 1d, deltalon = 1d;
            if (doubles_1[0] != 0) {
                if (ltd != 0) {
                    deltalat = ltd / doubles_1[0];
                }

                lt = lt + doubles_1[0] * deltalat;
                countlat++;
            }

            if (doubles_2[0] != 0) {
                lt = lt + doubles_2[0] * deltalat;
                countlat++;
            }

            if (doubles_3[0] != 0) {
                lt = lt + doubles_3[0] * deltalat;
                countlat++;
            }
            if (doubles_4[0] != 0) {
                lt = lt + doubles_4[0] * deltalat;
                countlat++;
            }
            /*
             * if(ltd!=0) { lt=lt+ltd; countlat++; }
             */
            lt = lt / countlat;


            if (doubles_1[1] != 0) {
                if (lng != 0) {
                    deltalon = lng / doubles_1[1];
                }

                ln = ln + doubles_2[1] * deltalon;
                countlon++;
            }

            if (doubles_2[1] != 0) {
                ln = ln + doubles_2[1] * deltalon;
                countlon++;
            }

            if (doubles_3[1] != 0) {
                ln = ln + doubles_3[1] * deltalon;
                countlon++;
            }
            if (doubles_4[1] != 0) {
                ln = ln + doubles_4[1] * deltalon;
                countlon++;
            }
            /*
             * if(lng!=0) { ln=ln+lng; countlon++; }
             */
            ln = ln / countlon;


            String lat = "" + lt;
            String lon = "" + ln;
            lat = lat.substring(0, lat.indexOf('.') + 6);
            lon = lon.substring(0, lon.indexOf('.') + 6);

            return new double[]{Double.parseDouble(lat), Double.parseDouble(lon)};


        } catch (Exception e) {
        }

        return LatLon(mcc, mnc, lac, cid);
    }

//получаем координаты с Google DB
// double[0] - Lat; double[1] - Lon
    public static double[] GoogleLatLon(int MCC, int MNC, int LAC, int CID) {

        if (table.containsKey("google/" + MCC + "/" + MNC + "/" + LAC + "/" + CID)) {
            return (double[]) table.get("google/" + MCC + "/" + MNC + "/" + LAC + "/" + CID);
        }

        double[] latlon = new double[2];

        try {
            HttpConnection hc = (HttpConnection) Connector.open("http://www.google.com/glm/mmap");

            hc.setRequestMethod(HttpConnection.POST);

            byte[] data = new byte[55];

            data[0x01] = 14; // fixed
//data[0x01] = (byte) 0x0E; // fixed

            data[0x10] = 27; // fixed
//data[0x10] = (byte) 0x1B; // fixed

            for (int i = 0x2F; i <= 0x32; i++) {
                data[i] = Byte.MAX_VALUE; // fixed
            }
            if (CID > 65536) {
                data[0x1c] = 5; // UTMS - 6 hex digits
            } else {
                data[0x1c] = 3; // GSM - 4 hex digits
            }
            int stOfs = 0x1f;
            data[stOfs++] = (byte) ((CID >> 24) & 0xFF); // 0x1f

            data[stOfs++] = (byte) ((CID >> 16) & 0xFF);

            data[stOfs++] = (byte) ((CID >> 8) & 0xFF);

            data[stOfs++] = (byte) ((CID) & 0xFF);

            data[stOfs++] = (byte) ((LAC >> 24) & 0xFF); //0x23

            data[stOfs++] = (byte) ((LAC >> 16) & 0xFF);

            data[stOfs++] = (byte) ((LAC >> 8) & 0xFF);

            data[stOfs++] = (byte) ((LAC) & 0xFF);

            data[stOfs++] = (byte) ((MNC >> 24) & 0xFF); // 0x27

            data[stOfs++] = (byte) ((MNC >> 16) & 0xFF);

            data[stOfs++] = (byte) ((MNC >> 8) & 0xFF);

            data[stOfs++] = (byte) ((MNC) & 0xFF);

            data[stOfs++] = (byte) ((MCC >> 24) & 0xFF); // 0x2b

            data[stOfs++] = (byte) ((MCC >> 16) & 0xFF);

            data[stOfs++] = (byte) ((MCC >> 8) & 0xFF);

            data[stOfs++] = (byte) ((MCC) & 0xFF);



            hc.setRequestProperty("Content-Type", "application/binary");

            hc.setRequestProperty("Content-Length", Integer.toString(data.length));

            OutputStream os = hc.openOutputStream();

            os.write(data);
            os.close();

            DataInputStream in = hc.openDataInputStream();

            in.readShort();
            in.readByte();

            if (in.readInt() == 0) {

                double latitude = (double) in.readInt() / 1000000;

                double longitude = (double) in.readInt() / 1000000;

                in.close();
                hc.close();
                return new double[]{latitude, longitude};
            }
            in.close();
            hc.close();


            return latlon;

        } catch (Throwable e) {
            //main.m.error.append("cellgps.google: " + e.toString() + "\n");
        }
//return new double[] { 0, 0 };

        return latlon;
    }

//получаем координаты с Yandex DB
// double[0] - Lat; double[1] - Lon
    public static double[] YandexLatLon(int MCC, int MNC, int LAC, int CID) {
        if (table.containsKey("ya/" + MCC + "/" + MNC + "/" + LAC + "/" + CID)) {
            return (double[]) table.get("ya/" + MCC + "/" + MNC + "/" + LAC + "/" + CID);
        }

        double[] latlon = new double[2];

        try {
            String url = "http://mobile.maps.yandex.net/cellid_location/" + "?countrycode=" + Integer.toString(MCC) + "&operatorid=" + Integer.toString(MNC) + "&lac=" + Integer.toString(LAC) + "&cellid=" + Integer.toString(CID);

            HttpConnection hc = (HttpConnection) Connector.open(url);
            hc.setRequestMethod(HttpConnection.GET);
            InputStream in = hc.openInputStream();
            StringBuffer str = new StringBuffer();
            int ch;
            while ((ch = in.read()) != -1) {
                str.append((char) ch);
            }
            in.close();
            hc.close();

            String s = str.toString();

            s = replace(s, "\"", "");
            String[] s1 = splitString(s, " ");
            String sLat = "0.0", sLon = "0.0";

            for (int i = 0; i < s1.length; i++) {
                if (s1[i].startsWith("latitude=")) {
                    sLat = splitString(s1[i], "=")[1].trim();
                }
                if (s1[i].startsWith("longitude=")) {
                    sLon = splitString(s1[i], "=")[1].trim();
                }
            }

            latlon[0] = Double.parseDouble(sLat);
            latlon[1] = Double.parseDouble(sLon);
            return latlon;

        } catch (Throwable e) {
            //main.m.error.append("cellgps.yandex: " + e.toString() + "\n");
////main.m.error.append(s+"\n");
        }
//return new double[] { 0, 0 };

        return latlon;

    }
//получаем координаты с location-api DB
// double[0] - Lat; double[1] - Lon

    public static double[] locationLatLon(int MCC, int MNC, int LAC, int CID) {
        if (table.containsKey("loc/" + MCC + "/" + MNC + "/" + LAC + "/" + CID)) {
            return (double[]) table.get("loc/" + MCC + "/" + MNC + "/" + LAC + "/" + CID);
        }

        double[] latlon = new double[2];

        try {
            String url = "http://www.location-api.com/cell/get/?key=v0sszp3ul8s6mzaad30e&output=text&mcc=" + Integer.toString(MCC) + "&mnc=" + Integer.toString(MNC) + "&lac=" + Integer.toString(LAC) + "&cellid=" + Integer.toString(CID);

            HttpConnection hc = (HttpConnection) Connector.open(url);
            hc.setRequestMethod(HttpConnection.GET);
            InputStream in = hc.openInputStream();
            StringBuffer str = new StringBuffer();
            int ch;
            while ((ch = in.read()) != -1) {
                str.append((char) ch);
            }
            in.close();
            hc.close();

            String s = str.toString();

            s = replace(s, "\"", "");
            String[] s1 = splitString(s, " ");
            String sLat = "0.0", sLon = "0.0";

            for (int i = 0; i < s1.length; i++) {
                if (s1[i].startsWith("lat=")) {
                    sLat = splitString(s1[i], "=")[1].trim();
                }
                if (s1[i].startsWith("lon=")) {
                    sLon = splitString(s1[i], "=")[1].trim();
                }
            }

            latlon[0] = Double.parseDouble(sLat);
            latlon[1] = Double.parseDouble(sLon);
            return latlon;

        } catch (Throwable e) {
            //main.m.error.append("cellgps.location: " + e.toString() + "\n");
////main.m.error.append(s+"\n");
        }
//return new double[] { 0, 0 };

        return latlon;

    }

    public static double[] OpenCellLatLon(int MCC, int MNC, int LAC, int CID) {
        if (table.containsKey("open/" + MCC + "/" + MNC + "/" + LAC + "/" + CID)) {
            return (double[]) table.get("open/" + MCC + "/" + MNC + "/" + LAC + "/" + CID);
        }

        double[] latlon = new double[2];

        try {

            String url = "http://www.opencellid.org/cell/get?mcc=" + Integer.toString(MCC) + "&mnc" + Integer.toString(MNC) + "&lac=" + Integer.toString(LAC) + "&cellid=" + Integer.toString(CID);

            HttpConnection hc = (HttpConnection) Connector.open(url);
            hc.setRequestMethod(HttpConnection.GET);
            InputStream in = hc.openInputStream();
            StringBuffer str = new StringBuffer();
            int ch;
            while ((ch = in.read()) != -1) {
                str.append((char) ch);
            }
            in.close();
            hc.close();
            String s = str.toString();

            s = replace(s, "\"", "");

            int iLat = s.indexOf("lat=") + 4;

            String sLat = s.substring(iLat, s.indexOf(" ", iLat));


            int iLon = s.indexOf("lon=") + 4;

            String sLon = s.substring(iLon, s.indexOf(" ", iLon));


            latlon[0] = Double.parseDouble(sLat);
            latlon[1] = Double.parseDouble(sLon);
            return latlon;

        } catch (Throwable e) {
            //main.m.error.append("cellgps.opencell: " + e.toString() + "\n");
        }
//return new double[] { 0, 0 };

        return latlon;

    }

    public static String[] splitString(String string_1, String string_2) {
        if (string_1 == null) {
            return null;
        }
        if (string_1.equals("") || string_2 == null || string_2.length() == 0) {
            return new String[]{string_1};
        }
        Vector vector_1 = new Vector();
        int int_1 = 0;
        int int_2 = string_1.indexOf(string_2, int_1);
        while (int_2 != -1) {
            vector_1.addElement(string_1.substring(int_1, int_2));
            int_1 = int_2 + string_2.length();
            int_2 = string_1.indexOf(string_2, int_1);
        }
        vector_1.addElement(string_1.substring(int_1));
        String[] strings_1 = new String[vector_1.size()];
        vector_1.copyInto(strings_1);
        //vector_1 = null;
        return strings_1;
    }

    public static String replace(String s, String s1, String s2) {
        String out = " ";
        s = s + " ";
        int i = s.indexOf(s1);
        while (i > -1) {
            out += s.substring(0, i) + s2;
            s = s.substring(i + s1.length());
            i = s.indexOf(s1);
        }
        out += s;
        return out.substring(1, out.length() - 1);
    }

    public static String CellId() {
        String string_1 = "";
        try {
            string_1 = System.getProperty("com.3g.net.roaming.cellid");


            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("com.lge.net.cellid");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("device.cellid");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("Cell-ID");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("CellID");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("Siemens.CID");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("phone.cid");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("com.nokia.mid.cellid");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                try {
                    string_1 = Integer.toString(Integer.parseInt(System.getProperty("com.sonyericsson.net.cellid"), 16));
                } catch (Throwable exception_1) {
                }
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                try {
                    string_1 = Integer.toString(Integer.parseInt(System.getProperty("com.sonyericsson.net.cid"), 16));
                } catch (Throwable exception_2) {
                }
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("phone.cid");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("com.samsung.cellid");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("MPJC_CID");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("cid");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("CELLID");
            } else {
                return string_1;
            }
        } catch (Throwable exception_3) {
            string_1 = exception_3.toString();
        }
        return string_1 == null ? "" : string_1;
    }

    public static String LAC() {
        String string_1 = "";
        try {

            string_1 = System.getProperty("phone.lac");
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("com.3g.net.roaming.lac");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("com.lge.net.lac");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("device.lac");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("Siemens.LAC");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("com.nokia.mid.lac");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("phone.lai");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("com.nokia.mid.LAC");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                try {
                    string_1 = Integer.toString(Integer.parseInt(System.getProperty("com.sonyericsson.net.lac"), 16));
                } catch (Throwable exception_1) {
                }
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("LocAreaCode");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("com.samsung.lac");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("MPJC_LAC");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("lac");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("LAC");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("com.lge.net.rat");
            } else {
                return string_1;
            }

            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("com.nokia.mid.areacode");
            } else {
                return string_1;
            }

            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("com.nokia.mid.locareacode");
            } else {
                return string_1;
            }

            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("com.nokia.mid.LocAreaCode");
            } else {
                return string_1;
            }

            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("com.nokia.mid.lai");
            } else {
                return string_1;
            }



        } catch (Throwable exception_2) {
            string_1 = exception_2.toString();
        }
        return string_1 == null ? "" : string_1;
    }

    public static String getIMSI() {
        String string_1 = "";
        try {
            string_1 = System.getProperty("IMSI");
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("phone.imsi");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("com.lge.imsi");
            } else {
                return string_1;
            }


            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("com.3g.net.phone.imsi");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("com.nokia.mid.mobinfo.IMSI");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("com.nokia.mid.imsi");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("com.nokia.mid.Mobinfo.IMSI");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("com.sonyericsson.imsi");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("IMSI");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("imsi");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("com.siemens.imsi");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("com.samsung.imsi");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("com.nokia.mid.imsi");
            } else {
                return string_1;
            }

        } catch (Throwable exception_1) {
            string_1 = exception_1.toString();
        }
        return string_1 == null ? "" : string_1;
    }

    public static String MCC() {
        String string_1 = "";
        try {
               string_1 = System.getProperty("com.3g.net.roaming.mcc");

            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("com.lge.net.mcc");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("device.mcc");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("MCC");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("Siemens.MCC");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("phone.mcc");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("com.nokia.mid.countrycode");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("com.nokia.mid.mcc");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("com.sonyericsson.net.mcc");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("com.sonyericsson.net.cmcc");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = getIMSI().equals("") ? "" : getIMSI().substring(0, 3);
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("com.samsung.mcc");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("com.siemens.mcc");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("mcc");
            } else {
                return string_1;
            }
        } catch (Throwable exception_1) {
            string_1 = exception_1.toString();
        }
        return string_1 == null ? "" : string_1;
    }

    public static String MNC() {
        String string_1 = "";
        try {
               string_1 = System.getProperty("com.3g.net.roaming.mnc");


            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("com.lge.net.mnc");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("device.mnc");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("MNC");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("Siemens.MNC");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("phone.mnc");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("com.nokia.mid.networkID");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("NETWORK");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("device.network");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("com.nokia.mid.networkid");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("com.nokia.mid.mnc");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("com.nokia.mid.MNC");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = getIMSI().equals("") ? "" : getIMSI().substring(3, 5);
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("com.sonyericsson.net.mnc");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("com.sonyericsson.net.cmnc");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("com.samsung.mnc");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("com.siemens.mnc");
            } else {
                return string_1;
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("mnc");
            } else {
                return string_1;
            }
        } catch (Throwable exception_1) {
            string_1 = exception_1.toString();
        }
        return string_1 == null ? "" : string_1;
    }

    public static void parserData(byte[] data) {


        KXmlParser iXmlParser = getParser(data);

        //System.out.println("LBS.parseLBS() "+ new String(data));


        if (iXmlParser != null) {

//            String xmldataStr = getXmlContent(inputArray);
//            System.out.println(xmldataStr);

            try {
                String iNode = null;
                while ((iNode = getNextNode(iXmlParser)) != null) {
                    if (iNode.compareTo("ya_lbs_response") == 0) {
                        String iSubNode = null;
                        while ((iSubNode = getNextSubNode(iNode, iXmlParser)) != null) {
                            if (iSubNode.compareTo("error") == 0) {
                                //lbs.error = 1;
                                //lbs.errorMessage =
//getNextText(iXmlParser);
                            } else if (iSubNode.compareTo("position") == 0) {
                                String iSubNode2 = null;
                                while ((iSubNode2 = getNextSubNode(iSubNode, iXmlParser)) != null) {
                                    String value = getNextText(iXmlParser);
                                    if (iSubNode2.compareTo("latitude") == 0) {
                                        ltd = Double.parseDouble(value);

                                    } else if (iSubNode2.compareTo("longitude") == 0) {
                                        lng = Double.parseDouble(value);
                                    } else if (iSubNode2.compareTo("altitude") == 0) {
                                        //lbs.altitude = value;
                                    } else if (iSubNode2.compareTo("precision") == 0) {
                                        //lbs.precision = value;
                                    } else if (iSubNode2.compareTo("altitude_precision") == 0) {
                                        //lbs.altitude_precision = value;
                                    } else if (iSubNode2.compareTo("type") == 0) {
                                        //lbs.type = value;
                                    }
                                }
                            }
                        }
                    }
                }

                /*
                 * if (lbs.latitudude == null || lbs.longitude == null) {
                 * lbs.latitude = null; lbs.longitude = null; }
                 */
            } catch (Exception e) {
                //main.m.error.append("cellgps.parseData: " + e.toString() + "\n");
//                lbs.error = 1;
            }
        }

    }

    static void sendData() {
        int status = 0;
        HttpConnection httpConnection = null;
        InputStream is = null;
        OutputStream os = null;
//		System.out.println("Network.sendData() " + netJob.url);

        try {
            httpConnection = (HttpConnection) Connector.open("http://api.lbs.yandex.net/geolocation");
            httpConnection.setRequestMethod(HttpConnection.POST);
            httpConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + "F0D467AAE11BE10F57B");
            os = httpConnection.openOutputStream();
            byte[] data_send = createPost();
            httpConnection.setRequestProperty("Content-Length", Integer.toString(data_send.length));
            os.write(data_send);
            os.close();
            os = null;

            status = httpConnection.getResponseCode();

            if ((status == HttpConnection.HTTP_NOT_IMPLEMENTED)
                    || (status == HttpConnection.HTTP_VERSION)
                    || (status == HttpConnection.HTTP_INTERNAL_ERROR)
                    || (status == HttpConnection.HTTP_GATEWAY_TIMEOUT)
                    || (status == HttpConnection.HTTP_BAD_GATEWAY)
                    || (status != HttpConnection.HTTP_OK)) {
//				System.err.print("WARNING: Server error status ["+status+"] ");
//				System.err.println("returned for url ["+url+"]");
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }
                if (httpConnection != null) {
                    httpConnection.close();
                }
                return;
            }

            is = httpConnection.openInputStream();
            String type = httpConnection.getType();

            int len = (int) httpConnection.getLength();
            if (len > 0) {
                byte[] data = new byte[len];

                int actual = 0;
                is.read(data);
                int offset = 0;

                while ((actual = is.read(data, offset, len - offset)) != -1) {
//					System.out.println("Network.sendData() " +  actual +" "+ len );
//LocationMidlet.addItem("data", actual+ " " + len);
                    offset += actual;
                }
                parserData(data);
            }
        } catch (Exception e) {
            //main.m.error.append("cellgps.sendData: " + e.toString() + "\n");

            e.printStackTrace();
//			LocationMidlet.addItem("error", e.getMessage());
// TODO: handle exception
        }// finally {

        try {
            if (is != null) {
                is.close();
            }
        } catch (Exception e1) {
            is = null;

        }
        try {
            if (os != null) {
                os.close();
            }
        } catch (Exception e2) {
            os = null;
        }
        try {
            if (httpConnection != null) {
                httpConnection.close();
            }
        } catch (Exception e3) {
            httpConnection = null;
        }
        // }
    }

    public static byte[] createPost() throws IOException {


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            StringBuffer postString = new StringBuffer();

            String key = Location.ykey;
            String contentType = "xml";
            byte[] value = getData();

            postString.setLength(0);

            postString.append("--");
            postString.append("F0D467AAE11BE10F57B");
            postString.append("\r\nContent-Disposition: form-data; name=\"");
            postString.append(key);
            postString.append("\"");
            if (contentType != null && !contentType.equals("")) {
                postString.append("\r\n");
                postString.append("Content-Type:");
                postString.append(contentType);
            }
            postString.append("\r\n\r\n");
            baos.write(postString.toString().getBytes());
            baos.write(value);
            baos.write("\r\n".getBytes());
//            }

            baos.write(("--" + "F0D467AAE11BE10F57B" + "--\r\n").getBytes());

            byte[] buff = baos.toByteArray();
            Location.traffic += buff.length;
            return buff;
        } finally {

            try {
                baos.close();
            } catch (Exception e) {
            }
        }
    }

    public static byte[] getData() {

        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?>");
        sb.append("<ya_lbs_request>");
        sb.append("<common>");
        sb.append("<version>1.0</version>");
        sb.append("<radio_type>gsm</radio_type>");
        sb.append("<api_key>").append(Location.ykey).append("</api_key>");
        sb.append("</common>");
        sb.append("<gsm_cells>");
        sb.append("<cell>");
        sb.append("<countrycode>").append(mcc2).append("</countrycode>");

        sb.append("<operatorid>").append(mnc2).append("</operatorid>");
        sb.append("<cellid>").append(cid2).append("</cellid>");
        sb.append("<lac>").append(lac2).append("</lac>");
        sb.append("<signal_strength>signal</signal_strength>");
        sb.append("</cell>");
        sb.append("</gsm_cells>");
        sb.append("</ya_lbs_request>");
        return sb.toString().getBytes();
    }
}