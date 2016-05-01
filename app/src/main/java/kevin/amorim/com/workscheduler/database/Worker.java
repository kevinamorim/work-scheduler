package kevin.amorim.com.workscheduler.database;

public class Worker {

    private int id;
    private String name;
    private int workHours;

    public Worker() {

    }

    public Worker(String name, int workHours) {
        this.name = name;
        this.workHours = workHours;
    }

    public Worker(int id, String name, int workHours) {
        this.id = id;
        this.name = name;
        this.workHours = workHours;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWorkHours(int workHours) {
        this.workHours = workHours;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getWorkHours() {
        return workHours;
    }
}
