import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Queue;

/**
 * Created by Aaron Colborg on 10/20/2016.
 */
public class ProcessList {
    public ArrayList<Process> getProcessSort() {
        return processSort;
    }



    public void addProcess(Process process){
        processSort.add(process);
        processSortArrival();
    }

    private void processSortArrival(){
        Collections.sort(processSort, new Comparator<Process>() {
            @Override
            public int compare(Process process1, Process process2)
            {
                return process1.getStartTime() - (process2.getStartTime());
            }
        });
    }

    public void printList(){
        for(Process p : processSort){
            System.out.println("[id: " + p.getProcessId() + ", startTime: " + p.getStartTime() + ", burstDuration: " + p.getBurstDuration() +"]");
        }
    }
    public void setProcessSort(ArrayList<Process> processSort) {
        this.processSort = processSort;
    }

    ArrayList<Process> processSort = new ArrayList<>();
    public int size(){
        return processSort.size();
    }

    public void putQueue(){

    }

    public ProcessList() {

    }


}
