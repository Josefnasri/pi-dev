package tn.esprit.crud.models;
import java.util.Date;




public class CodePromo {

    private int id;
    private int code;
    private Date date_exp;
    private int utilise;
    private int user_id;


    public void setdate_exp(Date date_exp) {
        this.date_exp = date_exp;
    }



    public CodePromo(  int code, Date date_exp, int utilise, int user_id) {

        this.code = code;
        this.date_exp = date_exp;
        this.utilise = utilise;
        this.user_id = user_id;

    }


    public CodePromo() {
        this.id = id;
        this.code = code;
        this.date_exp = date_exp;
        this.utilise = utilise;
        this.user_id = user_id;


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

  public java.sql.Date getDate_exp() {this.date_exp = date_exp;
      return null;
  }

    public void setDate_exp(Date date_exp) {this.date_exp = date_exp;}

    public int getUtilise() {
        return utilise;
    }

    public void setUtilise(int utilise) {
        this.utilise = utilise;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "CodePromo{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", date_exp=" + date_exp +
                ", utilise=" + utilise +
                ", user_id=" + user_id +
                '}';
    }
}
