package DAL.DAObjects;

import DAL.DTObjects.*;
import java.util.List;

public interface IDAO {


    public IDTO getObj(String[] keys);

    public IDTO loadObjectFromDB(String[] key);

    public boolean storeObjToDB(IDTO obj);

    public boolean updateObj(IDTO obj, int[] valsToUpdate);

    public boolean deleteObj(String[] keys);

    public List<IDTO> getAllObjsFromDB();

    public void loadAllObjsFromDB();

    public void freeCache();

    public void deleteDB();



}
