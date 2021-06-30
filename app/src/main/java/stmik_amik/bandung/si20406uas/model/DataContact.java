package stmik_amik.bandung.si20406uas.model;

public class DataContact {

    private String name;
    private String phone;

    private String key;

    public DataContact(){

    }

    public DataContact(String name, String phone){
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return " " + name + '\n' +
                " " + phone ;
    }
}
