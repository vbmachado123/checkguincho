package util;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.balbino.checkguincho.R;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import dao.ChecklistDao;
import dao.CorDao;
import dao.FigurasDao;
import dao.FotosDao;
import dao.InspecaoDao;
import dao.LocalizacaoDao;
import dao.MarcaDao;
import dao.ModeloDao;
import dao.UsuarioDao;
import model.Checklist;
import model.Cor;
import model.FigurasInspecao;
import model.FotosInspecao;
import model.Inspecao;
import model.Localizacao;
import model.Marca;
import model.Modelo;
import model.Usuario;

import static android.app.PendingIntent.getActivity;

public class GeraPDF {
    private File file;
    private Document documento;

    private Font fontBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    private Font fontNormal = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);

    public Document GerarPDF(String destino, String observacao, Activity activity)throws Exception {
        /* RECUPERAR OS OBJETOS PARA EXIBIR */
        Cor cor = new CorDao(activity).recupera();
        Checklist checklist = new ChecklistDao(activity).recupera();
        FigurasInspecao figuras = new FigurasDao(activity).recupera();
        FotosInspecao fotos = new FotosDao(activity).recupera();
        Localizacao localizacao = new LocalizacaoDao(activity).recupera();
        Inspecao inspecao = new InspecaoDao(activity).recupera();
        Marca marca = new MarcaDao(activity).recupera();
        Modelo modelo = new ModeloDao(activity).recupera();
        Usuario usuario = new UsuarioDao(activity).recupera();

        Localizacao localizacaoOrigem = new LocalizacaoDao(activity).getById(localizacao.getId() - 1);

        SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat horaFormatada = new SimpleDateFormat("HH:mm");

        Rectangle pagesize = new Rectangle(PageSize.A4);
        documento = new Document(pagesize);
        PdfWriter.getInstance(documento, new FileOutputStream(destino));
        documento.open();

        Drawable dGrade = ContextCompat.getDrawable(activity, R.drawable.grade);
        BitmapDrawable drawable = ((BitmapDrawable) dGrade);
        Bitmap bmpGrade = drawable.getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream(); //Reutilizar
        bmpGrade.compress(Bitmap.CompressFormat.PNG, 50, baos);

        Paragraph cabecalho = new Paragraph();
        cabecalho.add(new Phrase(usuario.getNomeEmpresa() + "\n" +
                usuario.getNomeMorotista() + "\n" + "\n" +
                usuario.getTelefoneMotorista()));
        cabecalho.setAlignment(Element.ALIGN_RIGHT);
        documento.add(cabecalho);

        Image imagemGrade = Image.getInstance(baos.toByteArray());
        imagemGrade.scaleAbsolute(pagesize);
        imagemGrade.setAbsolutePosition(22,02);
        documento.add(imagemGrade);

        Drawable dLogo = ContextCompat.getDrawable(activity, R.drawable.logo);
        BitmapDrawable drawableLogo = ((BitmapDrawable) dLogo);
        Bitmap bmpGradeLogo = drawableLogo.getBitmap();
        ByteArrayOutputStream baosLogo = new ByteArrayOutputStream();
        bmpGradeLogo.compress(Bitmap.CompressFormat.PNG, 20, baosLogo);

        Image imagemLogo = Image.getInstance(baosLogo.toByteArray());
        imagemLogo.scaleAbsolute(PageSize.A10);
        imagemLogo.setAbsolutePosition(50, 723); //400y MEIO DA FOLHA
        documento.add(imagemLogo);

        Paragraph branco = new Paragraph(" ");
        documento.add(branco);

        /* INFORMATIVOS */
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String dataAtendimento = dataFormatada.format(date);
        String horaAtendimento = horaFormatada.format(date);
        Paragraph linha1 = new Paragraph();
        linha1.add(new Phrase("Data: ", fontBold));
        linha1.add(new Phrase(dataAtendimento + "                 ", fontNormal));
        linha1.add(new Phrase("Hora: ", fontBold));
        linha1.add(new Phrase(horaAtendimento + "     ", fontNormal));
        documento.add(linha1);

        documento.add(branco);

        /* INFORMAÇÕES PROPRIETÁRIO VEICULO */
        Paragraph linha2 = new Paragraph();
        linha2.add(new Phrase("Proprietário: ", fontBold));
        linha2.add(new Phrase(inspecao.getNomeProprietario() + "       ", fontNormal));
        linha2.add(new Phrase("Telefone: ", fontBold));
        linha2.add(new Phrase(inspecao.getTelefone(), fontNormal));
        documento.add(linha2);

        /* INFORMAÇÕES VEICULO */
        Paragraph linha3 = new Paragraph();
        linha3.add(new Phrase("Veículo: ", fontBold));
        linha3.add(new Phrase(marca.getMarca() + " - " + modelo.getModelo() + "       ", fontNormal));
        linha3.add(new Phrase("Ano: ", fontBold));
        linha3.add(new Phrase(String.valueOf(inspecao.getAno()) + "   ", fontNormal));
        linha3.add(new Phrase("Cor: ", fontBold));
        linha3.add(new Phrase(String.valueOf(cor.getCor()) + "    ", fontNormal));
        linha3.add(new Phrase("Placas: ", fontBold));
        linha3.add(new Phrase(String.valueOf(inspecao.getPlaca()), fontNormal));
        documento.add(linha3);

        Paragraph linha4 = new Paragraph();
        if( inspecao.getInspecao() != 0 ){ //Recusa inspeção
            linha4.add(new Phrase("Origem: ", fontBold));
            linha4.add(new Phrase("\nDestino: ", fontBold));
            documento.add(linha4);

            Drawable dFigura = ContextCompat.getDrawable(activity, R.drawable.figura);
            BitmapDrawable bdFigura = ((BitmapDrawable) dFigura);
            Bitmap bmpFigura = bdFigura.getBitmap();
            ByteArrayOutputStream baosFigura = new ByteArrayOutputStream();
            bmpFigura.compress(Bitmap.CompressFormat.JPEG, 20, baosFigura);

            Image imagemFigura = Image.getInstance(baosFigura.toByteArray());
            imagemFigura.scaleAbsolute(PageSize.A7);
            imagemFigura.setAbsolutePosition(50, 300); //400y MEIO DA FOLHA
            documento.add(imagemFigura);

            Image imagemAssinaturaRecusa = Image.getInstance(inspecao.getCaminhoAssinaturaRecusa());
            imagemAssinaturaRecusa.scalePercent(12, 02);
            imagemAssinaturaRecusa.setAbsolutePosition(370, 245); //400y MEIO DA FOLHA
            documento.add(imagemAssinaturaRecusa);

            int pula = 0;
            while(pula <= 22){
                documento.add(branco);
                pula++;
            }

            Paragraph linhaObservacao = new Paragraph();
            linhaObservacao.add(new Phrase(observacao));
            documento.add(linhaObservacao);

        } else {
            /* INSPEÇÃO COMPLETA
            INFORMAÇÕES LOCALIZAÇÃO */
            linha4.add(new Phrase("Origem: ", fontBold));
            linha4.add(new Phrase(localizacaoOrigem.getEndereco() + " - " + localizacaoOrigem.getData() + "\n ", fontNormal));
            linha4.add(new Phrase("Destino: ", fontBold));
            linha4.add(new Phrase(localizacao.getEndereco() + " - " + localizacao.getData(), fontNormal));
            documento.add(linha4);

            Image imagemFigura = Image.getInstance(figuras.getCaminhoFigura());
            imagemFigura.scaleAbsolute(PageSize.A7);
            imagemFigura.setAbsolutePosition(50, 300); //400y MEIO DA FOLHA
            documento.add(imagemFigura);

            documento.add(branco);
            documento.add(branco);
            /*documento.add(branco);*/

            String espacoCheck = "                                 ";

            Paragraph check = new Paragraph();
            check.setAlignment(Element.ALIGN_RIGHT);
            check.add(new Phrase(checklist.getRadio() + espacoCheck + "\n", fontNormal));
            check.add(new Phrase(checklist.getEstepe() + espacoCheck + "\n", fontNormal));
            check.add(new Phrase(checklist.getMacaco() + espacoCheck + "\n", fontNormal));
            check.add(new Phrase(checklist.getChaveRodas() + espacoCheck + "\n", fontNormal));
            check.add(new Phrase(checklist.getTriangulo() + espacoCheck + "\n", fontNormal));
            check.add(new Phrase(checklist.getExtintor() + espacoCheck + "\n", fontNormal));
            check.add(new Phrase(checklist.getCalotas() + espacoCheck + "\n", fontNormal));
            check.add(new Phrase(checklist.getTapetesBorracha() + espacoCheck + "\n", fontNormal));
            check.add(new Phrase(checklist.getTapetesCarpete() + espacoCheck + "\n", fontNormal));
            check.add(new Phrase(checklist.getRodaLiga() + espacoCheck + "\n", fontNormal));
            check.add(new Phrase(checklist.getFaroisAux() + espacoCheck + "\n", fontNormal));
            check.add(new Phrase(checklist.getBateria() + espacoCheck + "\n", fontNormal));
            check.add(new Phrase(checklist.getRetrovisorEletrico() + espacoCheck + "\n", fontNormal));
            check.add(new Phrase(checklist.getBagageiro() + espacoCheck + "\n", fontNormal));
            check.add(new Phrase(checklist.getRack() + espacoCheck + "\n", fontNormal));
            check.add(new Phrase(checklist.getProtetorCarter() + espacoCheck + "\n", fontNormal));
            check.add(new Phrase(checklist.getDocumentos() + espacoCheck + "\n", fontNormal));
            check.add(new Phrase(checklist.getChaves() + espacoCheck + "\n", fontNormal));
            check.add(new Phrase(checklist.getMoto() + espacoCheck + "\n", fontNormal));
            documento.add(check);

            int pula =0;
            while(pula != 3) {
                documento.add(branco);
                pula++;
            }

            if(observacao != null) {
                Paragraph linhaObservacao = new Paragraph();
                linhaObservacao.add(new Phrase(observacao));
                documento.add(linhaObservacao);
            }

            pula =0;
            while(pula != 2) {
                documento.add(branco);
                pula++;
            }

            String[] assinaturaRetira = figuras.getCaminhoAssinaturaRetira().split("_");
            String[] assinaturaEntrega = figuras.getCaminhoAssinaturaEntrega().split("_");

            /* ASSINATURAS */
            Paragraph assinaRetira = new Paragraph();
            assinaRetira.setAlignment(Element.ALIGN_CENTER);
            assinaRetira.add(new Phrase("Nome: " , fontBold));
            assinaRetira.add(new Phrase(assinaturaRetira[1] + "\n", fontNormal));
            assinaRetira.add(new Phrase("Rg: " , fontBold));
            assinaRetira.add(new Phrase(assinaturaRetira[2] + "\n", fontNormal));
            documento.add(assinaRetira);

            Image imagemAssinaturaRetira = Image.getInstance(figuras.getCaminhoAssinaturaRetira());
            imagemAssinaturaRetira.scalePercent(12, 02);
            imagemAssinaturaRetira.setAbsolutePosition(490, 160); //400y MEIO DA FOLHA
            documento.add(imagemAssinaturaRetira);

            documento.add(branco);

            /* ASSINATURA RETIRADA */
            Paragraph assinaEntrega = new Paragraph();
            assinaEntrega.setAlignment(Element.ALIGN_CENTER);
            assinaEntrega.add(new Phrase("Nome: " , fontBold));
            assinaEntrega.add(new Phrase(assinaturaEntrega[1] + "\n", fontNormal));
            assinaEntrega.add(new Phrase("Rg: " , fontBold));
            assinaEntrega.add(new Phrase(assinaturaEntrega[2] + "\n", fontNormal));
            documento.add(assinaEntrega);

            Image imagemAssinaturaEntrega = Image.getInstance(figuras.getCaminhoAssinaturaEntrega());
            imagemAssinaturaEntrega.scalePercent(12, 02);
            imagemAssinaturaEntrega.setAbsolutePosition(490, 120); //400y MEIO DA FOLHA
            documento.add(imagemAssinaturaEntrega);

            documento.add(branco);

            Paragraph assinaPrestador = new Paragraph();
            assinaPrestador.setAlignment(Element.ALIGN_CENTER);
            assinaPrestador.add(new Phrase("Nome: " , fontBold));
            assinaPrestador.add(new Phrase(usuario.getNomeMorotista() + "\n", fontNormal));
            assinaPrestador.add(new Phrase("Rg: " , fontBold));
            assinaPrestador.add(new Phrase(usuario.getRgMotorista() + "\n", fontNormal));
            documento.add(assinaPrestador);

            Image imagemAssinaturaPrestador = Image.getInstance(usuario.getCaminhoAssinatura());
            imagemAssinaturaPrestador.scalePercent(12, 02);
            imagemAssinaturaPrestador.setAbsolutePosition(490, 80); //400y MEIO DA FOLHA
            documento.add(imagemAssinaturaPrestador);

            /* PÁGINA COM AS IMAGENS */
            documento.newPage();

            Drawable dGrade2 = ContextCompat.getDrawable(activity, R.drawable.grade2);
            BitmapDrawable drawable2 = ((BitmapDrawable) dGrade2);
            Bitmap bmpGrade2 = drawable2.getBitmap();

            ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
            bmpGrade2.compress(Bitmap.CompressFormat.PNG, 50, baos2);

            Image imagemGrade2 = Image.getInstance(baos2.toByteArray());
            imagemGrade2.scaleAbsolute(pagesize);
            imagemGrade2.setAbsolutePosition(22,02);
            documento.add(imagemGrade2);

            Rectangle tamanhoFotos = new Rectangle(PageSize.A8);

            int x = 8, y = 6;
            int aX = 150, aY = 600;

            Image imagemPainel = Image.getInstance(fotos.getCaminhoFotoPainel());
            imagemPainel.scalePercent (x, y);
            imagemPainel.setAbsolutePosition(aX,aY);
            documento.add(imagemPainel);

            Image imagemLateral = Image.getInstance(fotos.getCaminhoFotoLado());
            imagemLateral.scalePercent (x, y);
            imagemLateral.setAbsolutePosition(aX,aY - 200);
            documento.add(imagemLateral);

            Image imagemFrente = Image.getInstance(fotos.getCaminhoFotoFrente());
            imagemFrente.scalePercent (x, y);
            imagemFrente.setAbsolutePosition(aX,aY - 400);
            documento.add(imagemFrente);
        }

        documento.close();

        return documento;
    }
}
