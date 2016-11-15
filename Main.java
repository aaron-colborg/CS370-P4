import java.io.*;
import java.text.DecimalFormat;
import java.util.*;


/**
 * Created by Aaron Colborg on 10/20/2016.
 */
public class Main {

    static ProcessList sortedList = new ProcessList();
    //Handle quantum arg
    int quantum;

    public static void main(String[] args) {
        //Ask what sorting Alg they want
        Main main = new Main();
        main.quantum = Integer.parseInt(args[1]);
        Scanner input = new Scanner(System.in);
        System.out.print("Which scheduling algorithm would you like to use? (FCFS, PRESJF, RR): ");
        String userInput = input.nextLine();
        System.out.println(userInput);


        //All the setup for file reading////////////////////////
        BufferedReader br;
        FileInputStream fstream = null;
        try {
            fstream = new FileInputStream(args[0]);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
        br = new BufferedReader(new InputStreamReader(fstream));
        String strLine;
        //Read File Line By Line//////////////////////////////
        try {
            while ((strLine = br.readLine()) != null) {
                // Print the content on the console
                //System.out.println (strLine);
                //Seperate the line, [id, arrival, burst]
                List<String> processList = Arrays.asList(strLine.split(","));
                Process hold = new Process(Integer.parseInt(processList.get(1)), Integer.parseInt(processList.get(2)), Integer.parseInt(processList.get(0)));
                sortedList.addProcess(hold);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        //Close the input stream////////////////////////////////
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        /////////////ProcessList now contains all processes in file sorted based on arrival time//////////////////
        //Print the ProcessList
        //sortedList.printList();
        main.jobDispatcher(userInput, Integer.parseInt(args[1]));
    }

    /**
     * just send the program the the correct job handling method
     *
     * @param input
     */
    private void jobDispatcher(String input, int timeQuantum) {
        if (input.equals("FCFS")) {
            fcfsRunner();
        }
        if (input.equals("PRESJF")) {
            presjfRunner(timeQuantum);
        }
        if (input.equals("RR")) {
            rrRunner(timeQuantum);
        }
    }


    /**
     * Computes the order for FCFS and prints the table
     * Calc waiting time / turnaround time / throughput
     */
    private void fcfsRunner() {
        System.out.println("======================================================================");
        System.out.println("PID         StBurst         Eburst");
        int lastEndBurst = sortedList.getProcessSort().get(0).getStartTime();
        double wait = 0;
        double turnaround = 0;
        double finishTime = sortedList.getProcessSort().get(0).getBurstDuration() + sortedList.getProcessSort().get(0).getStartTime();
        double totalNeeded = 0;
        double count = 0;
        double turn = 0;

        for (Process p : sortedList.getProcessSort()) {
            count++;
            if (p.getStartTime() < lastEndBurst) {
                //System.out.println("ASDASD: " + lastEndBurst);
                System.out.println(p.getProcessId() + "             " + lastEndBurst + "              " + (lastEndBurst + p.getBurstDuration()));

                wait += (lastEndBurst - p.getStartTime());
                turnaround += ((lastEndBurst + p.getBurstDuration()) - p.getStartTime());
                if (((lastEndBurst + p.getBurstDuration()) > finishTime)) {
                    finishTime = lastEndBurst + p.getBurstDuration();
                }
                totalNeeded += p.getBurstDuration();
                lastEndBurst = lastEndBurst + p.getBurstDuration();
            } else {
                System.out.println(p.getProcessId() + "             " + p.getStartTime() + "              " + (p.getStartTime() + p.getBurstDuration()));
                turnaround += ((p.getStartTime() + p.getBurstDuration()) - p.getStartTime());
                if ((p.getStartTime() + p.getBurstDuration()) > finishTime) {
                    finishTime = p.getStartTime() + p.getBurstDuration();
                }
                lastEndBurst = p.getStartTime() + p.getBurstDuration();
                totalNeeded += p.getBurstDuration();
            }
            turn = turn + (lastEndBurst - p.getStartTime());
        }

        //Calc waiting time / turnaround time / throughput
        //Wait time = time started - time arrived
        double avgWait = wait / sortedList.size();
        System.out.println("\nAverage waiting time: " + String.format("%.3f", avgWait));
        double avgTurn = turnaround / sortedList.size();
        System.out.println("\nThroughput: " + (count / lastEndBurst));
        System.out.println("\nCPU Util: %" + (totalNeeded / lastEndBurst) * 100);
        System.out.println("\nAverage Turnaround Time: " + String.format("%.3f", (turn / sortedList.size())));
        System.out.println("======================================================================");
        System.exit(0);
    }

    /**
     * Computes the order for PRESJF and prints the table
     */
    private void presjfRunner(int timeQuantum) {
        System.out.println("======================================================================");
        System.out.println("PID         StBurst         Eburst");
        double wait = 0;
        double turnaround = 0;
        double totalNeeded = 0;
        double count = 0;
        double turn = 0;
        boolean hasItem = hasItem();
        int i = 0;
        int move = 0;
        int lastEndBurst = sortedList.getProcessSort().get(0).getStartTime();
        int actualStart = 0;

            Process p = getShortestThatArrived(lastEndBurst);
            //System.out.println("pid: " + p.getProcessId() + "pd: " + p.getBurstDuration());

            if (p.getStartTime() > lastEndBurst) {
                if (move == i) {
                    //System.out.println("here");
                    lastEndBurst                 } else if (move != i) {
                    //System.out.println("here");
                    move = i;
                    i = 0;
                    //  0) {
                if (p.getBurstDuration() > timeQuantum) {
                    if (p.getStartTime() > lastEndBurst) {
                        System.out.println(p.getProcessId() + "            " + p.getStartTime() + "              " + (p.getStartTime() + timeQuantum));
                        lastEndBurst = p.getStartTime() + timeQuantum;
                        p.setBurstDuration(p.getBurstDuration() - timeQuantum);
                    } else if (p.getStartTime() <= lastEndBurst) {
                        System.out.println(p.getProcessId() + "            " + lastEndBurst + "              " + (dBurst = lastEndBurst + timeQuantum;
                        p.setBurstDuration(p.getBurstDuration() - timeQuantum);
                    }
                } else if (p.getBurstDuration() <= timeQuantum) {
                    if (p.getStartTime() > lastEndBurst) {
                        System.out.println(p.getProcessId() + "             " + p.getStartTime() + "              " + (p.getStartTime() + p.getBurstDuration()));
                        lastEndBurst = p.getStartTime() + p.getBurstDuration();
                        p.setBurstDuration(p.getBurstDuration() - p.getBurstDuration());
                    } else if (p.getStartTime() <= lastEndBurst) {
                        //System.out.println("this");
                        System.out.println(p.getProcessId() + "             " + lastEndBurst + "              " + (lastEndBurst + p.getBurstDuration()));
                        lastEndBurst = lastEndBurst + p.getBurstDuration();
                        p.setBurstDuration(p.getBurstDuration() - p.getBurstDuration());
                    }
                }
                //calc wait time
                if (p.getStartTime() > actualStart) {

                } else {
                    wait += (actualStart - p.getNewStartTime());
                }

                //System.out.println("as: " + actualStart + "  gns: " + p.getNewStartTime() + "  wait: " + wait);


                if (p.getStartTime() > lastEndBurst) {
                    actualStart = p.getStartTime();
                } else {
                    actualStart = lastEndBurst;
                }

                p.setNewStartTime(lastEndBurst);
            }
            turn = turn + (lastEndBurst - p.getStartTime());
            hasItem = hasItem();
        }

        double avgWait = wait / sortedList.size();
        System.out.println("\nAverage waiting time: " + String.format("%.3f", avgWait));
        count = sortedList.size();
        System.out.println("\nThroughput: " + (count / lastEndBurst));
        System.out.println("\nAverage Turnaround Time: " + String.format("%.3f", (turn / sortedList.size())));
        System.out.println("======================================================================");
    }

    private Process getShortestThatArrived(int lastEndBurst) {
        int i = 0;
        Process r = sortedList.getProcessSort().get(0);
        for (i = 0; i < sortedList.getProcessSort().size(); i++) {
            Process p = sortedList.getProcessSort().get(i);
            if (p.getBurstDuration() != 0) {
                if (p.getStartTime() <= lastEndBurst) {
                    if (p.getBurstDuration() < r.getBurstDuration()) {
                        r = p;
                    }
                } else {

                }
            } else {
                r = sortedList.getProcessSort().get(i + 1);
            }
        }
        //System.out.println("r: " + r.getProcessId());
        return r;
    }

    private boolean hasItem() {
        boolean r = false;
        for (Process p : sortedList.getProcessSort()) {
            if (p.getBurstDuration() > 0) {
                r = true;
                return r;
            } else {

            }
        }
        return r;
    }


    /**
     * Computes the order for RR and prints the table
     */
    private void rrRunner(int timeQuantum) {
        System.out.println("======================================================================");
        System.out.println("PID         StBurst         Eburst");
        int hold = 1;
        int lastEndBurst = sortedList.getProcessSort().get(0).getStartTime();
        int i = 0;
        int move = 0;
        double wait = 0;
        double turnaround = 0;
        double finishTime = sortedList.getProcessSort().get(0).getBurstDuration() + sortedList.getProcessSort().get(0).getStartTime();
        double totalNeeded = 0;
        double count = 0;
        double turn = 0;
        int actualStart = 0;

        while (hold != 0) {
            hold = 0;
            for (i = 0; i < sortedList.getProcessSort().size(); i++) {
                Process p = sortedList.getProcessSort().get(i);
                //System.out.println("i: "+ i);
                if (p.getStartTime() > lastEndBurst) {
                    if (move == i) {
                        //System.out.println("here");
                        lastEndBurst = p.getStartTime();
                        //System.out.println("ls: " + lastEndBurst);
                    } else if (move != i) {
                        // System.out.println("here");
                        move = i;
                        i = 0;
                        // System.out.println("here after i: " + i);
                        break;
                    }
                }
                if (p.getBurstDuration() != 0) {
                    //System.out.println("here2");
                    hold = 1;
                    if (p.getBurstDuration() > timeQuantum) {
                        if (p.getStartTime() > lastEndBurst) {
                            System.out.println(p.getProcessId() + "            " + p.getStartTime() + "              " + (p.getStartTime() + timeQuantum));
                            lastEndBurst = p.getStartTime() + timeQuantum;
                            p.setBurstDuration(p.getBurstDuration() - timeQuantum);
                            actualStart = p.getStartTime();
                        } else if (p.getStartTime() <= lastEndBurst) {
                            System.out.println(p.getProcessId() + "            " + lastEndBurst + "              " + (lastEndBurst + timeQuantum));
                            lastEndBurst = lastEndBurst + timeQuantum;
                            p.setBurstDuration(p.getBurstDuration() - timeQuantum);
                            actualStart = lastEndBurst;
                        }
                    } else if (p.getBurstDuration() <= timeQuantum) {
                        if (p.getStartTime() > lastEndBurst) {
                            System.out.println(p.getProcessId() + "             " + p.getStartTime() + "              " + (p.getStartTime() + p.getBurstDuration()));
                            lastEndBurst = p.getStartTime() + p.getBurstDuration();
                            p.setBurstDuration(p.getBurstDuration() - p.getBurstDuration());
                            actualStart = p.getStartTime();
                        } else if (p.getStartTime() <= lastEndBurst) {
                            System.out.println(p.getProcessId() + "             " + lastEndBurst + "              " + (lastEndBurst + p.getBurstDuration()));
                            lastEndBurst = lastEndBurst + p.getBurstDuration();
                            p.setBurstDuration(p.getBurstDuration() - p.getBurstDuration());
                            actualStart = lastEndBurst;
                        }
                        turn = turn + (lastEndBurst - p.getStartTime());
                    }
                } else if (p.getBurstDuration() == 0) {
                    hold = 0;
                }
                //calc wait time
                wait += (actualStart - p.getNewStartTime());
                p.setNewStartTime(lastEndBurst);
            }
        }

        //Calc waiting time / turnaround time / throughput
        //Wait time = time started - time arrived
        double avgWait = wait / sortedList.size();
        System.out.println("\nAverage waiting time: " + String.format("%.3f", avgWait));
        double avgTurn = turnaround / sortedList.size();
        count = sortedList.size();
        System.out.println("\nThroughput: " + (count / lastEndBurst));
        System.out.println("\nAverage Turnaround Time: " + String.format("%.3f", (turn / sortedList.size())));
        System.out.println("======================================================================");
        System.exit(0);
    }

    private static DecimalFormat df2 = new DecimalFormat(".##");


}
