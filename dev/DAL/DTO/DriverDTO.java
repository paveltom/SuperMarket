package DAL.DTO;

public class DriverDTO implements DTO
{
    public String Id;
    public String License;
    public String Zone;
    public String Name, Cellphone;
    public String Diary;

    public DriverDTO(String id, String license, String zone,String name, String cellphone, String diary)
    {
        Id = id;
        License = license;
        Zone = zone;
        Name = name;
        Cellphone = cellphone;
        Diary = diary;
    }


    @Override
    public String getKey() {
        return null;
    }

    @Override
    public String toString() {
        return null;
    }

}
