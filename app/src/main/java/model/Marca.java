package model;

import com.google.android.gms.common.api.Response;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Marca extends Response implements Serializable {

    @SerializedName("ID")
    @Expose
    private int id;

    @SerializedName("NOME")
    @Expose
    private String marca;

    @SerializedName("Marcas")
    @Expose
    public List<Marca> Marcas = new ArrayList<>();

    public static ArrayList<Object> convert(JSONArray jsonArray){
        ArrayList<Object> listaMarca = new ArrayList<Object>();
        try{
            for(int i=0, l=jsonArray.length(); i<l; i++){
                listaMarca.add(jsonArray.get(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listaMarca;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String pegajson(){
        String jsonMarca = "{\n" +
                "  \"caminhao\" : [ {\n" +
                "    \"ID\" : 1,\n" +
                "    \"NOME\" : \"CHEVROLET\"\n" +
                "  }, {\n" +
                "    \"ID\" : 2,\n" +
                "    \"NOME\" : \"VOLKSWAGEN\"\n" +
                "  }, {\n" +
                "    \"ID\" : 3,\n" +
                "    \"NOME\" : \"FIAT\"\n" +
                "  }, {\n" +
                "    \"ID\" : 4,\n" +
                "    \"NOME\" : \"MERCEDES-BENZ\"\n" +
                "  }, {\n" +
                "    \"ID\" : 6,\n" +
                "    \"NOME\" : \"CHANA\"\n" +
                "  }, {\n" +
                "    \"ID\" : 13,\n" +
                "    \"NOME\" : \"FORD\"\n" +
                "  }, {\n" +
                "    \"ID\" : 14,\n" +
                "    \"NOME\" : \"HYUNDAI\"\n" +
                "  }, {\n" +
                "    \"ID\" : 16,\n" +
                "    \"NOME\" : \"KIA\"\n" +
                "  }, {\n" +
                "    \"ID\" : 23,\n" +
                "    \"NOME\" : \"TOYOTA\"\n" +
                "  }, {\n" +
                "    \"ID\" : 24,\n" +
                "    \"NOME\" : \"RENAULT\"\n" +
                "  }, {\n" +
                "    \"ID\" : 27,\n" +
                "    \"NOME\" : \"AGRALE\"\n" +
                "  }, {\n" +
                "    \"ID\" : 53,\n" +
                "    \"NOME\" : \"VOLVO\"\n" +
                "  }, {\n" +
                "    \"ID\" : 84,\n" +
                "    \"NOME\" : \"ISUZU\"\n" +
                "  }, {\n" +
                "    \"ID\" : 85,\n" +
                "    \"NOME\" : \"IVECO\"\n" +
                "  }, {\n" +
                "    \"ID\" : 131,\n" +
                "    \"NOME\" : \"UTILITARIOS AGRICOLAS\"\n" +
                "  }, {\n" +
                "    \"ID\" : 164,\n" +
                "    \"NOME\" : \"TRAILER\"\n" +
                "  }, {\n" +
                "    \"ID\" : 167,\n" +
                "    \"NOME\" : \"RANDON\"\n" +
                "  }, {\n" +
                "    \"ID\" : 242,\n" +
                "    \"NOME\" : \"SCANIA\"\n" +
                "  }, {\n" +
                "    \"ID\" : 261,\n" +
                "    \"NOME\" : \"OUTROS\"\n" +
                "  }, {\n" +
                "    \"ID\" : 263,\n" +
                "    \"NOME\" : \"ONIBUS\"\n" +
                "  }, {\n" +
                "    \"ID\" : 266,\n" +
                "    \"NOME\" : \"UTILITARIOS PESADOS\"\n" +
                "  }, {\n" +
                "    \"ID\" : 270,\n" +
                "    \"NOME\" : \"MOTOR-HOME\"\n" +
                "  }, {\n" +
                "    \"ID\" : 274,\n" +
                "    \"NOME\" : \"MAN\"\n" +
                "  }, {\n" +
                "    \"ID\" : 275,\n" +
                "    \"NOME\" : \"NAVISTAR\"\n" +
                "  }, {\n" +
                "    \"ID\" : 277,\n" +
                "    \"NOME\" : \"SINOTRUK\"\n" +
                "  }, {\n" +
                "    \"ID\" : 284,\n" +
                "    \"NOME\" : \"SCHIFFER\"\n" +
                "  }, {\n" +
                "    \"ID\" : 285,\n" +
                "    \"NOME\" : \"GUERRA\"\n" +
                "  }, {\n" +
                "    \"ID\" : 292,\n" +
                "    \"NOME\" : \"MICHIGAN\"\n" +
                "  } ],\n" +
                "  \"carros\" : [ {\n" +
                "    \"ID\" : 1,\n" +
                "    \"NOME\" : \"CHEVROLET\"\n" +
                "  }, {\n" +
                "    \"ID\" : 2,\n" +
                "    \"NOME\" : \"VOLKSWAGEN\"\n" +
                "  }, {\n" +
                "    \"ID\" : 3,\n" +
                "    \"NOME\" : \"FIAT\"\n" +
                "  }, {\n" +
                "    \"ID\" : 4,\n" +
                "    \"NOME\" : \"MERCEDES-BENZ\"\n" +
                "  }, {\n" +
                "    \"ID\" : 5,\n" +
                "    \"NOME\" : \"CITROEN\"\n" +
                "  }, {\n" +
                "    \"ID\" : 6,\n" +
                "    \"NOME\" : \"CHANA\"\n" +
                "  }, {\n" +
                "    \"ID\" : 7,\n" +
                "    \"NOME\" : \"HONDA\"\n" +
                "  }, {\n" +
                "    \"ID\" : 8,\n" +
                "    \"NOME\" : \"SUBARU\"\n" +
                "  }, {\n" +
                "    \"ID\" : 10,\n" +
                "    \"NOME\" : \"FERRARI\"\n" +
                "  }, {\n" +
                "    \"ID\" : 11,\n" +
                "    \"NOME\" : \"BUGATTI\"\n" +
                "  }, {\n" +
                "    \"ID\" : 12,\n" +
                "    \"NOME\" : \"LAMBORGHINI\"\n" +
                "  }, {\n" +
                "    \"ID\" : 13,\n" +
                "    \"NOME\" : \"FORD\"\n" +
                "  }, {\n" +
                "    \"ID\" : 14,\n" +
                "    \"NOME\" : \"HYUNDAI\"\n" +
                "  }, {\n" +
                "    \"ID\" : 15,\n" +
                "    \"NOME\" : \"JAC\"\n" +
                "  }, {\n" +
                "    \"ID\" : 16,\n" +
                "    \"NOME\" : \"KIA\"\n" +
                "  }, {\n" +
                "    \"ID\" : 17,\n" +
                "    \"NOME\" : \"GURGEL\"\n" +
                "  }, {\n" +
                "    \"ID\" : 18,\n" +
                "    \"NOME\" : \"DODGE\"\n" +
                "  }, {\n" +
                "    \"ID\" : 19,\n" +
                "    \"NOME\" : \"CHRYSLER\"\n" +
                "  }, {\n" +
                "    \"ID\" : 20,\n" +
                "    \"NOME\" : \"BENTLEY\"\n" +
                "  }, {\n" +
                "    \"ID\" : 21,\n" +
                "    \"NOME\" : \"SSANGYONG\"\n" +
                "  }, {\n" +
                "    \"ID\" : 22,\n" +
                "    \"NOME\" : \"PEUGEOT\"\n" +
                "  }, {\n" +
                "    \"ID\" : 23,\n" +
                "    \"NOME\" : \"TOYOTA\"\n" +
                "  }, {\n" +
                "    \"ID\" : 24,\n" +
                "    \"NOME\" : \"RENAULT\"\n" +
                "  }, {\n" +
                "    \"ID\" : 25,\n" +
                "    \"NOME\" : \"ACURA\"\n" +
                "  }, {\n" +
                "    \"ID\" : 26,\n" +
                "    \"NOME\" : \"ADAMO\"\n" +
                "  }, {\n" +
                "    \"ID\" : 27,\n" +
                "    \"NOME\" : \"AGRALE\"\n" +
                "  }, {\n" +
                "    \"ID\" : 28,\n" +
                "    \"NOME\" : \"ALFA ROMEO\"\n" +
                "  }, {\n" +
                "    \"ID\" : 29,\n" +
                "    \"NOME\" : \"AMERICAR\"\n" +
                "  }, {\n" +
                "    \"ID\" : 31,\n" +
                "    \"NOME\" : \"ASTON MARTIN\"\n" +
                "  }, {\n" +
                "    \"ID\" : 32,\n" +
                "    \"NOME\" : \"AUDI\"\n" +
                "  }, {\n" +
                "    \"ID\" : 34,\n" +
                "    \"NOME\" : \"BEACH\"\n" +
                "  }, {\n" +
                "    \"ID\" : 35,\n" +
                "    \"NOME\" : \"BIANCO\"\n" +
                "  }, {\n" +
                "    \"ID\" : 36,\n" +
                "    \"NOME\" : \"BMW\"\n" +
                "  }, {\n" +
                "    \"ID\" : 37,\n" +
                "    \"NOME\" : \"BORGWARD\"\n" +
                "  }, {\n" +
                "    \"ID\" : 38,\n" +
                "    \"NOME\" : \"BRILLIANCE\"\n" +
                "  }, {\n" +
                "    \"ID\" : 41,\n" +
                "    \"NOME\" : \"BUICK\"\n" +
                "  }, {\n" +
                "    \"ID\" : 42,\n" +
                "    \"NOME\" : \"CBT\"\n" +
                "  }, {\n" +
                "    \"ID\" : 43,\n" +
                "    \"NOME\" : \"NISSAN\"\n" +
                "  }, {\n" +
                "    \"ID\" : 44,\n" +
                "    \"NOME\" : \"CHAMONIX\"\n" +
                "  }, {\n" +
                "    \"ID\" : 46,\n" +
                "    \"NOME\" : \"CHEDA\"\n" +
                "  }, {\n" +
                "    \"ID\" : 47,\n" +
                "    \"NOME\" : \"CHERY\"\n" +
                "  }, {\n" +
                "    \"ID\" : 48,\n" +
                "    \"NOME\" : \"CORD\"\n" +
                "  }, {\n" +
                "    \"ID\" : 49,\n" +
                "    \"NOME\" : \"COYOTE\"\n" +
                "  }, {\n" +
                "    \"ID\" : 50,\n" +
                "    \"NOME\" : \"CROSS LANDER\"\n" +
                "  }, {\n" +
                "    \"ID\" : 51,\n" +
                "    \"NOME\" : \"DAEWOO\"\n" +
                "  }, {\n" +
                "    \"ID\" : 52,\n" +
                "    \"NOME\" : \"DAIHATSU\"\n" +
                "  }, {\n" +
                "    \"ID\" : 53,\n" +
                "    \"NOME\" : \"VOLVO\"\n" +
                "  }, {\n" +
                "    \"ID\" : 54,\n" +
                "    \"NOME\" : \"DE SOTO\"\n" +
                "  }, {\n" +
                "    \"ID\" : 55,\n" +
                "    \"NOME\" : \"DETOMAZO\"\n" +
                "  }, {\n" +
                "    \"ID\" : 56,\n" +
                "    \"NOME\" : \"DELOREAN\"\n" +
                "  }, {\n" +
                "    \"ID\" : 57,\n" +
                "    \"NOME\" : \"DKW-VEMAG\"\n" +
                "  }, {\n" +
                "    \"ID\" : 59,\n" +
                "    \"NOME\" : \"SUZUKI\"\n" +
                "  }, {\n" +
                "    \"ID\" : 60,\n" +
                "    \"NOME\" : \"EAGLE\"\n" +
                "  }, {\n" +
                "    \"ID\" : 61,\n" +
                "    \"NOME\" : \"EFFA\"\n" +
                "  }, {\n" +
                "    \"ID\" : 63,\n" +
                "    \"NOME\" : \"ENGESA\"\n" +
                "  }, {\n" +
                "    \"ID\" : 64,\n" +
                "    \"NOME\" : \"ENVEMO\"\n" +
                "  }, {\n" +
                "    \"ID\" : 65,\n" +
                "    \"NOME\" : \"FARUS\"\n" +
                "  }, {\n" +
                "    \"ID\" : 66,\n" +
                "    \"NOME\" : \"FERCAR\"\n" +
                "  }, {\n" +
                "    \"ID\" : 68,\n" +
                "    \"NOME\" : \"FNM\"\n" +
                "  }, {\n" +
                "    \"ID\" : 69,\n" +
                "    \"NOME\" : \"PONTIAC\"\n" +
                "  }, {\n" +
                "    \"ID\" : 70,\n" +
                "    \"NOME\" : \"PORSCHE\"\n" +
                "  }, {\n" +
                "    \"ID\" : 72,\n" +
                "    \"NOME\" : \"GEO\"\n" +
                "  }, {\n" +
                "    \"ID\" : 74,\n" +
                "    \"NOME\" : \"GRANCAR\"\n" +
                "  }, {\n" +
                "    \"ID\" : 75,\n" +
                "    \"NOME\" : \"GREAT WALL\"\n" +
                "  }, {\n" +
                "    \"ID\" : 76,\n" +
                "    \"NOME\" : \"HAFEI\"\n" +
                "  }, {\n" +
                "    \"ID\" : 78,\n" +
                "    \"NOME\" : \"HOFSTETTER\"\n" +
                "  }, {\n" +
                "    \"ID\" : 79,\n" +
                "    \"NOME\" : \"HUDSON\"\n" +
                "  }, {\n" +
                "    \"ID\" : 80,\n" +
                "    \"NOME\" : \"HUMMER\"\n" +
                "  }, {\n" +
                "    \"ID\" : 82,\n" +
                "    \"NOME\" : \"INFINITI\"\n" +
                "  }, {\n" +
                "    \"ID\" : 83,\n" +
                "    \"NOME\" : \"INTERNATIONAL\"\n" +
                "  }, {\n" +
                "    \"ID\" : 86,\n" +
                "    \"NOME\" : \"JAGUAR\"\n" +
                "  }, {\n" +
                "    \"ID\" : 87,\n" +
                "    \"NOME\" : \"JEEP\"\n" +
                "  }, {\n" +
                "    \"ID\" : 88,\n" +
                "    \"NOME\" : \"JINBEI\"\n" +
                "  }, {\n" +
                "    \"ID\" : 89,\n" +
                "    \"NOME\" : \"JPX\"\n" +
                "  }, {\n" +
                "    \"ID\" : 90,\n" +
                "    \"NOME\" : \"KAISER\"\n" +
                "  }, {\n" +
                "    \"ID\" : 91,\n" +
                "    \"NOME\" : \"KOENIGSEGG\"\n" +
                "  }, {\n" +
                "    \"ID\" : 92,\n" +
                "    \"NOME\" : \"LAUTOMOBILE\"\n" +
                "  }, {\n" +
                "    \"ID\" : 93,\n" +
                "    \"NOME\" : \"LAUTOCRAFT\"\n" +
                "  }, {\n" +
                "    \"ID\" : 94,\n" +
                "    \"NOME\" : \"LADA\"\n" +
                "  }, {\n" +
                "    \"ID\" : 95,\n" +
                "    \"NOME\" : \"LANCIA\"\n" +
                "  }, {\n" +
                "    \"ID\" : 96,\n" +
                "    \"NOME\" : \"LAND ROVER\"\n" +
                "  }, {\n" +
                "    \"ID\" : 97,\n" +
                "    \"NOME\" : \"LEXUS\"\n" +
                "  }, {\n" +
                "    \"ID\" : 98,\n" +
                "    \"NOME\" : \"LIFAN\"\n" +
                "  }, {\n" +
                "    \"ID\" : 99,\n" +
                "    \"NOME\" : \"LINCOLN\"\n" +
                "  }, {\n" +
                "    \"ID\" : 100,\n" +
                "    \"NOME\" : \"LOBINI\"\n" +
                "  }, {\n" +
                "    \"ID\" : 101,\n" +
                "    \"NOME\" : \"LOTUS\"\n" +
                "  }, {\n" +
                "    \"ID\" : 102,\n" +
                "    \"NOME\" : \"MAHINDRA\"\n" +
                "  }, {\n" +
                "    \"ID\" : 104,\n" +
                "    \"NOME\" : \"MASERATI\"\n" +
                "  }, {\n" +
                "    \"ID\" : 106,\n" +
                "    \"NOME\" : \"MATRA\"\n" +
                "  }, {\n" +
                "    \"ID\" : 107,\n" +
                "    \"NOME\" : \"MAYBACH\"\n" +
                "  }, {\n" +
                "    \"ID\" : 108,\n" +
                "    \"NOME\" : \"MAZDA\"\n" +
                "  }, {\n" +
                "    \"ID\" : 109,\n" +
                "    \"NOME\" : \"MENON\"\n" +
                "  }, {\n" +
                "    \"ID\" : 110,\n" +
                "    \"NOME\" : \"MERCURY\"\n" +
                "  }, {\n" +
                "    \"ID\" : 111,\n" +
                "    \"NOME\" : \"MITSUBISHI\"\n" +
                "  }, {\n" +
                "    \"ID\" : 112,\n" +
                "    \"NOME\" : \"MG\"\n" +
                "  }, {\n" +
                "    \"ID\" : 113,\n" +
                "    \"NOME\" : \"MINI\"\n" +
                "  }, {\n" +
                "    \"ID\" : 114,\n" +
                "    \"NOME\" : \"MIURA\"\n" +
                "  }, {\n" +
                "    \"ID\" : 115,\n" +
                "    \"NOME\" : \"MORRIS\"\n" +
                "  }, {\n" +
                "    \"ID\" : 116,\n" +
                "    \"NOME\" : \"MP LAFER\"\n" +
                "  }, {\n" +
                "    \"ID\" : 117,\n" +
                "    \"NOME\" : \"MPLM\"\n" +
                "  }, {\n" +
                "    \"ID\" : 118,\n" +
                "    \"NOME\" : \"NEWTRACK\"\n" +
                "  }, {\n" +
                "    \"ID\" : 119,\n" +
                "    \"NOME\" : \"NISSIN\"\n" +
                "  }, {\n" +
                "    \"ID\" : 120,\n" +
                "    \"NOME\" : \"OLDSMOBILE\"\n" +
                "  }, {\n" +
                "    \"ID\" : 121,\n" +
                "    \"NOME\" : \"PAG\"\n" +
                "  }, {\n" +
                "    \"ID\" : 122,\n" +
                "    \"NOME\" : \"PAGANI\"\n" +
                "  }, {\n" +
                "    \"ID\" : 123,\n" +
                "    \"NOME\" : \"PLYMOUTH\"\n" +
                "  }, {\n" +
                "    \"ID\" : 124,\n" +
                "    \"NOME\" : \"PUMA\"\n" +
                "  }, {\n" +
                "    \"ID\" : 125,\n" +
                "    \"NOME\" : \"RENO\"\n" +
                "  }, {\n" +
                "    \"ID\" : 126,\n" +
                "    \"NOME\" : \"REVA-I\"\n" +
                "  }, {\n" +
                "    \"ID\" : 127,\n" +
                "    \"NOME\" : \"ROLLS-ROYCE\"\n" +
                "  }, {\n" +
                "    \"ID\" : 129,\n" +
                "    \"NOME\" : \"ROMI\"\n" +
                "  }, {\n" +
                "    \"ID\" : 130,\n" +
                "    \"NOME\" : \"SEAT\"\n" +
                "  }, {\n" +
                "    \"ID\" : 131,\n" +
                "    \"NOME\" : \"UTILITARIOS AGRICOLAS\"\n" +
                "  }, {\n" +
                "    \"ID\" : 132,\n" +
                "    \"NOME\" : \"SHINERAY\"\n" +
                "  }, {\n" +
                "    \"ID\" : 137,\n" +
                "    \"NOME\" : \"SAAB\"\n" +
                "  }, {\n" +
                "    \"ID\" : 139,\n" +
                "    \"NOME\" : \"SHORT\"\n" +
                "  }, {\n" +
                "    \"ID\" : 141,\n" +
                "    \"NOME\" : \"SIMCA\"\n" +
                "  }, {\n" +
                "    \"ID\" : 142,\n" +
                "    \"NOME\" : \"SMART\"\n" +
                "  }, {\n" +
                "    \"ID\" : 143,\n" +
                "    \"NOME\" : \"SPYKER\"\n" +
                "  }, {\n" +
                "    \"ID\" : 144,\n" +
                "    \"NOME\" : \"STANDARD\"\n" +
                "  }, {\n" +
                "    \"ID\" : 145,\n" +
                "    \"NOME\" : \"STUDEBAKER\"\n" +
                "  }, {\n" +
                "    \"ID\" : 146,\n" +
                "    \"NOME\" : \"TAC\"\n" +
                "  }, {\n" +
                "    \"ID\" : 147,\n" +
                "    \"NOME\" : \"TANGER\"\n" +
                "  }, {\n" +
                "    \"ID\" : 148,\n" +
                "    \"NOME\" : \"TRIUMPH\"\n" +
                "  }, {\n" +
                "    \"ID\" : 149,\n" +
                "    \"NOME\" : \"TROLLER\"\n" +
                "  }, {\n" +
                "    \"ID\" : 150,\n" +
                "    \"NOME\" : \"UNIMOG\"\n" +
                "  }, {\n" +
                "    \"ID\" : 154,\n" +
                "    \"NOME\" : \"WIESMANN\"\n" +
                "  }, {\n" +
                "    \"ID\" : 155,\n" +
                "    \"NOME\" : \"CADILLAC\"\n" +
                "  }, {\n" +
                "    \"ID\" : 156,\n" +
                "    \"NOME\" : \"AM GEN\"\n" +
                "  }, {\n" +
                "    \"ID\" : 157,\n" +
                "    \"NOME\" : \"BUGGY\"\n" +
                "  }, {\n" +
                "    \"ID\" : 158,\n" +
                "    \"NOME\" : \"WILLYS OVERLAND\"\n" +
                "  }, {\n" +
                "    \"ID\" : 161,\n" +
                "    \"NOME\" : \"KASEA\"\n" +
                "  }, {\n" +
                "    \"ID\" : 162,\n" +
                "    \"NOME\" : \"SATURN\"\n" +
                "  }, {\n" +
                "    \"ID\" : 163,\n" +
                "    \"NOME\" : \"SWELL MINI\"\n" +
                "  }, {\n" +
                "    \"ID\" : 175,\n" +
                "    \"NOME\" : \"SKODA\"\n" +
                "  }, {\n" +
                "    \"ID\" : 239,\n" +
                "    \"NOME\" : \"KARMANN GHIA\"\n" +
                "  }, {\n" +
                "    \"ID\" : 254,\n" +
                "    \"NOME\" : \"KART\"\n" +
                "  }, {\n" +
                "    \"ID\" : 258,\n" +
                "    \"NOME\" : \"HANOMAG\"\n" +
                "  }, {\n" +
                "    \"ID\" : 261,\n" +
                "    \"NOME\" : \"OUTROS\"\n" +
                "  }, {\n" +
                "    \"ID\" : 265,\n" +
                "    \"NOME\" : \"HILLMAN\"\n" +
                "  }, {\n" +
                "    \"ID\" : 288,\n" +
                "    \"NOME\" : \"HRG\"\n" +
                "  }, {\n" +
                "    \"ID\" : 295,\n" +
                "    \"NOME\" : \"GAIOLA\"\n" +
                "  }, {\n" +
                "    \"ID\" : 338,\n" +
                "    \"NOME\" : \"TATA\"\n" +
                "  }, {\n" +
                "    \"ID\" : 341,\n" +
                "    \"NOME\" : \"DITALLY\"\n" +
                "  }, {\n" +
                "    \"ID\" : 345,\n" +
                "    \"NOME\" : \"RELY\"\n" +
                "  }, {\n" +
                "    \"ID\" : 346,\n" +
                "    \"NOME\" : \"MCLAREN\"\n" +
                "  }, {\n" +
                "    \"ID\" : 534,\n" +
                "    \"NOME\" : \"GEELY\"\n" +
                "  } ],\n" +
                "  \"motos\" : [ {\n" +
                "    \"ID\" : 7,\n" +
                "    \"NOME\" : \"HONDA\"\n" +
                "  }, {\n" +
                "    \"ID\" : 27,\n" +
                "    \"NOME\" : \"AGRALE\"\n" +
                "  }, {\n" +
                "    \"ID\" : 36,\n" +
                "    \"NOME\" : \"BMW\"\n" +
                "  }, {\n" +
                "    \"ID\" : 59,\n" +
                "    \"NOME\" : \"SUZUKI\"\n" +
                "  }, {\n" +
                "    \"ID\" : 98,\n" +
                "    \"NOME\" : \"LIFAN\"\n" +
                "  }, {\n" +
                "    \"ID\" : 102,\n" +
                "    \"NOME\" : \"MAHINDRA\"\n" +
                "  }, {\n" +
                "    \"ID\" : 132,\n" +
                "    \"NOME\" : \"SHINERAY\"\n" +
                "  }, {\n" +
                "    \"ID\" : 133,\n" +
                "    \"NOME\" : \"KASINSKI\"\n" +
                "  }, {\n" +
                "    \"ID\" : 135,\n" +
                "    \"NOME\" : \"YAMAHA\"\n" +
                "  }, {\n" +
                "    \"ID\" : 136,\n" +
                "    \"NOME\" : \"GARINNI\"\n" +
                "  }, {\n" +
                "    \"ID\" : 140,\n" +
                "    \"NOME\" : \"SUNDOWN\"\n" +
                "  }, {\n" +
                "    \"ID\" : 170,\n" +
                "    \"NOME\" : \"KAWASAKI\"\n" +
                "  }, {\n" +
                "    \"ID\" : 171,\n" +
                "    \"NOME\" : \"POLARIS\"\n" +
                "  }, {\n" +
                "    \"ID\" : 176,\n" +
                "    \"NOME\" : \"ADLY\"\n" +
                "  }, {\n" +
                "    \"ID\" : 177,\n" +
                "    \"NOME\" : \"AMAZONAS\"\n" +
                "  }, {\n" +
                "    \"ID\" : 178,\n" +
                "    \"NOME\" : \"APRILIA\"\n" +
                "  }, {\n" +
                "    \"ID\" : 179,\n" +
                "    \"NOME\" : \"ATALA\"\n" +
                "  }, {\n" +
                "    \"ID\" : 180,\n" +
                "    \"NOME\" : \"BAJAJ\"\n" +
                "  }, {\n" +
                "    \"ID\" : 181,\n" +
                "    \"NOME\" : \"BENELLI\"\n" +
                "  }, {\n" +
                "    \"ID\" : 182,\n" +
                "    \"NOME\" : \"BETA\"\n" +
                "  }, {\n" +
                "    \"ID\" : 183,\n" +
                "    \"NOME\" : \"BIMOTA\"\n" +
                "  }, {\n" +
                "    \"ID\" : 184,\n" +
                "    \"NOME\" : \"BRANDY\"\n" +
                "  }, {\n" +
                "    \"ID\" : 185,\n" +
                "    \"NOME\" : \"BRAVA\"\n" +
                "  }, {\n" +
                "    \"ID\" : 186,\n" +
                "    \"NOME\" : \"BRP\"\n" +
                "  }, {\n" +
                "    \"ID\" : 187,\n" +
                "    \"NOME\" : \"BUELL\"\n" +
                "  }, {\n" +
                "    \"ID\" : 188,\n" +
                "    \"NOME\" : \"BUENO\"\n" +
                "  }, {\n" +
                "    \"ID\" : 190,\n" +
                "    \"NOME\" : \"CAGIVA\"\n" +
                "  }, {\n" +
                "    \"ID\" : 191,\n" +
                "    \"NOME\" : \"MOBILETE\"\n" +
                "  }, {\n" +
                "    \"ID\" : 192,\n" +
                "    \"NOME\" : \"DAELIM\"\n" +
                "  }, {\n" +
                "    \"ID\" : 193,\n" +
                "    \"NOME\" : \"DAFRA\"\n" +
                "  }, {\n" +
                "    \"ID\" : 194,\n" +
                "    \"NOME\" : \"DAYANG\"\n" +
                "  }, {\n" +
                "    \"ID\" : 195,\n" +
                "    \"NOME\" : \"DAYUN\"\n" +
                "  }, {\n" +
                "    \"ID\" : 196,\n" +
                "    \"NOME\" : \"DERBI\"\n" +
                "  }, {\n" +
                "    \"ID\" : 197,\n" +
                "    \"NOME\" : \"DUCATI\"\n" +
                "  }, {\n" +
                "    \"ID\" : 198,\n" +
                "    \"NOME\" : \"EMME\"\n" +
                "  }, {\n" +
                "    \"ID\" : 200,\n" +
                "    \"NOME\" : \"FYM\"\n" +
                "  }, {\n" +
                "    \"ID\" : 201,\n" +
                "    \"NOME\" : \"GAS GAS\"\n" +
                "  }, {\n" +
                "    \"ID\" : 202,\n" +
                "    \"NOME\" : \"GREEN\"\n" +
                "  }, {\n" +
                "    \"ID\" : 203,\n" +
                "    \"NOME\" : \"HAOBAO\"\n" +
                "  }, {\n" +
                "    \"ID\" : 204,\n" +
                "    \"NOME\" : \"HARLEY-DAVIDSON\"\n" +
                "  }, {\n" +
                "    \"ID\" : 205,\n" +
                "    \"NOME\" : \"HARTFORD\"\n" +
                "  }, {\n" +
                "    \"ID\" : 206,\n" +
                "    \"NOME\" : \"HERO\"\n" +
                "  }, {\n" +
                "    \"ID\" : 207,\n" +
                "    \"NOME\" : \"HUSABERG\"\n" +
                "  }, {\n" +
                "    \"ID\" : 208,\n" +
                "    \"NOME\" : \"HUSQVARNA\"\n" +
                "  }, {\n" +
                "    \"ID\" : 209,\n" +
                "    \"NOME\" : \"IROS\"\n" +
                "  }, {\n" +
                "    \"ID\" : 210,\n" +
                "    \"NOME\" : \"JIAPENG VOLCANO\"\n" +
                "  }, {\n" +
                "    \"ID\" : 211,\n" +
                "    \"NOME\" : \"JOHNNYPAG\"\n" +
                "  }, {\n" +
                "    \"ID\" : 212,\n" +
                "    \"NOME\" : \"JONNY\"\n" +
                "  }, {\n" +
                "    \"ID\" : 213,\n" +
                "    \"NOME\" : \"KAHENA\"\n" +
                "  }, {\n" +
                "    \"ID\" : 214,\n" +
                "    \"NOME\" : \"KIMCO\"\n" +
                "  }, {\n" +
                "    \"ID\" : 215,\n" +
                "    \"NOME\" : \"LAQUILA\"\n" +
                "  }, {\n" +
                "    \"ID\" : 216,\n" +
                "    \"NOME\" : \"LANDUM\"\n" +
                "  }, {\n" +
                "    \"ID\" : 217,\n" +
                "    \"NOME\" : \"LAVRALE\"\n" +
                "  }, {\n" +
                "    \"ID\" : 218,\n" +
                "    \"NOME\" : \"LERIVO\"\n" +
                "  }, {\n" +
                "    \"ID\" : 219,\n" +
                "    \"NOME\" : \"LON-V\"\n" +
                "  }, {\n" +
                "    \"ID\" : 220,\n" +
                "    \"NOME\" : \"TRICICLO\"\n" +
                "  }, {\n" +
                "    \"ID\" : 221,\n" +
                "    \"NOME\" : \"MALAGUTI\"\n" +
                "  }, {\n" +
                "    \"ID\" : 222,\n" +
                "    \"NOME\" : \"MIZA\"\n" +
                "  }, {\n" +
                "    \"ID\" : 223,\n" +
                "    \"NOME\" : \"MOTO GUZZI\"\n" +
                "  }, {\n" +
                "    \"ID\" : 224,\n" +
                "    \"NOME\" : \"MRX\"\n" +
                "  }, {\n" +
                "    \"ID\" : 225,\n" +
                "    \"NOME\" : \"MV AUGUSTA\"\n" +
                "  }, {\n" +
                "    \"ID\" : 226,\n" +
                "    \"NOME\" : \"MVK\"\n" +
                "  }, {\n" +
                "    \"ID\" : 227,\n" +
                "    \"NOME\" : \"ORCA\"\n" +
                "  }, {\n" +
                "    \"ID\" : 228,\n" +
                "    \"NOME\" : \"PEGASSI\"\n" +
                "  }, {\n" +
                "    \"ID\" : 229,\n" +
                "    \"NOME\" : \"PIAGGIO\"\n" +
                "  }, {\n" +
                "    \"ID\" : 230,\n" +
                "    \"NOME\" : \"REGAL RAPTOR\"\n" +
                "  }, {\n" +
                "    \"ID\" : 231,\n" +
                "    \"NOME\" : \"SANYANG\"\n" +
                "  }, {\n" +
                "    \"ID\" : 232,\n" +
                "    \"NOME\" : \"SIAMOTO\"\n" +
                "  }, {\n" +
                "    \"ID\" : 233,\n" +
                "    \"NOME\" : \"TARGOS\"\n" +
                "  }, {\n" +
                "    \"ID\" : 234,\n" +
                "    \"NOME\" : \"TRAXX\"\n" +
                "  }, {\n" +
                "    \"ID\" : 235,\n" +
                "    \"NOME\" : \"VENTO\"\n" +
                "  }, {\n" +
                "    \"ID\" : 236,\n" +
                "    \"NOME\" : \"WUYANG\"\n" +
                "  }, {\n" +
                "    \"ID\" : 245,\n" +
                "    \"NOME\" : \"GARRA\"\n" +
                "  }, {\n" +
                "    \"ID\" : 246,\n" +
                "    \"NOME\" : \"X MOTOS\"\n" +
                "  }, {\n" +
                "    \"ID\" : 248,\n" +
                "    \"NOME\" : \"TRICKER\"\n" +
                "  }, {\n" +
                "    \"ID\" : 253,\n" +
                "    \"NOME\" : \"LAMBRETA\"\n" +
                "  }, {\n" +
                "    \"ID\" : 261,\n" +
                "    \"NOME\" : \"OUTROS\"\n" +
                "  }, {\n" +
                "    \"ID\" : 269,\n" +
                "    \"NOME\" : \"SCOOTER\"\n" +
                "  }, {\n" +
                "    \"ID\" : 281,\n" +
                "    \"NOME\" : \"ZONGSHEN\"\n" +
                "  }, {\n" +
                "    \"ID\" : 282,\n" +
                "    \"NOME\" : \"BIRELLI\"\n" +
                "  }, {\n" +
                "    \"ID\" : 294,\n" +
                "    \"NOME\" : \"WALK MACHINE\"\n" +
                "  }, {\n" +
                "    \"ID\" : 297,\n" +
                "    \"NOME\" : \"FBM\"\n" +
                "  }, {\n" +
                "    \"ID\" : 299,\n" +
                "    \"NOME\" : \"ARIEL\"\n" +
                "  }, {\n" +
                "    \"ID\" : 340,\n" +
                "    \"NOME\" : \"DUCAR\"\n" +
                "  }, {\n" +
                "    \"ID\" : 341,\n" +
                "    \"NOME\" : \"DITALLY\"\n" +
                "  }, {\n" +
                "    \"ID\" : 342,\n" +
                "    \"NOME\" : \"MARVA\"\n" +
                "  }, {\n" +
                "    \"ID\" : 343,\n" +
                "    \"NOME\" : \"WOLVER\"\n" +
                "  }, {\n" +
                "    \"ID\" : 344,\n" +
                "    \"NOME\" : \"KTM\"\n" +
                "  }, {\n" +
                "    \"ID\" : 347,\n" +
                "    \"NOME\" : \"LEOPARD\"\n" +
                "  }, {\n" +
                "    \"ID\" : 348,\n" +
                "    \"NOME\" : \"JAWA\"\n" +
                "  }, {\n" +
                "    \"ID\" : 349,\n" +
                "    \"NOME\" : \"BULL\"\n" +
                "  }, {\n" +
                "    \"ID\" : 358,\n" +
                "    \"NOME\" : \"CAN-AM\"\n" +
                "  }, {\n" +
                "    \"ID\" : 359,\n" +
                "    \"NOME\" : \"ACELLERA\"\n" +
                "  }, {\n" +
                "    \"ID\" : 483,\n" +
                "    \"NOME\" : \"VICTORY\"\n" +
                "  }, {\n" +
                "    \"ID\" : 484,\n" +
                "    \"NOME\" : \"INDIAN\"\n" +
                "  }, {\n" +
                "    \"ID\" : 532,\n" +
                "    \"NOME\" : \"BRAVAX\"\n" +
                "  }, {\n" +
                "    \"ID\" : 533,\n" +
                "    \"NOME\" : \"GARELLI\"\n" +
                "  } ]\n" +
                "}\n";
        return jsonMarca;
    }
}
