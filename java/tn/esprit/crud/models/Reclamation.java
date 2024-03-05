package entities;

import java.sql.Date;

public class Reclamation {
    private int request_id;
    private Date request_date;
    private int customer_id;
    private String description;
    private String status;

    public int getRequest_id() {
        return request_id;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }

    public Date getRequest_date() {
        return request_date;
    }

    public void setRequest_date(Date request_date) {
        this.request_date = request_date;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                "request_id=" + request_id +
                ", request_date=" + request_date +
                ", customer_id=" + customer_id +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public Reclamation() {
    }

    public Reclamation(int request_id, Date request_date, int customer_id, String description, String status) {
        this.request_id = request_id;
        this.request_date = request_date;
        this.customer_id = customer_id;
        this.description = description;
        this.status = status;
    }

    public Reclamation(Date request_date, int customer_id, String description, String status) {
        this.request_date = request_date;
        this.customer_id = customer_id;
        this.description = description;
        this.status = status;
    }
}
