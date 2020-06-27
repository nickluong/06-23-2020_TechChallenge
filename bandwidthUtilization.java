import java.io.File;
import java.util.*;

public class serverInfo {

    // server instance variables
    public String serverName;
    public String interfaceName;
    public double bandwidth;

    // bandwidth utilization variables
    public List<serverInfo> serverList;
    public List<List<String>> bandwidths;
    public double bandwidthUtilization;

    public serverInfo() {
        this.serverName = "";
        this.interfaceName = "";
        this.bandwidth = 0.0;
        this.bandwidthUtilization = 0.0;
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

    public double getBandwidthUtilization() {
        return this.bandwidthUtilization;
    }

    public List<serverInfo> getServerList(){
        return this.serverList;
    }

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

    // takes in list of server bandwidth info and generates list of server info
    public void generateServerList(List<List<String>> bandwidths){
        List<serverInfo> servers = new ArrayList<serverInfo>();
        for (List<String> val : bandwidths){
            serverInfo newServer = new serverInfo();
            newServer.setServerName(val.get(0));
            newServer.setInterfaceName(val.get(1));
            newServer.setBandwidth(Double.parseDouble(val.get(2)));
            servers.add(newServer);
        }
        serverList = servers;
    }


    public static void main(String[] args) {
        serverInfo server = new serverInfo();
        server.getBandwidths();
        server.generateServerList(server.bandwidths);
        for(serverInfo s: server.getServerList()){
            System.out.println("---------------------------");
            System.out.println(s.serverName);
            System.out.println(s.interfaceName);
            System.out.println(s.bandwidth);
        }
    }

}


