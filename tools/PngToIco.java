import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

/**
 * Convierte un PNG en un .ico multitamaño (16, 24, 32, 48, 64, 128, 256 px).
 * Formato: cabecera ICONDIR + entradas ICONDIRENTRY + bloques PNG embebidos
 * (estándar soportado por Windows Vista y posteriores).
 *
 * Uso: java PngToIco.java <entrada.png> <salida.ico>
 */
public class PngToIco {

    private static final int[] TAMANOS = {16, 24, 32, 48, 64, 128, 256};

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.err.println("Uso: java PngToIco.java <entrada.png> <salida.ico>");
            System.exit(1);
        }

        File entrada = new File(args[0]);
        File salida = new File(args[1]);

        if (!entrada.exists()) {
            System.err.println("No existe el PNG: " + entrada.getAbsolutePath());
            System.exit(1);
        }

        BufferedImage origen = ImageIO.read(entrada);
        List<byte[]> bloquesPng = new ArrayList<>();

        for (int tam : TAMANOS) {
            BufferedImage redimensionada = new BufferedImage(tam, tam, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = redimensionada.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.drawImage(origen.getScaledInstance(tam, tam, Image.SCALE_SMOOTH), 0, 0, null);
            g.dispose();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(redimensionada, "png", baos);
            bloquesPng.add(baos.toByteArray());
        }

        int numImagenes = bloquesPng.size();
        int tamCabecera = 6 + 16 * numImagenes;
        int totalBytes = tamCabecera + bloquesPng.stream().mapToInt(b -> b.length).sum();

        ByteBuffer buf = ByteBuffer.allocate(totalBytes);
        buf.order(ByteOrder.LITTLE_ENDIAN);

        buf.putShort((short) 0);          // reserved
        buf.putShort((short) 1);          // tipo: icono
        buf.putShort((short) numImagenes);

        int offset = tamCabecera;
        for (int i = 0; i < numImagenes; i++) {
            byte[] png = bloquesPng.get(i);
            int tam = TAMANOS[i];
            byte dim = (byte) (tam >= 256 ? 0 : tam);
            buf.put(dim);                 // ancho
            buf.put(dim);                 // alto
            buf.put((byte) 0);            // paleta de colores
            buf.put((byte) 0);            // reservado
            buf.putShort((short) 1);      // planos
            buf.putShort((short) 32);     // bits por pixel
            buf.putInt(png.length);       // tamaño del bloque
            buf.putInt(offset);           // offset relativo
            offset += png.length;
        }

        for (byte[] png : bloquesPng) {
            buf.put(png);
        }

        try (FileOutputStream fos = new FileOutputStream(salida)) {
            fos.write(buf.array());
        }

        System.out.println("ICO generado: " + salida.getAbsolutePath()
                + " (" + numImagenes + " tamanos)");
    }
}