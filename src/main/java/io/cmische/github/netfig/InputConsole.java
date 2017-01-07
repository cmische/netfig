package io.cmische.github.netfig;


import javafx.scene.control.TextArea;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InputConsole extends InputStream {

    private static final Logger logger = Logger.getLogger( InputConsole.class.getName() );
    private final TextArea console;
    BlockingQueue<String> q;

    InputConsole(TextArea console) {
        this.console = console;
        q = new LinkedBlockingQueue<>();
    }

    private String stringInput;
    private int pos;

    @Override
    public int read() throws IOException {
        /*while (stringInput == null || stringInput.length() <= pos) {
            try {
                System.out.println("" + (stringInput == null) + (stringInput.length() <= pos));
                stringInput = q.take();
                pos = 0;
            } catch (InterruptedException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
        int ret = (int) stringInput.charAt(pos);
        pos++;
        return ret;
        */
        return 0;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        if (b == null) {
            throw new NullPointerException();
        } else if (off < 0 || len < 0 || len > b.length - off) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return 0;
        }

        int bytes_copied = 0;
        while (bytes_copied < 1) {
            while (stringInput == null || stringInput.length() <= pos) {
                System.out.println("" + stringInput);
                try {
                    stringInput = q.take();
                    System.out.println("s = " + stringInput);
                    pos = 0;
                } catch (InterruptedException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "NULL!", ex);
                }
            }
            int bytes_to_copy = len < stringInput.length() - pos ? len : stringInput.length() - pos;
            System.arraycopy(stringInput.getBytes(), pos, b, off, bytes_to_copy);
            pos += bytes_to_copy;
            bytes_copied += bytes_to_copy;
        }
        return bytes_copied;
    }

    /* public int read(byte b[], int off, int len) throws IOException {

        int c = read();
        if (c == -1) {
            return -1;
        }
        b[off] = (byte)c;

        int i = 1;
        try {
            for (; i < len ; i++) {
                c = read();
                if (c == -1) {
                    break;
                }
                b[off + i] = (byte)c;
            }
        } catch (IOException ee) {
        }
        return i;
    }*/

    @Override
    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

}
