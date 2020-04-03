package sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Conexao extends SQLiteOpenHelper {

    private static final String name = "banco.db";
    private static final int version = 1;

    public Conexao(Context context){
        super(context, name, null, version);
    }

    private String tabelaChecklist = "CREATE TABLE IF NOT EXISTS checklist(id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "idInspecao INTEGER, radio VARCHAR(3), estepe VARCHAR(3), macaco VARCHAR(3), chaveRodas VARCHAR(3), " +
            "triangulo VARCHAR(3), extintor VARCHAR(3), calotas VARCHAR(3), tapetesBorracha VARCHAR(3), tapetesCarpete VARCHAR(3)," +
            "rodaLiga VARCHAR(3), faroisAux VARCHAR(3), bateria VARCHAR(3), retrovisorEletrico VARCHAR(3), bagageiro VARCHAR(3)," +
            "rack VARCHAR(3), protetorCarter VARCHAR(3), documentos VARCHAR(3), chaves VARCHAR(3), moto VARCHAR(3))";

    private String tabelaCor = "CREATE TABLE IF NOT EXISTS cor(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "cor TEXT)";

    private String tabelaFiguras = "CREATE TABLE IF NOT EXISTS figurasInspecao(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "idInspecao INTEGER, caminhoFigura TEXT, caminhoAssinaturaRetira TEXT, caminhoAssinaturaEntrega TEXT)";

    private String tabelaFotos = "CREATE TABLE IF NOT EXISTS fotosInspecao(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "idInspecao INTEGER, caminhoFotoPainel TEXT, caminhoFotoFrente TEXT, caminhoFotoLado TEXT)";

    private String tabelaInspecao = "CREATE TABLE IF NOT EXISTS inspecao(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "idMarca INTEGER, idModelo INTEGER, idCor INTEGER, idUsuario INTEGER, nomeProprietario TEXT, telefone TEXT , " +
            "ano INTEGER, placa TEXT, inspecao INTEGER, caminhoAssinaturaRecusa TEXT)";

    private String tabelaLocalizacao = "CREATE TABLE IF NOT EXISTS localizacao(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "idTipoRegistro INTEGER, idInspecao INTEGER, data TEXT, latitude TEXT, longitude TEXT, endereco TEXT)";

    private String tabelaMarca = "CREATE TABLE IF NOT EXISTS marca(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "marca TEXT)";

    private String tabelaModelo = "CREATE TABLE IF NOT EXISTS modelo(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "idMarca INTEGER, modelo TEXT)";

    private String tabelaPdf = "CREATE TABLE IF NOT EXISTS pdf(id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "idInspecao INTEGER, caminhoDocumento TEXT)";

    private String tabelaTipoRegistro = "CREATE TABLE IF NOT EXISTS tipoRegistro(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "tipoRegistro TEXT)";

    private String tabelaUsuario = "CREATE TABLE IF NOT EXISTS usuario(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "email TEXT, senha TEXT, nomeEmpresa TEXT, cnpjEmpresa TEXT, nomeMotorista TEXT, rgMotorista TEXT, " +
            "telefoneMotorista TEXT, caminhoLogo TEXT, caminhoAssinatura TEXT)";

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(tabelaUsuario);
        db.execSQL(tabelaCor);
        db.execSQL(tabelaMarca);
        db.execSQL(tabelaModelo);
        db.execSQL(tabelaFiguras);
        db.execSQL(tabelaFotos);
        db.execSQL(tabelaLocalizacao);
        db.execSQL(tabelaInspecao);
        db.execSQL(tabelaTipoRegistro);
        db.execSQL(tabelaChecklist);
        db.execSQL(tabelaPdf);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(tabelaUsuario);
        db.execSQL(tabelaCor);
        db.execSQL(tabelaMarca);
        db.execSQL(tabelaModelo);
        db.execSQL(tabelaFiguras);
        db.execSQL(tabelaFotos);
        db.execSQL(tabelaLocalizacao);
        db.execSQL(tabelaInspecao);
        db.execSQL(tabelaTipoRegistro);
        db.execSQL(tabelaChecklist);
        db.execSQL(tabelaPdf);
    }
}
