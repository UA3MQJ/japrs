package loc;
import java.io.IOException;
import javax.microedition.io.Connector;
import javax.microedition.sensor.*;

public class SensorApi implements DataListener {

    private int[] channels;
    private String[] channelNames;
    private SensorConnection sensor;
    private String URL; //URL для коннекта
    private int numChannels; //количество каналов
    private int dataType; //тип приёма данных
    private double minValue,//минимальное значение
            maxValue; //максимальное значение

    public SensorApi() {
        getInfo();
    }
//0 - заряд батареи,
//1 - мощность сигнала,
//2 - шаги в секунду,
//3 - количество шагов (за день),
//4 - данные акселерометра.

    public static String getSensor(int mode) {
        String sensor = "battery_charge";
        switch (mode) {
            case 1:
                sensor = "network_field_intensity";
                break;
            case 2:
                sensor = "com.sonyericsson.io.sensor.steps_per_minute";
                break;
            case 3:
                sensor = "step_count";
                break;
            case 4:
                sensor = "acceleration";
                break;
        }
        try {
            SensorInfo[] info = SensorManager.findSensors(sensor, null);
            SensorConnection sc = (SensorConnection) Connector.open(info[0].getUrl());
            Data data[] = sc.getData(1);
            return String.valueOf(data[0].getIntValues()[0]);
        } catch (Throwable ex) {
            //ex.printStackTrace();
            return "0";
        }
    }

    private void getInfo() {
        //Получаем список ВСЕХ сенсоров
        SensorInfo[] info = SensorManager.findSensors(null, null);
        if (info == null || info.length == 0) {
            return; //ничего не нашли, выходим
        }        //Сканируем найденные сенсоры
        for (int i = 0; i < info.length; i++) {
            //Узнаём тип устройства
            String quantity = info[i].getQuantity().toLowerCase();
            //Нам нужен acceleration
            if (quantity.startsWith("accel")) {
                //Нашли девайс
                SensorInfo s = info[i];
                //Получаем URL для коннекта с ним
                URL = s.getUrl();
                //Получаем каналы приёма данных
                ChannelInfo[] ci = s.getChannelInfos();
                //Получаем количество каналов
                numChannels = ci.length;
                //Инициализируем переменные
                channels = new int[numChannels];
                channelNames = new String[numChannels];
                //Получаем тип приёма данных (TYPE_DOUBLE = 1, TYPE_INT = 2, TYPE_OBJECT = 4)
                dataType = ci[0].getDataType();
                MeasurementRange mr = ci[0].getMeasurementRanges()[0];
                //Получаем минимальное значение
                minValue = mr.getSmallestValue();
                //Получаем максимальное значение
                maxValue = mr.getLargestValue();
                //Получаем названия каналов (axis_x, axis_y, axis_z)
                for (int j = 0; j < numChannels; j++) {
                    channelNames[j] = ci[j].getName();
                }
            }
        }
        //Для того, чтобы подключить DataListener, нужно соединение
        try {
            //Открываем соединение, по раннее полученному URL
            sensor = (SensorConnection) Connector.open(URL);
            sensor.setDataListener(this, 1); //1 - размер буфера
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public void dataReceived(SensorConnection sensor, Data[] data, boolean isDataLost) {
        //Обработка данных
        for (int i = 0; i < data.length; i++) {
            //Получаем имя канала и сравниваем с сохраненными раннее
            //Данные получаем в виде int от -1024 до 1024
            for (int j = 0; j < numChannels; j++) {
                String name = data[i].getChannelInfo().getName();
                if (name.equals(channelNames[j])) {
                    channels[j] = getValue(data[i]);
                }
            }
        }
    }

    /**
     * Преобразование в int [-1024...+1024]
     */
    private int getValue(Object o) {
        int value = 0;
        double doublevalue = 0;
        if (dataType == ChannelInfo.TYPE_INT) {
            doublevalue = ((Data) o).getIntValues()[0];
        } else if (dataType == ChannelInfo.TYPE_DOUBLE) {
            doublevalue = ((Data) o).getDoubleValues()[0];
        }
        if (doublevalue >= 0) {
            value = (int) (doublevalue * 1024 / maxValue);
        } else {
            value = (int) (doublevalue * (-1024) / minValue);
        }

        return value;
    }
}
