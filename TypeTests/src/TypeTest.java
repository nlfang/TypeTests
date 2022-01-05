public class TypeTest {
    private String url; //url of type test
    private String keyboard; //name of keyboard
    private String switches; //name of switches
    private String keycaps; //name of keycaps

    //Constructor
    public TypeTest(String url, String keyboard, String switches, String keycaps) {
        this.url = url;
        this.keyboard = keyboard;
        this.switches = switches;
        this.keycaps = keycaps;
    }

    public String getURL() {
        return this.url;
    }

    public String getKeyboard() {
        return this.keyboard;
    }

    public String getSwitches() {
        return this.switches;
    }

    public String getKeycaps() {
        return this.keycaps;
    }
}
