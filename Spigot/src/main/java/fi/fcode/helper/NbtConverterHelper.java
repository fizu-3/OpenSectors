package fi.fcode.helper;

import net.minecraft.server.v1_8_R3.MojangsonParseException;
import net.minecraft.server.v1_8_R3.MojangsonParser;
import net.minecraft.server.v1_8_R3.NBTTagCompound;

public class NbtConverterHelper {

    public static NBTTagCompound convertStringToNBTCompound(String nbtString) {
        try {
            return MojangsonParser.parse(nbtString);
        } catch (MojangsonParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}