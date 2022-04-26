package xyz.flevarakis.arubatranquility.models.atq;

import java.io.Serializable;

public class Metrics implements Serializable {
    private int connected_count;
    private int device_up;
    private int wan_tunnels_no_issue;
    private int wan_uplinks_no_issue;
    private int device_down;
    private int failed_count;
    private int wan_tunnels_down;
    private int wan_uplinks_down;
    private int normal;
    private int warning;
    private int critical;
    private int total_sites;

    public int getConnectedCount() {
        return connected_count;
    }

    public int getDeviceUp() {
        return device_up;
    }

    public int getWanTunnelsNoIssue() {
        return wan_tunnels_no_issue;
    }

    public int getWanUplinksNoIssue() {
        return wan_uplinks_no_issue;
    }

    public int getDeviceDown() {
        return device_down;
    }

    public int getFailedCount() {
        return failed_count;
    }

    public int getWanTunnelsDown() {
        return wan_tunnels_down;
    }

    public int getWanUplinksDown() {
        return wan_uplinks_down;
    }

    public int getNormal() {
        return normal;
    }

    public int getWarning() {
        return warning;
    }

    public int getCritical() {
        return critical;
    }

    public int getTotalSites() {
        return total_sites;
    }
}
