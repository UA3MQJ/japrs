package loc;

public class SystemUtil {

    public static int index = 0;
    public static String consol = "";

    public static String[] decodeImei(String string_1) {

        String[] strings_1 = new String[6];
        String int_1;
        for (int i = 0; i < strings_1.length; i++) {
            strings_1[i] = "";
        }
        try {
            strings_1[0] = "IMEI " + string_1;

            int_1 = string_1.substring(0, 2);
            if (int_1.equals("00") || int_1.equals("02") || int_1.equals("03") || int_1.equals("04") || int_1.equals("05") || int_1.equals("06") || int_1.equals("07") || int_1.equals("08") || int_1.equals("09")) {
                strings_1[1] = "Manufactured group ".concat(String.valueOf(string_1.substring(0, 2))).concat(" (Test IMEI)");
            } else if (int_1.equals("01")) {
                strings_1[1] = "Manufactured group ".concat(String.valueOf(string_1.substring(0, 2))).concat(" PTCRB (USA)");
            } else if (int_1.equals("10")) {
                strings_1[1] = "Manufactured group ".concat(String.valueOf(string_1.substring(0, 2))).concat(" DECT device");
            } else if (int_1.equals("30")) {
                strings_1[1] = "Manufactured group ".concat(String.valueOf(string_1.substring(0, 2))).concat(" Iridium (USA)");
            } else if (int_1.equals("33")) {
                strings_1[1] = "Manufactured group ".concat(String.valueOf(string_1.substring(0, 2))).concat(" DGPT (Indonesia)");
            } else if (int_1.equals("35") || int_1.equals("44")) {
                strings_1[1] = "Manufactured group ".concat(String.valueOf(string_1.substring(0, 2))).concat(" BABT (United Kingdom)");
            } else if (int_1.equals("45")) {
                strings_1[1] = "Manufactured group ".concat(String.valueOf(string_1.substring(0, 2))).concat(" NTA (United Kingdom)");
            } else if (int_1.equals("49")) {
                strings_1[1] = "Manufactured group ".concat(String.valueOf(string_1.substring(0, 2))).concat(" BZT/BAPT (Germany)");
            } else if (int_1.equals("50")) {
                strings_1[1] = "Manufactured group ".concat(String.valueOf(string_1.substring(0, 2))).concat(" BZT ETS (Germany)");
            } else if (int_1.equals("51")) {
                strings_1[1] = "Manufactured group ".concat(String.valueOf(string_1.substring(0, 2))).concat(" Cetecom ICT (Germany)");
            } else if (int_1.equals("52")) {
                strings_1[1] = "Manufactured group ".concat(String.valueOf(string_1.substring(0, 2))).concat(" Cetecom (Germany)");
            } else if (int_1.equals("53")) {
                strings_1[1] = "Manufactured group ".concat(String.valueOf(string_1.substring(0, 2))).concat(" TUV (Germany)");
            } else if (int_1.equals("54")) {
                strings_1[1] = "Manufactured group ".concat(String.valueOf(string_1.substring(0, 2))).concat(" Phoenix Test Lab (Germany)");
            }
            strings_1[2] = "Model code " + String.valueOf(string_1.substring(2, 6));
            String string_2 = string_1.substring(6, 8);
            if (string_2.equals("76") || string_2.equals("67")) {
                strings_1[3] = "Final Assembly Code ".concat(String.valueOf(string_1.substring(6, 8))).concat(" (USA)");
            } else if (string_2.equals("04") || string_2.equals("40") || string_2.equals("19") || string_2.equals("91")) {
                strings_1[3] = "Final Assembly Code ".concat(String.valueOf(string_1.substring(6, 8))).concat(" (United Kingdom)");
            } else if (string_2.equals("02") || string_2.equals("20") || string_2.equals("78") || string_2.equals("87")) {
                strings_1[3] = "Final Assembly Code ".concat(String.valueOf(string_1.substring(6, 8))).concat(" (Germany)");
            } else if (string_2.equals("01") || string_2.equals("10") || string_2.equals("70") || string_2.equals("07")) {
                strings_1[3] = "Final Assembly Code ".concat(String.valueOf(string_1.substring(6, 8))).concat(" (Finland)");
            } else if (string_2.equals("03") || string_2.equals("30")) {
                strings_1[3] = "Final Assembly Code ".concat(String.valueOf(string_1.substring(6, 8))).concat(" (South Korea)");
            } else if (string_2.equals("08") || string_2.equals("80")) {
                strings_1[3] = "Final Assembly Code ".concat(String.valueOf(string_1.substring(6, 8))).concat(" (China)");
            }
            if (string_2.equals("00")) {
                strings_1[3] = "Final Assembly Code ".concat(String.valueOf(string_1.substring(6, 8))).concat(" (original factory)");
            } else {
                strings_1[3] = "Final Assembly Code ".concat(String.valueOf(string_1.substring(6, 8))).concat(" (unknown factory)");
            }
            strings_1[4] = "Serial Number ".concat(String.valueOf(string_1.substring(8, 14)));
            if (string_1.length() > 15) {
                strings_1[5] = "Software Version ".concat(String.valueOf(string_1.substring(14, 16)));
            } else {
                strings_1[5] = "Spare ".concat(String.valueOf(string_1.substring(14, 15)));
            }
        } catch (Throwable exception_1) {
        }
        return strings_1;
    }

    public static String getIMEI() {
        String string_1 = "";
        try {
            string_1 = System.getProperty("com.imei");
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("phone.imei");
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("com.nokia.IMEI");
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("com.nokia.mid.imei");
            }

            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("com.nokia.mid.mobinfo.IMEI");
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("com.nokia.mid.mobinfo.imei");
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("com.nokia.mid.IMEI");
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("com.sonyericsson.imei");
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("IMEI");
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("com.motorola.IMEI");
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("com.samsung.imei");
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("com.siemens.imei");
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                string_1 = System.getProperty("imei");
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                System.getProperty("com.3g.imei");
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                System.getProperty("com.lge.imei");
            }
            if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
                System.getProperty("device.imei");
            }
        } catch (Throwable ex) {
            return ex.getMessage();
        }
        string_1 = replace(string_1, "IMEI", "");
        string_1 = replace(string_1, "imei", "");
        string_1 = replace(string_1, "-", "");
        string_1 = replace(string_1, "//", "");

        return string_1 == null ? "0" : string_1;
    }

    public static String nativeDigitSupport() {
        String string_1 = "";
        string_1 = System.getProperty("com.nokia.mid.impl.nativeDigitSupport");
        return string_1 == null ? "" : string_1;
    }

    public static String operatorName() {
        String string_1 = "";
        string_1 = System.getProperty("com.nokia.mid.ons");
        return string_1 == null ? "" : string_1;
    }

    public static String serviceProvider() {
        String string_1 = "";
        string_1 = System.getProperty("com.nokia.mid.spn");
        return string_1 == null ? "" : string_1;
    }

    public static String battery() {
        String string_1 = "";
        string_1 = System.getProperty("com.nokia.mid.batterylevel");
        if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
            string_1 = System.getProperty("com.3g.net.phone.batterylevel");
        }
        if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
            string_1 = System.getProperty("com.lge.batterylevel");
        }
        if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
            string_1 = System.getProperty("MPJC_CAP");
        }
        if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
            String sens = System.getProperty("microedition.sensor.version");
            if (sens != null && !sens.equals("") && !sens.equals("null")) {
                string_1 = SensorApi.getSensor(0);
            }
        }
        return string_1 == null ? "" : string_1;
    }

    public static String traffic() {
        String string_1 = "";
        string_1 = System.getProperty("MPJCGPRS");
        return string_1 == null ? "" : string_1;
    }

    public static String signal() {
        String string_1 = System.getProperty("com.nokia.mid.networksignal");
        try {
            if (Location.reallyNull(string_1)) {
                string_1 = System.getProperty("MPJCRXLS");

                if (!Location.reallyNull(string_1)) {
                    string_1 = "-" + string_1.substring(0, string_1.indexOf(',')) + " dBm";
                }
            }

            if (Location.reallyNull(string_1)) {
                String sens = System.getProperty("microedition.sensor.version");
                if (!Location.reallyNull(sens)) {
                    string_1 = "-" + (((Integer.parseInt(SensorApi.getSensor(1)) * 76 / 100) + 35)) + " dBm";
                }

            }

        } catch (Exception e) {
            return "0";
        }

        if (!Location.reallyNull(string_1)) {
            return string_1;
        }

        return "0";
    }

    public static String TA_String() {
        String string_1 = "";
        string_1 = System.getProperty("MPJC_TA");
        return string_1 == null ? "" : string_1;
    }

    public static String gid1() {
        String string_1 = "";
        string_1 = System.getProperty("com.nokia.mid.gid1");
        return string_1 == null ? "" : string_1;
    }

    public static String gid2() {
        String string_1 = "";
        string_1 = System.getProperty("com.nokia.mid.gid2");
        return string_1 == null ? "" : string_1;
    }

    public static String msisdn() {
        String string_1 = "";
        string_1 = System.getProperty("com.nokia.mid.msisdn");
        return string_1 == null ? "" : string_1;
    }

    public static String productCode() {
        String string_1 = "";
        string_1 = System.getProperty("com.nokia.mid.productcode");
        return string_1 == null ? "" : string_1;
    }

    public static String cpuclock() {
        String string_1 = "";
        string_1 = System.getProperty("com.siemens.mp.cpu");
        return string_1 == null ? "" : string_1;
    }

    public static String osversion() {
        String string_1 = "";
        string_1 = System.getProperty("com.siemens.osversion");
        return string_1 == null ? "" : string_1;
    }

    public static String isFlipOpen() {
        String string_1 = "";
        string_1 = System.getProperty("com.sonyericsson.flipopen");
        if (string_1 == null || string_1.equals("null") || string_1.equals("")) {
            string_1 = System.getProperty("com.sonyericsson.jackknifeopen");
        }
        return string_1 == null ? "" : string_1;
    }

    public static String replace(String string_1, String string_2, String string_3) {
        String string_4 = " ";
        string_1 = String.valueOf(string_1).concat(" ");
        int int_1 = string_1.indexOf(string_2);
        while (int_1 > -1) {
            string_4 = String.valueOf(string_4).concat(String.valueOf(String.valueOf(string_1.substring(0, int_1)).concat(String.valueOf(string_3))));
            string_1 = string_1.substring(int_1 + string_2.length());
            int_1 = string_1.indexOf(string_2);
        }
        string_4 = String.valueOf(string_4).concat(String.valueOf(string_1));
        return string_4.substring(1, string_4.length() - 1);
    }
}