package com.paksoftwares.trialy;

import java.util.List;

public class LicenseResponse {
    private List<License> licenses;

    public List<License> getLicenses() {
        return licenses;
    }

    public void setLicenses(List<License> licenses) {
        this.licenses = licenses;
    }

    public static class License {
        private String license_id;
        private String expiry_date;
        private String start_time;
        private String last_launch_time;
        private int launch_count;
        private boolean active;

        public String getLicense_id() {
            return license_id;
        }

        public void setLicense_id(String license_id) {
            this.license_id = license_id;
        }

        public String getExpiry_date() {
            return expiry_date;
        }

        public void setExpiry_date(String expiry_date) {
            this.expiry_date = expiry_date;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getLast_launch_time() {
            return last_launch_time;
        }

        public void setLast_launch_time(String last_launch_time) {
            this.last_launch_time = last_launch_time;
        }

        public int getLaunch_count() {
            return launch_count;
        }

        public void setLaunch_count(int launch_count) {
            this.launch_count = launch_count;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }
    }
}