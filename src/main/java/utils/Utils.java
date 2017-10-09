package utils;

import dto.OrchestraDTOSortable;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;

public class Utils {

    public static String encode(String valueToEnc) throws Exception {
        return Base64.getEncoder().encodeToString(valueToEnc.getBytes());
    }

    public static String decode(String encryptedValue) throws Exception {
        byte[] decode = Base64.getDecoder().decode(encryptedValue.getBytes());
        return new String(decode);
    }


    @SuppressWarnings("unchecked")
    public static <T extends OrchestraDTOSortable> ArrayList<T> sortAndRemove(OrchestraDTOSortable[] obj, boolean sortByName) {
        List<T> ret = new ArrayList<T>();
        for (OrchestraDTOSortable orchestraDTOSortable : obj) {
            ret.add((T) orchestraDTOSortable);
        }

        if (sortByName) {
            ret.sort(Comparator.comparing(OrchestraDTOSortable::getName));
        } else {
            ret.sort((arg0, arg1) -> {
                Integer i1 = arg0.getId();
                Integer i2 = arg1.getId();
                return i1.compareTo(i2);
            });
        }

        // Remove casual called (J8 FTW)
        ret.removeIf(p -> p.getName().toLowerCase().equals("casual caller"));
        return (ArrayList<T>) ret;
    }

    public static boolean isStrNullOrEmpty (String str){
        return str == null || str.isEmpty();
    }

    public static boolean isStrNullOrEmpty (String... str){
        boolean value = false;
        for (String sr: str) {
            if(isStrNullOrEmpty(sr))
                return true;
        }
        return false;
    }
}
