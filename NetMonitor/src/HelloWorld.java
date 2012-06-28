
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Form;
import javax.microedition.midlet.MIDlet;
import loc.*;

public class HelloWorld extends MIDlet implements Runnable {

    Display display;
    Form form;

    public void startApp() {
        display = Display.getDisplay(this);
        form = new Form("NetMonitor");

        display.setCurrent(form);
        new Thread(this).start();
    }

    public void run() {
//проверяем не Нокиа ли
        if (!Location.reallyNull(Location.lac)) {
            //обновляем данные сети
            Location.getData();
            //получаем координаты
            Location.getCoordinates();
            //если есть доступ к силе сигнала
            if (!Location.reallyNull(SystemUtil.signal())) {
                form.append(("Нетмонитор: ") + "\nКод страны: " + String.valueOf(Location.mcc) + " \nКод сети: " + String.valueOf(Location.mnc) + " \nКод соты: " + String.valueOf(Location.lac) + " \nКод БC: " + String.valueOf(Location.cid) + " \nСила сигнала: " + SystemUtil.signal() + " \n");
            } //иначе
            else {
                form.append("Нетмонитор: " + "\nКод страны: " + String.valueOf(Location.mcc) + " \nКод сети: " + String.valueOf(Location.mnc) + " \nКод соты: " + String.valueOf(Location.lac) + " \nКод БС: " + String.valueOf(Location.cid) + " \n");
            }
        }
//прочие данные
        String txt = SystemUtil.nativeDigitSupport();

        if (!Location.reallyNull(txt)) {
            form.append(txt + "\n");
        }
        txt = SystemUtil.operatorName();

        if (!Location.reallyNull(txt)) {
            form.append(txt + "\n");
        }
        txt = SystemUtil.serviceProvider();

        if (!Location.reallyNull(txt)) {
            form.append(txt + "\n");
        }
        txt = SystemUtil.traffic();

        if (!Location.reallyNull(txt)) {
            form.append(txt + "\n");
        }
        txt = SystemUtil.gid1();

        if (!Location.reallyNull(txt)) {
            form.append(txt + "\n");
        }
        txt = SystemUtil.gid2();

        if (!Location.reallyNull(txt)) {
            form.append(txt + "\n");
        }
         //геоданные
        form.append("Улица: ".concat(String.valueOf(Location.getStreet().concat(" \n"))));
        form.append("Город: ".concat(String.valueOf(Location.getCity().trim().concat(" \n"))));
        form.append("Область: ".concat(String.valueOf(Location.getArea().concat(" \n"))));
        form.append("Страна: ".concat(String.valueOf(Location.getCountry().concat(" \n"))));
        form.append("Долгота: ".concat(String.valueOf(Location.getLongitude().concat(" \n"))));
        form.append("Широта: ".concat(String.valueOf(Location.getLatitude().concat(" \n"))));
        form.append("Высота над у.м.: ".concat(String.valueOf(Location.getElevation().concat(" м \n"))));
        //для спотсменов бонус
        String sens = System.getProperty("microedition.sensor.version");
        if (sens != null && sens.length() != 0 && !sens.equals("null")) {
            sens = SensorApi.getSensor(3);

            if (sens != null && sens.length() != 0 && !sens.equals("null") && !sens.equals("0")) {
                form.append("Сегодня пеших шагов: " + String.valueOf(sens) + " \n");
            }
        }


    }

    public void pauseApp() {
    }

    public void destroyApp(boolean flag) {
    }
}