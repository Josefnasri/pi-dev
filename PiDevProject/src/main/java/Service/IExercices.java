package Service;

public interface IExercices<E>
{
    public void add(E e);
    public void delete(int ID );
    public void update(int id ,String type , String titre);
    public void readall();
}
