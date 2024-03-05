package entities;

import java.sql.Date;

public class Response {
    private int response_id;
    private int request_id;
    private Date response_date;
    private String response_text;
    private String response_status;

    public int getResponse_id() {
        return response_id;
    }

    public void setResponse_id(int response_id) {
        this.response_id = response_id;
    }

    public int getRequest_id() {
        return request_id;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }

    public Date getResponse_date() {
        return response_date;
    }

    public void setResponse_date(Date response_date) {
        this.response_date = response_date;
    }

    public String getResponse_text() {
        return response_text;
    }

    public void setResponse_text(String response_text) {
        this.response_text = response_text;
    }

    public String getResponse_status() {
        return response_status;
    }

    public void setResponse_status(String response_status) {
        this.response_status = response_status;
    }

    @Override
    public String toString() {
        return "Response{" +
                "response_id=" + response_id +
                ", request_id=" + request_id +
                ", response_date=" + response_date +
                ", response_text='" + response_text + '\'' +
                ", response_status='" + response_status + '\'' +
                '}';
    }

    public Response() {
    }

    public Response(int response_id, int request_id, Date response_date, String response_text, String response_status) {
        this.response_id = response_id;
        this.request_id = request_id;
        this.response_date = response_date;
        this.response_text = response_text;
        this.response_status = response_status;
    }

    public Response(int request_id, Date response_date, String response_text, String response_status) {
        this.request_id = request_id;
        this.response_date = response_date;
        this.response_text = response_text;
        this.response_status = response_status;
    }
}
