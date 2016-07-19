package colors.com.example.firstplace.coloreyeze;

/**
 * Created by FirstPlace on 12/07/2016.
 */
public class Device {
    private String deviceName;
    private String deviceIP;
    private long deviceId;
    private String deviceColor;
    private String apiKey;
    private int devicePixels;

    public int getDevicePixels() {
        return devicePixels;
    }

    public void setDevicePixels(int devicePixels) {
        this.devicePixels = devicePixels;
    }
    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceIP() {
        return deviceIP;
    }

    public void setDeviceIP(String deviceIP) {
        this.deviceIP = deviceIP;
    }

    public long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceColor() {
        return deviceColor;
    }

    public void setDeviceColor(String deviceColor) {
        this.deviceColor = deviceColor;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }



}
