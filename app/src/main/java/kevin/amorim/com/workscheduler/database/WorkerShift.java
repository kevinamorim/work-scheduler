package kevin.amorim.com.workscheduler.database;

public class WorkerShift {

    private int id;
    private Worker worker;
    private Shift shift;

    public WorkerShift() {

    }

    public WorkerShift(int id, Worker worker, Shift shift) {
        this.id = id;
        this.worker = worker;
        this.shift = shift;
    }

    public WorkerShift(Worker worker, Shift shift) {
        this.worker = worker;
        this.shift = shift;
    }

}
