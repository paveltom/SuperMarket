package DAL.DAObjects;

import DAL.DTO.DTO;
import DAL.IdentityMap;

import java.util.List;

public class TrucksDAO implements IDAO{

    private IdentityMap trucksIM;

    public TrucksDAO(){
        trucksIM = new IdentityMap();
    }


    public void updateTruckDiary(String key, String shifts){

    }

    @Override
    public DTO getObj(String[] keys) {
        return null;
    }

    @Override
    public DTO loadObjectFromDB(String[] key) {
        return null;
    }

    @Override
    public boolean storeObjToDB(DTO obj) {
        return false;
    }

    @Override
    public boolean updateObj(DTO obj, int[] valsToUpdate) {
        return false;
    }

    @Override
    public boolean deleteObj(String[] keys) {
        return false;
    }

    @Override
    public List<DTO> getAllObjsFromDB() {
        return null;
    }

    @Override
    public void loadAllObjsFromDB() {

    }

    @Override
    public void freeCache() {

    }

    @Override
    public void deleteDB() {

    }
}
