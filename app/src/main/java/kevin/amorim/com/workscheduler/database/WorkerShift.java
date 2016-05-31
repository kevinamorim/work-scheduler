package kevin.amorim.com.workscheduler.database;

public class WorkerShift {

    /*
        Database fields
     */

    private int id;
    private int workerId;
    private int shiftId;

    public WorkerShift() {

    }

    public WorkerShift(int id, int workerId, int shiftId) {
        this.id = id;
        this.workerId = workerId;
        this.shiftId = shiftId;
    }

    public WorkerShift(int workerId, int shiftId) {
        this.workerId = workerId;
        this.shiftId = shiftId;
    }

}
