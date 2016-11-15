/**
 * Created by Aaron Colborg on 10/20/2016.
 */
public class Process {
    public void setNewStartTime(int newStartTiem) {
        this.newStartTime = newStartTiem;
    }

    int startTime;

    public int getNewStartTime() {
        return newStartTime;
    }

    int newStartTime;
    int burstDuration;
    int processId;

    public Process(int startTime, int burstDuration, int processId){
        this.startTime = startTime;
        this.burstDuration = burstDuration;
        this.processId = processId;
        this.newStartTime = startTime;
    }

    public int getBurstDuration() {
        return burstDuration;
    }

    public void setBurstDuration(int burstDuration) {
        this.burstDuration = burstDuration;
    }

    public int getStartTime() {

        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getProcessId() {
        return processId;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }
}
