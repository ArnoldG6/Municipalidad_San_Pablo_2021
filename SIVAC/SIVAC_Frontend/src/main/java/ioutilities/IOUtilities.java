/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ioutilities;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 *
 * @author jegon
 */
public class IOUtilities {
    public static void copy(InputStream in, OutputStream out, int bufferSize) throws IOException {
        
        try (ReadableByteChannel source = Channels.newChannel(in);
                WritableByteChannel target = Channels.newChannel(out)) {
            ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
            while (source.read(buffer) != -1) {

                // Coloca la posición del buffer al inicio para
                //comenzar a obtener bytes..
                buffer.flip();

                // Copia los bytes del buffer al canal de salida..
                while (buffer.hasRemaining()) {
                    target.write(buffer);
                }
                buffer.clear();
            }
        }
    }
    
    public static void copy(InputStream in, OutputStream out) throws IOException {
        copy(in, out, DEFAULT_BUFFER_SIZE);
    }
    
    public static final int DEFAULT_BUFFER_SIZE = 16 * 1024; // 16 Kb
}
