package DAL.DTO;

public class DriverDTO implements DTO
{
    public String Id;
    public String License;
    public String Zone;
    public String Name, Cellphone;
    public String Diary;
    public String FutureShifts;

    public DriverDTO(String id, String license, String zone,String name, String cellphone, String diary)
    {
        Id = id;
        License = license;
        Zone = zone;
        Name = name;
        Cellphone = cellphone;
        Diary = diary;
        FutureShifts = "";
    }


    @Override
    public String getKey() {
        return Id;
    }

    @Override
    public String toString() {
        return null;
    }

}
