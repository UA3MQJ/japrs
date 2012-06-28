package loc;
import org.kxml2.io.KXmlParser;

import java.io.*;

//import tools.Unicode;
public abstract class Parser {

    /**
     * getEncoding
     *
     * @param array byte[]
     * @return String
     */
    private static String getEncoding(byte[] array) {
        try {
            int size = array.length - 8;
            int i = 0;
            //encoding
            // Так как слово аглиское то оно входит до 127 тоесть все положительные
            StringBuffer buf = new StringBuffer();
            for (; i < size; i++) {
                if (array[i] == 'e'
                        && array[i + 1] == 'n'
                        && array[i + 2] == 'c'
                        && array[i + 3] == 'o'
                        && array[i + 4] == 'd'
                        && array[i + 5] == 'i'
                        && array[i + 6] == 'n'
                        && array[i + 7] == 'g') {
                    int flag = 0;
                    int index = i;
                    while (index < size) {
                        if (array[index] == '\"') {
                            index++;
                            flag++;
                        }
                        if (flag == 2) {
                            return buf.toString().toLowerCase();
                        } else if (flag > 0) {
                            buf.append((char) array[index]);
                        }
                        index++;
                    }
                }
            }
        } catch (Exception e) {
        }
        return "unknown";
    }

    /**
     * создает объект парсера и подает ему на вход полученные данные
     *
     * @param data byte[]
     * @return KXmlParser
     */
    protected static KXmlParser getParser(byte[] data) {
        KXmlParser xmlParser;
        try {
            InputStreamReader iStreamReader;
            xmlParser = new KXmlParser();
            String encoding = getEncoding(data);
            if (encoding.equals("unknown")) {
                try {
                    iStreamReader = new InputStreamReader(new ByteArrayInputStream(data), "windows-1251");
                } catch (UnsupportedEncodingException e2) {
                    try {
                        iStreamReader = new InputStreamReader(new ByteArrayInputStream(data), "UTF-8");
                    } catch (Exception e3) {
                        return null;
                    }
                }
            } else {
                if (encoding.equals("windows-1251")) {
                    data = com.gui.TextUtils.convCp1251ToUnicode(data).getBytes("UTF-8");
                }
                iStreamReader = new InputStreamReader(new ByteArrayInputStream(data), "UTF-8");
            }
            xmlParser.setInput(iStreamReader);
        } catch (Exception e) {
            return null;
        }
        return xmlParser;
    }

    /**
     * ищет следующую вершину в xml
     *
     * @param xmlParser KXmlParser
     * @return int
     */
    private static int nextParserNode(KXmlParser xmlParser) {
        int iParserEvent = -1;
        try {
            iParserEvent = xmlParser.next();
        } catch (Exception e) {
        }
        return iParserEvent;
    }

    /**
     * ищет следующую подвершину в xml
     *
     * @param node String
     * @param xmlParser KXmlParser
     * @return String
     */
    protected static String getNextSubNode(String node, KXmlParser xmlParser) {
        int iParserEvent = nextParserNode(xmlParser);
        while (iParserEvent != KXmlParser.END_DOCUMENT) {
            if (iParserEvent == KXmlParser.START_TAG) {
                return xmlParser.getName();
            } else if (iParserEvent == KXmlParser.END_TAG) {
                if (node != null && xmlParser.getName().compareTo(node) == 0) {
                    return null;
                }
            }
            iParserEvent = nextParserNode(xmlParser);
        }
        return null;
    }

    /**
     * getNextNode
     *
     * @param xmlParser KXmlParser
     * @return String
     */
    protected static String getNextNode(KXmlParser xmlParser) {
        int iParserEvent = nextParserNode(xmlParser);
        while (iParserEvent != KXmlParser.END_DOCUMENT) {
            if (iParserEvent == KXmlParser.START_TAG) {
                return xmlParser.getName();
            }
            iParserEvent = nextParserNode(xmlParser);
        }
        return null;
    }

    /**
     * getNextText
     *
     * @param xmlParser KXmlParser
     * @return String
     */
    protected static String getNextText(KXmlParser xmlParser) {
        int iParserEvent = nextParserNode(xmlParser);
        while (iParserEvent != KXmlParser.END_DOCUMENT) {
            if (iParserEvent == KXmlParser.TEXT) {
                return xmlParser.getText();
            } else if (iParserEvent == KXmlParser.END_TAG) {
                return "";
            }
            iParserEvent = nextParserNode(xmlParser);
        }
        return null;
    }

    /**
     * getXmlContent
     *
     * @param data byte[]
     * @return String
     */
    protected static String getXmlContent(byte[] data) {
        if (data == null) {
            return "";
        }
        String strData = new String(data);
        int xmlTagIdx = strData.indexOf("<?xml");
        if (xmlTagIdx < 0) {
            return strData;
        }
        return strData.substring(xmlTagIdx);
    }
}
