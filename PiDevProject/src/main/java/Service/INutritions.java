package Service;

public interface INutritions <N>
{
    public void add(N n);
    public void update(int identifiant , String plan);
    public void delete(int identifiant);
    public void readall();
}
