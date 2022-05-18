package DAL.DAObjects;

import DAL.DTO.*;
import java.util.List;

public interface IDAO {


    public DTO getObj(String[] keys);

    public DTO loadObjectFromDB(String[] key);

    public boolean storeObjToDB(DTO obj);

    public boolean updateObj(DTO obj);

    public boolean deleteObj(String[] keys);

    public List<DTO> getAllObjsFromDB();

    public void loadAllObjsFromDB();

    public void freeCache();

    public void deleteDB();



}
