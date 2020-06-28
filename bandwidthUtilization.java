import java.io.File;
import java.util.*;

public class serverInfo {

    // server instance variables
    private String serverName;
    private String interfaceName;
    private double bandwidth;

    // bandwidth utilization variables
    public List<serverInfo> serverList;
    public List<List<String>> bandwidths;

    public serverInfo() {
        this.serverName = "";
        this.interfaceName = "";
        this.bandwidth = 0.0;
    }

    // setters

    public void setServerName(String name){
        this.serverName = name;
    }

    public void setInterfaceName(String name){
        this.interfaceName = name;
    }

    public void setBandwidth(double num){
        this.bandwidth = num;
    }

    // getters

    public String getServerName(){
        return this.serverName;
    }

    public String getInterfaceName(){
        return this.interfaceName;
    }

    public double getBandwidth(){
        return this.bandwidth;
    }

    public List<serverInfo> getServerList(){
        return this.serverList;
    }

    // creates arraylist of bandwidth info
    public void getBandwidths() {
        List<List<String>> records = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("data/bandwidth.csv"));) {
            while (scanner.hasNextLine()) {
                records.add(getRecordFromLine(scanner.nextLine()));
            }
            records.remove(0);
        }
        catch (Exception e){
            System.out.println("File not found");
        }
        this.bandwidths = records;
    }

    // csv line reading helper
    private List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<String>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(",");
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }

    //create arraylist of netbitrate info
    //loop through netbitrates, calculating and printing utilization every line
    public void printBandwidthUtilization() {
        List<List<String>> netbitrates = new ArrayList<>();
        List<serverInfo> serverInfoList = this.getServerList();
        try (Scanner scanner = new Scanner(new File("data/netbitrate.csv"));) {
            while (scanner.hasNextLine()) {
                netbitrates.add(getRecordFromLine(scanner.nextLine()));
            }
            netbitrates.remove(0); // remove first line
            for (List<String> val : netbitrates){
               String serverName = val.get(1);
               String interfaceName = val.get(2);
               double netbitrate = Double.parseDouble(val.get(3));
               double bandwidth = findServerBandwidth(serverInfoList, serverName, interfaceName);
               val.set(3, netbitrate/bandwidth+"");
               System.out.println(val);
            }
        }
        catch (Exception e){
            System.out.println("File not found");
        }
    }

    // return bandwidth of specific server by checking server name and interface name
    public double findServerBandwidth(List<serverInfo> serverList, String serverName, String interfaceName) {
        for(serverInfo s : serverList){
             if(s.getServerName().equals(serverName) && s.getInterfaceName().equals(interfaceName)){
                 return s.getBandwidth();
             }
        }
        return 0.0;
    }


    // takes in list of server bandwidth info and generates list of server info
    // makes checking server properties easier while matching up server and interface names
    public void generateServerList(){
        List<serverInfo> servers = new ArrayList<serverInfo>();
        for (List<String> val : this.bandwidths){
            serverInfo newServer = new serverInfo();
            newServer.setServerName(val.get(0));
            newServer.setInterfaceName(val.get(1));
            newServer.setBandwidth(Double.parseDouble(val.get(2)));
            servers.add(newServer);
        }
        serverList = servers;
    }


    //creates serverInfo instance, generates list of serverInfo from reading bandwidth.csv, prints calculated bandwidth utilization
    public static void main(String[] args) {
        serverInfo server = new serverInfo();
        server.getBandwidths();
        server.generateServerList();
        server.printBandwidthUtilization();
    }

}


